package eu.vitaliy.pointofsale.domain;

import java.math.BigDecimal;

/**
 * User: VitaliyOliynyk
 * Date: 28.05.13
 * Time: 15:00
 */
public class Product {
    private String code;
    private String name;
    private float price;

    public Product(String code, String name, float price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!code.equals(product.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
