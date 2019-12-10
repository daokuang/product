package com.amir.controller.emp.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuyq on 2019/9/18.
 */
public class EmpSalaryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String yearMonth;
    private Integer empId;
    private String empName;
    private Integer deptId;
    private String deptName;
    private String workName;
    private BigDecimal dayWork;
    private BigDecimal nightWork;
    private BigDecimal score;
    private BigDecimal sumWorkHour;
    private BigDecimal hourSalary;
    private BigDecimal restDay;
    private BigDecimal sumDay;
    private BigDecimal daySalary;
    private BigDecimal dhbt;
    private Integer type;
    private BigDecimal timeSalary;
    private BigDecimal nigthSnack;
    private BigDecimal phoneSubsidy;
    private BigDecimal mealSubsidy;
    private BigDecimal socialSubsidy;
    private BigDecimal hourSubsidy;
    private BigDecimal otherSubsidy;
    private BigDecimal sumSusbsidy;
    private BigDecimal realSalary;
    private BigDecimal ylbx;
    private BigDecimal sybx;
    private BigDecimal yiliaobx;
    private BigDecimal gjj;
    private BigDecimal otherDeduction;
    private BigDecimal sumDeduction;
    private BigDecimal trueSalary;
    private BigDecimal normalOvertime; //正常加班工时
    private BigDecimal normalOvertimeSalary;// 金额1 正常加班工时*17.325
    private BigDecimal holiayOvertime; //假日加班工时
    private BigDecimal holiayOvertimeSalary;// 金额2 假日加班工时*23.1
    private BigDecimal legalOvertime; //法假加班工时 法定假日（白班天数*8+晚班*3）
    private BigDecimal legalOvertimeSalary;// 法假日加班工时(白班天数*8+晚班*3)*3*基本时薪(11.55)
    private Date createTime;
    private Date lastModifyTime;
    private Integer isDelete;
    private BigDecimal workDay;
    private BigDecimal basicSalary;

    //计件工特有
    private BigDecimal workYearSubsidy; // 工龄
    private BigDecimal percentSubsidy;  //计件10%
    private BigDecimal latheWorkerSubsidy;  //车工
    private BigDecimal newLatheWorkerSubsidy; // 新车工
    private BigDecimal twoSideSubsidy; //复面补贴

    //汇总表 特有
    private BigDecimal baseWorkHour;// 基本工资工时  正常白班天数*8
    private BigDecimal baseWorkHourSalary;// 基本工资工时金额
    private BigDecimal achievements;// 绩效


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public BigDecimal getDayWork() {
        return dayWork;
    }

    public void setDayWork(BigDecimal dayWork) {
        this.dayWork = dayWork;
    }

    public BigDecimal getNightWork() {
        return nightWork;
    }

    public void setNightWork(BigDecimal nightWork) {
        this.nightWork = nightWork;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getSumWorkHour() {
        return sumWorkHour;
    }

    public void setSumWorkHour(BigDecimal sumWorkHour) {
        this.sumWorkHour = sumWorkHour;
    }

    public BigDecimal getHourSalary() {
        return hourSalary;
    }

    public void setHourSalary(BigDecimal hourSalary) {
        this.hourSalary = hourSalary;
    }

    public BigDecimal getRestDay() {
        return restDay;
    }

    public void setRestDay(BigDecimal restDay) {
        this.restDay = restDay;
    }

    public BigDecimal getSumDay() {
        return sumDay;
    }

    public void setSumDay(BigDecimal sumDay) {
        this.sumDay = sumDay;
    }

    public BigDecimal getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(BigDecimal daySalary) {
        this.daySalary = daySalary;
    }

    public BigDecimal getDhbt() {
        return dhbt;
    }

    public void setDhbt(BigDecimal dhbt) {
        this.dhbt = dhbt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getTimeSalary() {
        return timeSalary;
    }

    public void setTimeSalary(BigDecimal timeSalary) {
        this.timeSalary = timeSalary;
    }

    public BigDecimal getNigthSnack() {
        return nigthSnack;
    }

    public void setNigthSnack(BigDecimal nigthSnack) {
        this.nigthSnack = nigthSnack;
    }

    public BigDecimal getPhoneSubsidy() {
        return phoneSubsidy;
    }

    public void setPhoneSubsidy(BigDecimal phoneSubsidy) {
        this.phoneSubsidy = phoneSubsidy;
    }

    public BigDecimal getMealSubsidy() {
        return mealSubsidy;
    }

    public void setMealSubsidy(BigDecimal mealSubsidy) {
        this.mealSubsidy = mealSubsidy;
    }

    public BigDecimal getSocialSubsidy() {
        return socialSubsidy;
    }

    public void setSocialSubsidy(BigDecimal socialSubsidy) {
        this.socialSubsidy = socialSubsidy;
    }

    public BigDecimal getHourSubsidy() {
        return hourSubsidy;
    }

    public void setHourSubsidy(BigDecimal hourSubsidy) {
        this.hourSubsidy = hourSubsidy;
    }

    public BigDecimal getOtherSubsidy() {
        return otherSubsidy;
    }

    public void setOtherSubsidy(BigDecimal otherSubsidy) {
        this.otherSubsidy = otherSubsidy;
    }

    public BigDecimal getSumSusbsidy() {
        return sumSusbsidy;
    }

    public void setSumSusbsidy(BigDecimal sumSusbsidy) {
        this.sumSusbsidy = sumSusbsidy;
    }

    public BigDecimal getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(BigDecimal realSalary) {
        this.realSalary = realSalary;
    }

    public BigDecimal getYlbx() {
        return ylbx;
    }

    public void setYlbx(BigDecimal ylbx) {
        this.ylbx = ylbx;
    }

    public BigDecimal getSybx() {
        return sybx;
    }

    public void setSybx(BigDecimal sybx) {
        this.sybx = sybx;
    }

    public BigDecimal getYiliaobx() {
        return yiliaobx;
    }

    public void setYiliaobx(BigDecimal yiliaobx) {
        this.yiliaobx = yiliaobx;
    }

    public BigDecimal getGjj() {
        return gjj;
    }

    public void setGjj(BigDecimal gjj) {
        this.gjj = gjj;
    }

    public BigDecimal getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(BigDecimal otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    public BigDecimal getSumDeduction() {
        return sumDeduction;
    }

    public void setSumDeduction(BigDecimal sumDeduction) {
        this.sumDeduction = sumDeduction;
    }

    public BigDecimal getTrueSalary() {
        return trueSalary;
    }

    public void setTrueSalary(BigDecimal trueSalary) {
        this.trueSalary = trueSalary;
    }

    public BigDecimal getNormalOvertime() {
        return normalOvertime;
    }

    public void setNormalOvertime(BigDecimal normalOvertime) {
        this.normalOvertime = normalOvertime;
    }

    public BigDecimal getNormalOvertimeSalary() {
        return normalOvertimeSalary;
    }

    public void setNormalOvertimeSalary(BigDecimal normalOvertimeSalary) {
        this.normalOvertimeSalary = normalOvertimeSalary;
    }

    public BigDecimal getHoliayOvertime() {
        return holiayOvertime;
    }

    public void setHoliayOvertime(BigDecimal holiayOvertime) {
        this.holiayOvertime = holiayOvertime;
    }

    public BigDecimal getHoliayOvertimeSalary() {
        return holiayOvertimeSalary;
    }

    public void setHoliayOvertimeSalary(BigDecimal holiayOvertimeSalary) {
        this.holiayOvertimeSalary = holiayOvertimeSalary;
    }

    public BigDecimal getLegalOvertime() {
        return legalOvertime;
    }

    public void setLegalOvertime(BigDecimal legalOvertime) {
        this.legalOvertime = legalOvertime;
    }

    public BigDecimal getLegalOvertimeSalary() {
        return legalOvertimeSalary;
    }

    public void setLegalOvertimeSalary(BigDecimal legalOvertimeSalary) {
        this.legalOvertimeSalary = legalOvertimeSalary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public BigDecimal getWorkDay() {
        return workDay;
    }

    public void setWorkDay(BigDecimal workDay) {
        this.workDay = workDay;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getWorkYearSubsidy() {
        return workYearSubsidy;
    }

    public void setWorkYearSubsidy(BigDecimal workYearSubsidy) {
        this.workYearSubsidy = workYearSubsidy;
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

    public BigDecimal getBaseWorkHour() {
        return baseWorkHour;
    }

    public void setBaseWorkHour(BigDecimal baseWorkHour) {
        this.baseWorkHour = baseWorkHour;
    }

    public BigDecimal getBaseWorkHourSalary() {
        return baseWorkHourSalary;
    }

    public void setBaseWorkHourSalary(BigDecimal baseWorkHourSalary) {
        this.baseWorkHourSalary = baseWorkHourSalary;
    }

    public BigDecimal getAchievements() {
        return achievements;
    }

    public void setAchievements(BigDecimal achievements) {
        this.achievements = achievements;
    }
}
