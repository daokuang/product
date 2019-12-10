package com.amir.service.productpm;

import com.amir.mapper.product.ProductMapper;
import com.amir.model.product.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by liuyq on 2019/8/6.
 */
@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    public Product getByNO(String productNo) {
        return productMapper.getByNo(productNo);
    }

    public Integer add(Product product) {
        return Optional.ofNullable(product).map(v -> {
            productMapper.insertSelective(product);
            return product.getId();
        }).orElse(0);
    }

    public Integer update(Product product) {
        return Optional.ofNullable(product).map(v -> {
            productMapper.updateByPrimaryKeySelective(product);
            return product.getId();
        }).orElse(0);
    }

    public List<Product> getAll() {
        return productMapper.getList(null, null);
    }
}
