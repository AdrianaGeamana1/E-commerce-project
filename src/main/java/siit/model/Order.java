package siit.model;

import java.time.LocalDateTime;

public class Order {

    private int id;
    private String number;

    private double value;
    private LocalDateTime placed;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getPlaced() {
        return placed;
    }

    public void setPlaced(LocalDateTime placed) {
        this.placed = placed;
    }

    @Override
    public boolean equals(Object obj) {
        Order other = (Order) obj;
        return this.getNumber().equals(other.getNumber()) && this.getValue() == other.getValue() && this.getPlaced().equals(other.getPlaced());
    }
    @Override
    public int hashCode() {
        int result=0;
        result= result+(getNumber() != null ? getNumber().hashCode() : 0);
        result=result+(getValue()!=0?(int)getValue()*31:0);
        return result;
    }
}
