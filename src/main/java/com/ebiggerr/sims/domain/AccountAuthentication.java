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

package com.ebiggerr.sims.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/*
    An object that represents persistent data maintained in a database
 */
@Entity
@Table(name= "accountauthentication")
public class AccountAuthentication {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "accountid")
    private String accountID;

    @Column(name= "accountusername")
    private String accountUsername;

    @JsonIgnore
    @Column(name= "accountpassword")
    private String accountPassword;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountid", nullable = false)
    private Set<AccountRoleJoin> accRoles;

    @Column( name="lastlogin",nullable = true)
    private LocalDateTime lastLogin;

    @Column (name="lastactive",nullable = true)
    private LocalDateTime lastActive;

    public AccountAuthentication(){
        //no-arg constructor
    }

  /*  public accountauthentication(String accountID,
                                 String accountUsername,
                                 String accountPassword,
                                 Set<accountrole> accRoles){
        this.accountID=accountID;
        this.accountUsername=accountUsername;
        this.accountPassword=accountPassword;
        this.accRoles=accRoles;

    }*/

    public AccountAuthentication(String accountID,
                                 String accountUsername,
                                 String accountPassword,
                                 LocalDateTime lastLogin,
                                 LocalDateTime lastActive,
                                 Set<AccountRoleJoin> accRoles){
        this.accountID=accountID;
        this.accountUsername=accountUsername;
        this.accountPassword=accountPassword;
        this.accRoles=accRoles;
        this.lastLogin=lastLogin;
        this.lastActive=lastActive;

    }

    public String getAccountID() {
        return this.accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountUsername() {
        return this.accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountPassword() {
        return this.accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public Set<AccountRoleJoin> getAccRoles() {
        return this.accRoles;
    }

    public void setAccRoles(Set<AccountRoleJoin> accRoles) {
        this.accRoles = accRoles;
    }

    /*@Override
    public String toString(){

        return "The account username is: " + this.getAccountUsername()
                            + " with account ID of " + this.getAccountID()
                            + " and password of " + this.getAccountPassword()
                            + " and roles of " + this.getAccRoles().toString();
    }*/

    public AccountAuthenticationDTO getAccountAuthenticationDTOFromEntity(AccountAuthentication accountauthentication){

        AccountAuthenticationDTO accountauthenticationDTO= new AccountAuthenticationDTO();
        accountauthenticationDTO.setAccountID(accountauthentication.getAccountID());
        accountauthenticationDTO.setAccountUsername(accountauthentication.getAccountUsername());
        accountauthenticationDTO.setAccountPassword(accountauthentication.getAccountPassword());

        Set<AccountRoleJoin> accountRoleSet=accountauthentication.getAccRoles();
        Iterator<AccountRoleJoin> accountRoleIterator = accountRoleSet.iterator();

        AccountRoleJoin temp = new AccountRoleJoin();
        int size = accountRoleSet.size();

        Collection<String> roleList= new LinkedList<>();

        for( int index=0; index < size ; index ++){

            temp=accountRoleIterator.next();
            //String accountRole_accountID = temp.getAccountID();
            //String accountRole_roleID = temp.getRoleID();

            RoleDetails roleDetails = temp.getRoleDetailsSet();
            //String roleName=roledetails.getRoleName();

            roleList.add( roleDetails.getRoleName() );
        }

        accountauthenticationDTO.setAccRoles(roleList);

        return accountauthenticationDTO;
    }


}
