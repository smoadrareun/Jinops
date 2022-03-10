package com.hebeu.controller;

import com.hebeu.common.AssembleResponseMsg;
import com.hebeu.common.ResponseBody;
import com.hebeu.model.GoodsModel;
import com.hebeu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private GoodsService goodsService;
    public AssembleResponseMsg msg = new AssembleResponseMsg();

    @Autowired
    public void setGoodsService (GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    //查询所有商品数据
    @RequestMapping("/getList")
    public ResponseBody getList() {
        List<GoodsModel> list=goodsService.getList();
        if(list==null){
            return msg.failure(-1,"查询所有商品数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"查询所有商品未找到数据");
        }else{
            return msg.success("查询所有商品数据成功",list);
        }
    }

    //根据商品id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody getById(@PathVariable("id") Integer id) {
        GoodsModel goodsModel=goodsService.getById(id);
        if(goodsModel==null){
            return msg.failure(-1,"根据商品id查询数据失败");
        }else if(goodsModel.getId()==null){
            return msg.failure(-404,"根据商品id查询未找到数据");
        }else{
            return msg.success("根据商品id查询数据成功",goodsModel);
        }
    }

    //精确查询商品数据
    @RequestMapping("/find")
    public ResponseBody find(@RequestBody Map<String,Object> map) {
        List<GoodsModel> list=goodsService.find(map);
        if(list==null){
            return msg.failure(-1,"精确查询商品数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"精确查询商品未找到数据");
        }else{
            return msg.success("精确查询商品数据成功",list);
        }
    }

    //模糊查询商品数据
    @RequestMapping("/search")
    public ResponseBody search(@RequestBody Map<String,Object> map) {
        List<GoodsModel> list=goodsService.search(map);
        if(list==null){
            return msg.failure(-1,"模糊查询商品数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"模糊查询商品未找到数据");
        }else{
            return msg.success("模糊查询商品数据成功",list);
        }
    }

    //添加商品数据
    @RequestMapping("/insert")
    public ResponseBody insert(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=goodsService.insert(map);
        if(aBoolean==null){
            return msg.failure(-1,"添加商品数据失败");
        }else{
            return msg.success("添加商品数据成功");
        }
    }

    //添加商品地址信息
    @RequestMapping("/insertSpecInfo")
    public ResponseBody insertSpecInfo(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=goodsService.insertSpecInfo(map);
        if(aBoolean==null){
            return msg.failure(-1,"添加商品规格信息失败");
        }else if(!aBoolean){
            return msg.failure(-404,"添加信息的商品不存在");
        }else{
            return msg.success("添加商品规格信息成功");
        }
    }

    //根据商品id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") Integer id) {
        Boolean aBoolean=goodsService.delete(id);
        if(aBoolean==null){
            return msg.failure(-1,"删除商品数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"删除的商品不存在");
        }else{
            return msg.success("删除商品数据成功");
        }
    }

    //根据地址信息id删除数据
    @RequestMapping("/deleteSpecInfo/{id}")
    public ResponseBody deleteSpecInfo(@PathVariable("id") Integer id) {
        Boolean aBoolean=goodsService.deleteSpecInfo(id);
        if(aBoolean==null){
            return msg.failure(-1,"删除商品规格信息失败");
        }else{
            return msg.success("删除商品规格信息成功");
        }
    }

    //修改商品数据
    @RequestMapping("/update")
    public ResponseBody update(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=goodsService.update(map);
        if(aBoolean==null){
            return msg.failure(-1,"修改商品数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"修改的商品不存在");
        }else{
            return msg.success("修改商品数据成功");
        }
    }

    //修改商品地址信息
    @RequestMapping("/updateSpecInfo")
    public ResponseBody updateSpecInfo(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=goodsService.updateSpecInfo(map);
        if(aBoolean==null){
            return msg.failure(-1,"修改商品规格信息失败");
        }else if(!aBoolean){
            return msg.failure(-404,"修改的商品不存在");
        }else{
            return msg.success("修改商品规格信息成功");
        }
    }

    //请求查询商品数据
    @RequestMapping("/query")
    public ResponseBody query(@RequestBody Map<String,Object> map) {
        Map<String,Object> resultMap=goodsService.query(map);
        if(resultMap.get("goods")==null){
            return msg.failure(-1,"请求查询商品数据失败");
        }else if(((List<GoodsModel>) resultMap.get("goods")).size()==0){
            return msg.failure(-404,"请求查询商品未找到数据");
        }else{
            return msg.success("请求查询商品数据成功",resultMap);
        }
    }
}
