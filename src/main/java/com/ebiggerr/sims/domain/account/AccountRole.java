package com.ebiggerr.sims.domain.account;

import javax.persistence.*;

@Entity
@IdClass(accountIdRoleId.class)
@Table(name="accountrole")
public class AccountRole {

    @Id
    @Column(name="accountid")
    private String accountId;

    @Id
    @Column(name="roleid")
    private String roleId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
