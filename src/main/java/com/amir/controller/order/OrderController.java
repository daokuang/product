package com.amir.controller.order;

import com.alibaba.druid.util.StringUtils;
import com.amir.controller.base.ProductBaseController;
import com.amir.controller.order.vo.OrderProductVo;
import com.amir.controller.order.vo.OrderVo;
import com.amir.controller.order.vo.ProcessDetail;
import com.amir.exception.BusinessException;
import com.amir.model.AppXaResultHelper;
import com.amir.model.Page;
import com.amir.model.XaResult;
import com.amir.model.order.Order;
import com.amir.model.order.OrderProduct;
import com.amir.model.product.ProductProcedureWorkshop;
import com.amir.model.sys.SysUser;
import com.amir.service.order.OrderProductService;
import com.amir.service.order.OrderService;
import com.amir.service.order.ProductionProcedureConfirmService;
import com.amir.service.order.ProductionProcedureService;
import com.amir.service.productpm.ProductWorkshopService;
import com.amir.util.BigDecimalUtil;
import com.amir.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by liuyq on 2019/8/4.
 */
@RestController
@RequestMapping(value = "/order")
@Api(value = "OrderController", description = "订单管理", position = 1)
public class OrderController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(OrderController.class);
    @Resource
    private OrderService orderService;
    @Resource
    private OrderProductService orderProductService;
    @Resource
    private ProductWorkshopService productWorkshopService;
    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;
    @Resource
    private ProductionProcedureService productionProcedureService;

    @Transactional
    @RequestMapping(value = "/updateOrAdd", method = RequestMethod.POST)
    public XaResult<Integer> updateOrAdd(Integer id, String orderNo, String productNo, Integer num,
                                         String type, String unit, Integer maxNum, String completeDate,
                                         String customerName, Integer customerId, Integer isMore, Integer urgentLevel, String remark) {

        SysUser sysUser = getLoginUser();
        if (StringUtils.isEmpty(orderNo)) {
            return XaResult.error("订单No为空");
        }

        if (StringUtils.isEmpty(productNo)) {
            return XaResult.error("产品编号为空");
        }

        if (num == null) {
            return XaResult.error("数量为空");
        }
        if (num <= 0) {
            return XaResult.error("数量有误");
        }

        if (StringUtils.isEmpty(unit)) {
            return XaResult.error("单位为空");
        }
        if (StringUtils.isEmpty(type)) {
            return XaResult.error("类型为空");
        }
        if (maxNum == null) {
            return XaResult.error("上限数量为空");
        }

        if (maxNum < num) {
            return XaResult.error("上限数量有误");
        }
        if (customerId == null) {
            return XaResult.error("客户信息有误");
        }

        if (completeDate == null) {
            return XaResult.error("完成时间为空");
        }

        Integer orderID = null;
        OrderProduct orderProduct1 = new OrderProduct(null, orderNo, null,
                productNo, type, num, maxNum, unit, DateUtil.string2Date(completeDate, DateUtil.ymdFormat), customerId, customerName, null, null,
                null, null, null, new Date(), new Date(), 0);
        orderProduct1.setRemark(remark);
        if (id != null) {
            return XaResult.error("暂时不支持更新");
        } else {
            OrderProduct orderProduct = orderProductService.getByOrderNoAndProductNo(orderNo, productNo);
            if (null != orderProduct) {
                return XaResult.error("该型号的订单已经存在");
            }
            orderProduct1.setNotAssignNum(maxNum);
            orderProduct1.setAssignedNum(0);
            orderProduct1.setIsMore(isMore);
            orderProduct1.setUrgentLevel(urgentLevel);
            orderID = orderProductService.insert(orderProduct1, sysUser.getUserName());


        }
        return XaResult.success(orderID);

    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public XaResult<List<OrderVo>> list(String orderNo, String pmNo, String type, Integer customerId, String completeStartDate,
                                        String completeStartEnd, String createStartDate, String createEndDate, Integer pageSize, Integer currentPage) {

        LOGGER.info(getRequestParamsAndUrl());
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);

        if (!StringUtils.isEmpty(orderNo)) {
            orderNo = orderNo.trim();
        }
        if (!StringUtils.isEmpty(pmNo)) {
            pmNo = pmNo.trim();
        }

        Page<OrderVo> listPage = orderProductService.listPage(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate, page);
        List<OrderVo> list = listPage.getRows();
        XaResult<List<OrderVo>> result = AppXaResultHelper.success(listPage, list);
        Map<String, Integer> cuntMap = orderProductService.getCount(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate);
        Map map = Maps.newHashMap();
        map.put("orderNum", (int) listPage.getTotal());
        if (!MapUtils.isEmpty(cuntMap)) {
            map.put("bNum", Optional.ofNullable(cuntMap.get("靶类")).orElse(0));
            map.put("dstNum", Optional.ofNullable(cuntMap.get("大手套类")).orElse(0));
            map.put("xstNum", Optional.ofNullable(cuntMap.get("小手套类")).orElse(0));
            map.put("otherNum", Optional.ofNullable(cuntMap.get("其他类")).orElse(0));
            map.put("hjnNum", Optional.ofNullable(cuntMap.get("护具类")).orElse(0));
            map.put("gdstNum", Optional.ofNullable(cuntMap.get("格斗手套")).orElse(0));
            map.put("productNum", Optional.ofNullable(cuntMap.get("生产数")).orElse(0));
        }
        result.setMap(map);
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public XaResult<Integer> delete(String[] orderIds) throws BusinessException {
        if (null == orderIds || Arrays.asList(orderIds).size() <= 0) {
            return XaResult.error("请选择要删除的记录");
        }
        Integer num = orderService.delete(Arrays.asList(orderIds));
        return XaResult.success(num);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<OrderProduct> detail(Integer id) {
        if (id == null) return XaResult.error("id必传");

        OrderProduct orderProduct = orderProductService.getByID(id);
        if (null == orderProduct) return XaResult.error("订单不存在");
        orderProduct.setCompleteDateStr(DateUtil.dateToString(orderProduct.getCompleteDate(), DateUtil.ymdFormat));
        return XaResult.success(orderProduct);
    }


    /**
     * 获取所有未分配订单
     *
     * @return
     */
    @RequestMapping(value = "/notAssignOrder", method = RequestMethod.GET)
    public XaResult<List<Order>> orderProductVos(String orderNo) {
        List<Order> orders = orderService.notAssignOrder(orderNo);
        return XaResult.success(orders);
    }

    @RequestMapping(value = "/getOrderProductAndProcedure", method = RequestMethod.GET)
    public XaResult<List<OrderProductVo>> getOrderProductAndProcedure(Integer orderId) {
        if (orderId == null) XaResult.error("请输入订单id");

        List<OrderProduct> products = orderProductService.findByOrderId(orderId);
        List<OrderProductVo> productVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(products)) {
            products.stream().filter(t -> t != null).forEach(t -> {
                List<ProductProcedureWorkshop> productProcedureWorkshops =
                        productWorkshopService.getWorkShop(t.getProductNo(), 1);
                //todo 后续要返回工序数量
               /* productProcedureWorkshops.forEach(productProcedureWorkshop -> {
                    productProcedureWorkshop.setNum(productionProcedureService.
                            procedureCanAssignNum(t.getOrderNo(), t.getProductNo(), productProcedureWorkshop.getProcedureId()));
                });*/


                OrderProductVo orderProductVo = new OrderProductVo(t, productProcedureWorkshops);
                productVos.add(orderProductVo);
            });
        }
        return XaResult.success(productVos);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(String orderNo, String pmNo, String type, Integer customerId, String completeStartDate,
                       String completeStartEnd, String createStartDate, String createEndDate, HttpServletResponse response) {

        List<OrderVo> orderVos = orderProductService.list(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate);

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
        int j = 0;
        header.createCell(j++).setCellValue("订购客户");
        header.createCell(j++).setCellValue("订单号");
        header.createCell(j++).setCellValue("产品型号");
        header.createCell(j++).setCellValue("数量");
        header.createCell(j++).setCellValue("单位");
        header.createCell(j++).setCellValue("上限数量");
        header.createCell(j++).setCellValue("产品类别");
        header.createCell(j++).setCellValue("下达日期");
        header.createCell(j++).setCellValue("计划出厂");
        OrderVo orderVo = null;
        if (orderVos != null && orderVos.size() >= 1) {
            for (int i = 0; i < orderVos.size(); i++) {
                orderVo = orderVos.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(orderVo.getCustomerName());
                row.createCell(j++).setCellValue(orderVo.getOrderNo());
                row.createCell(j++).setCellValue(orderVo.getProductNo());
                row.createCell(j++).setCellValue(orderVo.getNum());
                row.createCell(j++).setCellValue(orderVo.getUnit());
                row.createCell(j++).setCellValue(orderVo.getMaxNum());
                row.createCell(j++).setCellValue(orderVo.getType());
                row.createCell(j++).setCellValue(orderVo.getCreateTime());
                row.createCell(j++).setCellValue(orderVo.getCompleteDate());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("订单.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("订单导出excel异常");
        }
    }

    /**
     * 订单工序详细列表
     *
     * @param workSpace
     * @param orderNo
     * @param productNo
     * @return
     */
    @RequestMapping(value = "/getProcessDetail", method = RequestMethod.GET)
    public XaResult<List<ProcessDetail>> getProcessDetail(String workSpace, String orderNo, String productNo) {
        if (StringUtils.isEmpty(workSpace) || StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(productNo))
            return XaResult.error("参数不全");
        if (workSpace.equals("前道车间-复面") || workSpace.equals("前道车间-质检")) {
            workSpace = "一车间";
        }
        OrderProduct orderProduct = orderProductService.getByOrderNoAndProductNo(orderNo, productNo);
        List<ProcessDetail> processDetails = productionProcedureConfirmService.getCompleteNum(workSpace, orderNo, productNo);
        processDetails.forEach(t -> {
            t.setPencent(BigDecimalUtil.div(t.getNum(), Double.valueOf(orderProduct.getMaxNum())) * 100);
        });

        return XaResult.success(processDetails);
    }

}
