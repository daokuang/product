package com.amir.vo.weixin;

public class MineIndexVo {

    private Integer empId;
    private String name;
    private String deptName;
    private String position;
    private Integer isShowMenu;//是否展示 计件上报

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getIsShowMenu() {
        return isShowMenu;
    }

    public void setIsShowMenu(Integer isShowMenu) {
        this.isShowMenu = isShowMenu;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
}
