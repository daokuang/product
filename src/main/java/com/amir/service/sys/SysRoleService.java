package com.amir.service.sys;

import com.amir.mapper.sys.SysRoleMapper;
import com.amir.model.sys.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;


    public SysRole get(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }


}
