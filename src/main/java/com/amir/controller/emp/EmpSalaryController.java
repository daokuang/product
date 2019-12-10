package com.amir.controller.emp;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.controller.base.ProductBaseController;
import com.amir.controller.emp.vo.EmpSalaryVo;
import com.amir.excel.BaseExcelHandler;
import com.amir.model.emp.EmpSalaryMonthly;
import com.amir.model.emp.Score;
import com.amir.model.emp.SummarySalaryMonthly;
import com.amir.model.salary.SalaryMonthly;
import com.amir.service.emp.EmpSalaryMonthlyService;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.ScoreService;
import com.amir.service.emp.SummarySalaryMonthlyService;
import com.amir.service.salary.SalaryMonthlyService;
import com.amir.util.BigDecimalUtil;
import com.btjf.application.components.xaresult.AppXaResultHelper;
import com.btjf.application.util.XaResult;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.page.Page;
import com.btjf.common.utils.BeanUtil;
import com.btjf.common.utils.DateUtil;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/9/17.
 */
@Api(value = "EmpController", description = "工资管理", position = 1)
@RequestMapping(value = "/empSalary")
@RestController("empSalaryControler")
public class EmpSalaryController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(EmpSalaryController.class);

    @Resource
    private EmpSalaryMonthlyService empSalaryMothlyService;

    @Resource
    private SummarySalaryMonthlyService summarySalaryMonthlyService;

    @Resource
    private EmpService empService;

    @Resource
    private SalaryMonthlyService salaryMonthlyService;
    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "/calculation", method = RequestMethod.POST)
    public XaResult<Integer> calculation(String yearMonth, String deptName, String empName, Integer type) {
        if (StringUtils.isEmpty(yearMonth)) {
            return XaResult.error("年月不能为空，格式为yyyy-MM");
        }
        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(yearMonth);
        if (salaryMonthly == null) {
            return XaResult.error("记录不存在");
        }
        if (salaryMonthly.getIsMore() == null) {
            return XaResult.error("该月份产值未设置");
        }
        if (!DateUtil.isAfter(DateUtil.string2Date(salaryMonthly.getYearMonth(), DateUtil.ymFormat),
                DateUtil.dateBefore(new Date(), Calendar.MONTH, 3))) {
            return XaResult.error("三个月前的信息不允许再度结算");
        }
        List<Score> scoreList = scoreService.getList(salaryMonthly.getYearMonth(), null, null);
        if (scoreList == null || scoreList.size() < 1) {
            return XaResult.error("该月份考勤分或3个分未导入");
        }
        List<EmpSalaryMonthly> empSalaryMonthlyList = empSalaryMothlyService.getList(salaryMonthly.getYearMonth(), null, null, null);
        if (empSalaryMonthlyList == null || empSalaryMonthlyList.size() < 1) {
            return XaResult.error("该月份考勤信息未导入");
        }

        Integer row = empSalaryMothlyService.calculation(yearMonth, deptName, empName, type);
        return XaResult.success(row);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<EmpSalaryVo>> list(String yearMonth, String deptName, String empName,
                                            Integer type, Integer currentPage, Integer pageSize) {

        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }

        if (type != null && (type != 1 && type != 2)) {
            return XaResult.error("type有误");
        }

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);

        Page<SummarySalaryMonthly> summarySalaryMonthlyPage = summarySalaryMonthlyService.getPage(yearMonth, deptName, empName, type, page);

        Page<EmpSalaryVo> empSalaryVoPage = BeanUtil.convertPage(summarySalaryMonthlyPage, EmpSalaryVo.class);
        List<EmpSalaryVo> empSalaryVos = empSalaryVoPage.getRows();
        if (!CollectionUtils.isEmpty(empSalaryVos)) {
            for (EmpSalaryVo empSalaryVo : empSalaryVos) {
                if (empSalaryVo == null) continue;
                if (type == null) {
                    //基本工资工时
                    empSalaryVo.setBaseWorkHour(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getDayWork() == null ? 0 : empSalaryVo.getDayWork().doubleValue(), 8.0)));
                    SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(empSalaryVo.getYearMonth());
                    if (salaryMonthly == null) continue;
                    //基本工资/(正常上班天数*8)*(白天上班天数*8)                                                                                                                     BigDecimalUtil.div(empSalaryVo.getDhbt().doubleValue()
                    empSalaryVo.setBaseWorkHourSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getWorkDay() == null ? 0 : empSalaryVo.getWorkDay().doubleValue() * 8, BigDecimalUtil.div(salaryMonthly.getBasicSalary().doubleValue(), BigDecimalUtil.mul(salaryMonthly.getExpectWorkDay(), 8)))));
                    empSalaryVo.setHourSalary(salaryMonthly.getHourlyWage());
                    //正常加班工时金额
                    empSalaryVo.setNormalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getNormalOvertime() == null ? 0 : empSalaryVo.getNormalOvertime().doubleValue(), 17.325)));

                    //假日白班天数*8+假日晚班天数*3 * 23.1= 金额
                    empSalaryVo.setHoliayOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getHoliayOvertime() == null ? 0 : empSalaryVo.getHoliayOvertime().doubleValue(), 23.1)));
                    //法定假日（白班天数*8+晚班*3） * 3 * 11.55
                    empSalaryVo.setLegalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getLegalOvertime() == null ? 0 : empSalaryVo.getLegalOvertime().doubleValue(), BigDecimalUtil.mul(3, 11.55))));
                    //汇总表覆盖计时工资
                    empSalaryVo.setTimeSalary(BigDecimal.valueOf(BigDecimalUtil.add(empSalaryVo.getBaseWorkHourSalary() == null ? 0 : empSalaryVo.getBaseWorkHourSalary().doubleValue(),
                            empSalaryVo.getNormalOvertimeSalary() == null ? 0 : empSalaryVo.getNormalOvertimeSalary().doubleValue(),
                            empSalaryVo.getHoliayOvertimeSalary() == null ? 0 : empSalaryVo.getHoliayOvertimeSalary().doubleValue(),
                            empSalaryVo.getLegalOvertimeSalary() == null ? 0 : empSalaryVo.getLegalOvertimeSalary().doubleValue())));

                    //绩效 应发工资-补贴合计-计时工资
                    empSalaryVo.setAchievements(BigDecimal.valueOf(BigDecimalUtil.sub(empSalaryVo.getRealSalary() == null ? 0 : empSalaryVo.getRealSalary().doubleValue(),
                            empSalaryVo.getSumSusbsidy() == null ? 0 : empSalaryVo.getSumSusbsidy().doubleValue(),
                            empSalaryVo.getTimeSalary().doubleValue())));
                }
            }
        }
        XaResult<List<EmpSalaryVo>> result = AppXaResultHelper.success(empSalaryVoPage, empSalaryVos);
        return result;
    }

    /**
     * 汇总
     *
     * @param yearMonth
     * @param deptName
     * @param empName
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(String yearMonth, String deptName, String empName, HttpServletResponse response) {

        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            throw new BusinessException("年月格式不符，请更正为yyyy-MM");
        }

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(2, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(3, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(5, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(6, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(7, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(7, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));

        int j = 0;
        header.createCell(j++).setCellValue("序号");
        header.createCell(j++).setCellValue("月份");
        header.createCell(j++).setCellValue("姓名");
        header.createCell(j++).setCellValue("部门");
        header.createCell(j++).setCellValue("工种");
        header.createCell(j++).setCellValue("时薪");
        header.createCell(j++).setCellValue("计件工资");
        header.createCell(j++).setCellValue("基本工资工时");
        header.createCell(j++).setCellValue("金额");
        header.createCell(j++).setCellValue("正常加班工时");
        header.createCell(j++).setCellValue("金额");
        header.createCell(j++).setCellValue("假日加班工时");
        header.createCell(j++).setCellValue("金额");
        header.createCell(j++).setCellValue("法假加班工时");
        header.createCell(j++).setCellValue("金额");
        header.createCell(j++).setCellValue("计时工资");
        header.createCell(j++).setCellValue("补贴");
        header.createCell(j++).setCellValue("绩效");
        header.createCell(j++).setCellValue("应发工资");
        header.createCell(j++).setCellValue("养老金");
        header.createCell(j++).setCellValue("医疗险");
        header.createCell(j++).setCellValue("失业金");
        header.createCell(j++).setCellValue("公积金");
        header.createCell(j++).setCellValue("扣款");
        header.createCell(j++).setCellValue("实发工资");

        List<SummarySalaryMonthly> summarySalaryMonthly = summarySalaryMonthlyService.getList(yearMonth, deptName, empName, null);
        List<EmpSalaryVo> empSalaryVos = BeanUtil.convertList(summarySalaryMonthly, EmpSalaryVo.class);
        if (!CollectionUtils.isEmpty(empSalaryVos)) {
            EmpSalaryVo empSalaryVo = null;
            for (int i = 0; i < empSalaryVos.size(); i++) {
                empSalaryVo = empSalaryVos.get(i);
                if (empSalaryVo == null) continue;

                //基本工资工时
                empSalaryVo.setBaseWorkHour(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getDayWork() == null ? 0 : empSalaryVo.getDayWork().doubleValue(), 8.0)));
                SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(empSalaryVo.getYearMonth());
                if (salaryMonthly == null) continue;
                //基本工资/(正常上班天数*8)*(白天上班天数*8)                                                                                                                     BigDecimalUtil.div(empSalaryVo.getDhbt().doubleValue()
                empSalaryVo.setBaseWorkHourSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getWorkDay() == null ? 0 : empSalaryVo.getWorkDay().doubleValue() * 8, BigDecimalUtil.div(salaryMonthly.getBasicSalary().doubleValue(), BigDecimalUtil.mul(salaryMonthly.getExpectWorkDay(), 8)))));
                empSalaryVo.setHourSalary(salaryMonthly.getHourlyWage());
                //正常加班工时金额
                empSalaryVo.setNormalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getNormalOvertime() == null ? 0 : empSalaryVo.getNormalOvertime().doubleValue(), 17.325)));

                //假日白班天数*8+假日晚班天数*3 * 23.1= 金额
                empSalaryVo.setHoliayOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getHoliayOvertime() == null ? 0 : empSalaryVo.getHoliayOvertime().doubleValue(), 23.1)));
                //法定假日（白班天数*8+晚班*3） * 3 * 11.55
                empSalaryVo.setLegalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getLegalOvertime() == null ? 0 : empSalaryVo.getLegalOvertime().doubleValue(), BigDecimalUtil.mul(3, 11.55))));
                //汇总表覆盖计时工资
                empSalaryVo.setTimeSalary(BigDecimal.valueOf(BigDecimalUtil.add(empSalaryVo.getBaseWorkHourSalary() == null ? 0 : empSalaryVo.getBaseWorkHourSalary().doubleValue(),
                        empSalaryVo.getNormalOvertimeSalary() == null ? 0 : empSalaryVo.getNormalOvertimeSalary().doubleValue(),
                        empSalaryVo.getHoliayOvertimeSalary() == null ? 0 : empSalaryVo.getHoliayOvertimeSalary().doubleValue(),
                        empSalaryVo.getLegalOvertimeSalary() == null ? 0 : empSalaryVo.getLegalOvertimeSalary().doubleValue())));

                //绩效 应发工资-补贴合计-计时工资
                empSalaryVo.setAchievements(BigDecimal.valueOf(BigDecimalUtil.sub(empSalaryVo.getRealSalary() == null ? 0 : empSalaryVo.getRealSalary().doubleValue(),
                        empSalaryVo.getSumSusbsidy() == null ? 0 : empSalaryVo.getSumSusbsidy().doubleValue(),
                        empSalaryVo.getTimeSalary().doubleValue())));

                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(empSalaryVo.getId());
                row.createCell(j++).setCellValue(empSalaryVo.getYearMonth());
                row.createCell(j++).setCellValue(empSalaryVo.getEmpName());
                row.createCell(j++).setCellValue(empSalaryVo.getDeptName());
                row.createCell(j++).setCellValue(empSalaryVo.getWorkName());
                row.createCell(j++).setCellValue(empSalaryVo.getHourSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getBasicSalary().toString()); //计件工资
                row.createCell(j++).setCellValue(empSalaryVo.getBaseWorkHour().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getBaseWorkHourSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getNormalOvertime().toString()); //正常加班工时
                row.createCell(j++).setCellValue(empSalaryVo.getNormalOvertimeSalary().toString());//金额
                row.createCell(j++).setCellValue(empSalaryVo.getHoliayOvertime().toString());//假日加班工时
                row.createCell(j++).setCellValue(empSalaryVo.getHoliayOvertimeSalary().toString());//金额
                row.createCell(j++).setCellValue(empSalaryVo.getLegalOvertime().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getLegalOvertimeSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getTimeSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSumSusbsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getAchievements().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getRealSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getYlbx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getYiliaobx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSybx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getGjj().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSumDeduction().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getTrueSalary().toString());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(yearMonth + "导出工资汇总表.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出汇总工资数据excel异常");
        }
    }

    @RequestMapping(value = "/percent/export", method = RequestMethod.GET)
    public void percentExport(String yearMonth, String deptName, String empName, HttpServletResponse response) {

        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            throw new BusinessException("年月格式不符，请更正为yyyy-MM");
        }

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);
        for (int i = 0; i < 30; i++) {
            sheet.setColumnWidth(i, (int) ((20 + 0.72) * 256));
        }
        int j = 0;
        header.createCell(j++).setCellValue("序号");
        header.createCell(j++).setCellValue("月份");
        header.createCell(j++).setCellValue("姓名");
        header.createCell(j++).setCellValue("部门");
        header.createCell(j++).setCellValue("工种");
        header.createCell(j++).setCellValue("白班");
        header.createCell(j++).setCellValue("晚班");
        header.createCell(j++).setCellValue("考勤分");
        header.createCell(j++).setCellValue("总工时");
        header.createCell(j++).setCellValue("时薪");
        header.createCell(j++).setCellValue("计件工资");
        header.createCell(j++).setCellValue("计时工资");
        header.createCell(j++).setCellValue("夜餐");
        header.createCell(j++).setCellValue("用餐补贴");
        header.createCell(j++).setCellValue("社保补贴");
        header.createCell(j++).setCellValue("工龄补贴");
        header.createCell(j++).setCellValue("住房补贴");
        header.createCell(j++).setCellValue("车工计件10%补贴");
        header.createCell(j++).setCellValue("车工补贴");
        header.createCell(j++).setCellValue("新车工补贴");
        header.createCell(j++).setCellValue("其他补贴");
        header.createCell(j++).setCellValue("补贴合计");
        header.createCell(j++).setCellValue("应发工资");
        header.createCell(j++).setCellValue("养老金");
        header.createCell(j++).setCellValue("医疗险");
        header.createCell(j++).setCellValue("失业金");
        header.createCell(j++).setCellValue("用餐扣款");
        header.createCell(j++).setCellValue("其他扣款");
        header.createCell(j++).setCellValue("代扣小计");
        header.createCell(j++).setCellValue("实发工资");

        List<SummarySalaryMonthly> summarySalaryMonthly = summarySalaryMonthlyService.getList(yearMonth, deptName, empName, 1);
        List<EmpSalaryVo> empSalaryVos = BeanUtil.convertList(summarySalaryMonthly, EmpSalaryVo.class);
        if (!CollectionUtils.isEmpty(empSalaryVos)) {
            EmpSalaryVo empSalaryVo = null;
            for (int i = 0; i < empSalaryVos.size(); i++) {
                empSalaryVo = empSalaryVos.get(i);
                if (empSalaryVo == null) continue;

                //基本工资工时
                empSalaryVo.setBaseWorkHour(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getDayWork() == null ? 0 : empSalaryVo.getDayWork().doubleValue(), 8.0)));
                SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(empSalaryVo.getYearMonth());
                if (salaryMonthly == null) continue;
                //基本工资/(正常上班天数*8)*(白天上班天数*8)                                                                                                                     BigDecimalUtil.div(empSalaryVo.getDhbt().doubleValue()
                empSalaryVo.setBaseWorkHourSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getWorkDay() == null ? 0 : empSalaryVo.getWorkDay().doubleValue() * 8, BigDecimalUtil.div(salaryMonthly.getBasicSalary().doubleValue(), BigDecimalUtil.mul(salaryMonthly.getExpectWorkDay(), 8)))));
                empSalaryVo.setHourSalary(salaryMonthly.getHourlyWage());
                //正常加班工时金额
                empSalaryVo.setNormalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getNormalOvertime() == null ? 0 : empSalaryVo.getNormalOvertime().doubleValue(), 17.325)));

                //假日白班天数*8+假日晚班天数*3 * 23.1= 金额
                empSalaryVo.setHoliayOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getHoliayOvertime() == null ? 0 : empSalaryVo.getHoliayOvertime().doubleValue(), 23.1)));
                //法定假日（白班天数*8+晚班*3） * 3 * 11.55
                empSalaryVo.setLegalOvertimeSalary(BigDecimal.valueOf(BigDecimalUtil.mul(empSalaryVo.getLegalOvertime() == null ? 0 : empSalaryVo.getLegalOvertime().doubleValue(), BigDecimalUtil.mul(3, 11.55))));
                //汇总表覆盖计时工资
                empSalaryVo.setTimeSalary(BigDecimal.valueOf(BigDecimalUtil.add(empSalaryVo.getBaseWorkHourSalary() == null ? 0 : empSalaryVo.getBaseWorkHourSalary().doubleValue(),
                        empSalaryVo.getNormalOvertimeSalary() == null ? 0 : empSalaryVo.getNormalOvertimeSalary().doubleValue(),
                        empSalaryVo.getHoliayOvertimeSalary() == null ? 0 : empSalaryVo.getHoliayOvertimeSalary().doubleValue(),
                        empSalaryVo.getLegalOvertimeSalary() == null ? 0 : empSalaryVo.getLegalOvertimeSalary().doubleValue())));

                //绩效 应发工资-补贴合计-计时工资
                empSalaryVo.setAchievements(BigDecimal.valueOf(BigDecimalUtil.sub(empSalaryVo.getRealSalary() == null ? 0 : empSalaryVo.getRealSalary().doubleValue(),
                        empSalaryVo.getSumSusbsidy() == null ? 0 : empSalaryVo.getSumSusbsidy().doubleValue(),
                        empSalaryVo.getTimeSalary().doubleValue())));

                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(empSalaryVo.getId());
                row.createCell(j++).setCellValue(empSalaryVo.getYearMonth());
                row.createCell(j++).setCellValue(empSalaryVo.getEmpName());
                row.createCell(j++).setCellValue(empSalaryVo.getDeptName());
                row.createCell(j++).setCellValue(empSalaryVo.getWorkName());
                row.createCell(j++).setCellValue(empSalaryVo.getDayWork().toString());//白班
                row.createCell(j++).setCellValue(empSalaryVo.getNightWork().toString());//晚班
                row.createCell(j++).setCellValue(empSalaryVo.getScore().toString());//考勤分
                row.createCell(j++).setCellValue(empSalaryVo.getSumWorkHour().toString());//总工时
                row.createCell(j++).setCellValue(empSalaryVo.getHourSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getBasicSalary().toString()); //计件工资
                row.createCell(j++).setCellValue(empSalaryVo.getTimeSalary().toString());//即使工资
                row.createCell(j++).setCellValue(empSalaryVo.getNigthSnack().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getMealSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSocialSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getWorkYearSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getHourSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getPercentSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getLatheWorkerSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getNewLatheWorkerSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getOtherSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSumSusbsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getRealSalary().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getYlbx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getYiliaobx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSybx().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getMealSubsidy().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getOtherDeduction().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getSumDeduction().toString());
                row.createCell(j++).setCellValue(empSalaryVo.getTrueSalary().toString());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(yearMonth + "导出计件工工资明细表.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出计件工工资明细excel异常");
        }
    }
}
