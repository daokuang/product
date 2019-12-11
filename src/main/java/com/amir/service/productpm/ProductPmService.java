package com.amir.service.productpm;

import com.amir.mapper.product.ProductMapper;
import com.amir.mapper.product.ProductPmMapper;
import com.amir.model.common.Page;
import com.amir.model.product.Product;
import com.amir.model.product.ProductPm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/7/29.
 */
@Service
public class ProductPmService {

    @Resource
    private ProductPmMapper productpmMapper;

    @Resource
    private ProductMapper productMapper;


    public Page<ProductPm> findListPage(String productNo, String pmNo, Integer status, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<ProductPm> productpms = productpmMapper.findList(productNo, pmNo, status);
        PageInfo pageInfo = new PageInfo(productpms);
        pageInfo.setList(productpms);
        return new Page<>(pageInfo);
    }

    public List<ProductPm> findList(String productNo, String pmNo, Integer status) {
        List<ProductPm> productpms = productpmMapper.findList(productNo, pmNo, status);
        return productpms;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Integer updateStatue(List<Integer> ids) {
        try {
            ids.stream().forEach(t -> {
                ProductPm productpm = new ProductPm();
                productpm.setId(t);
                productpm.setLastModifyTime(new Date());
                productpm.setStatus(1);
                productpmMapper.updateByPrimaryKeySelective(productpm);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return ids.size();
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Integer delete(List<Integer> ids) {
        try {
            ids.stream().forEach(t -> {
                ProductPm productpm = new ProductPm();
                productpm.setId(t);
                productpm.setLastModifyTime(new Date());
                productpm.setIsDelete(1);
                productpmMapper.updateByPrimaryKeySelective(productpm);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return ids.size();
    }

    public ProductPm getByID(Integer id) {
        return productpmMapper.selectByPrimaryKey(id);
    }

    public ProductPm getByNo(String productNo) {
        return productpmMapper.selectByNo(productNo);
    }

    public ProductPm getByNoAndPmNo(String productNo, String pmNo) {
        return productpmMapper.selectByNoAndPmNo(productNo, pmNo);
    }

    public Integer update(ProductPm productPm) {
        return productpmMapper.updateByPrimaryKeySelective(productPm);
    }

    public Integer add(ProductPm productPm) {
        productPm.setProductId(insertProduct(productPm));
        productpmMapper.insertSelective(productPm);
        return productPm.getId();
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Integer addBySameModel(String oldModel, String newModel) {
        List<ProductPm> productPms = productpmMapper.findListByProductNo(oldModel);
        Integer row = 0;
        if (productPms != null && productPms.size() > 0) {
            productPms.stream().filter(t -> t != null).forEach(t -> {
                t.setProductNo(newModel);
                t.setId(null);
                productpmMapper.insertSelective(t);
            });
            row = productPms.size();
        }
        return row;
    }

    public Integer saveList(List<ProductPm> productPms) {
        //初始化产品表
        if (!CollectionUtils.isEmpty(productPms)) {
            for (ProductPm productPm : productPms) {
                productPm.setProductId(insertProduct(productPm));
            }
        }

        return productpmMapper.saveList(productPms);
    }

    public Integer insertProduct(ProductPm productPm) {
        if (null == productMapper.getByNo(productPm.getProductNo())) {
            Product product = new Product();
            product.setCreateTime(new Date());
            product.setOperator("系统");
            product.setProductNo(productPm.getProductNo());
            product.setType(productPm.getType());
            product.setLastModifyTime(new Date());
            product.setIsDelete(0);
            product.setName(productPm.getProductNo());
            product.setUnit(productPm.getUnit());
            productMapper.insertSelective(product);
            return product.getId();
        } else {
            return 0;
        }
    }

    public List<ProductPm> findListByProductNoAndType(String productNo, String type) {
        return productpmMapper.findListByProductNoAndType(productNo, type);
    }

    public Integer deleteByPmNo(String pmNo) {
        return productpmMapper.deleteByPmNo(pmNo);
    }


    public Integer count(String productNo) {
        return productpmMapper.count(productNo);
    }
}
