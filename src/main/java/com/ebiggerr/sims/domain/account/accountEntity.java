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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
    An object that represents persistent data maintained in a database
 */
@Entity
@Table(name="accountauthentication")
public class accountEntity {

    @Id
    @Column(name="accountid")
    private String accountID;

    @Column(name="accountusername")
    private String accountUsername;

    @Column(name="accountpassword")
    private String accountPassword;

    private Long lastActive;

    private String accountStatus;




}
