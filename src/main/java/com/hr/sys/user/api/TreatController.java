package com.hr.sys.user.api;

import com.hr.sys.user.Service.TreatService;
import com.hr.sys.user.dto.TreatDTO;
import com.hr.sys.user.entity.Treatment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author fengz
 * @Date 2020/12/12 15:41
 */
@RestController
@Api(description = "员工待遇操作")
@RequestMapping("/treatment")
public class TreatController {

    @Autowired
    TreatService treatService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "worknumber",value = "工号",required = false,paramType = "query"),
            @ApiImplicitParam(name = "name",value = "姓名",required = false,paramType = "query")
    })
    @PostMapping("/find")
    public List<Treatment> find(String worknumber, String name) {
        return treatService.find(worknumber, name);
    }

    @PostMapping("/update")
    public String update(@RequestBody TreatDTO treatDTO, String worknumber){
        return treatService.update(treatDTO,worknumber);
    }
}
