package com.amir.service.pm;

import com.amir.mapper.pm.PmOutBillDetailMapper;
import com.amir.mapper.pm.PmOutBillMapper;
import com.amir.model.pm.Pm;
import com.amir.model.pm.PmOutBill;
import com.amir.model.pm.PmOutBillDetail;
import com.amir.service.productpm.ProductPmService;
import com.amir.vo.*;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.page.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/7/28.
 */
@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class PmOutService {

    private static final Logger LOGGER = Logger
            .getLogger(PmOutService.class);
    @Resource
    private PmOutBillMapper mapper;
    @Resource
    private PmOutBillDetailMapper pmOutBillDetailMapper;
    @Resource
    private PmService pmService;
    @Resource
    private ProductPmService productPmService;

    public Page<PmOutBillListVo> findListPage(String billNo, String orderNo, String productNo, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<PmOutBillListVo> pmList = mapper.findList(billNo, orderNo, productNo);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public PmOutBill getByBillNo(String billNo) {
        return mapper.getByBillNo(billNo);
    }

    public List<PmOutBillDetail> getListDetailByBillId(Integer billId) {
        return pmOutBillDetailMapper.getListDetailByBillId(billId);
    }

    public void createBill(PmOutBill pmOutBill, List<BillPmVo> list) {
        mapper.insertSelective(pmOutBill);
        for (int i = 0; i < list.size(); i++) {
            Pm pm = pmService.getByNo(list.get(i).getPmNo());
            if (pm.getNum().doubleValue() < list.get(i).getAllowNum()) {
                throw new BusinessException(pm.getPmNo() + " 材料库存" + pm.getNum() + " 不足核可领取数，" + list.get(i).getAllowNum());
            }
            PmOutBillDetail pmOutBillDetail = new PmOutBillDetail();
            pmOutBillDetail.setBillId(pmOutBill.getId());
            pmOutBillDetail.setPmId(pm.getId());
            pmOutBillDetail.setPmName(pm.getName());
            pmOutBillDetail.setPmNo(pm.getPmNo());
            pmOutBillDetail.setPerNum(pm.getNum());
            pmOutBillDetail.setPmBatchNo(list.get(i).getPmBatchNo());
            pmOutBillDetail.setNum(BigDecimal.valueOf(list.get(i).getAllowNum()));
            pmOutBillDetail.setBackNum(pmOutBillDetail.getPerNum().subtract(pmOutBillDetail.getNum()));
            pmOutBillDetail.setUnit(pm.getUnit());
            pmOutBillDetail.setRemark(pm.getRemark());
            pmOutBillDetail.setIsDelete(0);
            pmOutBillDetail.setOutDate(new Date());
            pmOutBillDetail.setCreateTime(new Date());
            pmOutBillDetail.setLastModifyTime(new Date());
            pmOutBillDetail.setOperator(pmOutBill.getOperator());
            pmOutBillDetailMapper.insertSelective(pmOutBillDetail);
            if (pmOutBill.getType() == 1) {//领料单 才减库存
                LOGGER.info("材料编号：" + pm.getPmNo() + "凭单据 " + pmOutBill.getBillNo() + " 出库 " + pmOutBillDetail.getNum());
                Pm pm1 = new Pm();
                pm1.setId(pm.getId());
                pm1.setNum(pmOutBillDetail.getBackNum());
                pm.setLastModifyTime(new Date());
                pmService.updateByID(pm1);
            }
        }

    }


    public List<PmOutBillListVo> findList(String billNo, String orderNo, String productNo) {
        List<PmOutBillListVo> pmList = mapper.findList(billNo, orderNo, productNo);
        return pmList;
    }

    public Page<PmOutUpListVo> findUpList(String orderNo, String productNo, Integer isInput, Integer customerName, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<PmOutUpListVo> pmList = mapper.findUpList(orderNo, productNo, isInput, customerName);
        if (pmList != null && pmList.size() > 0) {
            for (PmOutUpListVo vo : pmList) {
                if (isInput != null && isInput == 0) {
                    //未录入
                    vo.setInputNum(0);
                } else {
                    vo.setInputNum(productPmService.count(vo.getProductNo()));
                }
                vo.setBillNum(count(vo.getOrderNo(), vo.getProductNo()));
            }
        }
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    private Integer count(String orderNo, String productNo) {
        return mapper.count(orderNo, productNo);
    }

    public Page<PmInAndOutVo> findInAndOutListPage(String pmNo, String pmName, String orderNo,
                                                   Integer inOrOut, String operator, String startDate, String endDate, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<PmInAndOutVo> pmList = null;
        if (inOrOut == null) {
            if (StringUtils.isNotEmpty(orderNo)) {//只要出库记录
                pmList = mapper.findOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
            } else {
                //出库加入库
                pmList = mapper.findInAndOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
            }
        } else if (inOrOut == 1) {
            //入库
            pmList = mapper.findInList(pmNo, pmName, orderNo, operator, startDate, endDate);
        } else if (inOrOut == 2) {
            //出库
            pmList = mapper.findOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
        }
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }


    public List<PmInAndOutVo> findInAndOutList(String pmNo, String pmName, String orderNo, Integer inOrOut, String operator, String startDate, String endDate) {
        List<PmInAndOutVo> pmList = null;
        if (inOrOut == null) {
            if (StringUtils.isNotEmpty(orderNo)) {//只要出库记录
                pmList = mapper.findOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
            } else {
                //出库加入库
                pmList = mapper.findInAndOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
            }
        } else if (inOrOut == 1) {
            //入库
            pmList = mapper.findInList(pmNo, pmName, orderNo, operator, startDate, endDate);
        } else if (inOrOut == 2) {
            //出库
            pmList = mapper.findOutList(pmNo, pmName, orderNo, operator, startDate, endDate);
        }
        return pmList;
    }

    public Page<PmPlanOutVo> findPlanOutListPage(String pmNo, String operator,
                                                 String startDate, String endDate, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<PmPlanOutVo> pmList = mapper.findPlanOutList(pmNo, operator, startDate, endDate);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public void insert(PmOutBill pmOutBill, PmOutBillDetail pmOutBillDetail) {
        mapper.insertSelective(pmOutBill);
        pmOutBillDetail.setBillId(pmOutBill.getId());
        pmOutBillDetailMapper.insertSelective(pmOutBillDetail);
    }
}
