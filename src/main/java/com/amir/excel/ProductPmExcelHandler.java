package com.amir.excel;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.factory.ExcelImportFactory;
import com.amir.model.pm.Pm;
import com.amir.model.product.ProductPm;
import com.amir.service.dictionary.DictionaryService;
import com.amir.service.pm.PmService;
import com.amir.service.productpm.ProductPmService;
import com.amir.util.BigDecimalUtil;
import com.btjf.business.common.exception.BusinessException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/8/3.
 */
@Component
public class ProductPmExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("型号", "物料编号", "物料名称", "数量（耗料/双）",
            "单位", "类别", "序号", "备注").collect(Collectors.toList());

    @Resource
    private ProductPmService productPmService;

    @Resource
    private PmService pmService;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private ExcelImportFactory excelImportFactory;


    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List productPms, String operator) {
        if (productPms != null && productPms.size() > 0) {
            for (int i = 0; i < productPms.size(); i++) {
                ProductPm pm = (ProductPm) productPms.get(i);
                pm.setOperator(operator);
            }
        }
        productPmService.saveList(productPms);
    }

    @Override
    protected List create(XSSFRow row) throws Exception {

        List<ProductPm> productPms = new ArrayList<>();
        ProductPm productPm = new ProductPm();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    String productNo = getCellValue(row.getCell(i), i);
                    productPm.setProductNo(productNo);
                    break;

                case 1:
                    String pmSt = getCellValue(row.getCell(i), i);
                    Pm pm = pmService.getByNo(pmSt);
                    if (null == pm) {
                        throw new BusinessException("物料编号不存在");
                    }
                    if (null != productPmService.getByNoAndPmNo(productPm.getProductNo(), pm.getPmNo())) {
                        throw new BusinessException(productPm.getProductNo() + "型号的，" + pm.getPmNo() + "耗料已经存在");
                    }
                    productPm.setPmName(pm.getName());
                    productPm.setPmId(pm.getId());
                    productPm.setPmNo(pm.getPmNo());
                    break;
                case 3:
                    String num = getCellValue(row.getCell(i), i);
                    if (!StringUtils.isNumber(num)) {
                        throw new BusinessException("数量填写错误");
                    }
                    productPm.setNum(BigDecimalUtil.getBigDecimal(num));
                    productPm.setUnitNum(BigDecimal.valueOf(BigDecimalUtil.div(1d, productPm.getNum().doubleValue())));
                    break;
                case 4:
                    String unit = getCellValue(row.getCell(i), i);
                    if (null == dictionaryService.getListByNameAndType(unit, 2)) {
                        throw new BusinessException("单位填写错误");
                    } else {
                        productPm.setUnit(unit);
                    }
                    break;
                case 5:
                    String type = getCellValue(row.getCell(i), i);
                    if (CollectionUtils.isEmpty(dictionaryService.getListByNameAndType(type, 1))) {
                        throw new BusinessException("类别填写错误");
                    } else {
                        productPm.setType(type);
                    }
                    break;
                case 6:
                    String sequence = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(sequence) || !StringUtils.isNumber(sequence)) {
                        throw new BusinessException("序号填写错误");
                    } else {
                        productPm.setSequence(new Integer(sequence));
                    }
                    break;

                case 7:
                    String remark = getCellValue(row.getCell(i), i);
                    productPm.setRemark(remark);
                    productPm.setIsDelete(0);
                    productPm.setCreateTime(new Date());
                    productPm.setLastModifyTime(new Date());
                    //productPm.setOperator(operator);
                    productPm.setStatus(0);
                    break;
                default:
                    break;
            }
        }
        productPms.add(productPm);

        return productPms;

    }


    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 7 || i == 2)) {
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
