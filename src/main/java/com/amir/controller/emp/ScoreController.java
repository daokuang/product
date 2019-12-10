package com.amir.controller.emp;

import com.amir.controller.base.ProductBaseController;
import com.amir.model.emp.Score;
import com.amir.service.emp.ScoreService;
import com.wordnik.swagger.annotations.Api;
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
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by liuyq on 2019/9/11.
 */
@Api(value = "ScoreController", description = "考勤分管理", position = 1)
@RequestMapping(value = "/score/")
@RestController("scoreController")
public class ScoreController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(ScoreController.class);

    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "/exportFiveScore", method = RequestMethod.GET)
    public void exportThreeScore(String yearMonth, String empName, String deptName, HttpServletResponse response) {

        List<Score> scores = scoreService.getList(yearMonth, empName, deptName);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(2, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(3, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(5, (int) ((20 + 0.72) * 256));
        int j = 0;
        header.createCell(j++).setCellValue("名称");
        header.createCell(j++).setCellValue("考勤分");
        header.createCell(j++).setCellValue("五S分");
        header.createCell(j++).setCellValue("配合分");
        header.createCell(j++).setCellValue("质量分");
        header.createCell(j++).setCellValue("考核分");
        Score score = null;
        if (scores != null && scores.size() >= 1) {
            for (int i = 0; i < scores.size(); i++) {
                score = scores.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(score.getEmpName());
                row.createCell(j++).setCellValue(score.getCheckworkScore().toString());
                row.createCell(j++).setCellValue(score.getFiveScore().toString());
                row.createCell(j++).setCellValue(score.getCoordinationScore().toString());
                row.createCell(j++).setCellValue(score.getQualityScore().toString());
                row.createCell(j++).setCellValue(score.getScore().toString());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("5个分.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("5个分导出excel异常");
        }
    }


    @RequestMapping(value = "/exportCheckWorkScore", method = RequestMethod.GET)
    public void exportCheckWorkScore(String yearMonth, String empName, String deptName, HttpServletResponse response) {

        List<Score> scores = scoreService.getList(yearMonth, empName, deptName);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        int j = 0;
        header.createCell(j++).setCellValue("名称");
        header.createCell(j++).setCellValue("考勤分");
        Score score = null;
        if (scores != null && scores.size() >= 1) {
            for (int i = 0; i < scores.size(); i++) {
                score = scores.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(score.getEmpName());
                row.createCell(j++).setCellValue(score.getCheckworkScore().toString());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("考勤分.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("考勤分导出excel异常");
        }
    }


}
