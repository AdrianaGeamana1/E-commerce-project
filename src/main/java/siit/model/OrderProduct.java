package siit.model;

import java.math.BigDecimal;

public class OrderProduct {
    private int id;
    private BigDecimal quantity;
    private BigDecimal value;
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object obj) {
        OrderProduct other = (OrderProduct) obj;
        return this.getQuantity().equals(other.getQuantity()) && this.getValue().equals(other.getValue()) && this.getProduct().equals(other.getProduct());
    }
    @Override
    public int hashCode() {
        int result=0;
        result= result+(getQuantity() != null ? getQuantity().hashCode() : 0);
        result= result+(getValue() != null ? getValue().hashCode() : 0);
        result= result+(getProduct() != null ? getProduct().hashCode() : 0);
        return result;
    }
}
