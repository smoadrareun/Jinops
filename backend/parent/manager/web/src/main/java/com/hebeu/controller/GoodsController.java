package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.GoodsVo;
import com.hebeu.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: GoodsController
 * @Author: Smoadrareun
 * @Description: TODO 商品信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //根据商品id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") String id) {
        GoodsVo goodsVo = goodsService.getById(id);
        if (goodsVo == null) {
            return resp.failure(-1, "根据id查询商品信息失败");
        } else if (goodsVo.getId() == null) {
            return resp.failure(-404, "根据id查询商品信息未找到数据");
        } else {
            return resp.success("根据id查询商品信息成功", goodsVo);
        }
    }

    //查询所有商品数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<GoodsVo> list = goodsService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有商品信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有商品信息未找到数据");
        } else {
            return resp.success("查询所有商品信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询商品数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody GoodsVo goodsVo) {
        PageInfo<GoodsVo> pageInfo = goodsService.select(goodsVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询商品信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询商品信息未找到数据");
        } else {
            return resp.success("根据条件查询商品信息成功", pageInfo);
        }
    }

    //添加商品数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody GoodsVo goodsVo) {
        GoodsVo goodsVos = goodsService.insert(goodsVo);
        if (goodsVos == null || goodsVos.getId() == null) {
            return resp.failure(-1, "添加商品信息失败");
        } else {
            return resp.success("添加商品信息成功", goodsVos);
        }
    }

    //添加商品规格信息
    @RequestMapping("/insertSpecInfo")
    public ResponseBody<Object> insertSpecInfo(@RequestBody GoodsVo.SpecInfoVo specInfoVo) {
        GoodsVo goodsVo = goodsService.insertSpecInfo(specInfoVo);
        if (goodsVo == null || goodsVo.getId() == null) {
            return resp.failure(-1, "添加商品规格信息失败");
        } else {
            return resp.success("添加商品规格信息成功", goodsVo);
        }
    }

    //根据商品id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") Integer id) {
        Boolean aBoolean = goodsService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除商品信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的商品信息不存在");
        } else {
            return resp.success("删除商品信息成功", null);
        }
    }

    //根据规格信息id删除数据
    @RequestMapping("/deleteSpecInfo/{id}")
    public ResponseBody<Object> deleteSpecInfo(@PathVariable("id") String id) {
        Boolean aBoolean = goodsService.deleteSpecInfo(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除商品规格信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的商品规格信息不存在");
        } else {
            return resp.success("删除商品规格信息成功", null);
        }
    }

    //修改商品数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody GoodsVo goodsVo) {
        GoodsVo goodsVos = goodsService.update(goodsVo);
        if (goodsVos == null) {
            return resp.failure(-1, "修改商品信息失败");
        } else if (goodsVos.getId() == null) {
            return resp.failure(-404, "需修改的商品信息不存在");
        } else {
            return resp.success("修改商品信息成功", goodsVos);
        }
    }

    //修改商品规格信息
    @RequestMapping("/updateSpecInfo")
    public ResponseBody<Object> updateSpecInfo(@RequestBody GoodsVo.SpecInfoVo specInfoVo) {
        GoodsVo goodsVo = goodsService.updateSpecInfo(specInfoVo);
        if (goodsVo == null) {
            return resp.failure(-1, "修改商品规格信息失败");
        } else if (goodsVo.getId() == null) {
            return resp.failure(-404, "需修改的商品规格信息不存在");
        } else {
            return resp.success("修改商品规格信息成功", goodsVo);
        }
    }

}
