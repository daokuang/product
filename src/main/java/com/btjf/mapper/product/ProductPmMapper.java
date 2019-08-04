package com.btjf.mapper.product;

import com.btjf.model.product.ProductPm;
import com.btjf.model.product.ProductPmExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductPmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    long countByExample(ProductPmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int deleteByExample(ProductPmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int insert(ProductPm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int insertSelective(ProductPm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    List<ProductPm> selectByExample(ProductPmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    ProductPm selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ProductPm record, @Param("example") ProductPmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ProductPm record, @Param("example") ProductPmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductPm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_productpm
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductPm record);

    List<ProductPm> findList(@Param("productNo") String productNo, @Param("pmNo") String pmNo, @Param("status") Integer status);

    ProductPm selectByNo(@Param("productNo") String productNo);

    ProductPm selectByNoAndPmNo(@Param("productNo") String productNo, @Param("pmNO") String pmNO);

    List<ProductPm> findListByProductNo(@Param("productNo") String productNo);

    Integer saveList(@Param("productPmList") List<ProductPm> productPmList);
}