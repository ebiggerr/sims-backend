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

import com.ebiggerr.sims.enumeration.AccountStatus;
import com.ebiggerr.sims.enumeration.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
    An object that represents persistent data maintained in a database
 */
@Entity
@Table(name="accountauthentication")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class AccountEntity {

    @Id
    @Column(name="accountid")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accountID;

    @Column(name="accountusername")
    private String accountUsername;

    @JsonIgnore
    @Column(name="accountpassword")
    private String accountPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountstatus")
    @Type( type = "pgsql_enum" )
    private AccountStatus accountStatus;

    @Column(name = "lastactive", nullable = true)
    private LocalDateTime lastActive;

    @Column(name="lastlogin", nullable = true)
    private LocalDateTime lastLogin;

    @Column(name="accountemail")
    private String accountEmail;

    public AccountEntity(){

    }

    public AccountEntity(String accountID, String accountUsername, String accountPassword, AccountStatus accountStatus, LocalDateTime lastActive, LocalDateTime lastLogin, String accountEmail) {
        this.accountID = accountID;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
        this.accountStatus = accountStatus;
        this.lastActive = lastActive;
        this.lastLogin = lastLogin;
        this.accountEmail = accountEmail;
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

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
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

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }
}
