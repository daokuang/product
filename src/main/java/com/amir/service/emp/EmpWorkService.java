package com.amir.service.emp;

import com.amir.mapper.emp.EmpWorkMapper;
import com.amir.model.emp.EmpWork;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by liuyq on 2019/8/8.
 */
@Service
public class EmpWorkService {

    @Resource
    private EmpWorkMapper empWorkMapper;


    public List<EmpWork> getList() {
        return empWorkMapper.getList();
    }

    public EmpWork getByName(String name) {
        return empWorkMapper.getByName(name);
    }

    public Integer add(EmpWork empWork) {
        empWorkMapper.insertSelective(empWork);
        return empWork.getId();
    }

    public Integer update(EmpWork empWork) {
        return Optional.ofNullable(empWork).map(t -> empWorkMapper.updateByPrimaryKeySelective(t)).orElse(0);
    }

    public EmpWork getByID(Integer id) {
        return Optional.ofNullable(id).map(t -> empWorkMapper.selectByPrimaryKey(t)).orElse(null);
    }
}
