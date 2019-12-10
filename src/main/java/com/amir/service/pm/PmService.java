package com.amir.service.pm;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.mapper.pm.PmMapper;
import com.amir.model.pm.Pm;
import com.amir.model.pm.PmRequstPojo;
import com.amir.service.productpm.ProductPmService;
import com.btjf.common.page.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyq on 2019/7/28.
 */
@Service
public class PmService {

    @Resource
    private PmMapper pmMapper;

    @Resource
    private ProductPmService productPmService;

    public Page<Pm> findListPage(PmRequstPojo pmRequstPojo, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<Pm> pmList = pmMapper.findList(pmRequstPojo);
        PageInfo pageInfo = new PageInfo(pmList);
        pageInfo.setList(pmList);
        return new Page<>(pageInfo);
    }

    public Integer deleteByID(List<Integer> integers) {
        if (!CollectionUtils.isEmpty(integers)) {
            integers.stream().forEach(t -> {
                Pm pm = this.getByID(t);
                //删除耗料
                if (null != pm) {
                    productPmService.deleteByPmNo(pm.getPmNo());
                }
            });
            return pmMapper.deleteByID(integers);
        }
        return 0;
    }

    public Integer deleteByNo(List<String> nos) {
        if (CollectionUtils.isEmpty(nos)) {
            return 0;
        }
        return pmMapper.deleteByNo(nos);
    }

    public Pm getByID(Integer id) {
        if (id == null) return null;
        Pm pm = pmMapper.selectByPrimaryKey(id);
        return pm;
    }

    public List<Pm> findList(PmRequstPojo pmRequstPojo) {
        List<Pm> pmList = pmMapper.findList(pmRequstPojo);
        return pmList;
    }

    public Integer updateByID(Pm pm) {
        pmMapper.updateByPrimaryKeySelective(pm);
        return pm.getId();
    }

    public Integer insert(Pm pm) {
        pmMapper.insertSelective(pm);
        return pm.getId();
    }

    public Integer saveList(List<Pm> pmList, Boolean isCover) {
        if (CollectionUtils.isEmpty(pmList)) {
            return 0;
        }
        if (isCover) {
            List<String> stringList = Lists.newArrayList();
            pmList.stream().forEach(t -> stringList.add(t.getPmNo()));
            this.deleteByNo(stringList);
        }
        return pmMapper.saveList(pmList);
    }


    public Pm getByNo(String no) {
        if (no == null) return null;
        return pmMapper.getByNO(no);
    }
}
