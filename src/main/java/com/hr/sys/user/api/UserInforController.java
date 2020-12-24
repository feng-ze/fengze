package com.hr.sys.user.api;

import cn.hutool.poi.excel.ExcelReader;
import com.hr.sys.user.Service.UserInfoService;
import com.hr.sys.user.dto.Message;
import com.hr.sys.user.dto.UpdateDTO;
import com.hr.sys.user.dto.UserInfoDTO;
import com.hr.sys.user.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author fengz
 * @Date 2020/12/7 20:50
 */
@Api(description = "员工增删改查操作")
@RequestMapping("/user")
@RestController
public class UserInforController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/add")
    public Message add(@RequestBody UserInfoDTO userInfoDto){
        return userInfoService.add(userInfoDto);
    }

    @ApiImplicitParam(name = "worknumber",value = "工号",required = true,paramType = "query")
    @DeleteMapping("/delete")
    public Message delete(String worknumber){
        return userInfoService.delete(worknumber);
    }

    @PostMapping("/update")
    public Message update(@RequestBody UpdateDTO updateDto, String worknumber){
        return userInfoService.update(updateDto,worknumber);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "worknumber",value = "工号",required = false,paramType = "query"),
            @ApiImplicitParam(name = "name",value = "姓名",required = false,paramType = "query")
    })
    @PostMapping("/find")
    public List<UserInfo> find(String worknumber, String name){
        return userInfoService.find(worknumber,name);
    }

    @PostMapping("/all/find")
    public Page<UserInfo> findall(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return userInfoService.findall(pageNo,pageSize);
    }

}
