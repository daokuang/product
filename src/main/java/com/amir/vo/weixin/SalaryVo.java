package com.amir.vo.weixin;

import com.amir.model.emp.SummarySalaryMonthly;

import java.math.BigDecimal;

public class SalaryVo {

    private BigDecimal trueSalary;

    private BigDecimal basicSalary;//基本工资、计件工资
    private BigDecimal timeSalary;
    private BigDecimal score;

    private BigDecimal sumIncome;//收入合计
    private BigDecimal realSalary;
    private BigDecimal mealSubsidy;
    private BigDecimal nigthSnack;
    private BigDecimal socialSubsidy;
    private BigDecimal workYearSubsidy;
    private BigDecimal hourSubsidy;
    private BigDecimal percentSubsidy;
    private BigDecimal latheWorkerSubsidy;
    private BigDecimal newLatheWorkerSubsidy;
    private BigDecimal twoSideSubsidy;
    private BigDecimal otherSubsidy;

    private BigDecimal sumDeduction;//扣减合计
    private BigDecimal ylbx;
    private BigDecimal yiliaobx;
    private BigDecimal sybx;
    private BigDecimal mealDeduction;
    private BigDecimal otherDeduction;

    private BigDecimal sumCompany;//公司缴纳

    public SalaryVo() {
    }

    public SalaryVo(SummarySalaryMonthly summarySalaryMonthly) {
        basicSalary = summarySalaryMonthly.getBasicSalary() == null ? BigDecimal.ZERO : summarySalaryMonthly.getBasicSalary();
        trueSalary = summarySalaryMonthly.getTrueSalary() == null ? BigDecimal.ZERO : summarySalaryMonthly.getTrueSalary();
        timeSalary = summarySalaryMonthly.getTimeSalary() == null ? BigDecimal.ZERO : summarySalaryMonthly.getTimeSalary();
        score = summarySalaryMonthly.getScore() == null ? BigDecimal.ZERO : summarySalaryMonthly.getScore();

        realSalary = summarySalaryMonthly.getRealSalary() == null ? BigDecimal.ZERO : summarySalaryMonthly.getRealSalary();
        mealSubsidy = summarySalaryMonthly.getMealSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getMealSubsidy();
        nigthSnack = summarySalaryMonthly.getNigthSnack() == null ? BigDecimal.ZERO : summarySalaryMonthly.getNigthSnack();
        socialSubsidy = summarySalaryMonthly.getSocialSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getSocialSubsidy();
        workYearSubsidy = summarySalaryMonthly.getWorkYearSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getWorkYearSubsidy();
        hourSubsidy = summarySalaryMonthly.getHourSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getHourSubsidy();
        percentSubsidy = summarySalaryMonthly.getPercentSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getPercentSubsidy();
        latheWorkerSubsidy = summarySalaryMonthly.getLatheWorkerSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getLatheWorkerSubsidy();
        newLatheWorkerSubsidy = summarySalaryMonthly.getNewLatheWorkerSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getNewLatheWorkerSubsidy();
        twoSideSubsidy = summarySalaryMonthly.getTwoSideSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getTwoSideSubsidy();
        otherSubsidy = summarySalaryMonthly.getOtherSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getOtherSubsidy();

        ylbx = summarySalaryMonthly.getYlbx() == null ? BigDecimal.ZERO : summarySalaryMonthly.getYlbx();
        yiliaobx = summarySalaryMonthly.getYiliaobx() == null ? BigDecimal.ZERO : summarySalaryMonthly.getYiliaobx();
        sybx = summarySalaryMonthly.getSybx() == null ? BigDecimal.ZERO : summarySalaryMonthly.getSybx();
        mealDeduction = summarySalaryMonthly.getMealSubsidy() == null ? BigDecimal.ZERO : summarySalaryMonthly.getMealSubsidy();
        otherDeduction = summarySalaryMonthly.getOtherDeduction() == null ? BigDecimal.ZERO : summarySalaryMonthly.getOtherDeduction();

        sumIncome = realSalary.add(mealSubsidy).add(nigthSnack).add(socialSubsidy).add(workYearSubsidy).add(hourSubsidy)
                .add(percentSubsidy).add(latheWorkerSubsidy).add(newLatheWorkerSubsidy).add(twoSideSubsidy).add(otherSubsidy);
        sumDeduction = summarySalaryMonthly.getSumDeduction() == null ? BigDecimal.ZERO : summarySalaryMonthly.getSumDeduction();
        sumCompany = ylbx.add(yiliaobx).add(sybx);
    }


    public BigDecimal getTrueSalary() {
        return trueSalary;
    }

    public void setTrueSalary(BigDecimal trueSalary) {
        this.trueSalary = trueSalary;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getTimeSalary() {
        return timeSalary;
    }

    public void setTimeSalary(BigDecimal timeSalary) {
        this.timeSalary = timeSalary;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(BigDecimal sumIncome) {
        this.sumIncome = sumIncome;
    }

    public BigDecimal getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(BigDecimal realSalary) {
        this.realSalary = realSalary;
    }

    public BigDecimal getMealSubsidy() {
        return mealSubsidy;
    }

    public void setMealSubsidy(BigDecimal mealSubsidy) {
        this.mealSubsidy = mealSubsidy;
    }

    public BigDecimal getNigthSnack() {
        return nigthSnack;
    }

    public void setNigthSnack(BigDecimal nigthSnack) {
        this.nigthSnack = nigthSnack;
    }

    public BigDecimal getSocialSubsidy() {
        return socialSubsidy;
    }

    public void setSocialSubsidy(BigDecimal socialSubsidy) {
        this.socialSubsidy = socialSubsidy;
    }

    public BigDecimal getWorkYearSubsidy() {
        return workYearSubsidy;
    }

    public void setWorkYearSubsidy(BigDecimal workYearSubsidy) {
        this.workYearSubsidy = workYearSubsidy;
    }

    public BigDecimal getHourSubsidy() {
        return hourSubsidy;
    }

    public void setHourSubsidy(BigDecimal hourSubsidy) {
        this.hourSubsidy = hourSubsidy;
    }

    public BigDecimal getPercentSubsidy() {
        return percentSubsidy;
    }

    public void setPercentSubsidy(BigDecimal percentSubsidy) {
        this.percentSubsidy = percentSubsidy;
    }

    public BigDecimal getLatheWorkerSubsidy() {
        return latheWorkerSubsidy;
    }

    public void setLatheWorkerSubsidy(BigDecimal latheWorkerSubsidy) {
        this.latheWorkerSubsidy = latheWorkerSubsidy;
    }

    public BigDecimal getNewLatheWorkerSubsidy() {
        return newLatheWorkerSubsidy;
    }

    public void setNewLatheWorkerSubsidy(BigDecimal newLatheWorkerSubsidy) {
        this.newLatheWorkerSubsidy = newLatheWorkerSubsidy;
    }

    public BigDecimal getTwoSideSubsidy() {
        return twoSideSubsidy;
    }

    public void setTwoSideSubsidy(BigDecimal twoSideSubsidy) {
        this.twoSideSubsidy = twoSideSubsidy;
    }

    public BigDecimal getOtherSubsidy() {
        return otherSubsidy;
    }

    public void setOtherSubsidy(BigDecimal otherSubsidy) {
        this.otherSubsidy = otherSubsidy;
    }

    public BigDecimal getSumDeduction() {
        return sumDeduction;
    }

    public void setSumDeduction(BigDecimal sumDeduction) {
        this.sumDeduction = sumDeduction;
    }

    public BigDecimal getYlbx() {
        return ylbx;
    }

    public void setYlbx(BigDecimal ylbx) {
        this.ylbx = ylbx;
    }

    public BigDecimal getYiliaobx() {
        return yiliaobx;
    }

    public void setYiliaobx(BigDecimal yiliaobx) {
        this.yiliaobx = yiliaobx;
    }

    public BigDecimal getSybx() {
        return sybx;
    }

    public void setSybx(BigDecimal sybx) {
        this.sybx = sybx;
    }

    public BigDecimal getMealDeduction() {
        return mealDeduction;
    }

    public void setMealDeduction(BigDecimal mealDeduction) {
        this.mealDeduction = mealDeduction;
    }

    public BigDecimal getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(BigDecimal otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    public BigDecimal getSumCompany() {
        return sumCompany;
    }

    public void setSumCompany(BigDecimal sumCompany) {
        this.sumCompany = sumCompany;
    }
}
