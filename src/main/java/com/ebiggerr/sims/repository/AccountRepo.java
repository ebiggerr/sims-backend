/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.account.AccountEntity;
import com.zaxxer.hikari.util.IsolationLevel;
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
public interface AccountRepo extends JpaRepository <AccountEntity,String> {

    @Query(value="SELECT accountid from accountauthentication WHERE accountstatus='APPROVED' AND accountusername=?1", nativeQuery=true)
    Optional<String> getAccountIdOfAnApprovedAccountWithGivenUsername(String username);

    @Query(value="SELECT accountid from accountauthentication WHERE accountstatus='PENDING' AND accountusername=?1", nativeQuery=true)
    Optional<String> getAccountIdOfAnPendingAccountWithGivenUsername(String username);

    @Query(value="SELECT accountid from accountauthentication WHERE accountusername=?1", nativeQuery=true)
    Optional<String> getDuplicatesIfThereIsAnyWithGivenUsername(String username);

    @Query(value="SELECT accountid,accountusername,accountpassword,accountstatus,lastlogin,lastactive from accountauthentication WHERE accountstatus='PENDING'", nativeQuery=true)
    Optional<List<AccountEntity>> getAllPendingAccountsInAccountAuthenticationTable();

    @Query(value="SELECT MAX(accountauthentication.accountid) FROM accountauthentication", nativeQuery=true)
    String getMaxId();

    //@Deprecated // replaced with save() method available in JpaRepository
    /* @Modifying
    @Query(value="INSERT INTO accountauthentication (accountid,accountusername,accountpassword,accountstatus,accountemail) VALUES ( :userID, :username, :password,'PENDING', :accountemail ) ON CONFLICT (accountusername) DO NOTHING",nativeQuery=true)
    void insertANewRecordInTheAccountAuthenticationTableInTheDatabaseWithPendingAccountStatusAsDefault(@Param("userID")String userID, @Param("username")String username, @Param("password")String encodedPassword, @Param("accountemail")String email);
*/
    @Modifying
    //@Transactional
    @Query( value="UPDATE accountauthentication SET lastactive=?1 WHERE accountauthentication.accountusername=?2"
            ,nativeQuery=true)
    void updateTheLastActiveTimestampOfARecordInTheAccountAuthenticationTableWithGivenUsername(LocalDateTime timestamp, String accountUsername);

    @Modifying
    //@Transactional
    @Query( value="UPDATE accountauthentication SET lastlogin=?1 WHERE accountauthentication.accountusername=?2"
            ,nativeQuery=true)
    void updateTheLastLoginTimestampOfARecordInTheAccountAuthenticationTableWithGivenUsername(LocalDateTime timestamp, String accountUsername);

    @Modifying
    //@Transactional
    @Query(value="UPDATE accountauthentication SET accountstatus='REVOKED' WHERE accountauthentication.accountusername=?1", nativeQuery=true)
    void updateTheAccountStatusOfARecordInAccountAuthenticationToRevokedWithGivenUsername(String username);

}
