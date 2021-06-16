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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

public class accountAuthenticationDTO {

    private String accountID;
    private String accountUsername;

    @JsonIgnore
    private String accountPassword;

    private Collection<String> accRoles= new LinkedList<>();

    private LocalDateTime lastActive;
    private LocalDateTime lastLogin;

    public accountAuthenticationDTO(String accountID,
                                 String accountUsername,
                                 Collection<String> accRoles,
                                    LocalDateTime lastLogin,
                                    LocalDateTime lastActive){
        this.accountID=accountID;
        this.accountUsername=accountUsername;
        this.accRoles=accRoles;
        this.lastActive=lastActive;
        this.lastLogin=lastLogin;

    }

    public accountAuthenticationDTO(){

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

    public Collection<String> getAccRoles() {
        return accRoles;
    }

    public void setAccRoles(Collection<String> accRoles) {
        this.accRoles = accRoles;
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
