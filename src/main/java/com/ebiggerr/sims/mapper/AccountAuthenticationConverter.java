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

package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.AccountAuthentication;
import com.ebiggerr.sims.domain.AccountAuthenticationDTO;
import com.ebiggerr.sims.domain.AccountRoleJoin;
import com.ebiggerr.sims.domain.RoleDetails;

import java.util.*;
import java.util.stream.Collectors;

public class AccountAuthenticationConverter {

    /**
     * <h1> Convert an Entity to DTO </h1>
     *
     *
     * accountauthentication : Set&lt;accountrole&gt;
     * <br></br>
     * accountAuthenticationDTO : Collection&lt;String&gt;
     *
     * @param acc Entity accountauthentication.class
     * @return accountAuthenticationDTO
     */
    public AccountAuthenticationDTO entityToDTO(AccountAuthentication acc){

        //convert the Set<accountrole> to Collection<String>
        Set<AccountRoleJoin> accountRoleSet=acc.getAccRoles();
        Iterator<AccountRoleJoin> accountRoleIterator = accountRoleSet.iterator();

        AccountRoleJoin temp = new AccountRoleJoin();
        int size = accountRoleSet.size();

        Collection<String> roleList= new LinkedList<>();

        for( int index=0; index < size ; index ++){

            temp=accountRoleIterator.next();

            RoleDetails roleDetails = temp.getRoleDetailsSet();

            roleList.add( roleDetails.getRoleName() );
        }

        return new AccountAuthenticationDTO( acc.getAccountID(),acc.getAccountUsername(), roleList, acc.getLastLogin(),acc.getLastActive() );

    }

    /**
     * <h1> Convert list of Entity to list of DTO </h1>
     *
     * Using the base method of entityToDTO
     * <br></br>
     * Stream
     *
     * @param accList List of Entity
     * @return List of DTO
     */
    public List<AccountAuthenticationDTO> entitiesToDTO(List<AccountAuthentication> accList ){

        //@Deprecated
        /*List<AccountAuthenticationDTO> list=new LinkedList<>();

        for( int index=0; index< accList.size(); index++){
            list.add( this.entityToDTO( accList.get(index) ) );
        }*/

        return accList.stream().map(this::entityToDTO).collect(Collectors.toList());

        //return list;
    }
}
