package com.amir.service.sys;

import com.amir.mapper.sys.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


}
