package com.amir.controller;

import com.amir.controller.order.vo.WorkShopVo;
import com.amir.model.XaResult;
import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpWork;
import com.amir.model.sys.Sysdept;
import com.amir.service.dictionary.DictionaryService;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.EmpWorkService;
import com.amir.service.order.BillNoService;
import com.amir.service.productpm.ProductWorkshopService;
import com.amir.service.sys.SysDeptService;
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
import java.util.List;
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
    @Resource
    private BillNoService billNoService;
    @Resource
    private EmpWorkService empWorkService;

    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

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
     * 获取工种
     *
     * @return
     */
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public XaResult<List<EmpWork>> work() {
        List<EmpWork> list = empWorkService.getList();
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
        String pre = getBillNo(type);
        return XaResult.success(pre == null ? "" : pre + billNoService.getNo(type));
    }

    /**
     * 获取车间及车间负责人
     *
     * @return
     */
    @RequestMapping(value = "/workshop", method = RequestMethod.GET)
    public XaResult<List<WorkShopVo>> workshop() {
        List<Sysdept> list = sysDeptService.getWorkshopList();
        List<WorkShopVo> workShopVos = Lists.newArrayList();
        for (Sysdept sysdept : list) {
            if (null == sysdept) continue;
            List<Emp> emps = empService.getLeaderByDeptID(sysdept.getId());
            //List<ProductProcedureWorkshop> productProcedureWorkshops = productWorkshopService.findByWorkshopName(sysdept.getDeptName());
            workShopVos.add(new WorkShopVo(sysdept, emps, null));
        }
        return XaResult.success(workShopVos);
    }

    private String getBillNo(Integer type) {
        if (type == 1) {
            return "L";
        } else if (type == 2) {
            return "D";
        } else if (type == 3) {
            return "O";
        } else if (type == 4) {
            return "P";
        } else if (type == 5) {
            return "S";
        } else {
            return null;
        }
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
