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

package com.ebiggerr.sims.service.account;

import com.ebiggerr.sims.domain.account.accountEntity;
import com.ebiggerr.sims.domain.accountauthentication;
import com.ebiggerr.sims.repository.accRoleRepo;
import com.ebiggerr.sims.repository.accountAuthenticationRepo;
import com.ebiggerr.sims.repository.accountRepo;
import com.ebiggerr.sims.repository.roleDetailsRepo;
import com.ebiggerr.sims.service.idService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class accountService {

    private final accountRepo accountRepo;
    private final accountAuthenticationRepo accountAuthenticationRepo;
    private final roleDetailsRepo roleDetailsRepo;
    private final accRoleRepo accRoleRepo;

    private final String BASIC_ROLE="Staff";

    //constructor-based dependency injection
    //@Autowired
    public accountService(accountRepo accountRepo,accountAuthenticationRepo accountAuthenticationRepo, roleDetailsRepo roleDetailsRepo,accRoleRepo accRoleRepo){
        this.accountRepo=accountRepo;
        this.accountAuthenticationRepo=accountAuthenticationRepo;
        this.roleDetailsRepo=roleDetailsRepo;
        this.accRoleRepo=accRoleRepo;
    }

    /**
     * <h1>Registration of an account under the system</h1>
     * @param username email address of the account
     * @param password password of the account
     * @return [boolean] status of the operation successful : true ? false
     */
    public boolean registerAccount(String username,String password){

        Optional<String> duplicate = accountRepo.getIDByUsernameSearchDuplicates(username);

        //duplicates of username
        if( duplicate.isPresent() ) return false;

        String email = username;

        // abc@mail.com
        // extract abc as the username of the account
        username = username.split("@")[0];

        String latestID=accountRepo.getMaxID();
        String newID= idService.idIncrement(latestID);
        accountRepo.registerAccount(newID,username,password,email);

        return true;
    }

    /**
     * <h1> Update an account's Last Login time by username</h1>
     *
     * Update the Last Login timestamp using LocalDateTime.now()
     *
     * @param username account's username
     */
    public void updateLastLoginTime(String username){
        accountRepo.loginUpdateLastLogin( LocalDateTime.now() ,username);
    }

    public void logout(String accountUsername){
        accountRepo.logoutUpdateLastActive( LocalDateTime.now(),accountUsername);
    }

    public boolean revokeAnAccount(String accountUsername){

        Optional<String> id = accountRepo.getIDByUsername(accountUsername);

        if( id.isPresent() ){
            accountRepo.revokeUsingUsername(accountUsername);
            return true;
        }
        else{
            return false;
        }
    }

    public accountauthentication getByUsername(String username) {

        return accountAuthenticationRepo.findaccountAutheticationWithAccountRolesAndRoleDetailsByAccountUsername(username);

    }

    public List<accountEntity> getAllPending(){

        Optional<List<accountEntity>> list= accountRepo.getAllPending();
        if( list.isPresent() ){
            return list.get();
        }
        else{
            return null;
        }
    }

    /**
     * <h1> Update roles of an account with given username and roles</h1>
     * Update of roles will not happen if there is any conflict, and that account have that roles/authorities
     * For example: If user1 has roles of Admin, roles of "Admin" will not be updated again if the @param roles is Admin,Manager [ Update Manager only, ignoring Admin]
     *
     * @param arr [String array] Contains all the role names
     * @param username Target account's username
     * @return [boolean] Result of the operation ? Succeed : Failed [True : False]
     */
    public boolean updateRolesForAnAccount(String [] arr, String username){

        int size = arr.length;
        String temp="";
        Optional<String> roleName;
        int counter=0;

        //get accountID using username
        Optional<String> accountID = accountRepo.getIDByUsername(username);

        if( !accountID.isEmpty() ) {

            for (int index = 0; index < size; index++) {

                //role name
                temp = arr[index].trim();
                temp = StringUtils.capitalize(temp);

                //roleID
                roleName = roleDetailsRepo.getRoleIDByRoleName(temp);

                //if there is a role with that Name
                if( roleName.isPresent() ) accRoleRepo.addNewRecord( accountID.get() , roleName.get() ); //INSERT INTO ( accountID,roleID )
                else{
                    return false;
                }

            }
        }
        else return false;

        return true;
    }

    /**
     * <h1> Revoke roles of an account with given username and roles</h1>
     *
     * @param arr [String array] Contains all the role names
     * @param username Target account's username
     * @return [boolean] Result of the operation ? Succeed : Failed [True : False]
     */
    public boolean revokeRolesForAnAccount(String[] arr, String username) {

        int size = arr.length;
        String temp="";
        int counter=0;
        Optional<String> roleName;

        Optional<String> accountID= accountRepo.getIDByUsername(username);

        if( accountID.isPresent() ) {

            for (int index = 0; index < size; index++) {

                //role name
                temp = arr[index].trim().toLowerCase();
                temp = StringUtils.capitalize(temp);

                //roleID
                roleName = roleDetailsRepo.getRoleIDByRoleName(temp);

                Optional<String> ifItHasTheRole = accRoleRepo.search( accountID.get() , temp);

                if( ifItHasTheRole.isPresent() && roleName.isPresent() ) accRoleRepo.revokeRoles( accountID.get() , temp ); // DELETE WHERE accountID=? AND roleID=?
                else{
                    return false;
                }


            }
        }
        else return false;

        return true;
    }

    /**
     * <h1> Approving an account </h1>
     *
     * Update the accountStatus of newly registered account from "PENDING" to "APPROVED"
     * Give the account with basic authorities by default
     *
     * @param username Target account's username
     */
    public boolean approveAnAccount(String username){

        Optional<String> id = accountRepo.getIDByUsernamePending(username);

        if(id.isEmpty() ){
            return false;
        }
        else{
            //update the accountStatus from "PENDING" to "APPROVED"
            accountAuthenticationRepo.approveAccountByUsername(username);

            //get the roleID of the BASIC_ROLE
            String roleID;
            Optional<String> temp = roleDetailsRepo.getRoleIDByRoleName(BASIC_ROLE);
            if( temp.isPresent()) roleID = temp.get();
            else{
                return false;
            }
            //give the account with basic authorities by default
            accRoleRepo.addNewRecord( id.get() , roleID );

            return true;
        }
        //Optional<String> accountID = accountRepo.getIDByUsername(username);

        /*if( accountID.isPresent() ){

            //get the roleID of the BASIC_ROLE
            String roleID = roleDetailsRepo.getRoleIDByRoleName(BASIC_ROLE);

            //give the account with basic authorities by default
            accRoleRepo.addNewRecord( accountID.get() , roleID );

        }*/



    }

}
