package com.btjf.controller.productionorder;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.btjf.application.components.xaresult.AppXaResultHelper;
import com.btjf.application.util.XaResult;
import com.btjf.common.page.Page;
import com.btjf.common.utils.DateUtil;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.controller.productionorder.vo.BatchAssignVo;
import com.btjf.controller.productionorder.vo.ProductionOrderDetailVo;
import com.btjf.controller.productionorder.vo.ProductionOrderVo;
import com.btjf.model.order.*;
import com.btjf.model.sys.SysUser;
import com.btjf.service.order.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heige.aikajinrong.base.exception.BusinessException;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyq on 2019/8/8.
 * <p>
 * 生产单
 */
@RestController
@RequestMapping(value = "/productionOrder")
@Api(value = "ProductionOrderController", description = "生产单管理", position = 1)
public class ProductionOrderController extends ProductBaseController {

    @Resource
    private OrderProductService orderProductService;

    @Resource
    private ProductionOrderService productionOrderService;

    @Resource
    private ProductionProcedureService productionProcedureService;

    @Resource
    private ProductionLuoService productionLuoService;

    @Resource
    private ProductionProcedureScanService productionProcedureScanService;

    @Resource
    private BillNoService billNoService;

    @Resource
    private MultipleProductionService multipleProductionService;

    private static final Logger LOGGER = Logger
            .getLogger(ProductionOrderController.class);


    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public synchronized XaResult<String> assign(Integer orderProductId, Integer assignNum, String workshop, String workshopDirector,
                                                Integer isLuo, Integer luoNum, String procedure) throws BusinessException {

        if (orderProductId == null) return XaResult.error("订单型号id不能为null");
        OrderProduct orderProduct = orderProductService.getByID(orderProductId);
        if (null == orderProduct) return XaResult.error("订单不存在");
        if (null == assignNum) return XaResult.error("请输入分配数额");
        if (null == isLuo || (isLuo != 1 && isLuo != 0)) return XaResult.error("请选择是否分萝");
        if (isLuo == 1 && (null == luoNum || luoNum < 0)) return XaResult.error("请输入一萝数量");

        //if (isLuo == 1 && (orderProduct.getNotAssignNum() < luoNum)) return XaResult.error("可分配数量小于一萝数量，无法分萝处理");
        List<WorkShopVo.Procedure> procedures = Lists.newArrayList();
        if (!StringUtils.isEmpty(procedure)) {
            procedures = JSONObject.parseArray(procedure, WorkShopVo.Procedure.class);
        }
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setIsDelete(0);
        productionOrder.setOrderProductId(orderProductId);
        productionOrder.setCreateTime(new Date());
        productionOrder.setOrderNo(orderProduct.getOrderNo());
        productionOrder.setAssignNum(assignNum);
        productionOrder.setLastModifyTime(new Date());
        productionOrder.setIsLuo(isLuo);
        productionOrder.setLuoNum(luoNum);
        productionOrder.setOrderId(orderProduct.getOrderId());
        productionOrder.setProductNo(orderProduct.getProductNo());
        productionOrder.setMaxNum(orderProduct.getMaxNum());
        productionOrder.setWorkshop(workshop);
        productionOrder.setWorkshopDirector(workshopDirector);
        productionOrder.setProductionNo("P" + billNoService.getNo(4));

        Integer id = productionOrderService.assign(productionOrder, procedures);
        return XaResult.success(productionOrder.getProductionNo());
    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<ProductionOrderDetailVo> detail(String productionNo) {
        if (productionNo == null) return XaResult.error("请输入生产单编号");

        ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
        if (null == productionOrder) return XaResult.error("生产单不存在");

        OrderProduct orderProduct = orderProductService.getByID(productionOrder.getOrderId());

        //工序
        List<ProductionProcedure> productionProcedures = productionProcedureService.findByProductionNo(productionOrder.getProductionNo());

        ProductionOrderDetailVo productionOrderDetailVo = new ProductionOrderDetailVo(productionOrder, productionProcedures, orderProduct);
        return XaResult.success(productionOrderDetailVo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<ProductionOrderVo>> getList(String orderNo, String productionNo, String customerName, String productNo,
                                                     String productType, String workshop, String workshopDirector, String createStartTime,
                                                     String createEndTime, Integer pageSize, Integer currentPage) throws BusinessException {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        if (!StringUtils.isEmpty(productionNo)) {
            productionNo = productionNo.trim();
        }
        if (!StringUtils.isEmpty(orderNo)) {
            orderNo = orderNo.trim();
        }
        Page page = new Page(pageSize, currentPage);
        ProductionOrderVo productionOrderVo = new ProductionOrderVo();
        productionOrderVo.setOrderNo(orderNo);
        productionOrderVo.setProductionNo(productionNo);
        productionOrderVo.setCustomerName(customerName);
        productionOrderVo.setProductNo(productNo);
        productionOrderVo.setProductType(productType);
        productionOrderVo.setWorkshop(workshop);
        productionOrderVo.setWorkshopDirector(workshopDirector);
        productionOrderVo.setCreateEndTime(createEndTime);
        productionOrderVo.setCreateStartTime(createStartTime);
        Page<ProductionOrderVo> productionOrderVoPage = productionOrderService.getPage(productionOrderVo, page);
        XaResult<List<ProductionOrderVo>> result = AppXaResultHelper.success(productionOrderVoPage, productionOrderVoPage.getRows());
        return result;
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public XaResult getDetailByProductionNo(String productionNo) throws BusinessException {
        SysUser sysUser = getLoginUser();

        if (null == productionNo) return XaResult.error("请输入要打印是生成单号");

        productionNo = productionNo.trim();


        ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
        if (productionOrder == null) return XaResult.error("该生产单不存在");
        Map<String, Object> map = Maps.newHashMap();
        if (productionOrder.getType() == 2) {
            BatchAssignVo batchAssignVo = this.getBatchDetail(productionOrder, sysUser);
            map.put("batchAssignVo", batchAssignVo);
            map.put("type", 2);
        } else {
            List<ProductionOrderDetailVo> productionOrderDetailVos = Lists.newArrayList();
            //订单
            OrderProduct orderProduct = orderProductService.getByID(productionOrder.getOrderProductId());
            //工序
            List<ProductionProcedure> productionProcedures = productionProcedureService.findByProductionNo(productionOrder.getProductionNo());

            ProductionOrderDetailVo productionOrderDetailVo = new ProductionOrderDetailVo(productionOrder, productionProcedures, orderProduct);
            productionOrderDetailVo.setPrintTime(DateUtil.dateToString(new Date(), DateUtil.ymdFormat));
            productionOrderDetailVo.setPrinter(sysUser.getUserName());
            //未分
            if (productionOrder.getIsLuo() == 1) {
                List<ProductionLuo> productionLuos = productionLuoService.getByProductionNo(productionOrder.getProductionNo());
                if (!CollectionUtils.isEmpty(productionLuos)) {
                    productionLuos.stream().forEach(t -> {
                        try {
                            ProductionOrderDetailVo productionOrderDetailVo1 = (ProductionOrderDetailVo) BeanUtils.cloneBean(productionOrderDetailVo);
                            productionOrderDetailVo1.setAssignNum(t.getNum());
                            productionOrderDetailVo1.setCodeUrl(t.getCodeUrl());
                            productionOrderDetailVo1.setNum(orderProduct.getNum());
                            productionOrderDetailVo1.setUnit(orderProduct.getUnit());
                            productionOrderDetailVos.add(productionOrderDetailVo1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                productionOrderDetailVos.add(productionOrderDetailVo);
            }
            map.put("productionOrderDetailVos", productionOrderDetailVos);
            map.put("type", 1);
        }
        XaResult xaResult = XaResult.success();
        xaResult.setMap(map);
        return xaResult;
    }


    @RequestMapping(value = "/addPrintCount", method = RequestMethod.POST)
    public XaResult addPrintCount(String productionNo) {
        if (productionNo == null) return XaResult.error("请输入生成单号");
        ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
        if (null == productionOrder) return XaResult.error("生成单不存在");
        productionOrder.setPrintCount(productionOrder.getPrintCount() + 1);
        productionOrderService.update(productionOrder);
        return XaResult.success();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response, String orderNo, String productionNo, String customerName, String productNo,
                       String productType, String workshop, String workshopDirector, String createStartTime,
                       String createEndTime) {
        ProductionOrderVo productionOrderVo = new ProductionOrderVo();
        productionOrderVo.setOrderNo(orderNo);
        productionOrderVo.setProductionNo(productionNo);
        productionOrderVo.setCustomerName(customerName);
        productionOrderVo.setProductNo(productNo);
        productionOrderVo.setProductType(productType);
        productionOrderVo.setWorkshop(workshop);
        productionOrderVo.setWorkshopDirector(workshopDirector);
        productionOrderVo.setCreateEndTime(createEndTime);
        productionOrderVo.setCreateStartTime(createStartTime);
        List<ProductionOrderVo> productionOrderVoPage = productionOrderService.getList(productionOrderVo);


        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(2, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(3, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(5, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(6, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(7, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(9, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(10, (int) ((15 + 0.72) * 256));
        int j = 0;

        header.createCell(j++).setCellValue("订单号");
        header.createCell(j++).setCellValue("生产编号");
        header.createCell(j++).setCellValue("订购客户");
        header.createCell(j++).setCellValue("产品型号");
        header.createCell(j++).setCellValue("数量");
        header.createCell(j++).setCellValue("单位");
        header.createCell(j++).setCellValue("上限数量");
        header.createCell(j++).setCellValue("产品类别");
        header.createCell(j++).setCellValue("负责车间");
        header.createCell(j++).setCellValue("车间主任");
        header.createCell(j++).setCellValue("创建日期");
        ProductionOrderVo productionOrderVo1 = null;
        if (productionOrderVoPage != null && productionOrderVoPage.size() >= 1) {
            for (int i = 0; i < productionOrderVoPage.size(); i++) {
                productionOrderVo1 = productionOrderVoPage.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(productionOrderVo1.getOrderNo());
                row.createCell(j++).setCellValue(productionOrderVo1.getProductionNo());
                row.createCell(j++).setCellValue(productionOrderVo1.getCustomerName());
                row.createCell(j++).setCellValue(productionOrderVo1.getProductNo());
                row.createCell(j++).setCellValue(productionOrderVo1.getAssignNum());
                row.createCell(j++).setCellValue(productionOrderVo1.getUnit());
                row.createCell(j++).setCellValue(productionOrderVo1.getMaxNum());
                row.createCell(j++).setCellValue(productionOrderVo1.getProductType());
                row.createCell(j++).setCellValue(productionOrderVo1.getWorkshop());
                row.createCell(j++).setCellValue(productionOrderVo1.getWorkshopDirector());
                row.createCell(j++).setCellValue(productionOrderVo1.getCreateTime());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("生产单.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("生产单导出excel异常");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public XaResult<Integer> delete(String productionNo, String orderNo) throws BusinessException {
        if (StringUtils.isEmpty(productionNo) || StringUtils.isEmpty(orderNo)) {
            return XaResult.error("请选择要删除的生产单");
        }
        if (!CollectionUtils.isEmpty(productionProcedureScanService.select(orderNo, null, productionNo, null, null, null))) {
            return XaResult.error("该生成单已经有员工扫描操作，无法删除！！！");
        }

        Integer row = productionOrderService.delete(productionNo, orderNo);
        return XaResult.success(row);
    }

    @RequestMapping(value = "/batchAssign", method = RequestMethod.POST)
    public synchronized XaResult<String> batchAssign(String batchAssign) {
        if (StringUtils.isEmpty(batchAssign)) {
            return XaResult.error("分配为空");
        }

        BatchAssignVo batchAssignVo = JSONObject.parseObject(batchAssign, BatchAssignVo.class);

        //生产单
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setIsDelete(0);
        // productionOrder.setOrderProductId(orderProductId);
        productionOrder.setCreateTime(new Date());
        // productionOrder.setOrderNo(orderProduct.getOrderNo());
        //productionOrder.setAssignNum(assignNum);
        productionOrder.setLastModifyTime(new Date());

        productionOrder.setWorkshop(batchAssignVo.getWorkshop());
        productionOrder.setIsLuo(0);
        productionOrder.setWorkshopDirector(batchAssignVo.getWorkshopDirector());
        productionOrder.setProductionNo("P" + billNoService.getNo(4));


        productionOrderService.batchAssign(productionOrder, batchAssignVo);
        return XaResult.success(productionOrder.getProductionNo());
    }


    @RequestMapping(value = "batchAssign/detail", method = RequestMethod.GET)
    public XaResult<BatchAssignVo> batchAssignDetail(String productionNo) {

        SysUser sysUser = getLoginUser();

        if (StringUtils.isEmpty(productionNo)) {
            return XaResult.error("请输入生产单号");
        }

        ProductionOrder productionOrder = productionOrderService.getByNo(productionNo);
        if (productionOrder == null || productionOrder.getType() != 2) return XaResult.error("生产单编号错误");

        BatchAssignVo batchAssignVo = getBatchDetail(productionOrder, sysUser);

        return XaResult.success(batchAssignVo);
    }

    public BatchAssignVo getBatchDetail(ProductionOrder productionOrder, SysUser sysUser) {

        BatchAssignVo batchAssignVo = new BatchAssignVo();
        batchAssignVo.setCodeUrl(productionOrder.getCodeUrl());
        batchAssignVo.setPrintCount(productionOrder.getPrintCount() == 0 ? 1 : productionOrder.getPrintCount() + 1);
        batchAssignVo.setPrinter(sysUser.getLoginName());
        batchAssignVo.setPrintTime(DateUtil.dateToString(new Date(), DateUtil.ymdFormat));
        batchAssignVo.setWorkshop(productionOrder.getWorkshop());
        batchAssignVo.setWorkshopDirector(productionOrder.getWorkshopDirector());
        batchAssignVo.setProductionNo(productionOrder.getProductionNo());

        List<MultipleProduction> multipleProductions = multipleProductionService.getByProductionNo(productionOrder.getProductionNo());
        if (CollectionUtils.isEmpty(multipleProductions)) return batchAssignVo;

        List<BatchAssignVo.BatchAssignOrder> batchAssignOrders = Lists.newArrayList();
        multipleProductions.stream()
                .filter(t -> t != null)
                .forEach(t -> {

                    BatchAssignVo.BatchAssignOrder batchAssignOrder = new BatchAssignVo.BatchAssignOrder();
                    batchAssignOrder.setOrderNo(t.getOrderNo());
                    batchAssignOrder.setProductNo(t.getProductNo());

                    List<WorkShopVo.Procedure> procedures = WorkShopVo.Procedure.productionProcedureTransfor(
                            productionProcedureService.getByMultipleCantenZJ(t.getId()));
                    batchAssignOrder.setProcedures(procedures);

                    batchAssignOrders.add(batchAssignOrder);

                });
        batchAssignVo.setBatchAssignOrders(batchAssignOrders);
        return batchAssignVo;
    }
}

