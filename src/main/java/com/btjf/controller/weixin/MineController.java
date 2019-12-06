package com.btjf.controller.weixin;

import com.btjf.application.util.XaResult;
import com.btjf.common.utils.DateUtil;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.controller.weixin.vo.WxEmpVo;
import com.btjf.model.emp.Emp;
import com.btjf.model.emp.SummarySalaryMonthly;
import com.btjf.model.order.Order;
import com.btjf.model.sys.Sysdept;
import com.btjf.service.emp.EmpService;
import com.btjf.service.emp.SummarySalaryMonthlyService;
import com.btjf.service.order.ProductionProcedureConfirmService;
import com.btjf.service.sys.SysDeptService;
import com.btjf.util.BigDecimalUtil;
import com.btjf.vo.weixin.*;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Api(value = "MineController", description = "小程序 个人中心", position = 1)
@RequestMapping(value = "/wx/mine")
@RestController("mineController")
public class MineController  extends ProductBaseController {

    @Resource
    private EmpService empService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;
    @Resource
    private SummarySalaryMonthlyService summarySalaryMonthlyService;


    /**
     * 个人中心主页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public XaResult<MineIndexVo> importExcel(){
        WxEmpVo vo = getWxLoginUser();
        MineIndexVo mineIndexVo = null;
        if(vo != null){
            mineIndexVo = new MineIndexVo();
            mineIndexVo.setName(vo.getName());
            mineIndexVo.setEmpId(vo.getId());
            mineIndexVo.setDeptName(vo.getDeptName());
            mineIndexVo.setPosition(vo.getWorkName());
            if (vo.getIsLeader() == 1){
                mineIndexVo.setIsShowMenu(1);
            }
        }
        return XaResult.success(mineIndexVo);
    }


    /**
     * 计件上报-订单列表
     *获取当月 质检通过的订单
     * @return
     */
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public XaResult<List<OrderVo>> list(String date){
        //2019-08
        if (StringUtils.isEmpty(date)){
            return XaResult.error("月份不能为空");
        }
        //TODO 本部门订单
        WxEmpVo vo = getWxLoginUser();
        if(vo.getIsLeader() == null || vo.getIsLeader() != 1){
            return XaResult.error("当前人员没有权限进行该操作");
        }
        List<Order> list = productionProcedureConfirmService.getOrderByMouth(date, vo.getDeptName());
        List<OrderVo> voList = null;
        if(list != null && list.size() >0){
            voList = new ArrayList<>();
            for (Order o: list){
                OrderVo orderVo = new OrderVo();
                orderVo.setDate(DateUtil.dateToString(o.getCreateTime(),new SimpleDateFormat("yyyy/MM/dd")));
                orderVo.setOrderNo(o.getOrderNo());
                List<OrderProductVo> ops = productionProcedureConfirmService.getOrderProductByMouth(o.getOrderNo(), date, vo.getDeptName());
                orderVo.setList(ops);
                voList.add(orderVo);
            }

        }
        return XaResult.success(voList);
    }


    /**
     * 计件上报-订单产品详情
     *获取当月 质检通过的订单
     * @return
     */
    @RequestMapping(value = "/order/detail", method = RequestMethod.GET)
    public XaResult<List<EmpProcedureListVo>> detail(String orderNo, String productNo, String date){
        if (StringUtils.isEmpty(orderNo)){
            return XaResult.error("订单号不能为空");
        }
        if (StringUtils.isEmpty(productNo)){
            return XaResult.error("产品型号不能为空");
        }
        //2019-08
        if (StringUtils.isEmpty(date)){
            return XaResult.error("月份不能为空");
        }
        WxEmpVo vo = getWxLoginUser();
        if(vo.getIsLeader() == null || vo.getIsLeader() != 1){
            return XaResult.error("当前人员没有权限进行该操作");
        }
        List<EmpProcedureListVo> list = productionProcedureConfirmService.getEmpNum(orderNo, productNo, date, vo.getDeptName());

        return XaResult.success(list);
    }

    /**
     * 计件上报-添加员工
     *查询员工 是否存在
     * 返回 员工姓名 部门名字
     * @return
     */
    @RequestMapping(value = "/emp/query", method = RequestMethod.GET)
    public XaResult<MineIndexVo> query(String empName){
        if (StringUtils.isEmpty(empName)){
            return XaResult.error("员工名字不能为空");
        }
         Emp emp =  empService.getByName(empName);
        if(emp == null){
            return XaResult.error("查无此员工，请修改");
        }
        Sysdept dept = sysDeptService.get(emp.getDeptId());
        MineIndexVo vo = new MineIndexVo();
        vo.setEmpId(emp.getId());
        vo.setName(empName);
        vo.setDeptName(dept==null?null:dept.getDeptName());
        return XaResult.success(vo);
    }

    /**
     * 计件上报-获取部门所有人员
     * @return
     */
    @RequestMapping(value = "/emp/list", method = RequestMethod.GET)
    public XaResult<List<MineIndexVo>> empList(Integer deptId){
        if (deptId == null){
            return XaResult.error("部门ID不能为空");
        }
        List<MineIndexVo> empList =  empService.getByDeptId(deptId);
        return XaResult.success(empList);
    }

    /**
     * 计件上报-订单产品详情-上传工序计件
     *获取当月 质检通过的订单
     * @return
     */
    @RequestMapping(value = "/order/report", method = RequestMethod.POST)
    public XaResult<String> report(String date, String orderNo, String productNo, Integer procedureId,
                                                     String[] content){
        if (StringUtils.isEmpty(orderNo)){
            return XaResult.error("订单号不能为空");
        }
        if (StringUtils.isEmpty(productNo)){
            return XaResult.error("产品型号不能为空");
        }
        if (procedureId == null){
            return XaResult.error("工序ID不能为空");
        }
        if (content == null || content.length <1){
            return XaResult.error("上报内容不能为空");
        }
        //2019-08
        if (StringUtils.isEmpty(date)){
            return XaResult.error("月份不能为空");
        }
        List<EmpProcedureDetailVo> list = new ArrayList<>();
        Double num = 0.0;//上报的总数
        for (int i=0; i<content.length; i++){
            String ep = content[i];
            String[] s = ep.split("\\|");
            if(s.length <2){
                return XaResult.error("上报信息有误");
            }
            EmpProcedureDetailVo vo = new EmpProcedureDetailVo();
            vo.setEmpId(Integer.valueOf(s[0]));
            vo.setNum(Double.valueOf(s[1]));
            num = num + vo.getNum();
            list.add(vo);
        }

        WxEmpVo vo = getWxLoginUser();
        if(vo.getIsLeader() == null || vo.getIsLeader() != 1){
            return XaResult.error("当前人员没有权限进行该操作");
        }

//        Integer changeNum = productionProcedureConfirmService.getChangeNum(orderNo, productNo, procedureId, vo.getDeptName());
//        if(num < changeNum){
//            XaResult xaResult = new XaResult();
//            xaResult.setError(1002,"您设置的产量未达到上限数量，确认保存吗？");
//            return xaResult;
//        }else if(num > changeNum){
//            XaResult xaResult = new XaResult();
//            xaResult.setError(1001,"您设置的计件产量工序超过{" + changeNum + "}，请修改");
//            return xaResult;
//        }

        productionProcedureConfirmService.change(orderNo, productNo, procedureId, list, vo, date);
        return XaResult.success();
    }

    /**
     * 工作产出
     * @return
     */
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public XaResult<EmpWorkVo> work(String date){
        //2019-08
        if (StringUtils.isEmpty(date)){
            return XaResult.error("月份不能为空");
        }
        WxEmpVo vo = getWxLoginUser();
        //TODO 质检员 工作量另算
        EmpWorkVo empWorkVo = new EmpWorkVo();
        double total = 0.0;

        List<EmpDayWorkVo> dayWorkVos = productionProcedureConfirmService.analyseForDay(date, vo.getId());
        if(dayWorkVos == null || dayWorkVos.size() <1){
            return XaResult.error("查无数据");
        }
        for (EmpDayWorkVo dayWorkVo:dayWorkVos) {
            List<EmpDayWorkDetailVo> dayWorkDetailVoList =
                    productionProcedureConfirmService.getWorkForDay(dayWorkVo.getDate(), vo.getId());
            //为了应对 一天内 多次扫码，每次扫一个工序的情况
            List<EmpDayWorkDetailVo> dayWorkDetailVoList_new = new ArrayList<>();
            for (EmpDayWorkDetailVo dayWorkDetailVo:dayWorkDetailVoList) {
                //TODO 根据 type 罗ID  精确搜索工序
                String billNo = dayWorkDetailVo.getBillNo();
                if(dayWorkDetailVo.getLuoId() != null){
                    billNo = StringUtils.substringBefore(billNo, "-");
                }
                boolean flag = false;
                for (EmpDayWorkDetailVo newVo:dayWorkDetailVoList_new){
                    if(newVo.getBillNo().equals(dayWorkDetailVo.getBillNo()) &&
                            ((newVo.getLuoId() != null && newVo.getLuoId().equals(dayWorkDetailVo.getLuoId())) || (newVo.getLuoId() == null && dayWorkDetailVo.getLuoId() == null))
                            && newVo.getOrderNo().equals(dayWorkDetailVo.getOrderNo())
                            && newVo.getProductNo().equals(dayWorkDetailVo.getProductNo())
                            && newVo.getType().equals(dayWorkDetailVo.getType())
                            && newVo.getStatusDesc().equals(dayWorkDetailVo.getStatusDesc())){
                        flag = true;
                    }
                }
                if (!flag){
                    List<ProcedureInfoVo> procedureInfoVos =
                            productionProcedureConfirmService.getWorkProcedureInfo(dayWorkVo.getDate(), vo.getId(),
                                    dayWorkDetailVo.getOrderNo(), dayWorkDetailVo.getProductNo(),billNo, dayWorkDetailVo.getLuoId(),
                                    dayWorkDetailVo.getType());
                    dayWorkDetailVo.setProcedureInfoVoList(procedureInfoVos);
                    dayWorkDetailVoList_new.add(dayWorkDetailVo);
                }
            }
            dayWorkVo.setDayWorkDetailVoList(dayWorkDetailVoList_new);
            total = BigDecimalUtil.add(total, dayWorkVo.getSum());
        }
        empWorkVo.setTotal(total);
        empWorkVo.setDayWorkVoList(dayWorkVos);

        return XaResult.success(empWorkVo);
    }

    /**
     * 我的薪资
     * @return
     */
    @RequestMapping(value = "/salary", method = RequestMethod.GET)
    public XaResult<SalaryVo> salary(String yearMonth){
        //2019-08
        if (StringUtils.isEmpty(yearMonth)){
            return XaResult.error("月份不能为空");
        }
        WxEmpVo vo = getWxLoginUser();
        SummarySalaryMonthly summarySalaryMonthly = summarySalaryMonthlyService.getByYearMonthAndName(yearMonth, vo.getName());
        if (summarySalaryMonthly == null){
            return XaResult.error("未结算");
        }
        SalaryVo salaryVo = new SalaryVo(summarySalaryMonthly);
        return XaResult.success(salaryVo);
    }


}
