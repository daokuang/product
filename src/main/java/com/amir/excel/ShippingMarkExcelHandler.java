package com.amir.excel;

import com.amir.exception.BusinessException;
import com.amir.model.carton.ShippingMark;
import com.amir.model.carton.ShippingMarkImage;
import com.amir.model.customer.Customer;
import com.amir.model.product.Product;
import com.amir.service.carton.ShippingMarkService;
import com.amir.service.customer.CustomerService;
import com.amir.service.productpm.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class ShippingMarkExcelHandler extends BaseExcelHandler<ShippingMark> {

    public final static List<String> fields = Arrays.asList("客户名称", "特殊型号", "外箱唛头图片名称", "内箱唛头图片名称");

    @Resource
    private CustomerService customerService;
    @Resource
    private ProductService productService;
    @Resource
    private ShippingMarkService shippingMarkService;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected List<ShippingMark> create(XSSFRow row) throws BusinessException {
        List<ShippingMark> shippingMarkList = new ArrayList<>();
        ShippingMark shippingMark = new ShippingMark();
        String customerName = getCellValue(row.getCell(0), 0);
        String specialProductNumber = getCellValue(row.getCell(1), 1);
        String outsideShippingMarkImageName = getCellValue(row.getCell(2), 2);
        String insideShippingMarkImageName = getCellValue(row.getCell(3), 3);

        if (StringUtils.isBlank(outsideShippingMarkImageName) && StringUtils.isBlank(insideShippingMarkImageName)) {
            throw new BusinessException("外箱唛头和内箱唛头不可都为空");
        }

        Customer customer = customerService.getByName(customerName);
        if (customer == null) {
            throw new BusinessException("名称为“" + customerName + "”的客户不存在");
        }
        shippingMark.setCustomerId(customer.getId());

        if (specialProductNumber != null) {
            Product product = productService.getByNO(specialProductNumber);
            if (product == null) {
                throw new BusinessException("特殊型号“" + specialProductNumber + "”有误");
            }
            shippingMark.setProductNo(product.getProductNo());
        }

        if (StringUtils.isNotBlank(outsideShippingMarkImageName)) {
            String fileNameOnly = outsideShippingMarkImageName;
            if (fileNameOnly.contains(".")) {
                fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("."));
            }
            ShippingMarkImage shippingMarkImage = shippingMarkService.getByFileName(fileNameOnly);
            if (shippingMarkImage == null) {
                throw new BusinessException("未匹配到外箱唛头图片“" + outsideShippingMarkImageName + "”");
            }
            shippingMark.setOutsideMarkId(shippingMarkImage.getId());
        }

        if (StringUtils.isNotBlank(insideShippingMarkImageName)) {
            String fileNameOnly = insideShippingMarkImageName;
            if (fileNameOnly.contains(".")) {
                fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("."));
            }
            ShippingMarkImage shippingMarkImage = shippingMarkService.getByFileName(fileNameOnly);
            if (shippingMarkImage == null) {
                throw new BusinessException("未匹配到内箱唛头图片“" + insideShippingMarkImageName + "”");
            }
            shippingMark.setInsideMarkId(shippingMarkImage.getId());
        }

        shippingMarkList.add(shippingMark);
        return shippingMarkList;
    }

    @Override
    protected void insert(List<ShippingMark> shippingMarkList, String operator) {
        Date now = new Date();
        for (ShippingMark shippingMarkParam : shippingMarkList) {
            ShippingMark shippingMark;
            if (StringUtils.isNotBlank(shippingMarkParam.getProductNo())) {
                shippingMark = shippingMarkService.getByCustomerAndProduct(shippingMarkParam.getCustomerId(), shippingMarkParam.getProductNo());
            } else {
                shippingMark = shippingMarkService.getByCustomerAndNoProduct(shippingMarkParam.getCustomerId());
            }
            if (shippingMark == null) {
                shippingMark = new ShippingMark();
                shippingMark.setCreateTime(now);
            }
            shippingMark.setCustomerId(shippingMarkParam.getCustomerId());
            shippingMark.setProductNo(shippingMarkParam.getProductNo());
            if (shippingMarkParam.getOutsideMarkId() != null) {
                shippingMark.setOutsideMarkId(shippingMarkParam.getOutsideMarkId());
            }
            if (shippingMarkParam.getInsideMarkId() != null) {
                shippingMark.setInsideMarkId(shippingMarkParam.getInsideMarkId());
            }
            shippingMark.setUpdateTime(now);

            shippingMarkService.save(shippingMark);
        }
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value;
        if (cell == null && i == 2) {
            //特殊型号列 允许为空
            return null;
        }
        if (cell == null) {
            throw new BusinessException("第" + (i + 1) + "列" + fields.get(i) + " 填写错误");
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