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
