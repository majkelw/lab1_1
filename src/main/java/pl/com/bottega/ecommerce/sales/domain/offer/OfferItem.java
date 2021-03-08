/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    private final Product product;
    private final Money money;
    private final Date productSnapshotDate;
    private final Discount discount;
    private int quantity;
    private final BigDecimal totalCost;

    public OfferItem(Product product, Money money, Date productSnapshotDate, int quantity) {
        this(product, money, productSnapshotDate, quantity, null);
    }

    public OfferItem(Product product, Money money, Date productSnapshotDate, int quantity, Discount discount) {
        this.product = product;
        this.money = money;
        this.productSnapshotDate = productSnapshotDate;
        this.discount = discount;
        this.quantity = quantity;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.subtract(discount.getDiscount());
        }

        this.totalCost = money.getAmount().multiply(new BigDecimal(quantity)).subtract(discountValue);
    }


    public Date getProductSnapshotDate() {
        return productSnapshotDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Money getMoney() {
        return money;
    }

    public Discount getDiscount() {
        return discount;
    }

    /**
     * @param other
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.compareTo(other.totalCost) > 0) {
            max = totalCost;
            min = other.totalCost;
        } else {
            max = other.totalCost;
            min = totalCost;
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferItem offerItem = (OfferItem) o;
        return quantity == offerItem.quantity &&
                product.equals(offerItem.product) &&
                money.equals(offerItem.money) &&
                productSnapshotDate.equals(offerItem.productSnapshotDate) &&
                discount.equals(offerItem.discount) &&
                totalCost.equals(offerItem.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, money, productSnapshotDate, discount, quantity, totalCost);
    }
}
