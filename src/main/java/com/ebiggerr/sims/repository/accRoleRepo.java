package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.account.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface accRoleRepo extends JpaRepository<AccountRole,String> {

    @Modifying
    @Transactional
    @Query( value= "INSERT INTO accountrole (accountid,roleid) VALUES ( ?1,?2 ) ON CONFLICT (accountid,roleid) DO NOTHING", nativeQuery=true)
    void addNewRecord(String accountID, String roleID);

    @Modifying
    @Transactional
    @Query( value= "DELETE FROM accountrole WHERE accountid=?1 AND roleid=?2", nativeQuery=true)
    void revokeRoles(String accountID, String roleID);

    @Query( value= "SELECT accountid FROM accountrole WHERE accountid=?1 AND roleid=?2", nativeQuery=true)
    Optional<String> search (String accountID, String roleID);
}
