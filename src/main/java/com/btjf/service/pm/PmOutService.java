package com.btjf.service.pm;

import com.btjf.common.page.Page;
import com.btjf.mapper.pm.PmOutBillDetailMapper;
import com.btjf.mapper.pm.PmOutBillMapper;
import com.btjf.model.pm.PmOutBill;
import com.btjf.model.pm.PmOutBillDetail;
import com.btjf.vo.PmOutBillListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/7/28.
 */
@Service
public class PmOutService {

    @Resource
    private PmOutBillMapper mapper;

    @Resource
    private PmOutBillDetailMapper pmOutBillDetailMapper;

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

    public List<PmOutBillListVo> findList(String billNo, String orderNo, String productNo) {
        List<PmOutBillListVo> pmList = mapper.findList(billNo, orderNo, productNo);
        return pmList;
    }

//    public List<PmInVo> findList(String pmNo, String name, String type,String startDate,String endDate){
//        List<PmInVo> pmList = pmInMapper.findList(pmNo, name, type,startDate, endDate);
//        return pmList;
//    }
//
//    public void create(PmIn pmIn) {
//        pmInMapper.insertSelective(pmIn);
//    }

}
