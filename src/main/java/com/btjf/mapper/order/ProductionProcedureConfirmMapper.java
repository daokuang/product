package com.btjf.mapper.order;

import com.btjf.model.order.Order;
import com.btjf.model.order.ProductionProcedureConfirm;
import com.btjf.model.order.ProductionProcedureConfirmExample;
import com.btjf.model.product.ProductProcedure;
import com.btjf.vo.ProcedureYieldVo;
import com.btjf.vo.weixin.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductionProcedureConfirmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    long countByExample(ProductionProcedureConfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int deleteByExample(ProductionProcedureConfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int insert(ProductionProcedureConfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int insertSelective(ProductionProcedureConfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    List<ProductionProcedureConfirm> selectByExample(ProductionProcedureConfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    ProductionProcedureConfirm selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ProductionProcedureConfirm record, @Param("example") ProductionProcedureConfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ProductionProcedureConfirm record, @Param("example") ProductionProcedureConfirmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductionProcedureConfirm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_production_procedure_confirm
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductionProcedureConfirm record);

    /**
     * 注意！这里订单的创建时间被设为该订单下最新的生产单的创建时间
     */
    List<Order> getOrderByMouth(@Param("date") String date, @Param("deptName")String deptName);

    List<OrderProductVo> getOrderProductByMouth(@Param("orderNo")String orderNo,
                                                @Param("date") String date, @Param("deptName")String deptName);

    List<ProductProcedure> getByOrderAndProduct(@Param("orderNo")String orderNo, @Param("productNo")String productNo,
                                                @Param("date") String date, @Param("deptName")String deptName);

    List<EmpProcedureDetailVo> getEmpNum(@Param("orderNo")String orderNo, @Param("productNo")String productNo, @Param("id")Integer id,
                                         @Param("date") String date, @Param("deptName")String deptName);

    /**
     * 检查该生产单的该工序是否已调整，大于0表示已调整
     */
    Boolean checkProcedureConfirmChanged(@Param("orderNo") String orderNo, @Param("productNo") String productNo, @Param("id") Integer id,
                                         @Param("date") String date, @Param("deptName") String deptName);

    List<ProductionProcedureConfirm> select(ProductionProcedureConfirm productionProcedureConfirm);

    int delete(@Param("orderNo") String orderNo,
               @Param("productNo") String productNo, @Param("productionNo") String productionNo,
               @Param("louId") Integer louId,@Param("billOutNo") String billOutNo, @Param("procedureId") Integer procedureId);

    void deleteType2(@Param("orderNo")String orderNo, @Param("productNo")String productNo, @Param("id")Integer id,
                     @Param("date") String date, @Param("deptName")String deptName);

    List<ProductionProcedureConfirm> getCheckList(@Param("orderNo")String orderNo, @Param("productNo")String productNo,
                                                  @Param("date") String date, @Param("id")Integer id, @Param("deptName")String deptName);

    void updateChange(@Param("orderNo")String orderNo, @Param("productNo")String productNo,
                      @Param("date") String date, @Param("id")Integer id, @Param("deptName")String deptName);

    List<EmpDayWorkVo> analyseForDay(@Param("date")String date, @Param("empId")Integer empId);

    List<EmpDayWorkDetailVo> getWorkForDay(@Param("date")String date, @Param("empId")Integer empId);

    List<ProcedureInfoVo> getWorkProcedureInfo(@Param("date")String date, @Param("empId")Integer empId,
                                               @Param("orderNo")String orderNo, @Param("productNo")String productNo,
                                               @Param("billNo")String billNo, @Param("luoId")Integer luoId,
                                               @Param("type")Integer type);

    List<ProcedureYieldVo> yieldList(@Param("name")String name, @Param("deptId")Integer deptId, @Param("workId")Integer workId,
                                     @Param("orderNo")String orderNo,@Param("productNo")String productNo, @Param("procedureName")String procedureName,
                                     @Param("yearMonth")String yearMonth,@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("confirmed") Integer confirmed);

    List<ProductionProcedureConfirm> getUnSettlement(@Param("yearMonth")String yearMonth, @Param("empId")Integer empId);

    void updateSettlement(@Param("ids")List<Integer> ids);

    Integer getHandleNum(@Param("orderNo") String orderNo, @Param("procedureName") String procedureName, @Param("productNo") String productNo);

    ProductionProcedureConfirm getType2(@Param("orderNo") String orderNo, @Param("procedureName") String procedureName, @Param("productNo") String productNo);
}