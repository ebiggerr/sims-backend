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

import com.ebiggerr.sims.domain.AccountAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountAuthenticationRepo extends JpaRepository<AccountAuthentication,String> {



    /**
     * Return a list of @entity accountauthentication with the condition that the acccount having
     * accountstatus of "APPROVED"
     *
     * @return [List] accountauthenticaion
     */
    @Query(value="SELECT aA.accountid,aA.accountusername,aA.accountpassword,aA.lastlogin,aA.lastactive,aR.accountid,aR.roleid,rD.roleid,rD.rolename,rD.roledescription " +
            "FROM accountauthentication aA INNER JOIN accountrole aR on aA.accountid=aR.accountid " +
            "INNER JOIN roledetails rD on aR.roleid=rD.roleid " +
            "WHERE aA.accountstatus='APPROVED'",nativeQuery=true)
    List<AccountAuthentication> getAllApprovedAccountInTheDatabaseWithJoinedAccountRoleAndRoleDetails();

    /**
     * Return one @entity accountauthentication with the condition of matching username with
     * given username and the accountstatus is "APPROVED"
     * @param accountUsername username
     * @return [accountauthentication] @entity with matching username and accountstatus of "APPROVED"
     */
    @Query(value="SELECT aA.accountid,aA.accountusername,aA.accountpassword,aA.lastlogin,aA.lastactive,aR.accountid,aR.roleid,rD.roleid,rD.rolename,rD.roledescription " +
            "FROM accountauthentication aA INNER JOIN accountrole aR on aA.accountid=aR.accountid " +
            "INNER JOIN roledetails rD on aR.roleid=rD.roleid " +
            "WHERE aA.accountstatus='APPROVED' AND aA.accountusername=?",nativeQuery=true)
    AccountAuthentication getApprovedAccountInTheDatabaseWithJoinedAccountRoleAndRoleDetailsWithGivenAccountUsername(String accountUsername);


    /**
     *  Update the accountstatus of matching record to "APPROVED"
     * @param username username of account
     */
    @Modifying
    @Transactional
    @Query( value="UPDATE accountauthentication SET accountstatus='APPROVED' WHERE accountusername=?1", nativeQuery=true)
    void updateAnPendingAccountInTheDatabaseToApprovedStatusInAccountStatus(String username);


}
