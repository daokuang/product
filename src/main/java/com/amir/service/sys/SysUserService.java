package com.amir.service.sys;

import com.amir.mapper.sys.SysUserMapper;
import com.amir.model.sys.SysUser;
import com.amir.model.sys.SysUserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;


    public SysUser login(String loginName, String loginPwd) {
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        criteria.andLoginPwdEqualTo(loginPwd);
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        return (list == null || list.size() <= 0) ? null : list.get(0);
    }

}
