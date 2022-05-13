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
import com.hebeu.mapper.VendorMapper;
import com.hebeu.pojo.Vendor;
import com.hebeu.pojo.vo.VendorVo;
import com.hebeu.service.VendorService;
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
 * @ClassName: VendorServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 商户信息服务层实现类
 */

@Slf4j
@Service
public class VendorServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements VendorService {

    @Resource
    private VendorMapper vendorMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Vendor, VendorVo> PageInfo<VendorVo> pageInfoPoToVo(PageInfo<Vendor> pageInfo) {
        Page<VendorVo> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static VendorVo vendorPoToVo(Vendor vendor) {
        if (vendor == null) {
            return null;
        }
        VendorVo vendorVo = new VendorVo();
        BeanUtils.copyProperties(vendor, vendorVo);
        vendorVo.setId(vendor.getVenId());
        vendorVo.setRegTime(DateUtil.TimeMillToDate(vendor.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
        vendorVo.setLogTime(DateUtil.TimeMillToDate(vendor.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
        return vendorVo;
    }

    public static Vendor vendorVoToPo(VendorVo vendorVo) {
        if (vendorVo == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        BeanUtils.copyProperties(vendorVo, vendor);
        vendor.setId(null);
        vendor.setVenId(vendorVo.getId());
        vendor.setRegTime(DateUtil.DateToTimeMill(vendorVo.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
        vendor.setLogTime(DateUtil.DateToTimeMill(vendorVo.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
        return vendor;
    }

    //商户账号密码登录
    @Override
    public VendorVo login(String uname, String passwd) {
        log.info("商户账号密码登录开始，请求参数：{},{}", uname, passwd);
        VendorVo vendorVo = new VendorVo();
        try {
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Vendor::getUname, uname);
            wrapper.eq(Vendor::getPasswd, CodeUtil.getMD5Str(passwd, uname + "vendor"));
            Vendor vendor = new Vendor();
            vendor.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
            vendor.setLogTime(DateUtil.getCurrentTimeMill());
            vendorMapper.update(vendor, wrapper);
            List<Vendor> list = vendorMapper.selectList(wrapper);
            if (list.size() > 0) {
                vendorVo = vendorPoToVo(list.get(0));
                vendorVo.setToken(TokenUtil.getToken(vendorVo.getUname(), vendorVo.getPasswd(), "vendor"));
                vendorVo.setPasswd(null);
                log.info("商户账号密码登录成功：{}", vendorVo);
            }
        } catch (Exception e) {
            log.error("商户账号密码登录失败：", e);
            return null;
        }
        return vendorVo;
    }

    //商户Token登录
    @Override
    public VendorVo login(String token) {
        log.info("商户Token登录开始，请求参数：{}", token);
        VendorVo vendorVo = new VendorVo();
        try {
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            List<String> list = TokenUtil.verify(token);
            if (list.get(2).equals("vendor")) {
                wrapper.eq(Vendor::getUname, list.get(0));
                wrapper.eq(Vendor::getPasswd, list.get(1));
                Vendor vendor = new Vendor();
                vendor.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
                vendor.setLogTime(DateUtil.getCurrentTimeMill());
                vendorMapper.update(vendor, wrapper);
                List<Vendor> vendorList = vendorMapper.selectList(wrapper);
                if (vendorList.size() > 0) {
                    vendorVo = vendorPoToVo(vendorList.get(0));
                    vendorVo.setToken(TokenUtil.getToken(vendorVo.getUname(), vendorVo.getPasswd(), "vendor"));
                    vendorVo.setPasswd(null);
                    log.info("商户Token登录成功：{}", vendorVo);
                }
            }
        } catch (Exception e) {
            log.error("商户Token登录失败：", e);
            return null;
        }
        return vendorVo;
    }

    //根据id查询商户信息
    @Override
    public VendorVo getById(Integer id) {
        log.info("根据id查询商户信息开始，请求参数：{}", id);
        VendorVo vendorVo = new VendorVo();
        try {
            if (redisUtil.hasKey("vendor" + id)) {
                String str = String.valueOf(redisUtil.get("vendor" + id));
                Type type = new TypeToken<VendorVo>() {
                }.getType();
                vendorVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", vendorVo);
            } else {
                LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Vendor::getVenId, id);
                List<Vendor> list = vendorMapper.selectList(wrapper);
                if (list.size() > 0) {
                    list.get(0).setPasswd(null);
                    vendorVo = vendorPoToVo(list.get(0));
                    log.info("根据id查询商户信息成功：{}", vendorVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询商户信息失败：", e);
            return null;
        }
        return vendorVo;
    }

    //查询所有商户信息
    @Override
    public List<VendorVo> getList() {
        log.info("查询所有商户信息开始");
        List<VendorVo> vendorVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("vendor*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<VendorVo>() {
                    }.getType();
                    vendorVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
                List<Vendor> list = vendorMapper.selectList(wrapper);
                for (Vendor vendor : list) {
                    VendorVo vendorVo = vendorPoToVo(vendor);
                    vendorVo.setPasswd(null);
                    vendorVoList.add(vendorVo);
                    String json = new Gson().toJson(vendorVo);
                    redisUtil.set("vendor" + vendorVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有商户信息成功：{}", vendorVoList);
        } catch (Exception e) {
            log.error("查询所有商户信息失败：", e);
            return null;
        }
        return vendorVoList;
    }

    //根据条件查询商户信息
    @Override
    public PageInfo<VendorVo> select(VendorVo vendorVo) {
        log.info("根据条件查询商户信息开始，请求参数：{}", vendorVo);
        PageInfo<VendorVo> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getUname()), Vendor::getUname, vendorVo.getUname());
            wrapper.like(ObjectUtils.isNotEmpty(vendorVo.getNickname()), Vendor::getNickname, vendorVo.getNickname());
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getPhone()), Vendor::getPhone, vendorVo.getPhone());
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getEmail()), Vendor::getEmail, vendorVo.getEmail());
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getRegTime()), Vendor::getRegTime,
                    DateUtil.DateToTimeMill(vendorVo.getRegTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getLogTime()), Vendor::getLogTime,
                    DateUtil.DateToTimeMill(vendorVo.getLogTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.eq(ObjectUtils.isNotEmpty(vendorVo.getLogIp()), Vendor::getLogIp, vendorVo.getLogIp());
            PageHelper.startPage(ObjectUtils.isNotEmpty(vendorVo.getPageNum()) ? vendorVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(vendorVo.getPageSize()) ? vendorVo.getPageSize() : 10);
            List<Vendor> list = vendorMapper.selectList(wrapper);
            PageInfo<Vendor> pageInfo = new PageInfo<>(list);
            pageInfoVo = pageInfoPoToVo(pageInfo);
            for (Vendor vendor : list) {
                vendor.setPasswd(null);
                pageInfoVo.getList().add(vendorPoToVo(vendor));
            }
            log.info("根据条件查询商户信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询商户信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    //添加商户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VendorVo insert(VendorVo vendorVo) {
        log.info("添加商户信息开始，请求参数：{}", vendorVo);
        VendorVo vendorVos = new VendorVo();
        try {
            Vendor vendor = vendorVoToPo(vendorVo);
            vendor.setVenId(IDUtil.getID());
            vendor.setPasswd(CodeUtil.getMD5Str(vendor.getPasswd(), vendor.getUname() + "vendor"));
            vendor.setRegTime(DateUtil.getCurrentTimeMill());
            vendor.setLogTime(DateUtil.getCurrentTimeMill());
            vendor.setLogIp(IPUtil.getIpAddr(HttpUtil.getHttpServletRequest()));
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Vendor::getVenId, vendor.getVenId());
            wrapper.or().eq(Vendor::getUname, vendor.getUname());
            List<Vendor> list = vendorMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = vendorMapper.insert(vendor);
                if (count > 0) {
                    vendorVos = getById(vendor.getVenId());
                    String str = new Gson().toJson(vendorVos);
                    redisUtil.set("cart" + vendorVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加商户信息成功：{}",vendorVos);
                }
            }
        } catch (Exception e) {
            log.error("添加商户信息失败：", e);
            return null;
        }
        return vendorVos;
    }

    //删除商户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除商户信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Vendor::getVenId, id);
            List<Vendor> list = vendorMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = vendorMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("vendor" + id)) {
                        redisUtil.delete("vendor" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除商户信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除商户信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //修改商户信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public VendorVo update(VendorVo vendorVo) {
        log.info("修改商户信息开始，请求参数：{}", vendorVo);
        VendorVo vendorVos = new VendorVo();
        try {
            Vendor vendor = vendorVoToPo(vendorVo);
            LambdaQueryWrapper<Vendor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Vendor::getVenId, vendor.getVenId());
            List<Vendor> list = vendorMapper.selectList(wrapper);
            if (list.size() > 0) {
                if(ObjectUtils.isNotEmpty(vendor.getPasswd())){
                    vendor.setPasswd(CodeUtil.getMD5Str(vendor.getPasswd(), list.get(0).getUname() + "vendor"));
                }
                int count = vendorMapper.update(vendor, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("vendor" + vendor.getVenId())) {
                        redisUtil.delete("vendor" + vendor.getVenId());
                    }
                    vendorVos = getById(vendor.getVenId());
                    String str = new Gson().toJson(vendorVos);
                    redisUtil.set("vendor" + vendorVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改商户信息成功：{}",vendorVos);
                }
            }
        } catch (Exception e) {
            log.error("修改商户信息失败：", e);
            return null;
        }
        return vendorVos;
    }

}
