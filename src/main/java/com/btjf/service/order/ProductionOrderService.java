package com.btjf.service.order;

import com.btjf.mapper.order.ProductionOrderMapper;
import com.btjf.model.order.ProductionOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by liuyq on 2019/8/8.
 * <p>
 * 生产单
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ProductionOrderService {
    @Resource
    private ProductionOrderMapper productionOrderMappe;


    public ProductionOrder getByOrderProductID(Integer orderProductID) {
        if (orderProductID == null) return null;

       return productionOrderMappe.getByOrderProductID(orderProductID);
    }

}
