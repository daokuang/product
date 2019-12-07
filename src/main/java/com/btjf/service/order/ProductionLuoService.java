package com.btjf.service.order;

import com.alibaba.druid.util.StringUtils;
import com.btjf.mapper.order.ProductionLuoMapper;
import com.btjf.model.order.ProductionLuo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/8/8.
 * 分萝
 */

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class ProductionLuoService {

    @Resource
    private ProductionLuoMapper productionLuoMapper;


    public Integer insert(ProductionLuo productionLuo) {
        if (productionLuo == null) return 0;
        productionLuoMapper.insertSelective(productionLuo);
        return productionLuo.getId();
    }


    public List<ProductionLuo> getByProductionNo(String productionNo) {
        if (null == productionNo) return null;
        return productionLuoMapper.getByProductionNo(productionNo);
    }
    public ProductionLuo getByProductionNoAndSort(String productionNo, Integer sort) {
        if (null == productionNo) return null;
        return productionLuoMapper.getByProductionNoAndSort(productionNo, sort);
    }

    public ProductionLuo getById(Integer id){
        if(id == null) return null;
        return productionLuoMapper.selectByPrimaryKey(id);

    }

    public Integer update(ProductionLuo productionLuo){
        return productionLuoMapper.updateByPrimaryKeySelective(productionLuo);
    }

    public Integer deleteByProductionNo(String productionNo){
        if(StringUtils.isEmpty(productionNo)) return null;
        return productionLuoMapper.deleteByProductionNo(productionNo);
    }
}
