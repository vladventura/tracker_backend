package com.merrymen.shopbackend.part;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Entity
@Table
public class Part {
    @Id
    @SequenceGenerator(name = "part_sequence", sequenceName = "partSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_sequence")
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Price Bought At is missing")
    @PositiveOrZero(message = "Price must be a positive number")
    private Float priceBoughtAt;
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    private Float currentPrice;
    @NotNull(message = "Date bought At is missing")
    private LocalDate dateBoughtAt;

    public LocalDate getDateBoughtAt() {
        return dateBoughtAt;
    }

    public void setDateBoughtAt(LocalDate dateBoughtAt) {
        this.dateBoughtAt = dateBoughtAt;
    }

    public Part() {
    }

    public Part(String name, float priceBoughtAt) {
        this.name = name;
        this.priceBoughtAt = priceBoughtAt;
    }

    public Part(String name, Float priceBoughtAt, String description, Float currentPrice, LocalDate dateBoughtAt) {
        this.name = name;
        this.priceBoughtAt = priceBoughtAt;
        this.description = description;
        this.currentPrice = currentPrice;
        this.dateBoughtAt = dateBoughtAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPriceBoughtAt() {
        return priceBoughtAt;
    }

    public void setPriceBoughtAt(float priceBoughtAt) {
        this.priceBoughtAt = priceBoughtAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name + " " + this.priceBoughtAt;
    }
}
