package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "Categories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<subCategories> subcategories; // One product can have multiple categories
    @OneToMany(mappedBy = "Categories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> product; // One product can have multiple categories

    public int getId() {
        return id;
    }

    public List<subCategories> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<subCategories> subcategories) {
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
