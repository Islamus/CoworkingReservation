package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "workspaces")
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;  // например, "desk", "meeting_room"

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean available;

    // Конструкторы
    public WorkSpace() {}

    public WorkSpace(String type, double price, boolean available) {
        this.type = type;
        this.price = price;
        this.available = available;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
