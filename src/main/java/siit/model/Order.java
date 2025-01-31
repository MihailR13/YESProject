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
}
