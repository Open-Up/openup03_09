package com.linagora.openup.cqrs.storage;

import com.google.common.base.Preconditions;

public class AccountMapper {

    private int current;

    public AccountMapper() {
        this.current = 0;
    }

    public int getCurrent() {
        return current;
    }

    public void increment(int earnedMoney) {
        Preconditions.checkArgument(earnedMoney >= 0);
        current += earnedMoney;
    }
}
