package com.hr.sys.user.Service;

import com.hr.sys.user.dto.AddTreatDto;
import com.hr.sys.user.dto.Message;
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

    public List<Treatment> find(String worknumber, String name) {
        if (StringUtils.isEmpty(worknumber) && StringUtils.isEmpty(name)) {
            return treatmentRepo.findAll();
        } else if (StringUtils.isEmpty(worknumber)) {
            return treatmentRepo.findAllByName(name);
        } else if (StringUtils.isEmpty(name)) {
            return treatmentRepo.findByWorknumber(worknumber);
        }
        return treatmentRepo.findAllByWorknumberAndName(worknumber, name);
    }

    public Message update(TreatDTO treatDTO, String worknumber) {
        List<Treatment> list = treatmentRepo.findByWorknumber(worknumber);
        if (list.size() < 1) {
            return new Message("0", "失败");
        } else if (list.size() > 0) {
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

            double d = format(treatment.getSalary()) + format(treatment.getDinner()) + format(treatment.getHousing())
                     + format(treatment.getTraffic()) + format(treatment.getHeating()) + format(treatment.getAcc())
                     + format(treatment.getEnd()) + format(treatment.getMed()) + format(treatment.getTax())
                     + format(treatment.getUnem());
            treatment.setWages(d + "");
            System.out.println(treatment);
            treatmentRepo.save(treatment).toString();
            return new Message("1", "成功");
        }
        return new Message("0", "失败");
    }

    public Message add(AddTreatDto addTreatDto) {
        double s = Double.valueOf(addTreatDto.getSalary());

        try {
            List<Treatment> list = treatmentRepo.findByWorknumber(addTreatDto.getWorknumber());
            treatmentRepo.deleteById(list.get(0).getId());
            Treatment treatment = new Treatment();
            treatment.setId(list.get(0).getId());
            treatment.setName(list.get(0).getName());
            treatment.setWorknumber(list.get(0).getWorknumber());

            treatment.setAcc("-" + format(s * 0.03));//减去
            treatment.setDinner("300");
            treatment.setEnd("-" + format(s * 0.08));
            treatment.setHeating("100");
            treatment.setHousing("500");
            treatment.setMed("-" + format(s * 0.06));
            treatment.setSalary(addTreatDto.getSalary());
            if (s > 5000) {
                treatment.setTax("-" + format((s - 5000) * 0.03));
            } else {
                treatment.setTax("0");
            }
            treatment.setTraffic("100");
            treatment.setUnem("-" + format(s * 0.02));

            if (s > 5000) {
                treatment.setWages(format(s - s * (0.03 + 0.08 + 0.06 + 0.02) - (s - 5000) * 0.03 + 1000));
            } else {
                treatment.setWages(format(s - s * (0.03 + 0.08 + 0.06 + 0.02) + 1000));
            }



            treatmentRepo.save(treatment).toString();
            return new Message("1", "成功");
        } catch (Exception e) {
            return new Message("0", "失败");
        }
    }

    public static String format(double d) {
        return String.format("%.2f", d);
    }
    public static Double format(String d) {
        return Double.valueOf(d);
    }
}
