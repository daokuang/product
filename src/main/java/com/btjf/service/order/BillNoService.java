package com.btjf.service.order;

import com.btjf.common.utils.DateUtil;
import com.btjf.mapper.order.BillNoMapper;
import com.btjf.model.order.BillNo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class BillNoService {

    @Resource
    private BillNoMapper mapper;

    public String getNo(Integer type){
        String no = null;
        String date = DateUtil.dateToString(new Date(), DateUtil.ymdNoPFormat);
        synchronized (this){
            BillNo billNo = mapper.getByTypeAndDate(type,date);
            if(billNo == null){
                billNo = new BillNo();
                billNo.setDate(date);
                billNo.setType(type);
                billNo.setNum(1);
                mapper.insert(billNo);
                no = date + "0001";
            }else{
                int num = billNo.getNum() + 1;
                if(num >= 1000){
                    no = date + num;
                }else{
                    no = date + String.format("%04d", num);;
                }
                billNo.setNum(num);
                mapper.updateByPrimaryKey(billNo);
            }
        }
        return no;
    }

}
