package com.hr.sys.user.Service;

import com.hr.sys.user.dto.TreatDTO;
import com.hr.sys.user.entity.Treatment;
import com.hr.sys.user.repo.TreatmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author fengz
 * @Date 2020/12/12 15:42
 */
@Service
public class TreatService {
    @Autowired
    TreatmentRepo treatmentRepo;

    public List<Treatment> find(String worknumber, String name){
        if (StringUtils.isEmpty(worknumber) && StringUtils.isEmpty(name)){
            return treatmentRepo.findAll();
        }else if (StringUtils.isEmpty(worknumber)){
            return treatmentRepo.findAllByName(name);
        }else if (StringUtils.isEmpty(name)){
            return treatmentRepo.findByWorknumber(worknumber);
        }
        return treatmentRepo.findAllByWorknumberAndName(worknumber,name);
    }

    public String update(TreatDTO treatDTO, String worknumber) {
        List<Treatment> list = treatmentRepo.findByWorknumber(worknumber);
        if (list.size()<1){
            return "该员工不存在";
        }else if (list.size()>0){
            treatmentRepo.deleteById(list.get(0).getId());
            Treatment treatment = new Treatment();

            treatment.setId(list.get(0).getId());
            treatment.setName(list.get(0).getName());
            treatment.setWorknumber(list.get(0).getWorknumber());

            treatment.setAcc(treatDTO.getAcc());
            treatment.setDinner(treatDTO.getDinner());
            treatment.setEnd(treatDTO.getEnd());
            treatment.setHeating(treatDTO.getHeating());
            treatment.setHousing(treatDTO.getHousing());
            treatment.setMed(treatDTO.getMed());
            treatment.setSalary(treatDTO.getSalary());
            treatment.setTax(treatDTO.getTax());
            treatment.setTraffic(treatDTO.getTraffic());
            treatment.setUnem(treatDTO.getUnem());
            treatment.setWages(treatDTO.getWages());

            return treatmentRepo.save(treatment).toString();
        }
        return "失败";
    }
}
