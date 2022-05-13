package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Client;
import com.hebeu.pojo.vo.ClientVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientService
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层接口
 */

public interface ClientService extends IService<Client> {

    ClientVo login(String uname,String passwd);

    ClientVo login(String cliToken);

    ClientVo getById(Integer id);

    List<ClientVo> getList();

    PageInfo<ClientVo> select(ClientVo clientVo);

    ClientVo insert(ClientVo clientVo);

    ClientVo insertAddrInfo(ClientVo.AddrInfoVo addrInfoVo);

    Boolean delete(Integer id);

    Boolean deleteAddrInfo(String id);

    ClientVo update(ClientVo clientVo);

    ClientVo updateAddrInfo(ClientVo.AddrInfoVo addrInfoVo);

}
