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
import com.hebeu.mapper.ComInfoMapper;
import com.hebeu.mapper.OrderMapper;
import com.hebeu.pojo.ComInfo;
import com.hebeu.pojo.Order;
import com.hebeu.pojo.vo.OrderVo;
import com.hebeu.service.OrderService;
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
 * @ClassName: OrderServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 交易详情服务层实现类
 */

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ComInfoMapper comInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Order, OrderVo> PageInfo<OrderVo> pageInfoPoToVo(PageInfo<Order> pageInfo) {
        Page<OrderVo> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static OrderVo orderPoToVo(Order order, List<ComInfo> comInfoList) {
        if (order == null) {
            return null;
        }
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        orderVo.setId(order.getOrdId());
        orderVo.setDate(DateUtil.TimeMillToDate(order.getDate(), "yyyy-MM-dd HH:mm:ss"));
        orderVo.setComInfo(ComListToVo(comInfoList));
        return orderVo;
    }

    public static Order orderVoToPo(OrderVo orderVo) {
        if (orderVo == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderVo, order);
        order.setId(null);
        order.setOrdId(orderVo.getId());
        order.setDate(DateUtil.DateToTimeMill(orderVo.getDate(), "yyyy-MM-dd HH:mm:ss"));
        return order;
    }

    public static ComInfo ComVoToPo(OrderVo.ComInfoVo comInfoVo) {
        if (comInfoVo == null) {
            return null;
        }
        ComInfo comInfo = new ComInfo();
        BeanUtils.copyProperties(comInfoVo, comInfo);
        comInfo.setId(null);
        return comInfo;
    }

    public static List<OrderVo.ComInfoVo> ComListToVo(List<ComInfo> comInfoList) {
        if (comInfoList == null) {
            return null;
        }
        List<OrderVo.ComInfoVo> comInfoVoList = new ArrayList<>();
        for (ComInfo comInfo : comInfoList) {
            OrderVo.ComInfoVo comInfoVo = new OrderVo.ComInfoVo();
            BeanUtils.copyProperties(comInfo, comInfoVo);
            comInfoVo.setId(comInfo.getComId());
            comInfoVoList.add(comInfoVo);
        }
        return comInfoVoList;
    }

    //根据id查询交易详情
    @Override
    public OrderVo getById(String id) {
        log.info("根据id查询交易详情开始，请求参数：{}", id);
        OrderVo orderVo = new OrderVo();
        try {
            if (redisUtil.hasKey("order" + id)) {
                String str = String.valueOf(redisUtil.get("order" + id));
                Type type = new TypeToken<OrderVo>() {
                }.getType();
                orderVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", orderVo);
            } else {
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Order::getOrdId, id);
                List<Order> list = orderMapper.selectList(wrapper);
                if (list.size() > 0) {
                    LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
                    comInfoWrapper.eq(ComInfo::getOrdId, list.get(0).getOrdId());
                    List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
                    orderVo = orderPoToVo(list.get(0), comInfoList);
                    log.info("根据id查询交易详情成功：{}", orderVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询交易详情失败：", e);
            return null;
        }
        return orderVo;
    }

    //查询所有交易详情
    @Override
    public List<OrderVo> getList() {
        log.info("查询所有交易详情开始");
        List<OrderVo> orderVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("order*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<OrderVo>() {
                    }.getType();
                    orderVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                List<Order> list = orderMapper.selectList(wrapper);
                for (Order order : list) {
                    LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
                    comInfoWrapper.eq(ComInfo::getOrdId, order.getOrdId());
                    List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
                    OrderVo orderVo = orderPoToVo(order, comInfoList);
                    orderVoList.add(orderVo);
                    String json = new Gson().toJson(orderVo);
                    redisUtil.set("order" + orderVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有交易详情成功：{}", orderVoList);
        } catch (Exception e) {
            log.error("查询所有交易详情失败：", e);
            return null;
        }
        return orderVoList;
    }

    //根据条件查询交易详情
    @Override
    public PageInfo<OrderVo> select(OrderVo orderVo) {
        log.info("根据条件查询交易详情开始，请求参数：{}", orderVo);
        PageInfo<OrderVo> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(ObjectUtils.isNotEmpty(orderVo.getName()), Order::getName, orderVo.getName());
            wrapper.gt(ObjectUtils.isNotEmpty(orderVo.getStartTime()), Order::getDate,
                    DateUtil.DateToTimeMill(orderVo.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.lt(ObjectUtils.isNotEmpty(orderVo.getEndTime()), Order::getDate,
                    DateUtil.DateToTimeMill(orderVo.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(orderVo.getSort())
                    && orderVo.getSort() == 1, Order::getDate);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(orderVo.getSort())
                    && orderVo.getSort() == 2, Order::getDate);
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(orderVo.getSort())
                    && orderVo.getSort() == 3, Order::getPrice);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(orderVo.getSort())
                    && orderVo.getSort() == 4, Order::getPrice);
            PageHelper.startPage(ObjectUtils.isNotEmpty(orderVo.getPageNum()) ? orderVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(orderVo.getPageSize()) ? orderVo.getPageSize() : 10);
            List<Order> list = orderMapper.selectList(wrapper);
            PageInfo<Order> pageInfo = new PageInfo<>(list);
            pageInfoVo = pageInfoPoToVo(pageInfo);
            for (Order order : list) {
                LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
                comInfoWrapper.eq(ComInfo::getOrdId, order.getOrdId());
                List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
                pageInfoVo.getList().add(orderPoToVo(order, comInfoList));
            }
            log.info("根据条件查询交易详情成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询交易详情失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    //添加交易详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVo insert(OrderVo orderVo) {
        log.info("添加交易详情开始，请求参数：{}", orderVo);
        OrderVo orderVos = new OrderVo();
        try {
            Order order = orderVoToPo(orderVo);
            order.setOrdId(IDUtil.getShortUUID());
            order.setDate(DateUtil.getCurrentTimeMill());
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getOrdId, order.getOrdId());
            List<Order> list = orderMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = orderMapper.insert(order);
                if (count > 0) {
                    orderVos = getById(order.getOrdId());
                    String str = new Gson().toJson(orderVos);
                    redisUtil.set("cart" + orderVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加交易详情成功：{}",orderVos);
                }
            }
        } catch (Exception e) {
            log.error("添加交易详情失败：", e);
            return null;
        }
        return orderVos;
    }

    //添加交易订单详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVo insertComInfo(OrderVo.ComInfoVo comInfoVo) {
        log.info("添加交易订单详情开始，请求参数：{}", comInfoVo);
        OrderVo orderVo = new OrderVo();
        try {
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getOrdId, comInfoVo.getOrdId());
            List<Order> list = orderMapper.selectList(wrapper);
            if (list.size() > 0) {
                ComInfo comInfo = ComVoToPo(comInfoVo);
                comInfo.setComId(IDUtil.getShortUUID());
                LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
                comInfoWrapper.eq(ComInfo::getComId, comInfo.getComId());
                List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
                if (comInfoList.size() == 0) {
                    int count = comInfoMapper.insert(comInfo);
                    if (count > 0) {
                        if (redisUtil.hasKey("order" + list.get(0).getOrdId())) {
                            redisUtil.delete("order" + list.get(0).getOrdId());
                        }
                        orderVo = getById(list.get(0).getOrdId());
                        String str = new Gson().toJson(orderVo);
                        redisUtil.set("order" + list.get(0).getOrdId(), str);
                        log.info("从Redis缓存中添加数据成功");
                        log.info("添加交易订单详情成功：{}",orderVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("添加交易订单详情失败：", e);
            return null;
        }
        return orderVo;
    }

    //删除交易详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除交易详情开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getOrdId, id);
            List<Order> list = orderMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = orderMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("order" + id)) {
                        redisUtil.delete("order" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除交易详情成功");
                }
            }
        } catch (Exception e) {
            log.error("删除交易详情失败：", e);
            return null;
        }
        return aBoolean;
    }

    //删除交易订单详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteComInfo(String id) {
        log.info("删除交易订单详情开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
            comInfoWrapper.eq(ComInfo::getComId, id);
            List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
            if (comInfoList.size() > 0) {
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Order::getOrdId, comInfoList.get(0).getOrdId());
                List<Order> list = orderMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count = comInfoMapper.delete(comInfoWrapper);
                    if (count > 0) {
                        if (redisUtil.hasKey("order" + list.get(0).getOrdId())) {
                            redisUtil.delete("order" + list.get(0).getOrdId());
                        }
                        String str = new Gson().toJson(getById(list.get(0).getOrdId()));
                        redisUtil.set("order" + list.get(0).getOrdId(), str);
                        aBoolean = true;
                        log.info("从Redis缓存中删除数据成功");
                        log.info("删除交易订单详情成功");
                    }
                }
            }
        } catch (Exception e) {
            log.error("删除交易订单详情失败：", e);
            return null;
        }
        return aBoolean;
    }

    //修改交易详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVo update(OrderVo orderVo) {
        log.info("修改交易详情开始，请求参数：{}", orderVo);
        OrderVo orderVos = new OrderVo();
        try {
            Order order = orderVoToPo(orderVo);
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getOrdId, order.getOrdId());
            List<Order> list = orderMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = orderMapper.update(order, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("order" + order.getOrdId())) {
                        redisUtil.delete("order" + order.getOrdId());
                    }
                    orderVos = getById(order.getOrdId());
                    String str = new Gson().toJson(orderVos);
                    redisUtil.set("order" + orderVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改交易详情成功：{}",orderVos);
                }
            }
        } catch (Exception e) {
            log.error("修改交易详情失败：", e);
            return null;
        }
        return orderVos;
    }

    //修改交易订单详情
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVo updateComInfo(OrderVo.ComInfoVo comInfoVo) {
        log.info("修改交易订单详情开始，请求参数：{}", comInfoVo);
        OrderVo orderVo = new OrderVo();
        try {
            ComInfo comInfo = ComVoToPo(comInfoVo);
            LambdaQueryWrapper<ComInfo> comInfoWrapper = new LambdaQueryWrapper<>();
            comInfoWrapper.eq(ComInfo::getComId, comInfo.getComId());
            List<ComInfo> comInfoList = comInfoMapper.selectList(comInfoWrapper);
            if (comInfoList.size() > 0) {
                LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Order::getOrdId, comInfoList.get(0).getOrdId());
                List<Order> list = orderMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count = comInfoMapper.update(comInfo, comInfoWrapper);
                    if (count > 0) {
                        if (redisUtil.hasKey("order" + list.get(0).getOrdId())) {
                            redisUtil.delete("order" + list.get(0).getOrdId());
                        }
                        orderVo = getById(list.get(0).getOrdId());
                        String str = new Gson().toJson(orderVo);
                        redisUtil.set("order" + list.get(0).getOrdId(), str);
                        log.info("从Redis缓存中更新数据成功");
                        log.info("修改交易订单详情成功：{}",orderVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改交易订单详情失败：", e);
            return null;
        }
        return orderVo;
    }

}
