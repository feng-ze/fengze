package com.hr.sys.user.Service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.hr.sys.user.dto.LoadDTO;
import com.hr.sys.user.dto.Message;
import com.hr.sys.user.dto.RegDTO;
import com.hr.sys.user.dto.TreatmentDTO;
import com.hr.sys.user.entity.SysUser;
import com.hr.sys.user.entity.Treatment;
import com.hr.sys.user.entity.UserInfo;
import com.hr.sys.user.repo.SysuserRepo;
import com.hr.sys.user.repo.TreatmentRepo;
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

    @Autowired
    TreatmentRepo treatmentRepo;

    public Message reg(RegDTO regDTO) {

        SysUser sysUser = sysuserRepo.findAllByAccount(regDTO.getAccount());
        SysUser sysUser1 = sysuserRepo.findAllByWorknumber(regDTO.getWorknumber());
        if (!StringUtils.isEmpty(sysUser) || !StringUtils.isEmpty(sysUser1)){
            return new Message("0","请勿重复注册");
        }

        UserInfo userInfo = userInfoRepo.findAllByWorknunber(regDTO.getWorknumber());
        if (StringUtils.isEmpty(userInfo)){
            return new Message("0","工号不存在");
        }

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
                    return new Message("1",user.getWorknumber());
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
        sysUser.setRole(user.getRole());

        sysuserRepo.save(sysUser);
        sysuserRepo.delete(user);
        return new Message("1","密码修改成功");
    }

    public Message excel(MultipartFile file) throws IOException {
        InputStream input = file.getInputStream();
        //InputStream in = new ByteArrayInputStream(file.getBytes());
        ExcelReader reader = ExcelUtil.getReader(input, 0);
        List<TreatmentDTO> treatmentDTOS = reader.readAll(TreatmentDTO.class);
        List<UserInfo> userInfos = reader.readAll(UserInfo.class);
        System.out.println("file处理：\n"+userInfos);
        try{
            for (int i=0;i<userInfos.size();i++){
                UserInfo userInfo = userInfos.get(i);
                userInfo.setId(UUID.randomUUID().toString());
                Treatment treatment = new Treatment();
                treatment.setId(UUID.randomUUID().toString());
                treatment.setName(userInfo.getName());
                treatment.setWorknumber(userInfo.getWorknunber());
                treatmentRepo.save(treatment);
                userInfoRepo.save(userInfo);
            }

//            for (int i=0;i<treatmentDTOS.size();i++){
//                TreatmentDTO treatmentDTO = treatmentDTOS.get(i);
//                Treatment treatment = new Treatment();
//                treatment.setId(UUID.randomUUID().toString());
//                treatment.setName(treatmentDTO.getName());
//                treatment.setWorknumber(treatmentDTO.getWorknumber());
//
//                treatmentRepo.save(treatment);
//            }
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","数据导入失败");
        }
        return new Message("1","数据导入成功");
    }

}
