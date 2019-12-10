package com.amir.excel;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.common.utils.DateUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yj on 2019/7/31.
 */
public abstract class BaseExcelHandler {

    public static ThreadLocal<String> yearMonthCash = ThreadLocal.withInitial(() -> DateUtil.dateToString(new Date(), DateUtil.ymFormat));

    /**
     * 判断是否是对应的格式的日期字符串
     *
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static boolean isRightDateStr(String dateStr, String datePattern) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        try {
            //采用严格的解析方式，防止类似 “2017-05-35” 类型的字符串通过
            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
            Date date = (Date) dateFormat.parse(dateStr);
            //重复比对一下，防止类似 “2017-5-15” 类型的字符串通过
            String newDateStr = dateFormat.format(date);
            if (dateStr.equals(newDateStr)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    public abstract List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception;

    public List<String> checkLayout(MultipartFile file, List<String> fields, String operator) throws Exception {

        InputStream is = file.getInputStream();
        Workbook wb = WorkbookFactory.create(is);
        Sheet sheet = wb.getSheetAt(0);
        // 每页中的第一行为标题行，对标题行的特殊处理
        XSSFRow firstRow = (XSSFRow) sheet.getRow(0);
        int celllength = firstRow.getLastCellNum();

        List<String> response = new ArrayList<>();
        List<String> errResponse = new ArrayList<>();
        // 获取Excel中的列名
        for (int f = 0; f < celllength; f++) {
            XSSFCell cell = firstRow.getCell(f);
            String field = cell.getStringCellValue().trim();
            if (!fields.get(f).equals(field)) {
                wb.close();
                response.add("导入文档数据格式不符，请重选文件");
                response.add("Excel格式出错,第" + (f + 1) + "列表头应该为:" + fields.get(f));
                return response;
            }
        }
        // 将sheet转换为list
        List<T> result = new ArrayList<>();
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            XSSFRow row = (XSSFRow) sheet.getRow(j);
            if (row == null) continue;
            try {
                result.addAll(create(row));
            } catch (Exception e) {
                yearMonthCash.remove();
                e.printStackTrace();
                errResponse.add("第" + (j + 1) + "行数据 " + e.getMessage());
            }
        }
        //检验要导入的数据重复问题

        if (this.getClass().equals(ProductWorkshopExcelHandler.class) || getClass().equals(ProductPmExcelHandler.class)) {
            if (CollectionUtils.isNotEmpty(result)) {
                insert(result, operator);
                response.add("提交成功！新增导入" + result.size() + "条数据！");
            }
            if (errResponse.size() > 0) {
                int sum = sheet.getLastRowNum() - errResponse.size();
                response.add("导入失败，以下数据请修改后再重新上传");
                response.addAll(errResponse);
            }
        } else {
            if (errResponse.size() > 0) {
                int sum = sheet.getLastRowNum() - errResponse.size();
                response.add("导入失败，以下数据请修改后再重新上传");
                response.addAll(errResponse);
            } else {
                insert(result, operator);
                response.add("提交成功！新增导入" + result.size() + "条数据！");
            }
        }

        yearMonthCash.remove();
        wb.close();
        return response;
    }

    protected abstract void insert(List<T> list, String operator);

    protected abstract List<T> create(XSSFRow row) throws Exception;

    public String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType().ordinal() - 1) {
            case 0: // 数字
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case 1: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case 4: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case 2: // 公式
                //cellValue = cell.getCellFormula() + "";
                try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }

                break;
            case 3: // 空值
                cellValue = "";
                break;
            case 5: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue.trim();
    }
}
