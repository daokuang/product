package com.btjf.controller.weixin;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.btjf.application.util.XaResult;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.utils.DateUtil;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.controller.weixin.vo.WorkListVo;
import com.btjf.controller.weixin.vo.WxEmpVo;
import com.btjf.model.order.*;
import com.btjf.model.pm.PmOutBill;
import com.btjf.service.order.*;
import com.btjf.service.pm.PmOutService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.btjf.service.sys.ShortUrlService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.json4s.DefaultWriters;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyq on 2019/8/19.
 */
@RestController
@Api(value = "WorkController", description = "小程序 工作模块", position = 1)
@RequestMapping(value = "/wx/work")
public class WorkController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(WorkController.class);

    @Resource
    private ProductWorkshopService productWorkshopService;

    @Resource
    private ProductionProcedureService productionProcedureService;

    @Resource
    private ProductionOrderService productionOrderService;

    @Resource
    private PmOutService pmOutService;

    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;

    @Resource
    private ProductionProcedureScanService productionProcedureScanService;

    @Resource
    private ProductionLuoService productionLuoService;

    @Resource
    private OrderProductService orderProductService;

    @Resource
    private ShortUrlService shortUrlService;

    private static final List<String> NOTCONFIRM_DEPT = Arrays.asList("后道车间-中辅工", "后道车间-车工", "后道车间-小辅工", "包装车间", "外协质检", "质检部-成品质检", "仓库");

    @Resource
    private MultipleProductionService multipleProductionService;


    @RequestMapping(value = "getConfirmList", method = RequestMethod.GET)
    public XaResult<List<WorkListVo>> getConfigList(@ApiParam("订单id") Integer orderId, @ApiParam("订单编号") String orderNo,
                                                    @ApiParam("产品编号") String productNo, @ApiParam("生产单编号") String productionNo,
                                                    @ApiParam("罗id") Integer louId, @ApiParam("领料单编号") String billNo) throws BusinessException {
        WxEmpVo wxEmpVo = getWxLoginUser();
        if (orderId == null || orderNo == null || productNo == null) return XaResult.error("无效二维码");


        List<WorkListVo> result = Lists.newArrayList();
        WorkListVo workListVo = new WorkListVo();
        workListVo.setOrderId(orderId);
        workListVo.setOrderNo(orderNo);
        workListVo.setProductNo(productNo);
        String deptName = wxEmpVo.getDeptName();

        OrderProduct orderProduct = orderProductService.getByID(orderId);
        if (orderProduct == null) return XaResult.error("订单不存在");
        Integer assignNum = 0;
        String unit = orderProduct.getUnit();
        //生产单
        if (!StringUtils.isEmpty(productionNo)) {
            ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
            if (productionOrder == null) {
                LOGGER.info(wxEmpVo.getName() + "扫码生产单:" + productionNo + "没有您所需处理的工序。(如有疑问，请咨询客服");
                return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
            }
            if (!deptName.equals(productionOrder.getWorkshop())) {
                LOGGER.info(wxEmpVo.getName() + "扫码生产单:" + productionNo + "没有您所需处理的工序。(如有疑问，请咨询客服)");
                return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
            }
            assignNum = productionOrder.getAssignNum();
            List<WorkShopVo.Procedure> list = productionProcedureService.getConfigProcedure(null, productionNo);
            if (louId != null) {
                ProductionLuo productionLuo = productionLuoService.getById(louId);
                if (productionLuo != null) {
                    assignNum = productionLuo.getNum();
                }
            }
            workListVo.setLouId(louId);
            workListVo.setProcedures(list);
            workListVo.setProductionNo(productionNo);
            //领料单
        } else if (!StringUtils.isEmpty(billNo)) {
            PmOutBill bill = pmOutService.getByBillNo(billNo);
            if (bill == null) {
                LOGGER.info(wxEmpVo.getName() + "扫码领料单:" + billNo + "没有您所需处理的工序。(如有疑问，请咨询客服");
                return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
            }
            if (!deptName.equals(bill.getWorkshop())) {
                LOGGER.info(wxEmpVo.getName() + "扫码领料单:" + billNo + "没有您所需处理的工序。(如有疑问，请咨询客服)");
                return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
            }
            assignNum = bill.getDistributionNum();
            List<WorkShopVo.Procedure> list = productWorkshopService.getBySort(productNo, Arrays.asList(0, 1, 2, 3));
            workListVo.setProcedures(list);
            workListVo.setBillNo(billNo);
        } else {
            return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
        }
        result.add(workListVo);
        XaResult results = XaResult.success(result);
        if (wxEmpVo.getWorkName().equals("检验")) {
            if (NOTCONFIRM_DEPT.contains(wxEmpVo.getDeptName())) {
                return XaResult.error(wxEmpVo.getDeptName() + "默认无需质检");
            }
            Map map = Maps.newHashMap();
            map.put("assignNum", assignNum);
            map.put("unit", unit);
            results.setMap(map);
        }
        return results;
    }

    @RequestMapping(value = "/checkConfirm", method = RequestMethod.GET)
    public XaResult<String> checkConfirm(@ApiParam("订单id") Integer orderId, @ApiParam("订单编号") String orderNo,
                                         @ApiParam("产品编号") String productNo, @ApiParam("生产单编号") String productionNo,
                                         @ApiParam("罗id") Integer louId, @ApiParam("领料单编号") String billOutNo, String proceduresJosn) {
        if (orderId == null || orderNo == null || productNo == null || StringUtils.isEmpty(proceduresJosn))
            return XaResult.error("orderId，orderNo， productNo，proceduresJosn必填");

        List<WorkShopVo.Procedure> procedures = JSONObject.parseArray(proceduresJosn, WorkShopVo.Procedure.class);
        if ((StringUtils.isEmpty(productionNo) && StringUtils.isEmpty(billOutNo)) || (!StringUtils.isEmpty(productionNo) && !StringUtils.isEmpty(billOutNo)))
            throw new com.btjf.business.common.exception.BusinessException("生成单和领料单不能存在，且不能同时未空");
        StringBuffer stringBuffer = new StringBuffer();

        for (WorkShopVo.Procedure procedure : procedures) {
            if (procedure == null) continue;
            if (!CollectionUtils.isEmpty(productionProcedureScanService.select(orderNo, productNo, productionNo, louId, billOutNo, procedure.getProcedureId()))) {
                stringBuffer.append(procedure.getProcedureName() + "、");
            }
        }
        if (StringUtils.isEmpty(stringBuffer)) return XaResult.success();
        String result = stringBuffer.toString();
        if (result.length() - 1 == result.lastIndexOf("、")) {
            result = result.substring(0, result.length() - 1);
        }
        return XaResult.success(result);
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    public synchronized XaResult confirm(@ApiParam("订单id") Integer orderId, @ApiParam("订单编号") String orderNo,
                                         @ApiParam("产品编号") String productNo, @ApiParam("生产单编号") String productionNo,
                                         @ApiParam("罗id") Integer louId, @ApiParam("领料单编号") String billOutNo, String proceduresJosn) throws BusinessException {

        if (orderId == null || orderNo == null || productNo == null || StringUtils.isEmpty(proceduresJosn))
            return XaResult.error("orderId，orderNo， productNo，proceduresJosn必填");

        List<WorkShopVo.Procedure> procedures = JSONObject.parseArray(proceduresJosn, WorkShopVo.Procedure.class);

        WxEmpVo wxEmpVo = getWxLoginUser();
        if (wxEmpVo.getWorkName().equals("检验")) {
            return XaResult.error("您无权限确认工序");
        }
        //生产单
        if (!StringUtils.isEmpty(productionNo)) {
            try {
                checkComfig(orderNo, productNo, productionNo, louId, billOutNo, procedures);
            } catch (BusinessException e) {
                LOGGER.info(wxEmpVo.getName() + "扫码生成单:" + productionNo + "无效的二维码");
                throw new BusinessException("生成单：" + productionNo + "中的" + e.getMessage());
            }
            //领料单
        } else if (!StringUtils.isEmpty(billOutNo)) {
            try {
                checkComfig(orderNo, productNo, productionNo, louId, billOutNo, procedures);
            } catch (BusinessException e) {
                throw new BusinessException("领料单：" + billOutNo + "中的" + e.getMessage());
            }
        }
        Integer num = productionProcedureScanService.deleteAndInsert(orderNo, productNo, productionNo, louId, billOutNo, procedures, wxEmpVo, NOTCONFIRM_DEPT);

        return XaResult.success(num);
    }


    public void checkComfig(String orderNo, String productNo, String productionNo,
                            Integer louId, String billOutNo, List<WorkShopVo.Procedure> procedures) throws BusinessException {

        ProductionProcedureConfirm productionProcedureConfirm = new ProductionProcedureConfirm();
        productionProcedureConfirm.setOrderNo(orderNo);
        productionProcedureConfirm.setProductNo(productNo);
        productionProcedureConfirm.setType(1);
        if (!StringUtils.isEmpty(productionNo)) {
            productionProcedureConfirm.setProductionNo(productionNo);
            if (louId != null) {
                productionProcedureConfirm.setLuoId(louId);
            }
        }
        if (!StringUtils.isEmpty(billOutNo)) {
            productionProcedureConfirm.setPmOutBillNo(billOutNo);
        }

        for (WorkShopVo.Procedure procedure : procedures) {
            productionProcedureConfirm.setProcedureId(procedure.getProcedureId());
            productionProcedureConfirm.setIsChange(1);
            //是否调整
            if (!CollectionUtils.isEmpty(productionProcedureConfirmService.select(productionProcedureConfirm))) {
                throw new BusinessException("工序：(" + procedure.getProcedureName() + ")车间主任已经调整过了，无法再确认该工序");
            }
            //未调整   工序上个月最新一条有质检信息
            productionProcedureConfirm.setCreateTime(DateUtil.string2Date(DateUtil.dateToString(new Date(), DateUtil.ymdFormat), DateUtil.ymdFormat));
            productionProcedureConfirm.setIsChange(0);
            if (productionProcedureScanService.selectLastMonthIsPass(productionProcedureConfirm)) {
                throw new BusinessException("工序：(" + procedure.getProcedureName() + ")上月已经质检过，无法再确认该工序");
            }
        }

    }

    @RequestMapping(value = "inspectionConfirm", method = RequestMethod.POST)
    public synchronized XaResult inspectionConfig(@ApiParam("订单id") Integer orderId, @ApiParam("订单编号") String orderNo,
                                                  @ApiParam("产品编号") String productNo, @ApiParam("生产单编号") String productionNo,
                                                  @ApiParam("罗id") Integer louId, @ApiParam("领料单编号") String billOutNo) throws BusinessException {

        WxEmpVo wxEmpVo = getWxLoginUser();

        if (!wxEmpVo.getWorkName().equals("检验")) {
            return XaResult.error("身份错误");
        }

        if (productionNo == null && billOutNo == null) return XaResult.error("生产单号和领料单号不能同时为空");
        if (productionNo != null && billOutNo != null) return XaResult.error("生产单号和领料单号不能同时存在");
        ProductionOrder productionOrder = null;
        if (productionNo != null) {
            productionOrder =productionOrderService.getByNo(productionNo);
            if (productionOrder == null) return XaResult.error("生产单号不存在");
            if (!wxEmpVo.getDeptName().equals(productionOrder.getWorkshop())) return XaResult.error("您无法质检不属于自己部门的单子");
        }

        if (billOutNo != null) {
            PmOutBill pmOutBill = pmOutService.getByBillNo(billOutNo);
            if (pmOutBill == null) return XaResult.error("生产单不存在");
            if (!wxEmpVo.getDeptName().equals(pmOutBill.getWorkshop())) return XaResult.error("您无法质检不属于自己部门的单子");
        }


        ProductionProcedureConfirm productionProcedureConfirm = new ProductionProcedureConfirm();
        productionProcedureConfirm.setOrderNo(orderNo);
        productionProcedureConfirm.setProductNo(productNo);
        productionProcedureConfirm.setType(1);
        productionProcedureConfirm.setIsChange(1);
        productionProcedureConfirm.setIsDelete(0);
        if (!StringUtils.isEmpty(productionNo)) {
            productionProcedureConfirm.setProductionNo(productionNo);
            if(productionOrder.getType() == 2){
                productionProcedureConfirm.setOrderNo(null);
                productionProcedureConfirm.setProductNo(null);
            }
            if (louId != null) {
                productionProcedureConfirm.setLuoId(louId);
            }
        }
        if (!StringUtils.isEmpty(billOutNo)) {
            productionProcedureConfirm.setPmOutBillNo(billOutNo);
        }
        if (!CollectionUtils.isEmpty(productionProcedureConfirmService.select(productionProcedureConfirm))) {
            return XaResult.error("车间主任已经调整过了，无法再质检");
        }
        productionProcedureConfirm.setType(null);
        productionProcedureConfirm.setIsChange(2);
        if (!CollectionUtils.isEmpty(productionProcedureConfirmService.select(productionProcedureConfirm))) {
            return XaResult.error("已结算无法重新质检");
        }
        Integer row = productionProcedureConfirmService.add(orderId, orderNo, louId, billOutNo, productNo, productionNo, wxEmpVo, true);
        return XaResult.success(row);
    }

    @RequestMapping(value = "/getUrl", method = RequestMethod.GET)
    public XaResult<String> getUrl(@ApiParam("短链url") String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) return XaResult.error("短链必传");
        return XaResult.success(shortUrlService.getByShort(shortUrl));
    }

    /**
     * 多型号获取接口
     */

    @RequestMapping(value = "batchGetConfirmList", method = RequestMethod.GET)
    public XaResult<List<WorkListVo>> batchGetConfirmList(@ApiParam("订单id") Integer orderId, @ApiParam("订单编号") String orderNo,
                                                          @ApiParam("产品编号") String productNo, @ApiParam("生产单编号") String productionNo,
                                                          @ApiParam("罗id") Integer louId, @ApiParam("领料单编号") String billNo) throws BusinessException {

        WxEmpVo wxEmpVo = getWxLoginUser();
        if (StringUtils.isEmpty(productionNo)) return XaResult.error("无效二维码");

        List<WorkListVo> workListVos = Lists.newArrayList();

        String deptName = wxEmpVo.getDeptName();

        Integer assignNum = 0;
        String unit = "个";
        //生产单
        ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
        if (productionOrder == null) {
            LOGGER.info(wxEmpVo.getName() + "扫码生产单:" + productionNo + "没有您所需处理的工序。(如有疑问，请咨询客服");
            return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
        }
        if (!deptName.equals(productionOrder.getWorkshop())) {
            LOGGER.info(wxEmpVo.getName() + "扫码生产单:" + productionNo + "没有您所需处理的工序。(如有疑问，请咨询客服)");
            return XaResult.error("没有您所需处理的工序。(如有疑问，请咨询客服)");
        }
        List<MultipleProduction> multipleProductions =
                multipleProductionService.getByProductionNo(productionOrder.getProductionNo());
        if (!CollectionUtils.isEmpty(multipleProductions)) {
            multipleProductions.stream()
                    .filter(t -> t != null)
                    .forEach(t -> {
                        WorkListVo workListVo = new WorkListVo();
                        workListVo.setProductionNo(productionOrder.getProductionNo());
                        workListVo.setOrderId(t.getOrderId());
                        workListVo.setOrderNo(t.getOrderNo());
                        workListVo.setProductNo(t.getProductNo());
                        workListVo.setProcedures(WorkShopVo.Procedure.
                                productionProcedureTransfor(productionProcedureService.getByMultipleId(t.getId())));
                        workListVos.add(workListVo);

                    });

            assignNum = multipleProductions.stream()
                    .mapToInt(m -> m.getFristNum())
                    .sum();
        }

        XaResult result = XaResult.success(workListVos);
        if (wxEmpVo.getWorkName().equals("检验")) {
            if (NOTCONFIRM_DEPT.contains(wxEmpVo.getDeptName())) {
                return XaResult.error(wxEmpVo.getDeptName() + "默认无需质检");
            }
            Map map = Maps.newHashMap();
            map.put("assignNum", assignNum);
            map.put("unit", unit);
            result.setMap(map);
        }
        return result;
    }


    /**
     * 确认交验接口
     *
     * @param WorkListVoJson
     * @return
     */
    @RequestMapping(value = "/batchCheckConfirm", method = RequestMethod.POST)
    public XaResult<String> batchCheckConfirm(String WorkListVoJson) {
        if (StringUtils.isEmpty(WorkListVoJson))
            return XaResult.error("WorkListVoJson必填");

        List<WorkListVo> workListVos = JSONObject.parseArray(WorkListVoJson, WorkListVo.class);
        StringBuffer stringBuffer = new StringBuffer();
        if (!CollectionUtils.isEmpty(workListVos)) {
            workListVos.stream().forEach(t -> {
                List<WorkShopVo.Procedure> procedures = t.getProcedures();
                for (WorkShopVo.Procedure procedure : procedures) {
                    if (procedure == null) continue;
                    if (!CollectionUtils.isEmpty(productionProcedureScanService.select(t.getOrderNo(), t.getProductNo(), t.getProductionNo(),
                            t.getLouId(), t.getBillNo(), procedure.getProcedureId()))) {
                        stringBuffer.append("订单编号：" + t.getOrderNo() + "型号：" + t.getProductNo());
                        stringBuffer.append("工序：" + procedure.getProcedureName() + "、");
                    }
                }
            });

        }
        if (StringUtils.isEmpty(stringBuffer)) return XaResult.success();
        String result = stringBuffer.toString();
        if (result.length() - 1 == result.lastIndexOf("、")) {
            result = result.substring(0, result.length() - 1);
        }
        return XaResult.success(result);
    }

    /**
     * 确认接口
     *
     * @return
     * @throws BusinessException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @RequestMapping(value = "batchConfirm", method = RequestMethod.POST)
    public synchronized XaResult batchConfirm(String WorkListVoJson) throws BusinessException {

        if (StringUtils.isEmpty(WorkListVoJson))
            return XaResult.error("WorkListVoJson必填");

        List<WorkListVo> workListVos = JSONObject.parseArray(WorkListVoJson, WorkListVo.class);

        WxEmpVo wxEmpVo = getWxLoginUser();
        Integer num = 0;
        if (wxEmpVo.getWorkName().equals("检验")) {
            return XaResult.error("您无权限确认工序");
        }
        if (!CollectionUtils.isEmpty(workListVos)) {
            for (WorkListVo workListVo : workListVos) {
                //生产单
                if (!StringUtils.isEmpty(workListVo.getProductionNo())) {
                    try {
                        checkComfig(workListVo.getOrderNo(), workListVo.getProductNo(), workListVo.getProductionNo(), workListVo.getLouId(), workListVo.getBillNo(), workListVo.getProcedures());
                    } catch (BusinessException e) {
                        LOGGER.info(wxEmpVo.getName() + "扫码生成单:" + workListVo.getProductionNo() + "无效的二维码");
                        throw new BusinessException("生成单：" + workListVo.getProductionNo() + "中的" + e.getMessage());
                    }
                }
                num += productionProcedureScanService.deleteAndInsert(workListVo.getOrderNo(), workListVo.getProductNo(),
                        workListVo.getProductionNo(), workListVo.getLouId(), workListVo.getBillNo(), workListVo.getProcedures(), wxEmpVo, NOTCONFIRM_DEPT);
            }
        }

        return XaResult.success(num);
    }
}
