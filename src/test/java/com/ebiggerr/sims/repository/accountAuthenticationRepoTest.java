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

import com.ebiggerr.sims.domain.accountrole;
import com.ebiggerr.sims.domain.roledetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class accountAuthenticationRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private accountAuthenticationRepo accountAuthenticationRepo;

    @Test
    @DisplayName(value="Testing on listing out \"account authentication\" objects, with the matching account username")
    public void listOutAccountAuthenticationSearchByAccountUsername(){

        /*String account_username="user3";

        accountauthentication temp=new accountauthentication();

        Collection<accountauthentication> list_accountAuthentication= accountAuthenticationRepo.find(account_username);

        Iterator<accountauthentication> list_Iterator=list_accountAuthentication.iterator();

        while(list_Iterator.hasNext()){

            temp=list_Iterator.next();
            System.out.println(temp.toString());

        }
*/
    }

    @Test
    @DisplayName(value="Testing on listing out \"account authentication\" objects, with the matching account username")
    public void listOutAccountAuthenticationdasdaSearchByAccountUsername(){

        String account_username="user3";

        accountauthentication accountAuthentication= accountAuthenticationRepo.findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(account_username);

        String accountID= accountAuthentication.getAccountID();
        String accountUsername=accountAuthentication.getAccountUsername();
        String accountPassword=accountAuthentication.getAccountPassword();

        Set<accountrole> accountroleSet= accountAuthentication.getAccRoles();

        Iterator <accountrole> accountroleIterator = accountroleSet.iterator();

        accountrole temp=new accountrole();

        int size=accountroleSet.size();
        Set<String> list = null;

        for(int i=0; i< size; i++){

            temp=accountroleIterator.next();
            String accountrole_accountID=temp.getAccountID();
            String accountrole_roleID=temp.getRoleID();
            roledetails roleDetails= temp.getRoleDetailsSet();

            String rolename=roleDetails.getRoleName();
            System.out.println(rolename);

            //list.add(roleDetails.getRoleName());

        }

        /*if( list.contains("Inventory Administrator") || list.contains("Inventory Manager") || list.contains("Inventory Staff") )
        {
            System.out.println("success");
        }
        else{
            System.out.println("failed");
        }*/


        //System.out.println(accountAuthentication.toString());



    }


}