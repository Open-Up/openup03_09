package com.linagora.openup.cqrs.notification;

import com.linagora.openup.cqrs.storage.ProductMapper;

public class NotifyPurchase {

    private final ProductMapper productMapper;

    public NotifyPurchase(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public void notify(String name) {
        longNotificationGenerationDelay();
        productMapper.find(name)
            .ifPresent(product -> System.out.println("Product " + product.getName() + " was purchased for " + product.getVndPrice() + " VNDs"));
    }

    private void longNotificationGenerationDelay() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
