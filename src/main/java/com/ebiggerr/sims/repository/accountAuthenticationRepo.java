/*
 *  Copyright (C) 2020  Alexious Yong (Ebiggerr)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.accountauthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface accountAuthenticationRepo extends JpaRepository<accountauthentication,String> {



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
    List<accountauthentication> getAll();

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
    accountauthentication findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(String accountUsername);


    /**
     *  Update the accountstatus of matching record to "APPROVED"
     * @param username username of account
     */
    @Modifying
    @Transactional
    @Query( value="UPDATE accountauthentication SET accountstatus='APPROVED' WHERE accountusername=?1", nativeQuery=true)
    void approveAccountByUsername(String username);


}
