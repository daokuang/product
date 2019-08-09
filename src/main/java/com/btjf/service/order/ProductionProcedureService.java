package com.btjf.service.order;

import com.alibaba.druid.util.StringUtils;
import com.btjf.mapper.order.ProductionProcedureMapper;
import com.btjf.model.order.ProductionProcedure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/8/8.
 * 生产单工序
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ProductionProcedureService {
    @Resource
    private ProductionProcedureMapper productionProcedureMapper;


    public Integer insert(ProductionProcedure productionProcedure) {
        if (null == productionProcedure) return 0;
        productionProcedureMapper.insertSelective(productionProcedure);
        return productionProcedure.getId();
    }

    public List<ProductionProcedure> findByProductionNo(String productionNo) {
        if (StringUtils.isEmpty(productionNo)) return null;
        return productionProcedureMapper.findByProductionNo(productionNo);
    }
}
