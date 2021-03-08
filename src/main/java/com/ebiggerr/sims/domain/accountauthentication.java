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

    //private Long lastActive;

    //private String statusAcount;

    protected accountauthentication(){
        //no-arg constructor
    }

    public accountauthentication(String accountID,
                                 String accountUsername,
                                 String accountPassword){
        this.accountID=accountID;
        this.accountUsername=accountUsername;
        this.accountPassword=accountPassword;
        //this.lastActive=lastActive;
        //this.accountStatus=accountStatus;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Set<accountrole> getAccRoles() {
        return accRoles;
    }

    public void setAccRoles(Set<accountrole> accRoles) {
        this.accRoles = accRoles;
    }

    @Override
    public String toString(){

        return "The account username is: " + this.getAccountUsername()
                            + " with account ID of " + this.getAccountID()
                            + " and password of " + this.getAccountPassword()
                            + " and roles of " + this.getAccRoles().toString();
    }


}
