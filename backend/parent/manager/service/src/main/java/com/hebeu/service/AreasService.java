package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hebeu.pojo.Areas;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: AreasService
 * @Author: Smoadrareun
 * @Description: TODO 行政区划信息服务层接口
 */

public interface AreasService extends IService<Areas> {

    Areas getById(String id);

    List<Areas> getList();

    List<Areas> select(Areas areas);

}
