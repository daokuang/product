package com.btjf.mapper.emp;

import com.btjf.controller.weixin.vo.WxEmpVo;
import com.btjf.model.emp.Emp;
import com.btjf.model.emp.EmpExample;
import com.btjf.vo.EmpSubsidyVo;
import com.btjf.vo.weixin.EmpVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    long countByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int deleteByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int insert(Emp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int insertSelective(Emp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    List<Emp> selectByExample(EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    Emp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Emp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_Emp
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Emp record);

    List<Emp> getLeaderByDeptID(@Param("id") Integer id);

    List<Emp> getByName(@Param("name")String name);

    WxEmpVo selectByPhone(@Param("phone") String phone, @Param("password") String password);

    List<WxEmpVo> getAll();

    Emp getByPhoneOrIdCard(@Param("phone") String phone, @Param("idCard") String idCard);

    Emp getByIdCard(@Param("idCard")String idCard);

    List<EmpVo> getList(@Param("name") String name, @Param("deptId") Integer deptId, @Param("nativeSource") String nativeSource,
                        @Param("startEntryDate") String startEntryDate, @Param("endEntryDate") String endEntryDate);

    List<EmpSubsidyVo> phoneSubsidyList(@Param("name")String name, @Param("deptId")Integer deptId);

    List<EmpSubsidyVo> socialSubsidyList(@Param("name")String name, @Param("deptId")Integer deptId);

    List<com.btjf.controller.emp.vo.EmpSubsidyVo> getByNameAndDeptNameList(@Param("empName") String empName, @Param("deptName") String deptName, @Param("type") Integer type);
}