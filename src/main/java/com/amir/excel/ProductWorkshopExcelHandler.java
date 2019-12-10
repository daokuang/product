package com.amir.excel;

import com.alibaba.druid.util.StringUtils;
import com.amir.constant.WorkShopProductionMapEnum;
import com.amir.exception.BusinessException;
import com.amir.factory.ExcelImportFactory;
import com.amir.model.product.ProductProcedureWorkshop;
import com.amir.service.productpm.ProductService;
import com.amir.service.productpm.ProductWorkshopService;
import com.amir.util.ThreadPoolExecutorUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/8/12.
 */
@Component
public class ProductWorkshopExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("型号", "工序名称", "单价", "序号").collect(Collectors.toList());

    @Resource
    private ProductService productService;

    @Resource
    private ExcelImportFactory excelImportFactory;

    @Resource
    private ProductWorkshopService productWorkshopService;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        ThreadPoolExecutorUtil.getPool().execute(() -> {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ProductProcedureWorkshop productProcedureWorkshop = (ProductProcedureWorkshop) list.get(i);
                    productProcedureWorkshop.setOperator(operator);
                }
            }
            productWorkshopService.saveList(list);
        });
    }

    @Override
    protected List create(XSSFRow row) throws Exception {

        List<ProductProcedureWorkshop> productProcedureWorkshops = Lists.newArrayList();
        ProductProcedureWorkshop productProcedureWorkshop = new ProductProcedureWorkshop();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    String productNo = getCellValue(row.getCell(i), i);
                    if (null == productService.getByNO(productNo)) throw new BusinessException(productNo + "型号不存在");
                    productProcedureWorkshop.setProductNo(productNo);
                    break;
                case 1:
                    String procedureName = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(procedureName)) throw new BusinessException("工序名称未填写");
                    productProcedureWorkshop.setProcedureName(procedureName);
                    break;
                case 2:
                    String price = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(price)) throw new BusinessException("工序价格未填写");
                    if (!NumberUtils.isNumber(price)) throw new BusinessException("工序价格错误");
                    productProcedureWorkshop.setPrice(new BigDecimal(price.trim()));
                    break;
                case 3:
                    String sort = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(sort)) throw new BusinessException("工序序号未填写");
                    if (null == WorkShopProductionMapEnum.get(new Integer(sort)))
                        throw new BusinessException("请填写正确的序号(序号对应车间)");
                    productProcedureWorkshop.setSort(Integer.valueOf(sort));
                    break;

            }
        }
        productProcedureWorkshops.add(productProcedureWorkshop);
        return productProcedureWorkshops;
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && i == 6) {
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
