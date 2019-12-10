package com.amir.excel;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.factory.ExcelImportFactory;
import com.amir.model.pm.Pm;
import com.amir.service.dictionary.DictionaryService;
import com.amir.service.pm.PmService;
import com.btjf.business.common.exception.BusinessException;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/7/31.
 */
@Component
public class PmExcelHandler extends BaseExcelHandler {

    @Resource
    private PmService pmService;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private ExcelImportFactory excelImportFactory;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            throw new BusinessException("请上传excel文件");
        }
        // Workbook 通用
        Workbook workbook = null;
        try {
            InputStream inputStream = file.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new BusinessException("workbook工具解析excel文件报错!!");
        }
        List<String> result = Lists.newArrayList();
        //错误信息
        List<String> error = Lists.newArrayList();

        List<Pm> pmList = Lists.newArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        // 总行数
        int rows = sheet.getPhysicalNumberOfRows();
        int k = 0;//所在行数
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            k = i + 1;
            Cell cell0 = row.getCell(0);
            if (cell0 == null) {//cell0==null作为检查改row是否无数据的触发条件
                if (cellIsNull(cell0) && cellIsNull(row.getCell(1)) && cellIsNull(row.getCell(2)) && cellIsNull(row.getCell(3)) && cellIsNull(row.getCell(4)) && cellIsNull(row.getCell(5)) && cellIsNull(row.getCell(6)) && cellIsNull(row.getCell(7))) {
                    continue;
                }
            }

            try {
                Pm pm = new Pm();
                pm.setOperator(operator);
                pm.setCreateTime(new Date());
                //excel就只有 8 列数据;  从1开始循环取值取到8
                StringBuffer name = new StringBuffer();
                for (int j = 0; j < 8; j++) {
                    String stringCellValue;
                    Cell cell = row.getCell(j);
                    switch (j) {
                        //材料
                        case 0:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                if (StringUtils.isEmpty(stringCellValue)) {
                                    throw new BusinessException("第" + k + "行的材料编号为空");
                                } else if (null != pmService.getByNo(stringCellValue) && !Boolean.TRUE.equals(isCover)) {
                                    throw new BusinessException("第" + k + "行的材料编号已经存在");
                                } else {
                                    pm.setPmNo(stringCellValue);

                                }
                            } else {
                                throw new BusinessException("第" + k + "行的材料编号为空");
                            }
                            break;
                        //颜色
                        case 1:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                name.append(stringCellValue).append("-");
                                pm.setColour(stringCellValue);
                            }
                            break;
                        //规格
                        case 2:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                name.append(stringCellValue);
                                if (!stringCellValue.endsWith("-")) {
                                    name.append("-");
                                }
                                pm.setNorms(stringCellValue);
                            }
                            break;
                        //材质
                        case 3:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                name.append(stringCellValue);
                                if (!stringCellValue.endsWith("-")) {
                                    name.append("-");
                                }
                                pm.setMaterial(stringCellValue);
                            }
                            break;
                        //称呼
                        case 4:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                name.append(stringCellValue);
                                if (StringUtils.isEmpty(name.toString())) {
                                    throw new BusinessException("第" + k + "行的材料名称为空");
                                }
                                pm.setCallStr(stringCellValue);
                                pm.setName(name.toString());
                            }
                            break;

                        //类别
                        case 5:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                if (CollectionUtils.isEmpty(dictionaryService.getListByNameAndType(stringCellValue, 1))) {
                                    throw new BusinessException("第" + k + "行的材料类别不存在");
                                } else {
                                    pm.setType(stringCellValue);
                                }
                            } else {
                                throw new BusinessException("第" + k + "行的材料类别未填写");
                            }
                            break;
                        //单位
                        case 6:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                if (CollectionUtils.isEmpty(dictionaryService.getListByNameAndType(stringCellValue, 2))) {
                                    throw new BusinessException("第" + k + "行的材料单位有误");
                                } else {
                                    pm.setUnit(stringCellValue);
                                }
                            } else {
                                throw new BusinessException("第" + k + "行的材料单位未填写");
                            }
                            break;
                        //备注
                        case 7:
                            if (cell != null) {
                                cell.setCellType(CellType.STRING);
                                //去空格
                                stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                                pm.setRemark(stringCellValue);
                            }
                            break;

                    }
                    pm.setIsDelete(0);
                    pm.setName(name.toString());
                    pm.setNum(BigDecimal.ZERO);
                }
                pmList.add(pm);
            } catch (Exception e) {
                error.add(e.getMessage());
            }

        }
        //insert
        pmService.saveList(pmList, isCover);
        result.add("提交成功！新增导入" + pmList.size() + "条数据！");
        if (error.size() > 0) {
            result.add("导入失败，以下数据请修改后再重新上传");
            result.addAll(error);
        }

        return result;

    }

    @Override
    protected void insert(List list, String operator) {

    }

    @Override
    protected List create(XSSFRow row) throws Exception {
        return null;
    }

    /**
     * 判断excel单元格是否为空
     */
    private Boolean cellIsNull(Cell cell) {
        Boolean falg = false;
        if (cell == null) {
            falg = true;
        } else {
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
            if (StringUtils.isEmpty(cellValue)) {
                falg = true;
            }
        }
        return falg;
    }
}
