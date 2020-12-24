package com.hr.sys.user.repo;

import com.hr.sys.user.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author fengz
 * @Date 2020/12/14 0:20
 */
@Repository
public interface SysuserRepo extends JpaRepository<SysUser,String> {
    SysUser findAllByAccountAndPassword(String account,String password);
    SysUser findAllByAccount(String account);
    SysUser findAllByWorknumber(String worknumber);

}
