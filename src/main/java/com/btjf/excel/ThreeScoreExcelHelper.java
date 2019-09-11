package com.btjf.excel;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.service.emp.ScoreService;
import com.btjf.model.emp.Emp;
import com.btjf.model.emp.Score;
import com.btjf.service.emp.EmpService;
import com.btjf.service.sys.SysDeptService;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/9/10.
 */
@Service
public class ThreeScoreExcelHelper extends BaseExcelHandler {

    @Resource
    private EmpService empService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private SysDeptService sysDeptService;

    public final static List<String> fields = Stream.of("姓名", "5S分", "配合分", "质量分").collect(Collectors.toList());

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        String yearMonth = null;
        String fileName = file.getOriginalFilename();
        //todo
        yearMonth = fileName.split("考勤数据")[0];
        if (yearMonth.contains("-")) {
            yearMonthCash.set(yearMonth);
        } else {
            throw new BusinessException("文件格式错误");
        }

        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        if(!CollectionUtils.isEmpty(list)){
            list.stream().filter(t -> t != null).forEach(t -> scoreService.saveOrUpate((Score)t));
        }
    }

    @Override
    protected List<T> create(XSSFRow row) throws BusinessException {
        List sores = new ArrayList<>();
        Score score = new Score();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    if (getCellValue(row.getCell(i), i) == null) throw new BusinessException("名称不能为空");
                    Emp emp = empService.getByName(getCellValue(row.getCell(i), i));
                    if (emp == null) throw new BusinessException("名称为：" + getCellValue(row.getCell(i)) + "的员工不存在");
                    score.setEmpName(emp.getName());
                    score.setEmpId(emp.getId());
                    score.setDeptId(emp.getDeptId());
                    score.setDeptName(sysDeptService.get(emp.getDeptId()).getDeptName());
                    break;
                case 1:
                    String fiveSore = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(fiveSore)) {
                        score.setQualityScore(BigDecimal.ZERO);
                    } else {
                        score.setFiveScore(BigDecimal.valueOf(Double.parseDouble(fiveSore)));
                    }
                    break;
                case 2:
                    String coordinationScore = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(coordinationScore)) {
                        score.setQualityScore(BigDecimal.ZERO);
                    } else {
                        score.setCoordinationScore(BigDecimal.valueOf(Double.parseDouble(coordinationScore)));
                    }
                    break;
                case 3:
                    String qualityScore = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(qualityScore)) {
                        score.setQualityScore(BigDecimal.ZERO);
                    } else {
                        score.setQualityScore(BigDecimal.valueOf(Double.parseDouble(qualityScore)));
                    }
                    score.setYearMonth(yearMonthCash.get());
                    score.setIsDelete(0);
                    break;

            }
            sores.add(score);
        }
        return sores;
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 1 || i == 2 || i == 3)) {
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
