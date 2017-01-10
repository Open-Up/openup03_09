package com.linagora.openup.cqrs.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.linagora.openup.cqrs.storage.model.Product;

public class ProductMapper {
    private final List<Product> products;

    public ProductMapper() {
        products = new ArrayList<>();
    }

    public void add(Product product) {
        Preconditions.checkArgument(!find(product.getName()).isPresent());
        products.add(product);
    }

    public void remove(String name) {
        Optional<Product> productOptional = find(name);
        productOptional.ifPresent(products::remove);
    }

    public List<Product> list() {
        return ImmutableList.copyOf(products);
    }

    public Optional<Product> find(String name) {
        return products.stream()
            .filter(product -> product.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}
