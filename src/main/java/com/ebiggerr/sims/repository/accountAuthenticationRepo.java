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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface accountAuthenticationRepo extends JpaRepository<accountauthentication,String> {

    @Query(value="SELECT aA.accountid,aA.accountusername,aA.accountpassword,aR.accountid,aR.roleid,rD.roleid,rD.rolename,rD.roledescription " +
            "FROM accountauthentication aA INNER JOIN accountrole aR on aA.accountid = aR.accountid " +
            "INNER JOIN roledetails rD on aR.roleid = rD.roleid " +
            "WHERE aA.accountusername=?1",nativeQuery=true)
    accountauthentication findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(String accountUsername);

}
