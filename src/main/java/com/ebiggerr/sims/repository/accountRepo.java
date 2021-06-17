package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.account.accountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface accountRepo extends JpaRepository <accountEntity,String> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO accountauthentication (accountid,accountusername,accountpassword,accountstatus,accountemail) VALUES ( :userID, :username, :password,'PENDING', :accountemail ) ON CONFLICT (accountusername) DO NOTHING",nativeQuery=true)
    void registerAccount( @Param("userID")String userID, @Param("username")String username,@Param("password")String encodedPassword, @Param("accountemail")String email);

    @Modifying
    @Transactional
    @Query( value="UPDATE accountauthentication SET lastactive=?1 WHERE accountauthentication.accountusername=?2"
            ,nativeQuery=true)
    void logoutUpdateLastActive(LocalDateTime timestamp, String accountUsername);

    @Modifying
    @Transactional
    @Query( value="UPDATE accountauthentication SET lastlogin=?1 WHERE accountauthentication.accountusername=?2"
            ,nativeQuery=true)
    void loginUpdateLastLogin(LocalDateTime timestamp,String accountUsername);


    @Query(value="select max(accountauthentication.accountid) from accountauthentication", nativeQuery=true)
    String getMaxID();

    @Modifying
    @Transactional
    @Query(value="UPDATE accountauthentication SET accountstatus='REVOKED' WHERE accountauthentication.accountusername=?1", nativeQuery=true)
    void revokeUsingUsername(String username);

    @Query(value="SELECT accountid from accountauthentication WHERE accountstatus='APPROVED' AND accountusername=?1", nativeQuery=true)
    Optional<String> getIDByUsername(String username);

    @Query(value="SELECT accountid from accountauthentication WHERE accountstatus='PENDING' AND accountusername=?1", nativeQuery=true)
    Optional<String> getIDByUsernamePending(String username);

    @Query(value="SELECT accountid from accountauthentication WHERE accountusername=?1", nativeQuery=true)
    Optional<String> getIDByUsernameSearchDuplicates(String username);

    @Query(value="SELECT accountid,accountusername,accountpassword,accountstatus,lastlogin,lastactive from accountauthentication WHERE accountstatus='PENDING'", nativeQuery=true)
    Optional<List<accountEntity>> getAllPending();

}
