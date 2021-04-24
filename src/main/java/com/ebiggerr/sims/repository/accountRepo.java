package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.account.account;
import com.ebiggerr.sims.domain.account.accountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface accountRepo extends JpaRepository <accountEntity,String> {

    @Query(value="INSERT INTO accountauthentication (accountid,accountusername,accountpassword) " +
            "VALUES ( :accountEntity.accountid , :accountEntity.accountusername , :accountEntity.accountpassword )"
            ,nativeQuery=true)
    void registerAccount(@Param("accountEntity") accountEntity accountEntity);

    @Query( value="UPDATE accountauthentication SET lastActive=?1 WHERE accountauthentication.accountUsername=?2"
            ,nativeQuery=true)
    void logoutUpdateLastActive( @Param("accountUsername") Long timestamp, String accountUsername);




}
