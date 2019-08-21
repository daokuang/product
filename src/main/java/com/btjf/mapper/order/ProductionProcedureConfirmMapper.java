package com.btjf.mapper.order;

import com.btjf.model.order.Order;
import com.btjf.model.order.ProductionProcedureConfirm;
import com.btjf.model.order.ProductionProcedureConfirmExample;
import com.btjf.model.product.ProductProcedure;
import com.btjf.vo.weixin.EmpProcedureDetailVo;
import com.btjf.vo.weixin.OrderProductVo;
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

    List<Order> getOrderByMouth(@Param("date")String date);

    List<OrderProductVo> getOrderProductByMouth(@Param("orderNo")String orderNo);

    List<ProductProcedure> getByOrderAndProduct(@Param("orderNo")String orderNo, @Param("productNo")String productNo);

    List<EmpProcedureDetailVo> getEmpNum(@Param("orderNo")String orderNo, @Param("productNo")String productNo, @Param("id")Integer id);

    List<ProductionProcedureConfirm> select(ProductionProcedureConfirm productionProcedureConfirm);

    int delete(@Param("orderNo") String orderNo,
               @Param("productNo") String productNo, @Param("productionNo") String productionNo,
               @Param("louId") Integer louId,@Param("billOutNo") String billOutNo, @Param("") Integer procedureId);
}