package com.amir.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalaryHandler {

    public static void main(String[] args) {
        Integer months = getMonths("20120712");
        getWorkYearSubsidy(months, 2);
        getNewLatheWorkerSubsidy(months);
        //getTwoSideSubsidy(months);
    }

    public static Integer getMonths(String inDate) {
        Calendar from = getPerFirstDayOfMonth(inDate);
        Calendar to = getMinMonthDate(new Date());
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);

        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);

        return month;
    }

    /**
     * 获取复面补贴系数
     *
     * @return
     */
    public static Double getTwoSideSubsidy(Integer month, BigDecimal rate) {
        Double subsidy = 0.0d;
        if (month == 0) {
            subsidy = 1.0d;
        } else if (month == 1) {
            subsidy = 0.6d;
        } else if (month > 1 && month <= 3) {
            subsidy = 0.5d;
        } else if (month > 3 && month <= 5) {
            subsidy = 0.3d;
        } else if (month > 5) {
            subsidy = rate.doubleValue();
        }
        return subsidy;
    }

    /**
     * 获取工龄补贴
     *
     * @return
     */
    public static Double getNewLatheWorkerSubsidy(Integer month) {

        Double subsidy = 0.0d;
        if (month == 0) {
            subsidy = 50.0d;
        } else if (month == 1) {
            subsidy = 40.0d;
        } else if (month > 1 && month <= 4) {
            subsidy = 30.0d;
        } else if (month > 4 && month <= 7) {
            subsidy = 20.0d;
        } else if (month > 7 && month <= 10) {
            subsidy = 10.0d;
        } else if (month > 10) {
            subsidy = 5.0d;
        }
        return subsidy;
    }

    /**
     * 获取工龄补贴
     *
     * @param isMore
     * @return
     */
    public static Double getWorkYearSubsidy(Integer month, Integer isMore) {
        Double subsidy = 0.0d;
        if (month <= 12) {

        } else if (month > 12 && month <= 24) {
            subsidy = 30.0d;
        } else if (month > 12 && month <= 24) {
            subsidy = 30.0d;
        } else if (month > 24 && month <= 60) {
            subsidy = 50.0d;
        } else if (month > 60 && month <= 84) {
            subsidy = 80.0d;
        } else if (month > 84) {
            subsidy = 100.0d;
        }

        if (isMore == 2) {
            subsidy = BigDecimal.valueOf(subsidy).multiply(BigDecimal.valueOf(0.5)).doubleValue();
        }

        return subsidy;
    }

    /**
     * 描述:获取下一个月的第一天.
     *
     * @return
     */
    public static Calendar getPerFirstDayOfMonth(String repeatDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            if (StringUtils.isNotBlank(repeatDate) && !"null".equals(repeatDate)) {
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar;
    }


    /**
     * 获取任意时间的月第一天
     * 描述:<描述函数实现的功能>.
     *
     * @return
     */
    private static Calendar getMinMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

}
