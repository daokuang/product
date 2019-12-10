package com.amir.service.customer;

import com.amir.mapper.customer.CustomerMapper;
import com.amir.model.Page;
import com.amir.model.customer.Customer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/8/4.
 */
@Service
public class CustomerService {
    @Resource
    private CustomerMapper customerMapper;

    public Page<Customer> findListPage(String name, String startDate, String endDate, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<Customer> pmList = customerMapper.findList(name, startDate, endDate);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public Customer getByID(Integer id) {
        if (id == null) return null;

        return customerMapper.selectByPrimaryKey(id);
    }

    public Customer getByName(String name) {
        if (name == null) return null;

        return customerMapper.getByName(name);
    }

    public Integer updateByID(Customer customer) {
        if (customer == null) return 0;
        return customerMapper.updateByPrimaryKeySelective(customer);
    }

    public Integer insert(Customer customer) {
        if (customer == null) return 0;
        customerMapper.insertSelective(customer);
        return customer.getId();
    }

    public List<Customer> findList(String name, String startDate, String endDate) {
        List<Customer> pmList = customerMapper.findList(name, startDate, endDate);
        return pmList;
    }
}
