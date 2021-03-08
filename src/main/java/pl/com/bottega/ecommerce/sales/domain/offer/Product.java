package pl.com.bottega.ecommerce.sales.domain.offer;

import java.util.Objects;

public class Product {

    private String id;
    private String type;
    private String name;
    private Money money;


    public Product(String id, String type, String name, Money money) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.money = money;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Money getMoney() { return money; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(type, product.type) &&
                Objects.equals(name, product.name) &&
                Objects.equals(money, product.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, money);
    }
}
