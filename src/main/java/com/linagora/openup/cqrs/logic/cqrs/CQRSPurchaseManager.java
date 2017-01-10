package com.linagora.openup.cqrs.logic.cqrs;

import com.linagora.openup.cqrs.logic.PurchaseManager;

public class CQRSPurchaseManager implements PurchaseManager {

    public CQRSPurchaseManager() {
        /*
        Question 1 :

        Listens on a Kafka queue.

        Anytime a message arrives, it consume it, and do the corresponding purchase.
         */
    }

    @Override
    public void purchaseProduct(String name) {
        /*
        Question 2 :

        Send purchase orders on a new queue.
         */
    }
}
