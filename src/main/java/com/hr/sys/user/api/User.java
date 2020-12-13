package com.hr.sys.user.api;

import com.hr.sys.user.Service.UserService;
import com.hr.sys.user.dto.LoadDTO;
import com.hr.sys.user.dto.RegDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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

    @PostMapping("/reg")
    public String reg(@RequestBody RegDTO regDTO) {
        return userService.reg(regDTO);
    }

    @PostMapping("/load")
    public String load(@RequestBody LoadDTO loadDTO) {
        return userService.load(loadDTO);
    }

    @PostMapping("/info/update")
    public String update(@RequestBody LoadDTO loadDTO) {
        return userService.update(loadDTO);
    }

    @PostMapping("/Excel/input")
    public String Excel(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return null;
    }

}
