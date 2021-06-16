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
public class accountauthentication {

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
    private Set<accountrole> accRoles;

    @Column( name="lastlogin",nullable = true)
    private LocalDateTime lastLogin;

    @Column (name="lastactive",nullable = true)
    private LocalDateTime lastActive;

    public accountauthentication(){
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

    public accountauthentication(String accountID,
                                 String accountUsername,
                                 String accountPassword,
                                 LocalDateTime lastLogin,
                                 LocalDateTime lastActive,
                                 Set<accountrole> accRoles){
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

    public Set<accountrole> getAccRoles() {
        return this.accRoles;
    }

    public void setAccRoles(Set<accountrole> accRoles) {
        this.accRoles = accRoles;
    }

    /*@Override
    public String toString(){

        return "The account username is: " + this.getAccountUsername()
                            + " with account ID of " + this.getAccountID()
                            + " and password of " + this.getAccountPassword()
                            + " and roles of " + this.getAccRoles().toString();
    }*/

    public accountAuthenticationDTO getAccountAuthenticationDTOFromEntity(accountauthentication accountauthentication){

        accountAuthenticationDTO accountauthenticationDTO= new accountAuthenticationDTO();
        accountauthenticationDTO.setAccountID(accountauthentication.getAccountID());
        accountauthenticationDTO.setAccountUsername(accountauthentication.getAccountUsername());
        accountauthenticationDTO.setAccountPassword(accountauthentication.getAccountPassword());

        Set<accountrole> accountRoleSet=accountauthentication.getAccRoles();
        Iterator<accountrole> accountRoleIterator = accountRoleSet.iterator();

        accountrole temp = new accountrole();
        int size = accountRoleSet.size();

        Collection<String> roleList= new LinkedList<>();

        for( int index=0; index < size ; index ++){

            temp=accountRoleIterator.next();
            //String accountRole_accountID = temp.getAccountID();
            //String accountRole_roleID = temp.getRoleID();

            roledetails roleDetails = temp.getRoleDetailsSet();
            //String roleName=roledetails.getRoleName();

            roleList.add( roleDetails.getRoleName() );
        }

        accountauthenticationDTO.setAccRoles(roleList);

        return accountauthenticationDTO;
    }


}
