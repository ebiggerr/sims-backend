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
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name="accountrole")
public class accountrole implements Serializable {

    @JsonIgnore
    @Id
    @Column(name="accountid")
    private String accountID;

    @JsonIgnore
    @Id
    @Column(name="roleid")
    private String roleID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid", nullable = false)
    private roledetails roleDetailsSet;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public roledetails getRoleDetailsSet() {
        return roleDetailsSet;
    }

    public void setRoleDetailsSet(roledetails roleDetailsSet) {
        this.roleDetailsSet = roleDetailsSet;
    }

    @Override
    public String toString(){

        return "The account ID is: " + this.getAccountID()
                + " with role ID of " + this.getRoleID()
                + " with Role Name of " + this.getRoleDetailsSet().toString();

    };
}
