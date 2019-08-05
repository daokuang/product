package com.btjf.controller.customer;

/**
 * Created by liuyq on 2019/8/4.
 */

import com.btjf.application.components.xaresult.AppXaResultHelper;
import com.btjf.application.util.XaResult;
import com.btjf.common.page.Page;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.model.customer.Customer;
import com.btjf.service.customer.CustomerService;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@Api(value = "CustomerController", description = "客户管理", position = 1)
public class CustomerController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(CustomerController.class);

    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<Customer>> list(String name, String startDate, String endDate, Integer pageSize, Integer currentPage) {

        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<Customer> listPage = customerService.findListPage(name, startDate, endDate, page);
        XaResult<List<Customer>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<Customer> detail(Integer id) {
        if (id == null) return XaResult.error("id必填");

        Customer customer = customerService.getByID(id);
        if (customer == null) {
            return XaResult.error("该客户不存在");
        } else {
            return XaResult.success(customer);
        }
    }


    @RequestMapping(value = "/updateOrAdd", method = RequestMethod.POST)
    public XaResult<Integer> updateOrAdd(Integer id, String name, String phone, String tel, String address, String level, String industry,
                                         String source, String product, String linkMan, String salesman, String email, String qq,
                                         String msn, String bankName, String bankNo, String legalPerson, String duty, String remark) {

        if (name == null) return XaResult.error("名称必填");

        Customer customer = new Customer();
        customer.setName(name);
        customer.setIsDelete(0);
        customer.setLastModifyTime(new Date());
        customer.setAddress(address);
        customer.setBankName(bankName);
        customer.setMsn(msn);
        customer.setTel(tel);
        customer.setBankNo(bankNo);
        customer.setSalesman(salesman);
        customer.setPhone(phone);
        customer.setLevel(level);
        customer.setSource(source);
        customer.setProduct(product);
        customer.setLinkMan(linkMan);
        customer.setLegalPerson(legalPerson);
        customer.setDuty(duty);
        customer.setEmail(email);
        customer.setRemark(remark);
        customer.setIndustry(industry);
        customer.setQq(qq);

        //更新
        if (id != null) {
            customer.setId(id);
            customerService.updateByID(customer);
        } else {
            customer.setCreateTime(new Date());
            id = customerService.insert(customer);
        }
        return XaResult.success(id);

    }
}
