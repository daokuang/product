package com.amir.excel;

import com.amir.exception.BusinessException;
import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpSalaryMonthly;
import com.amir.model.emp.EmpSalaryMothlyPojo;
import com.amir.model.emp.Score;
import com.amir.model.salary.SalaryMonthly;
import com.amir.service.emp.EmpSalaryMonthlyService;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.EmpWorkService;
import com.amir.service.emp.ScoreService;
import com.amir.service.salary.SalaryMonthlyService;
import com.amir.service.sys.SysDeptService;
import com.amir.util.BigDecimalUtil;
import com.amir.util.DateUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/9/7.
 */
@Service
public class EmpSalaryMothlyHandler extends BaseExcelHandler {

    private final static List<String> neetNigthSnack = Stream.of("车工", "辅工", "大辅工", "杂工", "下料工", "印刷", "剪辅料", "切包边条",
            "分料", "电脑车工", "包装工", "新学徒车工", "新熟练车工", "小辅工", "整形，打包", "中辅工").collect(Collectors.toList());
    private static ThreadLocal<String> yearMonthCash = ThreadLocal.withInitial(() -> DateUtil.dateToString(new Date(), DateUtil.ymFormat));
    @Resource
    private EmpService empService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private EmpWorkService empWorkService;
    @Resource
    private SalaryMonthlyService salaryMonthlyService;
    @Resource
    private EmpSalaryMonthlyService empSalaryMonthlyService;
    @Resource
    private ScoreService scoreService;

    @Override
    public List<String> checkLayout(MultipartFile file, List<String> fields, String operator) throws Exception {

        List<String> response = new ArrayList<>();
        List<String> errResponse = new ArrayList<>();
        //文件名
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(".xlsx")) {
            response.add("导入失败，以下数据请修改后再重新上传");
            response.add("请导入后缀为xlsx的文件");
            return response;
        }

        InputStream is = file.getInputStream();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);


        String yearMonth = null;
        try {
            yearMonth = fileName.split("考勤数据")[0];
            if (yearMonth.contains("-")) {
                yearMonthCash.set(yearMonth);
            } else {
                throw new BusinessException("文件格式错误");
            }
        } catch (Exception e) {
            errResponse.add("文件名称格式不正确，请以 （yyyy-MM考勤数据） 命名");
        }

        // 日期
        XSSFRow dateRow = (XSSFRow) sheet.getRow(0);
        //星期
        XSSFRow webRow = (XSSFRow) sheet.getRow(1);
        //白班
        XSSFRow blackRow = (XSSFRow) sheet.getRow(2);
        //晚班
        XSSFRow nightRow = (XSSFRow) sheet.getRow(3);

        List<EmpSalaryMothlyPojo> result = new ArrayList<>();


        String name = null;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = (XSSFRow) sheet.getRow(i);

            if (row == null) break;

            if (row.getCell(0) != null && !StringUtils.isEmpty(getCellValue(row.getCell(0)))
                    && i % 2 == 0) {
                name = getCellValue(row.getCell(0));//名称
                Emp emp = empService.getByName(name);
                if (emp == null) {
                    errResponse.add("第" + (i + 1) + "行，名称为：" + name + "的员工不存在");
                }
            }


            for (int index = 1; index < dateRow.getPhysicalNumberOfCells(); index++) {

                EmpSalaryMothlyPojo model = new EmpSalaryMothlyPojo();
                model.setDate(getCellValue(dateRow.getCell(index)));
                model.setName(name);
                model.setNum(new Double(row.getCell(index) == null || StringUtils.isEmpty(getCellValue(row.getCell(index))) ? "0" : getCellValue(row.getCell(index))));
                model.setYearMonth(yearMonth);
                //白班
                if (i % 2 == 0) {
                    model.setIsLegal("法假".equals(getCellValue(blackRow.getCell(index))));
                    model.setIsHoliday("节假".equals(getCellValue(blackRow.getCell(index))));
                    model.setIsBlack(Boolean.TRUE);
                    //晚班
                } else {
                    model.setIsLegal("法假".equals(getCellValue(nightRow.getCell(index))));
                    model.setIsHoliday("节假".equals(getCellValue(nightRow.getCell(index))));
                    model.setIsBlack(Boolean.FALSE);
                }
                result.add(model);

            }
        }
        //检验要导入的数据重复问题
        if (errResponse.size() > 0) {
            int sum = sheet.getLastRowNum() - errResponse.size();
            response.add("导入失败，以下数据请修改后再重新上传");
            response.addAll(errResponse);
        } else {
            insert(result, operator);
            response.add("提交成功！新增导入" + (sheet.getLastRowNum() - 3) + "条数据！");
        }
        wb.close();
        return response;
    }

    @Override
    protected List<T> create(XSSFRow row) throws Exception {
        return null;
    }

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, null, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        if (!CollectionUtils.isEmpty(list)) {
            String yearMonth = yearMonthCash.get();
            yearMonthCash.remove(); //防止内存泄漏
            //按名称分组
            Map<String, List<EmpSalaryMothlyPojo>> modelMap = (Map<String, List<EmpSalaryMothlyPojo>>) list.stream().filter(t -> t != null).
                    collect(Collectors.groupingBy(EmpSalaryMothlyPojo::getName));
            for (Map.Entry<String, List<EmpSalaryMothlyPojo>> entry : modelMap.entrySet()) {
                EmpSalaryMonthly monthly = new EmpSalaryMonthly();
                //员工基本信息
                Emp emp = empService.getByName(entry.getKey());
                monthly.setEmpName(emp.getName());
                monthly.setDeptId(emp.getDeptId());
                monthly.setIsDelete(0);
                monthly.setEmpId(emp.getId());
                monthly.setDeptName(sysDeptService.get(emp.getDeptId()).getDeptName());
                monthly.setWorkName(empWorkService.getByID(emp.getWorkId()).getName());
                monthly.setSalary(emp.getSalary());
                monthly.setYearMonth(yearMonth);
                SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(monthly.getYearMonth());

                Double blackNum = BigDecimal.ZERO.doubleValue(); //白班天数 （非法假 假期）
                Double nightNum = BigDecimal.ZERO.doubleValue();  //晚班天数 （非法假 假期）
                Double holidayBlackNum = BigDecimal.ZERO.doubleValue(); //假期白班天数
                Double holidayNightNum = BigDecimal.ZERO.doubleValue(); //假期晚班天数
                Double legalBlackNum = BigDecimal.ZERO.doubleValue(); //法假白班天数
                Double legalNightNum = BigDecimal.ZERO.doubleValue(); //法假晚班天数
                for (EmpSalaryMothlyPojo empSalaryMothlyPojo : entry.getValue()) {
                    if (empSalaryMothlyPojo == null) continue;
                    //白班
                    if ((empSalaryMothlyPojo.getIsBlack() && !empSalaryMothlyPojo.getIsHoliday() && !empSalaryMothlyPojo.getIsLegal())) {
                        blackNum = BigDecimalUtil.add(blackNum, empSalaryMothlyPojo.getNum());
                    }
                    //晚班
                    if (!empSalaryMothlyPojo.getIsBlack() && !empSalaryMothlyPojo.getIsHoliday() && !empSalaryMothlyPojo.getIsLegal()) {
                        nightNum = BigDecimalUtil.add(nightNum, empSalaryMothlyPojo.getNum());
                    }
                    //假期白班天数
                    if (empSalaryMothlyPojo.getIsBlack() && empSalaryMothlyPojo.getIsHoliday() && !empSalaryMothlyPojo.getIsLegal()) {
                        holidayBlackNum = BigDecimalUtil.add(holidayBlackNum, empSalaryMothlyPojo.getNum());
                    }
                    //假期晚班
                    if (!empSalaryMothlyPojo.getIsBlack() && empSalaryMothlyPojo.getIsHoliday() && !empSalaryMothlyPojo.getIsLegal()) {
                        holidayNightNum = BigDecimalUtil.add(holidayNightNum, empSalaryMothlyPojo.getNum());
                    }
                    //法假白班
                    if (empSalaryMothlyPojo.getIsBlack() && !empSalaryMothlyPojo.getIsHoliday() && empSalaryMothlyPojo.getIsLegal()) {
                        legalBlackNum = BigDecimalUtil.add(legalBlackNum, empSalaryMothlyPojo.getNum());
                    }
                    //法假晚班
                    if (!empSalaryMothlyPojo.getIsBlack() && !empSalaryMothlyPojo.getIsHoliday() && empSalaryMothlyPojo.getIsLegal()) {
                        legalNightNum = BigDecimalUtil.add(legalNightNum, empSalaryMothlyPojo.getNum());
                    }

                }


                monthly.setDayWork(BigDecimal.valueOf(BigDecimalUtil.add(blackNum, holidayBlackNum)));
                monthly.setNightWork(BigDecimal.valueOf(BigDecimalUtil.add(nightNum, holidayNightNum)));
                monthly.setRestDay(BigDecimal.valueOf(legalBlackNum));
                //真实总天数  白班+晚班*0.5+公休=总天数    公休 = 白班是“法假”的数字之和
                Double sumWorkDay = BigDecimalUtil.add(monthly.getDayWork().doubleValue(), BigDecimalUtil.mul(nightNum, 0.5), legalBlackNum);
                //工作日   ① 工作日>总天数，工作日不调整；② 工作日<=总天数，工作日=总天数
                monthly.setWorkDay(BigDecimal.valueOf(salaryMonthly != null ? salaryMonthly.getExpectWorkDay() > sumWorkDay ? salaryMonthly.getExpectWorkDay() : sumWorkDay : sumWorkDay));
                monthly.setSumDay(BigDecimal.valueOf(sumWorkDay));
                monthly.setDayWorkHoliday(BigDecimal.valueOf(holidayBlackNum));
                monthly.setNightWorkHoliay(BigDecimal.valueOf(holidayNightNum));
                monthly.setDayWorkLegal(BigDecimal.valueOf(legalBlackNum));
                monthly.setNigthWorkLegal(BigDecimal.valueOf(legalNightNum));
                //考勤分 默认0
                //查询考勤分
                Score score = scoreService.getByNameAndYearMonth(emp.getName(), monthly.getYearMonth());
                if (score != null && score.getScore() != null) {
                    monthly.setScore(score.getScore());
                }

                //取固定工资/总天数，保留2位小数，四舍五入
                if (monthly.getSalary() == null) {
                    monthly.setDaySalary(BigDecimal.ZERO);
                } else {
                    monthly.setDaySalary(BigDecimal.valueOf(BigDecimalUtil.div(monthly.getSalary().doubleValue(), sumWorkDay)));
                }
                monthly.setRealSalary(BigDecimal.ZERO);
                monthly.setCreateTime(new Date());
                monthly.setLastModifyTime(new Date());
                monthly.setType(emp.getType());
                if (neetNigthSnack.contains(monthly.getWorkName())) { //计件工
                    monthly.setNigthSnack(BigDecimal.valueOf(monthly.getNightWork() != null ? BigDecimalUtil.mul(monthly.getNightWork().doubleValue(), 2) : 0));
                }
                empSalaryMonthlyService.save(monthly);
            }

        }
    }
}
