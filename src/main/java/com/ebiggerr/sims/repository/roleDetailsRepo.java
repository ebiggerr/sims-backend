package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.roledetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface roleDetailsRepo extends JpaRepository<roledetails,String> {

    @Query(value="SELECT roleid FROM roledetails WHERE rolename=?1", nativeQuery= true)
    Optional<String> getRoleIDByRoleName(String roleName);


}
