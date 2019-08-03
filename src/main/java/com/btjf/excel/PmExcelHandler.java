package com.btjf.excel;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.factory.ExcelImportFactory;
import com.btjf.model.pm.Pm;
import com.btjf.service.dictionary.DictionaryService;
import com.btjf.service.pm.PmService;
import com.google.common.collect.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
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
        } catch (InvalidFormatException e) {
            throw new BusinessException("workbook工具解析excel文件报错!!");
        }
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
                                error.add("第" + k + 1 + "行的材料编号为空");
                            } else if (null != pmService.getByNo(stringCellValue) && !Boolean.TRUE.equals(isCover)) {
                                error.add("第" + k + 1 + "行的材料编号已经存在");
                            } else {
                                pm.setPmNo(stringCellValue);

                            }
                        } else {
                            error.add("第" + k + 1 + "行的材料编号为空");
                        }
                        break;
                    //颜色
                    case 1:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        name.append(stringCellValue).append("-");
                        pm.setColour(stringCellValue);
                        break;
                    //规格
                    case 2:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        name.append(stringCellValue);
                        if (!stringCellValue.endsWith("-")) {
                            name.append("-");
                        }
                        pm.setNorms(stringCellValue);
                        break;
                    //材质
                    case 3:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        name.append(stringCellValue);
                        if (!stringCellValue.endsWith("-")) {
                            name.append("-");
                        }
                        pm.setMaterial(stringCellValue);
                        break;
                    //称呼
                    case 4:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        name.append(stringCellValue);
                        if (StringUtils.isEmpty(name.toString())) {
                            error.add("第" + k + 1 + "行的材料名称为空");
                        }
                        pm.setCallStr(stringCellValue);
                        pm.setName(name.toString());
                        break;

                    //类别
                    case 5:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        if (CollectionUtils.isEmpty(dictionaryService.getListByNameAndType(stringCellValue, 1))) {
                            error.add("第" + k + 1 + "行的材料类别有误为空");
                        } else {
                            pm.setType(stringCellValue);
                        }
                        break;
                    //单位
                    case 6:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        if (CollectionUtils.isEmpty(dictionaryService.getListByNameAndType(stringCellValue, 2))) {
                            error.add("第" + k + 1+"行的材料单位有误");
                        } else {
                            pm.setUnit(stringCellValue);
                        }
                        break;
                    //备注
                    case 7:
                        cell.setCellType(CellType.STRING);
                        //去空格
                        stringCellValue = cell.getStringCellValue().replaceAll("\u00A0", "").trim();
                        pm.setRemark(stringCellValue);
                        break;

                }
                pm.setIsDelete(0);
            }
            pmList.add(pm);
        }

        if (error.size() > 0) {
            return error;
        } else {
            //insert
            excelImportFactory.savePm(pmList, isCover);
            error.add("提交成功！新增导入" + sheet.getLastRowNum() + "条数据！");
            return error;
        }
    }

    @Override
    protected void insert(List list) {

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
