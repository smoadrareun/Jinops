package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.OrderVo;
import com.hebeu.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderController
 * @Author: Smoadrareun
 * @Description: TODO 交易信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //根据交易id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") String id) {
        OrderVo orderVo = orderService.getById(id);
        if (orderVo == null) {
            return resp.failure(-1, "根据id查询交易信息失败");
        } else if (orderVo.getId() == null) {
            return resp.failure(-404, "根据id查询交易信息未找到数据");
        } else {
            return resp.success("根据id查询交易信息成功", orderVo);
        }
    }

    //查询所有交易数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<OrderVo> list = orderService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有交易信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有交易信息未找到数据");
        } else {
            return resp.success("查询所有交易信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询交易数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody OrderVo orderVo) {
        PageInfo<OrderVo> pageInfo = orderService.select(orderVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询交易信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询交易信息未找到数据");
        } else {
            return resp.success("根据条件查询交易信息成功", pageInfo);
        }
    }

    //添加交易数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody OrderVo orderVo) {
        OrderVo orderVos = orderService.insert(orderVo);
        if (orderVos == null || orderVos.getId() == null) {
            return resp.failure(-1, "添加交易信息失败");
        } else {
            return resp.success("添加交易信息成功", orderVos);
        }
    }

    //添加交易订单详情
    @RequestMapping("/insertComInfo")
    public ResponseBody<Object> insertComInfo(@RequestBody OrderVo.ComInfoVo comInfoVo) {
        OrderVo orderVo = orderService.insertComInfo(comInfoVo);
        if (orderVo == null || orderVo.getId() == null) {
            return resp.failure(-1, "添加交易订单详情失败");
        } else {
            return resp.success("添加交易订单详情成功", orderVo);
        }
    }

    //根据交易id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") Integer id) {
        Boolean aBoolean = orderService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除交易信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的交易信息不存在");
        } else {
            return resp.success("删除交易信息成功", null);
        }
    }

    //根据订单详情id删除数据
    @RequestMapping("/deleteComInfo/{id}")
    public ResponseBody<Object> deleteComInfo(@PathVariable("id") String id) {
        Boolean aBoolean = orderService.deleteComInfo(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除交易订单详情失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的交易订单详情不存在");
        } else {
            return resp.success("删除交易订单详情成功", null);
        }
    }

    //修改交易数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody OrderVo orderVo) {
        OrderVo orderVos = orderService.update(orderVo);
        if (orderVos == null) {
            return resp.failure(-1, "修改交易信息失败");
        } else if (orderVos.getId() == null) {
            return resp.failure(-404, "需修改的交易信息不存在");
        } else {
            return resp.success("修改交易信息成功", orderVos);
        }
    }

    //修改交易订单详情
    @RequestMapping("/updateComInfo")
    public ResponseBody<Object> updateComInfo(@RequestBody OrderVo.ComInfoVo comInfoVo) {
        OrderVo orderVo = orderService.updateComInfo(comInfoVo);
        if (orderVo == null) {
            return resp.failure(-1, "修改交易订单详情失败");
        } else if (orderVo.getId() == null) {
            return resp.failure(-404, "需修改的交易订单详情不存在");
        } else {
            return resp.success("修改交易订单详情成功", orderVo);
        }
    }

}
