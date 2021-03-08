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

import java.util.Set;

public class accountDTO {

    private String accountID;
    private String accountUsername;
    private String accountPassword;

    private Set<String> accRoles;
    private Long lastActive;
    //private String accountStatus;

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

    public Set<String> getAccRoles() {
        return accRoles;
    }

    public void setAccRoles(Set<String> accRoles) {
        this.accRoles = accRoles;
    }

    public Long getLastActive() {
        return lastActive;
    }

    public void setLastActive(Long lastActive) {
        this.lastActive = lastActive;
    }

    /**
     *
     */
    public account getAccountFromDTO(){
        account acc=new account();


        return acc;
    }
}
