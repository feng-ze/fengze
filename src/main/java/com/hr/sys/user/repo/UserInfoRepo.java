package com.hr.sys.user.repo;

import com.hr.sys.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author fengz
 * @Date 2020/12/9 20:55
 */
@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,String> {

    List<UserInfo> findAllByWorknunberAndName(String worknumber,String name);

    List<UserInfo> findAllByName(String name);

    List<UserInfo> findByWorknunber(String worknumber);

    UserInfo findAllByWorknunber(String worknumber);
}
