package com.btjf.service.order;


import com.btjf.mapper.order.MultipleProductionMapper;
import com.btjf.model.order.MultipleProduction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MultipleProductionService {

    @Resource
    private MultipleProductionMapper multipleProductionMapper;

    public Integer add(MultipleProduction multipleProduction) {
        multipleProductionMapper.insertSelective(multipleProduction);
        return multipleProduction.getId();
    }

    public List<MultipleProduction> getByProductionNo(String productionNo) {
       return multipleProductionMapper.getByProductionNo(productionNo);
    }
}
