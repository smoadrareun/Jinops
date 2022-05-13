package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hebeu.pojo.Path;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: PathService
 * @Author: Smoadrareun
 * @Description: TODO 权限信息服务层接口
 */

public interface PathService extends IService<Path> {

    Path getById(Integer id);

    List<Path> getList();

    List<Path> select(Path path);

    Path insert(Path path);

    Boolean delete(Integer id);

    Path update(Path path);
    
}
