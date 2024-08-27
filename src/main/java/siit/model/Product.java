package siit.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal weight;
    private BigDecimal price;
    private String image;
    private int stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object obj) {
        Product other = (Product) obj;
        return this.getName().equals(other.getName()) && this.getPrice().equals(other.getPrice()) && this.getImage().equals(other.getImage());
    }
    @Override
    public int hashCode() {
        int result=0;
        result= result+(getName() != null ? getName().hashCode() : 0);
        result= result+(getPrice() != null ? getPrice().hashCode() : 0);
        result= result+(getImage() != null ? getImage().hashCode() : 0);
        return result;
    }
}
