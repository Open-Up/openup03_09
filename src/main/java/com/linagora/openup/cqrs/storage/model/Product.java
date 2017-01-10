package com.linagora.openup.cqrs.storage.model;

import com.google.common.base.Preconditions;

public class Product {
    private final String name;
    private final int vndPrice;

    public Product(String name, int vndPrice) {
        Preconditions.checkArgument(vndPrice >= 0);
        this.name = name;
        this.vndPrice = vndPrice;
    }

    public String getName() {
        return name;
    }

    public int getVndPrice() {
        return vndPrice;
    }
}
