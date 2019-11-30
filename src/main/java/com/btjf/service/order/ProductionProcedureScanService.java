package com.btjf.service.order;

import com.alibaba.druid.util.StringUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.utils.BeanUtil;
import com.btjf.controller.order.vo.OrderVo;
import com.btjf.controller.order.vo.ProcessDetail;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.controller.weixin.vo.WxEmpVo;
import com.btjf.mapper.order.ProductionProcedureConfirmMapper;
import com.btjf.mapper.order.ProductionProcedureScanMapper;
import com.btjf.model.order.ProductionLuo;
import com.btjf.model.order.ProductionOrder;
import com.btjf.model.order.ProductionProcedureConfirm;
import com.btjf.model.order.ProductionProcedureScan;
import com.btjf.model.pm.PmOutBill;
import com.btjf.model.product.ProductProcedure;
import com.btjf.service.pm.PmOutService;
import com.btjf.service.productpm.ProductProcedureService;
import com.btjf.util.BigDecimalUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/8/18.
 */
@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class ProductionProcedureScanService {

    private static final Logger LOGGER = Logger
            .getLogger(ProductionProcedureScanService.class);

    @Resource
    private ProductionProcedureScanMapper productionProcedureScanMapper;

    @Resource
    private ProductionOrderService productionOrderService;

    @Resource
    private ProductionLuoService productionLuoService;

    @Resource
    private ProductProcedureService productProcedureService;

    @Resource
    private ProductionProcedureConfirmMapper productionProcedureConfirmMapper;

    @Resource
    private PmOutService pmOutService;

    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;


    public Integer deleteAndInsert(String orderNo, String productNo, String productionNo,
                                   Integer louId, String billOutNo, List<WorkShopVo.Procedure> procedures,
                                   WxEmpVo wxEmpVo, List<String> NOTCONFIRM_DEPT) {
        if ((StringUtils.isEmpty(productionNo) && StringUtils.isEmpty(billOutNo)) || (!StringUtils.isEmpty(productionNo) && !StringUtils.isEmpty(billOutNo)))
            throw new BusinessException("生成单和领料单不能存在，且不能同时未空");
        Integer num = 0;
        if (productionNo != null) {
            ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
            if (null == productionOrder) throw new BusinessException(productionNo + "  生产单不存在");
            num = productionOrder.getAssignNum();
        }
        if (louId != null) {
            ProductionLuo productionLuo = productionLuoService.getById(louId);
            if (productionLuo == null) throw new BusinessException(louId + " 分萝记录不存在");
            num = productionLuo.getNum();
        }
        if (billOutNo != null) {
            PmOutBill pmOutBill = pmOutService.getByBillNo(billOutNo);
            if (pmOutBill == null) throw new BusinessException(billOutNo + "领料单记录不存在");
            num = pmOutBill.getDistributionNum();
        }

        Integer row = 0;

        Integer deleteRow = productionProcedureConfirmMapper.delete(orderNo, productNo, productionNo, louId, billOutNo, null);
        if (deleteRow > 0) LOGGER.info("员工确认工序 删除之前质检的工序条数：" + deleteRow);
        for (WorkShopVo.Procedure procedure : procedures) {
            if (procedure == null) continue;
            row++;
            //删除扫码数据   质检数据
            //productionProcedureConfirmMapper.delete(orderNo, productNo, productionNo, louId, billOutNo, procedure.getProcedureId());
            productionProcedureScanMapper.delete(orderNo, productNo, productionNo, louId, billOutNo, procedure.getProcedureId());


            ProductProcedure productProcedure = productProcedureService.getById(procedure.getProcedureId());
            //插入扫码数据
            ProductionProcedureScan productionProcedureScan = new ProductionProcedureScan();
            productionProcedureScan.setEmpId(wxEmpVo.getId());
            productionProcedureScan.setNum(num == null ? procedure.getNum() : num);
            productionProcedureScan.setOrderNo(orderNo);
            productionProcedureScan.setProcedureId(productProcedure.getId());
            productionProcedureScan.setCreateTime(new Date());
            productionProcedureScan.setIsDelete(0);
            productionProcedureScan.setProcedureName(productProcedure.getProcedureName());
            productionProcedureScan.setPrice(productProcedure.getPrice());
            productionProcedureScan.setLuoId(louId);
            productionProcedureScan.setPmOutBillNo(billOutNo);
            productionProcedureScan.setProductNo(productNo);
            productionProcedureScan.setProductionNo(productionNo);
            productionProcedureScan.setMoney(BigDecimal.valueOf(BigDecimalUtil.mul(Double.valueOf(num == null ? procedure.getNum() : num), productProcedure.getPrice().doubleValue())));
            productionProcedureScan.setStatus(0);

            productionProcedureScanMapper.insert(productionProcedureScan);
        }
        //获取订单所属车间
        String deptName = null;
        if (productionNo != null) {
            ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
            if (productionOrder != null) deptName = productionOrder.getWorkshop();
        }

        if (billOutNo != null) {
            PmOutBill pmOutBill = pmOutService.getByBillNo(billOutNo);
            if (pmOutBill != null) deptName = pmOutBill.getWorkshop();

        }
        wxEmpVo.setDeptName(deptName);
        if (NOTCONFIRM_DEPT.contains(deptName)) {
            //无需质检的生成质检信息
            productionProcedureConfirmService.add(null, orderNo, louId, billOutNo, productNo, productionNo, wxEmpVo, false);
        }

        LOGGER.info("订单号：" + orderNo + "，型号：" + productNo + "确认入库完成！！！新增" + row + "条记录");
        return row;
    }

    public List<ProductionProcedureScan> select(String orderNo, String productNo, String productionNo, Integer louId, String billOutNo, Integer procedureId) {
        return productionProcedureScanMapper.select(orderNo, productNo, productionNo, louId, billOutNo, procedureId);
    }

    public Integer updateStatue(ProductionProcedureScan t) {
        return productionProcedureScanMapper.updateSatue(t.getId(), t.getStatus());
    }

    public Boolean selectLastMonthIsPass(ProductionProcedureConfirm productionProcedureConfirm) {

        ProductionProcedureScan productionProcedureScan = productionProcedureScanMapper.selectLastMonth(BeanUtil.convert(productionProcedureConfirm, ProductionProcedureScan.class));
        if (null == productionProcedureScan || productionProcedureScan.getStatus() == 0) {
            return false;
        } else if (productionProcedureScan.getStatus() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 扫描数
     *
     * @param orderNo
     * @param procedureName
     * @param productNo
     * @return
     */
    public Integer getHandleNum(String orderNo, String procedureName, String productNo) {
        return productionProcedureScanMapper.getHandleNum(orderNo, procedureName, productNo);

    }

    public List<ProcessDetail> getByProcduct(List<Integer> ids, String orderNo, String product) {
        return productionProcedureScanMapper.getByProcduct(ids, orderNo, product);
    }
}
