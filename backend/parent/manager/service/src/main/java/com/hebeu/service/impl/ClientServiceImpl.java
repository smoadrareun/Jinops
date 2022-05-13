package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.*;
import com.hebeu.mapper.AddrInfoMapper;
import com.hebeu.mapper.ClientMapper;
import com.hebeu.pojo.AddrInfo;
import com.hebeu.pojo.Client;
import com.hebeu.pojo.vo.ClientVo;
import com.hebeu.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层实现类
 */

@Slf4j
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private AddrInfoMapper addrInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Client,ClientVo> PageInfo<ClientVo> pageInfoPoToVo(PageInfo<Client> pageInfo){
        Page<ClientVo> page=new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static ClientVo clientPoToVo(Client client, List<AddrInfo> addrInfoList) {
        if (client == null) {
            return null;
        }
        ClientVo clientVo = new ClientVo();
        BeanUtils.copyProperties(client, clientVo);
        clientVo.setId(client.getCliId());
        clientVo.setRegTime(DateUtil.TimeMillToDate(client.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
        clientVo.setLogTime(DateUtil.TimeMillToDate(client.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
        clientVo.setAddrInfo(AddrListToVo(addrInfoList));
        return clientVo;
    }

    public static Client clientVoToPo(ClientVo clientVo) {
        if (clientVo == null) {
            return null;
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientVo, client);
        client.setId(null);
        client.setCliId(clientVo.getId());
        client.setRegTime(DateUtil.DateToTimeMill(clientVo.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
        client.setLogTime(DateUtil.DateToTimeMill(clientVo.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
        return client;
    }

    public static AddrInfo AddrVoToPo(ClientVo.AddrInfoVo addrInfoVo) {
        if (addrInfoVo == null) {
            return null;
        }
        AddrInfo addrInfo = new AddrInfo();
        BeanUtils.copyProperties(addrInfoVo, addrInfo);
        addrInfo.setId(null);
        return addrInfo;
   }

    public static List<ClientVo.AddrInfoVo> AddrListToVo(List<AddrInfo> addrInfoList) {
        if (addrInfoList == null) {
            return null;
        }
        List<ClientVo.AddrInfoVo> addrInfoVoList = new ArrayList<>();
        for (AddrInfo addrInfo : addrInfoList) {
            ClientVo.AddrInfoVo addrInfoVo = new ClientVo.AddrInfoVo();
            BeanUtils.copyProperties(addrInfo, addrInfoVo);
            addrInfoVo.setId(addrInfo.getAddrId());
            addrInfoVoList.add(addrInfoVo);
        }
        return addrInfoVoList;
    }

    //客户账号密码登录
    @Override
    public ClientVo login(String uname, String passwd) {
        log.info("客户账号密码登录开始，请求参数：{},{}", uname, passwd);
        ClientVo clientVo = new ClientVo();
        try {
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Client::getUname, uname);
            wrapper.eq(Client::getPasswd, CodeUtil.getMD5Str(passwd, uname + "client"));
            Client client = new Client();
            client.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
            client.setLogTime(DateUtil.getCurrentTimeMill());
            clientMapper.update(client, wrapper);
            List<Client> list = clientMapper.selectList(wrapper);
            if (list.size() > 0) {
                LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                addrInfoWrapper.eq(AddrInfo::getCliId, list.get(0).getCliId());
                List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                clientVo = clientPoToVo(list.get(0), addrInfoList);
                clientVo.setToken(TokenUtil.getToken(clientVo.getUname(), clientVo.getPasswd(), "client"));
                clientVo.setPasswd(null);
                log.info("客户账号密码登录成功：{}", clientVo);
            }
        } catch (Exception e) {
            log.error("客户账号密码登录失败：", e);
            return null;
        }
        return clientVo;
    }

    //客户Token登录
    @Override
    public ClientVo login(String token) {
        log.info("客户Token登录开始，请求参数：{}", token);
        ClientVo clientVo = new ClientVo();
        try {
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            List<String> list = TokenUtil.verify(token);
            if (list.get(2).equals("client")) {
                wrapper.eq(Client::getUname, list.get(0));
                wrapper.eq(Client::getPasswd, list.get(1));
                Client client = new Client();
                client.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
                client.setLogTime(DateUtil.getCurrentTimeMill());
                clientMapper.update(client, wrapper);
                List<Client> clientList = clientMapper.selectList(wrapper);
                if (clientList.size() > 0) {
                    LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                    addrInfoWrapper.eq(AddrInfo::getCliId, clientList.get(0).getCliId());
                    List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                    clientVo = clientPoToVo(clientList.get(0), addrInfoList);
                    clientVo.setToken(TokenUtil.getToken(clientVo.getUname(), clientVo.getPasswd(), "client"));
                    clientVo.setPasswd(null);
                    log.info("客户Token登录成功：{}", clientVo);
                }
            }
        } catch (Exception e) {
            log.error("客户Token登录失败：", e);
            return null;
        }
        return clientVo;
    }

    //根据id查询客户信息
    @Override
    public ClientVo getById(Integer id) {
        log.info("根据id查询客户信息开始，请求参数：{}", id);
        ClientVo clientVo = new ClientVo();
        try {
            if (redisUtil.hasKey("client" + id)) {
                String str = String.valueOf(redisUtil.get("client" + id));
                Type type = new TypeToken<ClientVo>() {
                }.getType();
                clientVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", clientVo);
            } else {
                LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Client::getCliId, id);
                List<Client> list = clientMapper.selectList(wrapper);
                if (list.size() > 0) {
                    LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                    addrInfoWrapper.eq(AddrInfo::getCliId, list.get(0).getCliId());
                    List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                    list.get(0).setPasswd(null);
                    clientVo = clientPoToVo(list.get(0), addrInfoList);
                    log.info("根据id查询客户信息成功：{}", clientVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询客户信息失败：", e);
            return null;
        }
        return clientVo;
    }

    //查询所有客户信息
    @Override
    public List<ClientVo> getList() {
        log.info("查询所有客户信息开始");
        List<ClientVo> clientVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("client*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<ClientVo>() {
                    }.getType();
                    clientVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
                List<Client> list = clientMapper.selectList(wrapper);
                for (Client client : list) {
                    LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                    addrInfoWrapper.eq(AddrInfo::getCliId, client.getCliId());
                    List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                    ClientVo clientVo = clientPoToVo(client, addrInfoList);
                    clientVo.setPasswd(null);
                    clientVoList.add(clientVo);
                    String json = new Gson().toJson(clientVo);
                    redisUtil.set("client" + clientVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有客户信息成功：{}", clientVoList);
        } catch (Exception e) {
            log.error("查询所有客户信息失败：", e);
            return null;
        }
        return clientVoList;
    }

    //根据条件查询客户信息
    @Override
    public PageInfo<ClientVo> select(ClientVo clientVo) {
        log.info("根据条件查询客户信息开始，请求参数：{}", clientVo);
        PageInfo<ClientVo> pageInfoVo=new PageInfo<>();
        try {
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getUname()), Client::getUname, clientVo.getUname());
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getPhone()), Client::getPhone, clientVo.getPhone());
            wrapper.like(ObjectUtils.isNotEmpty(clientVo.getNickname()), Client::getNickname, clientVo.getNickname());
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getEmail()), Client::getEmail, clientVo.getEmail());
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getPoint()), Client::getPoint, clientVo.getPoint());
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getDefAdd()), Client::getDefAdd, clientVo.getDefAdd());
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getRegTime()), Client::getRegTime,
                    DateUtil.DateToTimeMill(clientVo.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getLogTime()), Client::getLogTime,
                    DateUtil.DateToTimeMill(clientVo.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.eq(ObjectUtils.isNotEmpty(clientVo.getLogIp()), Client::getLogIp, clientVo.getLogIp());
            PageHelper.startPage(ObjectUtils.isNotEmpty(clientVo.getPageNum()) ? clientVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(clientVo.getPageSize()) ? clientVo.getPageSize() : 10);
            List<Client> list = clientMapper.selectList(wrapper);
            PageInfo<Client> pageInfo=new PageInfo<>(list);
            pageInfoVo=pageInfoPoToVo(pageInfo);
            for (Client client : list) {
                LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                addrInfoWrapper.eq(AddrInfo::getCliId, client.getCliId());
                List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                client.setPasswd(null);
                pageInfoVo.getList().add(clientPoToVo(client,addrInfoList));
            }
            log.info("根据条件查询客户信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询客户信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    //添加客户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientVo insert(ClientVo clientVo) {
        log.info("添加客户信息开始，请求参数：{}", clientVo);
        ClientVo clientVos = new ClientVo();
        try {
            Client client = clientVoToPo(clientVo);
            client.setCliId(IDUtil.getID());
            client.setPasswd(CodeUtil.getMD5Str(client.getPasswd(), client.getUname() + "client"));
            client.setRegTime(DateUtil.getCurrentTimeMill());
            client.setLogTime(DateUtil.getCurrentTimeMill());
            client.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Client::getCliId, client.getCliId());
            wrapper.or().eq(Client::getUname, client.getUname());
            List<Client> list = clientMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = clientMapper.insert(client);
                if (count > 0) {
                    clientVos = getById(client.getCliId());
                    String str = new Gson().toJson(clientVos);
                    redisUtil.set("cart" + clientVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加客户信息成功：{}",clientVos);
                }
            }
        } catch (Exception e) {
            log.error("添加客户信息失败：", e);
            return null;
        }
        return clientVos;
    }

    //添加客户地址信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientVo insertAddrInfo(ClientVo.AddrInfoVo addrInfoVo) {
        log.info("添加客户地址信息开始，请求参数：{}", addrInfoVo);
        ClientVo clientVo = new ClientVo();
        try {
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Client::getCliId, addrInfoVo.getCliId());
            List<Client> list = clientMapper.selectList(wrapper);
            if (list.size() > 0) {
                AddrInfo addrInfo = AddrVoToPo(addrInfoVo);
                addrInfo.setAddrId(IDUtil.getShortUUID());
                LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
                addrInfoWrapper.eq(AddrInfo::getAddrId, addrInfo.getAddrId());
                List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
                if (addrInfoList.size() == 0) {
                    int count = addrInfoMapper.insert(addrInfo);
                    if (count > 0) {
                        if (redisUtil.hasKey("client" + list.get(0).getCliId())) {
                            redisUtil.delete("client" + list.get(0).getCliId());
                        }
                        clientVo = getById(list.get(0).getCliId());
                        String str = new Gson().toJson(clientVo);
                        redisUtil.set("client" + list.get(0).getCliId(), str);
                        log.info("从Redis缓存中添加数据成功");
                        log.info("添加客户地址信息成功：{}",clientVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("添加客户地址信息失败：", e);
            return null;
        }
        return clientVo;
    }

    //删除客户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除客户信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Client::getCliId, id);
            List<Client> list = clientMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = clientMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("client" + id)) {
                        redisUtil.delete("client" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除客户信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除客户信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //删除客户地址信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAddrInfo(String id) {
        log.info("删除客户地址信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
            addrInfoWrapper.eq(AddrInfo::getAddrId, id);
            List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
            if (addrInfoList.size() > 0) {
                LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Client::getCliId, addrInfoList.get(0).getCliId());
                List<Client> list = clientMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count=addrInfoMapper.delete(addrInfoWrapper);
                    if(count>0){
                        if (redisUtil.hasKey("client" + list.get(0).getCliId())) {
                            redisUtil.delete("client" + list.get(0).getCliId());
                        }
                        String str = new Gson().toJson(getById(list.get(0).getCliId()));
                        redisUtil.set("client" + list.get(0).getCliId(), str);
                        aBoolean = true;
                        log.info("从Redis缓存中删除数据成功");
                        log.info("删除客户地址信息成功");
                    }
                }
            }
        } catch (Exception e) {
            log.error("删除客户地址信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //修改客户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientVo update(ClientVo clientVo) {
        log.info("修改客户信息开始，请求参数：{}", clientVo);
        ClientVo clientVos = new ClientVo();
        try {
            Client client = clientVoToPo(clientVo);
            LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Client::getCliId, client.getCliId());
            List<Client> list = clientMapper.selectList(wrapper);
            if (list.size() > 0) {
                if(ObjectUtils.isNotEmpty(client.getPasswd())){
                    client.setPasswd(CodeUtil.getMD5Str(client.getPasswd(), list.get(0).getUname() + "client"));
                }
                int count = clientMapper.update(client, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("client" + client.getCliId())) {
                        redisUtil.delete("client" + client.getCliId());
                    }
                    clientVos = getById(client.getCliId());
                    String str = new Gson().toJson(clientVos);
                    redisUtil.set("client" + clientVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改客户信息成功：{}",clientVos);
                }
            }
        } catch (Exception e) {
            log.error("修改客户信息失败：", e);
            return null;
        }
        return clientVos;
    }

    //修改客户地址信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientVo updateAddrInfo(ClientVo.AddrInfoVo addrInfoVo) {
        log.info("修改客户地址信息开始，请求参数：{}", addrInfoVo);
        ClientVo clientVo = new ClientVo();
        try {
            AddrInfo addrInfo = AddrVoToPo(addrInfoVo);
            LambdaQueryWrapper<AddrInfo> addrInfoWrapper = new LambdaQueryWrapper<>();
            addrInfoWrapper.eq(AddrInfo::getAddrId, addrInfo.getAddrId());
            List<AddrInfo> addrInfoList = addrInfoMapper.selectList(addrInfoWrapper);
            if (addrInfoList.size() > 0) {
                LambdaQueryWrapper<Client> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Client::getCliId, addrInfoList.get(0).getCliId());
                List<Client> list = clientMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count = addrInfoMapper.update(addrInfo, addrInfoWrapper);
                    if (count > 0) {
                        if (redisUtil.hasKey("client" + list.get(0).getCliId())) {
                            redisUtil.delete("client" + list.get(0).getCliId());
                        }
                        clientVo = getById(list.get(0).getCliId());
                        String str = new Gson().toJson(clientVo);
                        redisUtil.set("client" + list.get(0).getCliId(), str);
                        log.info("从Redis缓存中更新数据成功");
                        log.info("修改客户地址信息成功：{}",clientVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改客户地址信息失败：", e);
            return null;
        }
        return clientVo;
    }

}
