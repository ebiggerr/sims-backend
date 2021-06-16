package com.ebiggerr.sims.domain.account;

import java.io.Serializable;
import java.util.Objects;

public class accountIdRoleId implements Serializable {

    private String accountId;
    private String roleId;

    public accountIdRoleId(String accountID, String roleID) {
        this.accountId = accountID;
        this.roleId = roleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        accountIdRoleId that = (accountIdRoleId) o;
        return accountId.equals(that.accountId) &&
                roleId.equals(that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, roleId);
    }
}
