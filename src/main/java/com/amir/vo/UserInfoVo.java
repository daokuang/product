package com.amir.vo;

import com.amir.model.sys.SysUser;

import java.io.Serializable;

public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = -5470738157139736496L;

    private Integer id;
    private Integer deptId;
    private Integer roleId;

    private String deptName;
    private String roleName;
    private String loginName;
    private String userName;
    private String secretKey;

    public UserInfoVo() {
    }

    public UserInfoVo(SysUser sysUser) {
        if (sysUser != null) {
            this.id = sysUser.getId();
            this.deptId = sysUser.getDeptId();
            this.roleId = sysUser.getRoleId();
            this.loginName = sysUser.getLoginName();
            this.userName = sysUser.getUserName();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
