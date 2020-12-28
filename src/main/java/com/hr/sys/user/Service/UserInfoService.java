package com.hr.sys.user.Service;

import cn.hutool.core.util.RandomUtil;
import com.hr.sys.user.dto.*;
import com.hr.sys.user.entity.SysUser;
import com.hr.sys.user.entity.Treatment;
import com.hr.sys.user.entity.UserInfo;
import com.hr.sys.user.repo.SysuserRepo;
import com.hr.sys.user.repo.TreatmentRepo;
import com.hr.sys.user.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @Author fengz
 * @Date 2020/12/7 20:54
 */
@Service
public class UserInfoService {

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    TreatmentRepo treatmentRepo;

    @Autowired
    SysuserRepo sysuserRepo;

    public Message add(UserInfoDTO userInfoDto){

        UserInfo user = new UserInfo();
        try {
            user.setId(RandomUtil.randomUUID());
            user.setAddress(userInfoDto.getAddress());
            user.setBirthday(userInfoDto.getBirthday());
            user.setCard(userInfoDto.getCard());
            user.setEducation(userInfoDto.getEducation());
            user.setMajor(userInfoDto.getMajor());
            user.setMarriage(userInfoDto.getMarriage());
            user.setName(userInfoDto.getName());
            user.setPhone(userInfoDto.getPhone());
            user.setPlatform(userInfoDto.getPlatform());
            user.setSchool(userInfoDto.getSchool());
            user.setSex(userInfoDto.getSex());
            user.setWorknunber(userInfoDto.getWorknunber());
            user.setWorktime(userInfoDto.getWorktime());
            user.setIdcard(userInfoDto.getIdcard());

            Treatment treatment = new Treatment();
            treatment.setId(RandomUtil.randomUUID());
            treatment.setName(userInfoDto.getName());
            treatment.setWorknumber(userInfoDto.getWorknunber());
            treatmentRepo.save(treatment);

            userInfoRepo.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","添加用户失败");
        }

        return new Message("1","添加用户成功");
    }

    public Message delete(String worknumber){
       UserInfo userInfo = userInfoRepo.findAllByWorknunber(worknumber);
       Treatment treatment = treatmentRepo.findAllByWorknumber(worknumber);
       if (!StringUtils.isEmpty(userInfo)){
           userInfoRepo.delete(userInfo);
           if (!StringUtils.isEmpty(treatment)){
               treatmentRepo.delete(treatment);
           }
       }else {
           return new Message("0","失败");
       }
        return new Message("1","成功");
    }

    public Message update(UpdateDTO updateDto, String worknumber){
        try{
            UserInfo userInfo = userInfoRepo.findAllByWorknunber(worknumber);
            userInfoRepo.deleteById(userInfo.getId());
            userInfo.setPhone(updateDto.getPhone());
            userInfo.setMarriage(updateDto.getMarriage());
            userInfo.setEducation(updateDto.getEducation());
            userInfo.setCard(updateDto.getCard());
            userInfo.setAddress(updateDto.getAddress());
            userInfoRepo.save(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","失败");
        }
        return new Message("1","成功");
    }

    public List<UserInfo> find(String worknumber, String name){
        if (StringUtils.isEmpty(worknumber) && StringUtils.isEmpty(name)){
            return null;
        }else if (StringUtils.isEmpty(worknumber)){
            return userInfoRepo.findAllByName(name);
        }else if (StringUtils.isEmpty(name)){
            return userInfoRepo.findByWorknunber(worknumber);
        }
        return userInfoRepo.findAllByWorknunberAndName(worknumber,name);
    }

    public Page<UserInfo> findall(int pageNo, int pageSize) {
        PageRequest request = PageRequest.of(pageNo, pageSize);
        return userInfoRepo.findAll(request);
    }

    public Message forget(forgetDTO forgetDTO) {
        UserInfo userInfo = userInfoRepo.findAllByWorknunberAndIdcard(forgetDTO.getWorknumber(),forgetDTO.getIdcard());
        if (StringUtils.isEmpty(userInfo)){
            return new Message("0","用户不存在");
        }
        try {
            SysUser user = sysuserRepo.findAllByAccount(forgetDTO.getAccount());
            if (StringUtils.isEmpty(user)){
                return new Message("0","修改失败,请核对自己的账号信息");
            }
            SysUser sysUser = new SysUser();
            sysUser.setId(UUID.randomUUID().toString());
            sysUser.setName(user.getName());
            sysUser.setAccount(forgetDTO.getAccount());
            sysUser.setPassword(forgetDTO.getNewpassword());
            sysUser.setWorknumber(forgetDTO.getWorknumber());
            sysUser.setRole(user.getRole());

            sysuserRepo.save(sysUser);
            sysuserRepo.delete(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Message("0","密码找回失败");
        }
        return new Message("1","密码找回成功");
    }
}
