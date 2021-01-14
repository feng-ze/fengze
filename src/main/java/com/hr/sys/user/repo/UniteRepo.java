package com.hr.sys.user.repo;

import com.hr.sys.user.entity.Unite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author fengz
 * @Date 2021/1/13 10:25
 */
@Repository
public interface UniteRepo extends JpaRepository<Unite,String> {
    Unite findAllByUserIdAndTreatId(String userId,String treatId);
}
