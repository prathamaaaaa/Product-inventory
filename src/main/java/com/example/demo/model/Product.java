package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(precision = 10, scale = 0)
    private BigDecimal Price;
    @ManyToOne
    @JoinColumn(name = "SubCategory_Id", nullable = false) // Foreign Key Column
    private subCategories subCategories;

    @ManyToOne
    @JoinColumn(name = "Category_id", nullable = false)
    private Categories Categories;

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public subCategories getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(subCategories subCategories) {
        this.subCategories = subCategories;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public String getName() {
        return name;
    }

    public subCategories getSubCategory() {
        return subCategories;
    }

    public void setSubCategory(subCategories subCategory) {
        subCategories = subCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categories getCategories() {
        return Categories;
    }

    public void setCategories(Categories categories) {
        Categories = categories;
    }
//    public void setName(String trim) {
//    }
}
