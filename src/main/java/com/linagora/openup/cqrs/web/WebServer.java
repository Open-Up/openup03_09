package com.linagora.openup.cqrs.web;

import static spark.Spark.*;

import com.linagora.openup.cqrs.logic.PurchaseManager;
import com.linagora.openup.cqrs.logic.SimplePurchaseManager;
import com.linagora.openup.cqrs.storage.AccountMapper;
import com.linagora.openup.cqrs.storage.ProductMapper;
import com.linagora.openup.cqrs.storage.model.Product;

public class WebServer {

    public static void main(String [] args) {
        new WebServer().start();
    }

    private final ProductMapper productMapper;
    private final JsonTransformer jsonTransformer;
    private final PurchaseManager purchaseManager;
    private final AccountMapper accountMapper;

    public WebServer() {
        productMapper = new ProductMapper();
        jsonTransformer = new JsonTransformer();
        accountMapper = new AccountMapper();
        purchaseManager = new SimplePurchaseManager(accountMapper, productMapper);
        /*
        Question 3

        Change to CQRSPurchaseManager
         */
    }

    public void start() {
        get("/products",
            (req, res) -> productMapper.list(),
            jsonTransformer);

        get("/products/:name",
            (req, res) -> productMapper.find(req.params("name")),
            jsonTransformer);

        post("/products/:name",
            (req, res) -> {
                productMapper.add(
                    new Product(
                        req.params("name"),
                        Integer.parseInt(req.body())));
                return "Operation successful";
            },
            jsonTransformer);

        get("/account",
            (req, res) -> accountMapper.getCurrent(),
            jsonTransformer);

        get("/purchase/:name",
            (req, res) -> {
                purchaseManager.purchaseProduct(req.params("name"));
                return "Operation successful";
            });
    }

}
