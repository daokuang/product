package com.amir.excel;

import com.amir.exception.BusinessException;
import com.amir.model.carton.ShippingMark;
import com.amir.service.carton.ShippingMarkService;
import com.amir.service.customer.CustomerService;
import com.amir.service.productpm.ProductService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ShippingMarkExcelHandler extends BaseExcelHandler<ShippingMark> {

    public final static List<String> fields = Arrays.asList("客户名称", "图片名称", "特殊型号", "唛头类型");

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
/*        for (int i = 0; i < fields.size(); i++) {
            String cellValue = getCellValue(row.getCell(i), i);
            if (cellValue == null) {
                continue;
            }
            switch (i) {
                case 0:

                    shippingMark.set(getCellValue(row.getCell(i), i));
                    break;
                case 1:
                    shippingMark.setPmName(getCellValue(row.getCell(i), i));
                    break;
                case 2:
                    shippingMark.setType(getCellValue(row.getCell(i), i));
                    break;
                case 3:
                    shippingMark.setSupplier(getCellValue(row.getCell(i), i));
                    break;
                case 4:
                    shippingMark.setNum(BigDecimal.valueOf(Double.parseDouble(getCellValue(row.getCell(i), i))));
                    break;
                case 5:
                    shippingMark.setUnit(getCellValue(row.getCell(i), i));
                    break;
                case 6:
                    shippingMark.setRemark(getCellValue(row.getCell(i), i));
                    break;
                default:
                    break;
            }
        }
        Pm pm = pmService.getByNo(shippingMark.getPmNo());
        if (pm == null) {
            throw new BusinessException("第" + 1 + "列" + fields.get(0) + " 填写错误");
        } else if (!pm.getName().equals(shippingMark.getPmName())) {
            throw new BusinessException("第" + 2 + "列" + fields.get(1) + " 填写错误");
        } else if (!pm.getType().equals(shippingMark.getType())) {
            throw new BusinessException("第" + 3 + "列" + fields.get(2) + " 填写错误");
        } else if (!pm.getUnit().equals(shippingMark.getUnit())) {
            throw new BusinessException("第" + 6 + "列" + fields.get(5) + " 填写错误");
        }*/
        shippingMarkList.add(shippingMark);
        return shippingMarkList;
    }

    @Override
    protected void insert(List shippingMarkList, String operator) {
/*
        if (shippingMarkList != null && shippingMarkList.size() > 0) {
            for (int i = 0; i < shippingMarkList.size(); i++) {
                PmIn pmIn = (PmIn) shippingMarkList.get(i);
                Pm pm = pmService.getByNo(pmIn.getPmNo());

                pmIn.setPmId(pm.getId());
                pmIn.setPerNum(pm.getNum());
                pmIn.setBackNum(pm.getNum().add(pmIn.getNum()));
                pmIn.setOperator(operator);
                pmIn.setCreateTime(new Date());
                pmIn.setIsDelete(0);
                pmIn.setInDate(new Date());
                pmInService.create(pmIn);
                Pm pm1 = new Pm();
                pm1.setId(pm.getId());
                pm1.setNum(pmIn.getBackNum());
                pm.setLastModifyTime(new Date());
                pmService.updateByID(pm1);
            }
        }
*/
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value;
        if (cell == null && i == 2) {
            //特殊型号列 允许为空
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
