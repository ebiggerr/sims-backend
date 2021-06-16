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

package com.ebiggerr.sims.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
    An object that represents persistent data maintained in a database
 */
@Entity
@Table(name="accountauthentication")
public class accountEntity {

    @Id
    @Column(name="accountid")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String accountID;

    @Column(name="accountusername")
    private String accountUsername;

    @JsonIgnore
    @Column(name="accountpassword")
    private String accountPassword;

    @Column(name ="accountstatus")
    private String AccountStatus;

    @Column(name = "lastactive", nullable = true)
    private LocalDateTime lastActive;

    @Column(name="lastlogin", nullable = true)
    private LocalDateTime lastLogin;

    public accountEntity(){

    }

    public accountEntity(String accountID, String accountUsername, String accountPassword, String accountStatus, LocalDateTime lastActive, LocalDateTime lastLogin) {
        this.accountID = accountID;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
        AccountStatus = accountStatus;
        this.lastActive = lastActive;
        this.lastLogin = lastLogin;
    }

    /*   public account getAccountFromDTO(){

        account acc=new account();
        acc.setAccountID(accountID);
        acc.setAccountUsername(accountUsername);
        acc.setAccountPassword(accountPassword);

        return acc;

    }*/

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

    public String getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        AccountStatus = accountStatus;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
