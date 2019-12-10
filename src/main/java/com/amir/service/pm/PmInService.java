package com.amir.service.pm;

import com.amir.mapper.pm.PmInMapper;
import com.amir.model.Page;
import com.amir.model.pm.PmIn;
import com.amir.vo.PmInVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/7/28.
 */
@Service
public class PmInService {

    @Resource
    private PmInMapper pmInMapper;

    public Page<PmInVo> findListPage(String pmNo, String name, String type, String startDate, String endDate, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<PmInVo> pmList = pmInMapper.findList(pmNo, name, type, startDate, endDate);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public List<PmInVo> findList(String pmNo, String name, String type, String startDate, String endDate) {
        List<PmInVo> pmList = pmInMapper.findList(pmNo, name, type, startDate, endDate);
        return pmList;
    }

    public void create(PmIn pmIn) {
        pmInMapper.insertSelective(pmIn);
    }

//    public Integer insert(Pm pm){
//        pmInMapper.insertSelective(pm);
//        return pm.getId();
//    }
//
//    public Integer saveList(List<Pm> pmList){
//       return pmInMapper.saveList(pmList);
//    }
}
