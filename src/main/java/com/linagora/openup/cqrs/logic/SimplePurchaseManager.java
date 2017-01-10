package com.linagora.openup.cqrs.logic;

import com.linagora.openup.cqrs.storage.AccountMapper;
import com.linagora.openup.cqrs.storage.ProductMapper;
import com.linagora.openup.cqrs.storage.model.Product;

public class SimplePurchaseManager implements PurchaseManager {
    private final AccountMapper accountMapper;
    private final ProductMapper productMapper;

    public SimplePurchaseManager(AccountMapper accountMapper, ProductMapper productMapper) {
        this.accountMapper = accountMapper;
        this.productMapper = productMapper;
    }

    @Override
    public void purchaseProduct(String name) {
        longTraitement();
        Product product = productMapper.find(name).orElseThrow(() -> new IllegalArgumentException(name + " does not exist"));
        accountMapper.increment(product.getVndPrice());
    }

    private void longTraitement() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
