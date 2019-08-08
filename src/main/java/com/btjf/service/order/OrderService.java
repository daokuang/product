package com.btjf.service.order;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.mapper.order.OrderMapper;
import com.btjf.model.order.Order;
import com.btjf.model.order.OrderProduct;
import com.btjf.service.pm.PmOutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/8/4.
 */
@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderProductService orderProductService;

    @Resource
    private ProductionOrderService productionOrderService;

    @Resource
    private PmOutService pmOutService;

    public Order getByNo(String orderNo) {
        return orderMapper.getByNo(orderNo);
    }

    public Integer insert(Order order) {
        if(null == order) return 0;
        orderMapper.insertSelective(order);
        return order.getId();
    }

    public Integer delete(List<String> ids) {
        Integer i= 0;
       for(String id : ids){
           OrderProduct orderProduct = orderProductService.getByID(new Integer(id));
           if(null == orderProduct) continue;
           if(null != productionOrderService.getByOrderProductID(new Integer(id))){
               throw new BusinessException("订单编号为："+orderProduct.getOrderNo()+",型号为："+orderProduct.getProductNo()
               +"已经生成了生产单，无法删除！！！");
           }
           if(null != pmOutService.findList(null, orderProduct.getOrderNo(), orderProduct.getProductNo())){
               throw new BusinessException("订单编号为："+orderProduct.getOrderNo()+",型号为："+orderProduct.getProductNo()
                       +"已经生成耗料单，无法删除！！！");
           }

           //删除
           orderProductService.deleteById(new Integer(id));

           if(CollectionUtils.isEmpty(orderProductService.findByOrderId(orderProduct.getOrderId()))){
               orderMapper.deletByID(orderProduct.getOrderId());
           }
           i++;
       }
       return i;
    }

    public List<Order> findAll() {
        return orderMapper.findAll();
    }
}
