package com.hr.sys.user.Service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.hr.sys.user.dto.LoadDTO;
import com.hr.sys.user.dto.Message;
import com.hr.sys.user.dto.RegDTO;
import com.hr.sys.user.entity.SysUser;
import com.hr.sys.user.entity.UserInfo;
import com.hr.sys.user.repo.SysuserRepo;
import com.hr.sys.user.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author fengz
 * @Date 2020/12/14 0:19
 */
@Service
public class UserService {

    @Autowired
    SysuserRepo sysuserRepo;

    @Autowired
    UserInfoRepo userInfoRepo;

    public Message reg(RegDTO regDTO) {
        SysUser user = new SysUser();
        user.setId(UUID.randomUUID().toString());
        user.setAccount(regDTO.getAccount());
        user.setName(regDTO.getName());
        user.setPassword(regDTO.getPassword());
        user.setWorknumber(regDTO.getWorknumber());
        user.setRole("0");
        try {
            sysuserRepo.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","注册失败！");
        }
        return new Message("1","注册成功");
    }

    public Message load(LoadDTO loadDTO) {
        SysUser user = sysuserRepo.findAllByAccountAndPassword(loadDTO.getAccount(),loadDTO.getPassword());
        if (StringUtils.isEmpty(user)){
            return new Message("0","账号或者密码错误！");
        }else{
            if (loadDTO.getPassword().equals(user.getPassword())){
                if (user.getRole().equals("0")){
                    return new Message("0",user.getWorknumber());
                }else{
                    return new Message("1","HR管理员");
                }

            }
        }
        return new Message("0","账号或者密码错误！");
    }

    public Message update(LoadDTO loadDTO) {
        SysUser user = sysuserRepo.findAllByAccount(loadDTO.getAccount());
        if (StringUtils.isEmpty(user)){
            return new Message("0","修改失败,请核对自己的账号信息");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setName(user.getName());
        sysUser.setAccount(loadDTO.getAccount());
        sysUser.setPassword(loadDTO.getPassword());
        sysUser.setRole("0");
        sysuserRepo.delete(user);
        sysuserRepo.save(sysUser);

        return new Message("1","密码修改成功");
    }

    public Message excel(MultipartFile file) throws IOException {

        InputStream in = new ByteArrayInputStream(file.getBytes());
        ExcelReader reader = ExcelUtil.getReader(in, 0);
        List<UserInfo> userInfos = reader.readAll(UserInfo.class);
        System.out.println("file处理：\n"+userInfos);
        try{
            for (int i=0;i<userInfos.size();i++){
                UserInfo userInfo = userInfos.get(i);
                userInfo.setId(UUID.randomUUID().toString());
                userInfoRepo.save(userInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","数据导入失败");
        }
        return new Message("1","数据导入成功");
    }

}
