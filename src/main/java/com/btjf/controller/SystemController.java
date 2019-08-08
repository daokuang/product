package com.btjf.controller;

import com.btjf.application.util.XaResult;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.model.emp.Emp;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.model.sys.Sysdept;
import com.btjf.service.dictionary.DictionaryService;
import com.btjf.service.emp.EmpService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.btjf.service.sys.SysDeptService;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Random;
import java.util.UUID;


/**
 * Created by Administrator on 2018/7/3 0003.
 */
@Api(value = "SystemController", description = "系统", position = 1)
@RequestMapping(value = "/system")
@RestController("systemController")
public class SystemController {

    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private EmpService empService;
    @Resource
    private ProductWorkshopService productWorkshopService;

    /**
     * 获取数据
     *
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public XaResult<List<String>> data(@ApiParam("数据类型 1 材料类别  2材料单位  3产品类型") Integer type) {
        if (type == null) {
            return XaResult.error("数据类型为空");
        }
        List<String> list = dictionaryService.getList(type);
        return XaResult.success(list);
    }

    /**
     * 获取部门
     *
     * @return
     */
    @RequestMapping(value = "/dept", method = RequestMethod.GET)
    public XaResult<List<Sysdept>> dept() {
        List<Sysdept> list = sysDeptService.getList();
        return XaResult.success(list);
    }

    /**
     * 获取单号
     * type 1 领料单  2订单号开单 3 订单 4 生产单
     *
     * @return
     */
    @RequestMapping(value = "/getNo", method = RequestMethod.GET)
    public XaResult<String> getNo(Integer type) {
        return XaResult.success(getBillNo(type));
    }

    /**
     * 获取车间及车间负责人及车间工序
     *
     * @return
     */
    @RequestMapping(value = "/workshop", method = RequestMethod.GET)
    public XaResult<List<WorkShopVo>> workshop(){
        List<Sysdept> list = sysDeptService.getWorkshopList();
        List<WorkShopVo> workShopVos = Lists.newArrayList();
        for (Sysdept sysdept : list) {
            if (null == sysdept) continue;
            List<Emp> emps = empService.getLeaderByDeptID(sysdept.getId());
            List<ProductProcedureWorkshop> productProcedureWorkshops = productWorkshopService.findByWorkshopName(sysdept.getDeptName());
            workShopVos.add(new WorkShopVo(sysdept, emps, productProcedureWorkshops));
        }
        return XaResult.success(workShopVos);
    }


    private String getBillNo(Integer type) {
        if(type == 1){
            return "L" + getDateTime() + getOrderIdByUUId();
        }else if(type == 2){
            return "D" + getDateTime() + getOrderIdByUUId();
        }else if(type == 3){
            return "O" + getDateTime() + getOrderIdByUUId();
        }else if(type == 4){
            return "P" + getDateTime() + getOrderIdByUUId();
        }else{
            return null;
        }
    }

    private static String getDateTime(){
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    private String getOrderIdByUUId() {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }
}
