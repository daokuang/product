package com.amir.service.sys;

import com.amir.mapper.sys.SysdeptMapper;
import com.amir.model.sys.Sysdept;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysDeptService {

    @Resource
    private SysdeptMapper sysdeptMapper;


    public Sysdept get(Integer id) {
        return sysdeptMapper.selectByPrimaryKey(id);
    }


    public List<Sysdept> getList() {

        return sysdeptMapper.getList();
    }

    public List<Sysdept> getWorkshopList() {
        return sysdeptMapper.getWorkshopList();
    }

    public Sysdept getByName(String name) {
        return sysdeptMapper.getByName(name);
    }
}
