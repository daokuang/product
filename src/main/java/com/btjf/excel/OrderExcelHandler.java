package com.btjf.excel;

import com.alibaba.druid.util.StringUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.utils.DateUtil;
import com.btjf.model.customer.Customer;
import com.btjf.model.order.OrderProduct;
import com.btjf.service.customer.CustomerService;
import com.btjf.service.order.OrderProductService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/9/3.
 */
@Component
public class OrderExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("订单号", "型号", "数量",
            "上限数量", "类别", "单位", "出货日期", "客户", "是否追加", "紧急程度", "备注信息").collect(Collectors.toList());

    @Resource
    private CustomerService customerService;

    @Resource
    private OrderProductService orderProductService;


    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                OrderProduct orderProduct = (OrderProduct) list.get(i);
                if (orderProduct == null) continue;
                orderProductService.insert(orderProduct, operator);
            }
        }
    }

    @Override
    protected List create(XSSFRow row) throws Exception {
        List<OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    String orderNo = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(orderNo)) {
                        throw new BusinessException("订单No为空");
                    }
                    orderProduct.setOrderNo(orderNo);
                    break;
                case 1:
                    String productNo = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(productNo)) {
                        throw new BusinessException("产品编号为空");
                    }
                    orderProduct.setProductNo(productNo);
                    break;
                case 2:
                    String num = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(num)) {
                        throw new BusinessException("数量为空");
                    }
                    if (!StringUtils.isNumber(num)) {
                        throw new BusinessException("数量有误");
                    }
                    orderProduct.setNum(StringUtils.stringToInteger(num));
                    break;
                case 3:
                    String maxNum = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(maxNum)) {
                        throw new BusinessException("上限数量为空");
                    }
                    if (!StringUtils.isNumber(maxNum)) {
                        throw new BusinessException("上限数量有误");
                    }
                    orderProduct.setMaxNum((int) Double.parseDouble(maxNum));
                    orderProduct.setAssignedNum(0);
                    orderProduct.setNotAssignNum(orderProduct.getMaxNum());
                    break;
                case 4:
                    String type = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(type)) {
                        throw new BusinessException("类别为空");
                    }
                    orderProduct.setProductType(type);
                    break;
                case 5:
                    String unit = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(unit)) {
                        throw new BusinessException("单位为空");
                    }
                    orderProduct.setUnit(unit);
                    break;
                case 6:
                    String completeDate = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(completeDate)) {
                        throw new BusinessException("出货日期为空");
                    }
                    try {
                        orderProduct.setCompleteDate(DateUtil.string2Date(completeDate, DateUtil.ymdFormat));
                    } catch (Exception e) {
                        throw new BusinessException("出货日期格式错误,应该为：yyyy-MM-dd");
                    }
                    break;
                case 7:
                    String custName = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(custName)) {
                        throw new BusinessException("客户名称为空");
                    }
                    Customer customer = customerService.getByName(custName);
                    if (customer == null) throw new BusinessException("客户不存在");
                    orderProduct.setCustomerId(customer.getId());
                    orderProduct.setCustomerName(customer.getName());
                    break;
                case 8:
                    String isMore = getCellValue(row.getCell(i), i);
                    Integer isMoreInt = 2;
                    if ("是".equals(isMore)) {
                        isMoreInt = 1;
                    }
                    orderProduct.setIsMore(isMoreInt);
                    break;
                case 9:
                    String urgentLevel = getCellValue(row.getCell(i), i);
                    Integer urgentLevelInt = 1;
                    if ("紧急".equals(urgentLevel)) {
                        urgentLevelInt = 2;
                    }
                    orderProduct.setUrgentLevel(urgentLevelInt);
                    orderProduct.setCreateTime(new Date());
                    orderProduct.setIsDelete(0);
                    orderProduct.setLastModifyTime(new Date());
                    break;
                case 10:
                    String remark = getCellValue(row.getCell(i), i);
                    orderProduct.setRemark(remark);
                    break;
            }
        }
        orderProducts.add(orderProduct);
        return orderProducts;
    }


    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 8 || i == 9)) {
            //备注列 允许为空
            return null;
        }
        try {
            value = getCellValue(cell);
        } catch (Exception e) {
            throw new BusinessException("第" + (i + 1) + "列" + fields.get(i) + " 填写错误");
        }
        if (value.equals("非法字符") || value.equals("未知类型")) {
            throw new BusinessException("第" + (i + 1) + "列" + fields.get(i) + " 填写错误");
        }
        return value;
    }
}
