package com.hr.sys.user.Service;

import cn.hutool.core.util.RandomUtil;
import com.hr.sys.user.dto.Message;
import com.hr.sys.user.dto.UpdateDTO;
import com.hr.sys.user.dto.UserInfoDTO;
import com.hr.sys.user.entity.Treatment;
import com.hr.sys.user.entity.UserInfo;
import com.hr.sys.user.repo.TreatmentRepo;
import com.hr.sys.user.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public UserInfo add(UserInfoDTO userInfoDto){
        UserInfo user = new UserInfo();
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

        Treatment treatment = new Treatment();
        treatment.setId(RandomUtil.randomUUID());
        treatment.setName(userInfoDto.getName());
        treatment.setWorknumber(userInfoDto.getWorknunber());
        treatmentRepo.save(treatment);

        return userInfoRepo.save(user);
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
}
