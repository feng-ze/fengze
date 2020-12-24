package com.hr.sys.user.api;

import com.hr.sys.user.Service.UserInfoService;
import com.hr.sys.user.Service.UserService;
import com.hr.sys.user.dto.LoadDTO;
import com.hr.sys.user.dto.Message;
import com.hr.sys.user.dto.RegDTO;
import com.hr.sys.user.dto.forgetDTO;
import com.hr.sys.user.entity.SysUser;
import com.hr.sys.user.entity.UserInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


/**
 * @Author fengz
 * @Date 2020/12/13 20:37
 */
@RestController
@Api(description = "登录注册")
@RequestMapping("/user")
public class User {

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/reg")
    public Message reg(@RequestBody RegDTO regDTO) {
        return userService.reg(regDTO);
    }

    @PostMapping("/load")
    public Message load(@RequestBody LoadDTO loadDTO) {
        return userService.load(loadDTO);
    }

    @PostMapping("/info/update")
    public Message update(@RequestBody LoadDTO loadDTO) {
        return userService.update(loadDTO);
    }

    @PostMapping("/Excel/input")
    public Message Excel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return userService.excel(file);
    }

    /**
     * 忘记密码：点击忘记密码，需要用户输入自己的工号,身份证号码,账号，身份证号码（新加表字段），根绝工号,身份证号码去查询userinfo，如果该对象存在，则修改用户密码
     */

    @PostMapping("/forget")
    public Message forgetPassword(@RequestBody forgetDTO forgetDTO){
        return userInfoService.forget(forgetDTO);
    }


}
