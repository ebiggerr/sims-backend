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

import com.ebiggerr.sims.domain.account.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccRoleRepo extends JpaRepository<AccountRole,String> {

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
