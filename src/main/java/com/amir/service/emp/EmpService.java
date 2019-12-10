package com.amir.service.emp;

import com.amir.controller.emp.vo.EmpSubsidyVo;
import com.amir.controller.weixin.vo.WxEmpVo;
import com.amir.mapper.emp.EmpMapper;
import com.amir.model.Page;
import com.amir.model.emp.Emp;
import com.amir.vo.weixin.EmpVo;
import com.amir.vo.weixin.MineIndexVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/8/8.
 */
@Service
public class EmpService {

    @Resource
    private EmpMapper empMapper;


    public List<Emp> getLeaderByDeptID(Integer id) {
        return empMapper.getLeaderByDeptID(id);
    }

    public Emp getByName(String empName) {
        List<Emp> list = empMapper.getByName(empName);
        return (list == null || list.size() == 0) ? null : list.get(0);
    }

    public WxEmpVo login(String phone, String password) {
        WxEmpVo wxEmpVo = empMapper.selectByPhone(phone, password);
        return wxEmpVo;
    }

    public Emp getByPhoneOrIdCard(String phone, String idCard) {
        Emp emp = empMapper.getByPhoneOrIdCard(phone, idCard);
        return emp;
    }

    public Emp getByID(Integer id) {
        Emp emp = empMapper.selectByPrimaryKey(id);
        return emp;
    }

    public Integer update(Emp emp) {
        if (emp == null) return 0;
        return empMapper.updateByPrimaryKeySelective(emp);
    }

    public void create(Emp emp) {
        empMapper.insert(emp);
    }

    public Emp getByIdCard(String idCard) {
        return empMapper.getByIdCard(idCard);
    }

    public Page<EmpVo> getPage(String name, Integer deptId, String nativeSource, String startEntryDate, String endEntryDate, Page page) {

        PageHelper.startPage(page.getPage(), page.getRp());
        List<EmpVo> emps = this.getList(name, deptId, nativeSource, startEntryDate, endEntryDate);
        PageInfo pageInfo = new PageInfo(emps);
        pageInfo.setList(emps);
        return new Page<>(pageInfo);
    }

    public List<EmpVo> getList(String name, Integer deptId, String nativeSource, String startEntryDate, String endEntryDate) {
        return empMapper.getList(name, deptId, nativeSource, startEntryDate, endEntryDate);

    }

    public List<WxEmpVo> getAll() {
        return empMapper.getAll();
    }

    public Page<com.amir.vo.EmpSubsidyVo> phoneSubsidyList(String name, Integer deptId, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<com.amir.vo.EmpSubsidyVo> pmList = empMapper.phoneSubsidyList(name, deptId);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public Page<com.amir.vo.EmpSubsidyVo> socialSubsidyList(String name, Integer deptId, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<com.amir.vo.EmpSubsidyVo> pmList = empMapper.socialSubsidyList(name, deptId);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public Page<EmpSubsidyVo> getByNameAndDeptNamePage(String empName, String deptName, Integer type, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<EmpSubsidyVo> empSubsidyVos = this.getByNameAndDeptNameList(empName, deptName, type);
        PageInfo pageInfo = new PageInfo(empSubsidyVos);
        pageInfo.setList(empSubsidyVos);
        return new Page<>(pageInfo);
    }

    public List<EmpSubsidyVo> getByNameAndDeptNameList(String empName, String deptName, Integer type) {
        return empMapper.getByNameAndDeptNameList(empName, deptName, type);
    }

    public List<MineIndexVo> getByDeptId(Integer deptId) {
        return empMapper.getByDeptId(deptId);
    }
}
