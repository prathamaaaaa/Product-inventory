
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subCategories")
public class subCategories{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // Category name

    @ManyToOne
    @JoinColumn(name = "Category_id", nullable = false)
    private Categories Categories; // Link category to a product

    @OneToMany(mappedBy = "subCategories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> Product;

    public List<Product> getProduct() {
        return Product;
    }

    public void setProduct(List<Product> product) {
        Product = product;
    }


    public Categories getCategories() {
        return Categories;
    }

    public void setCategories(Categories categories) {
        Categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
