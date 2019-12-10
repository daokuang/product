package com.amir.excel;

import com.amir.model.pm.Pm;
import com.amir.model.pm.PmIn;
import com.amir.service.pm.PmInService;
import com.amir.service.pm.PmService;
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
 * Created by yj on 2019/7/31.
 */
@Component
//@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
public class PmInExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("物料编号", "物料名称", "物料类别",
            "供应单位", "初始库或入库数量", "物料单位", "备注").collect(Collectors.toList());

    @Resource
    private PmInService pmInService;
    @Resource
    private PmService pmService;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List pmInList, String operator) {
        if (pmInList != null && pmInList.size() > 0) {
            for (int i = 0; i < pmInList.size(); i++) {
                PmIn pmIn = (PmIn) pmInList.get(i);
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
    }

    @Override
    protected List create(XSSFRow row) throws BusinessException {
        List<PmIn> pmInList = new ArrayList<>();
        PmIn pmIn = new PmIn();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    pmIn.setPmNo(getCellValue(row.getCell(i), i));
                    break;
                case 1:
                    pmIn.setPmName(getCellValue(row.getCell(i), i));
                    break;
                case 2:
                    pmIn.setType(getCellValue(row.getCell(i), i));
                    break;
                case 3:
                    pmIn.setSupplier(getCellValue(row.getCell(i), i));
                    break;
                case 4:
                    pmIn.setNum(BigDecimal.valueOf(Double.parseDouble(getCellValue(row.getCell(i), i))));
                    break;
                case 5:
                    pmIn.setUnit(getCellValue(row.getCell(i), i));
                    break;
                case 6:
                    pmIn.setRemark(getCellValue(row.getCell(i), i));
                    break;
                default:
                    break;
            }
        }
        Pm pm = pmService.getByNo(pmIn.getPmNo());
        if (pm == null) {
            throw new BusinessException("第" + 1 + "列" + fields.get(0) + " 填写错误");
        } else if (!pm.getName().equals(pmIn.getPmName())) {
            throw new BusinessException("第" + 2 + "列" + fields.get(1) + " 填写错误");
        } else if (!pm.getType().equals(pmIn.getType())) {
            throw new BusinessException("第" + 3 + "列" + fields.get(2) + " 填写错误");
        } else if (!pm.getUnit().equals(pmIn.getUnit())) {
            throw new BusinessException("第" + 6 + "列" + fields.get(5) + " 填写错误");
        }
        pmInList.add(pmIn);
        return pmInList;
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
