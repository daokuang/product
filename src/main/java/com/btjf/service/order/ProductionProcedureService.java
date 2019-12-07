package com.btjf.service.order;

import com.alibaba.druid.util.StringUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.utils.BeanUtil;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.mapper.order.ProductionProcedureMapper;
import com.btjf.model.order.OrderProduct;
import com.btjf.model.order.ProductionProcedure;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

/**
 * Created by liuyq on 2019/8/8.
 * 生产单工序
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class ProductionProcedureService {
    @Resource
    private ProductionProcedureMapper productionProcedureMapper;

    @Resource
    private OrderProductService orderProductService;


    public Integer insert(ProductionProcedure productionProcedure) {
        if (null == productionProcedure) return 0;
        productionProcedureMapper.insertSelective(productionProcedure);
        return productionProcedure.getId();
    }

    public List<ProductionProcedure> findByProductionNo(String productionNo) {
        if (StringUtils.isEmpty(productionNo)) return null;
        return productionProcedureMapper.findByProductionNo(productionNo);
    }

    /**
     * 获取确认工序列表
     *
     * @param deptName     部门名称
     * @param productionNo 生产单编号
     * @return
     */
    public List<WorkShopVo.Procedure> getConfigProcedure(String deptName, String productionNo) {
        List<ProductionProcedure> productionProcedures = productionProcedureMapper.getConfigProcedure(deptName, productionNo);
        return BeanUtil.convertList(productionProcedures, WorkShopVo.Procedure.class);
    }

    public Integer deleteByProductionNo(String productionNo) {
        if (StringUtils.isEmpty(productionNo)) return 0;
        return productionProcedureMapper.deleteByProductionNo(productionNo);
    }

    /**
     * 工序可分配数额
     *
     * @param orderNo
     * @param productNo
     * @return
     */
    public Integer procedureCanAssignNum(String orderNo, String productNo, Integer procedureId) {
        if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productNo) || null == procedureId)
            throw new BusinessException("订单，型号.工序不能为空");
        Integer assignNum = productionProcedureMapper.procedureCanAssignNum(orderNo, productNo, procedureId);

        OrderProduct product = orderProductService.getByOrderNoAndProductNo(orderNo, productNo);
        return Optional.ofNullable(product).map(v -> {
            return v.getMaxNum() - (assignNum == null ? 0 : assignNum);
        }).orElse(0);
    }

    public List<ProductionProcedure> isContainZj(String procedureName, String productionNo, Integer multipleProductionId) {
        Boolean flage = false;
        return productionProcedureMapper.isContainZj(procedureName, productionNo, multipleProductionId);

    }
    public List<ProductionProcedure> getByMultipleId(Integer id) {
        return productionProcedureMapper.getByMultipleId(id);
    }

    /**
     * 通过工序id删除
     * @param id
     */
    public void deletByProductionProcedureId(Integer id){
        productionProcedureMapper.deletByProductionProcedureId(id);
    }

}
