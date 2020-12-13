package com.hr.sys.user.repo;

import com.hr.sys.user.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author fengz
 * @Date 2020/12/11 9:44
 */
@Repository
public interface TreatmentRepo extends JpaRepository<Treatment,String> {
    Treatment findAllByWorknumber(String worknumber);

    List<Treatment> findAllByWorknumberAndName(String worknumber,String name);

    List<Treatment> findAllByName(String name);

    List<Treatment> findByWorknumber(String worknumber);
}
