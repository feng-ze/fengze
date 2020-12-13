package com.hr.sys.user.Service;

import com.hr.sys.user.dto.LoadDTO;
import com.hr.sys.user.dto.RegDTO;
import com.hr.sys.user.entity.SysUser;
import com.hr.sys.user.repo.SysuserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Author fengz
 * @Date 2020/12/14 0:19
 */
@Service
public class UserService {

    @Autowired
    SysuserRepo sysuserRepo;

    public String reg(RegDTO regDTO) {
        SysUser user = new SysUser();
        user.setId(UUID.randomUUID().toString());
        user.setAccount(regDTO.getAccount());
        user.setName(regDTO.getName());
        user.setPassword(regDTO.getPassword());
        user.setRole("0");
        try {
            sysuserRepo.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return "注册失败！";
        }
       return "注册成功";
    }

    public String load(LoadDTO loadDTO) {
        SysUser user = sysuserRepo.findAllByAccountAndPassword(loadDTO.getAccount(),loadDTO.getPassword());
        if (StringUtils.isEmpty(user)){
            return "账号或者密码错误！";
        }else{
            if (loadDTO.getPassword().equals(user.getPassword())){
                return "登录成功";
            }
        }
        return "账号或者密码错误！";
    }

    public String update(LoadDTO loadDTO) {
        SysUser user = sysuserRepo.findAllByAccount(loadDTO.getAccount());
        if (StringUtils.isEmpty(user)){
            return "修改失败,请核对自己的账号信息";
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setName(user.getName());
        sysUser.setAccount(loadDTO.getAccount());
        sysUser.setPassword(loadDTO.getPassword());
        sysUser.setRole("0");
        sysuserRepo.delete(user);
        sysuserRepo.save(sysUser);

        return "密码修改成功";
    }
}
