package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.StoreVo;
import com.hebeu.service.StoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: StoreController
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //根据店铺id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") Integer id) {
        StoreVo storeVo = storeService.getById(id);
        if (storeVo == null) {
            return resp.failure(-1, "根据id查询店铺信息失败");
        } else if (storeVo.getId() == null) {
            return resp.failure(-404, "根据id查询店铺信息未找到数据");
        } else {
            return resp.success("根据id查询店铺信息成功", storeVo);
        }
    }

    //查询所有店铺数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<StoreVo> list = storeService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有店铺信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有店铺信息未找到数据");
        } else {
            return resp.success("查询所有店铺信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询店铺数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody StoreVo storeVo) {
        PageInfo<StoreVo> pageInfo = storeService.select(storeVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询店铺信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询店铺信息未找到数据");
        } else {
            return resp.success("根据条件查询店铺信息成功", pageInfo);
        }
    }

    //添加店铺数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody StoreVo storeVo) {
        StoreVo storeVos = storeService.insert(storeVo);
        if (storeVos == null || storeVos.getId() == null) {
            return resp.failure(-1, "添加店铺信息失败");
        } else {
            return resp.success("添加店铺信息成功", storeVos);
        }
    }

    //添加店铺配送信息
    @RequestMapping("/insertDistInfo")
    public ResponseBody<Object> insertDistInfo(@RequestBody StoreVo.DistInfoVo distInfoVo) {
        StoreVo storeVo = storeService.insertDistInfo(distInfoVo);
        if (storeVo == null || storeVo.getId() == null) {
            return resp.failure(-1, "添加店铺配送信息失败");
        } else {
            return resp.success("添加店铺配送信息成功", storeVo);
        }
    }

    //根据店铺id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") Integer id) {
        Boolean aBoolean = storeService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除店铺信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的店铺信息不存在");
        } else {
            return resp.success("删除店铺信息成功", null);
        }
    }

    //根据配送信息id删除数据
    @RequestMapping("/deleteDistInfo/{id}")
    public ResponseBody<Object> deleteDistInfo(@PathVariable("id") String id) {
        Boolean aBoolean = storeService.deleteDistInfo(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除店铺配送信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的店铺配送信息不存在");
        } else {
            return resp.success("删除店铺配送信息成功", null);
        }
    }

    //修改店铺数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody StoreVo storeVo) {
        StoreVo storeVos = storeService.update(storeVo);
        if (storeVos == null) {
            return resp.failure(-1, "修改店铺信息失败");
        } else if (storeVos.getId() == null) {
            return resp.failure(-404, "需修改的店铺信息不存在");
        } else {
            return resp.success("修改店铺信息成功", storeVos);
        }
    }

    //修改店铺配送信息
    @RequestMapping("/updateDistInfo")
    public ResponseBody<Object> updateDistInfo(@RequestBody StoreVo.DistInfoVo distInfoVo) {
        StoreVo storeVo = storeService.updateDistInfo(distInfoVo);
        if (storeVo == null) {
            return resp.failure(-1, "修改店铺配送信息失败");
        } else if (storeVo.getId() == null) {
            return resp.failure(-404, "需修改的店铺配送信息不存在");
        } else {
            return resp.success("修改店铺配送信息成功", storeVo);
        }
    }

}
