package com.example.demo.controller;


import com.example.demo.model.Categories;
import com.example.demo.model.Product;
import com.example.demo.model.subCategories;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @GetMapping("/all")
    public String showPage(@RequestParam(defaultValue = "List") String page, Model model) {
        model.addAttribute("page", page);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        List<Categories> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<subCategories> subCategories = subCategoryRepository.findAll();
        model.addAttribute("subCategories", subCategories);
        return "Component";
    }


    @PostMapping("/save-category")
    public String savecategory(@RequestParam("category[]") List<String> categoryNames) {
        for (String name : categoryNames) {
            if (name != null && !name.trim().isEmpty()) {
                Categories categories = new Categories();
                categories.setName(name.trim());
                categoryRepository.save(categories);
            }
        }
        return "redirect:/all";
    }

    @PostMapping("/save-subcategory")
    public String savesubCategory(@RequestParam("categoryId") int categoryId,
                               @RequestParam("subcategoryName[]") List<String> subcategoryNames) {
        Optional<Categories> categoriesOpt = categoryRepository.findById(categoryId);

        if (categoriesOpt.isPresent()) {
            Categories categories = categoriesOpt.get();

            for (String subcategoryName : subcategoryNames) {
                if (subcategoryName != null && !subcategoryName.trim().isEmpty()) {
                    subCategories subcategory = new subCategories();
                    subcategory.setName(subcategoryName.trim());
                    subcategory.setCategories(categories);
                    subCategoryRepository.save(subcategory);
                }
            }
        }
        return "redirect:/all";
    }
    @PostMapping("/save-product")
    public String saveProduct(
            @RequestParam("categoriesId") int categoryId,
            @RequestParam("subcategoryId") int subcategoryId,
            @RequestParam("productPrice") List<BigDecimal> price,
            @RequestParam("productName") List<String> productNames) {

        System.out.println("Received categoryId: " + categoryId);
        System.out.println("Received subcategoryId: " + subcategoryId);

        Optional<Categories> categoriesOptional = categoryRepository.findById(categoryId);
        Optional<subCategories> subCategoriesOptional = subCategoryRepository.findById(subcategoryId);

        if (categoriesOptional.isPresent() && subCategoriesOptional.isPresent()) {
            Categories categories = categoriesOptional.get();
            subCategories subcategory = subCategoriesOptional.get();

            for (int i = 0; i < productNames.size(); i++) {
                String productName = productNames.get(i);
                BigDecimal prices = (i < price.size()) ? price.get(i) : BigDecimal.ZERO;

                if (productName != null && !productName.trim().isEmpty()) {
                    Product product = new Product();
                    product.setName(productName.trim());
                    product.setCategories(categories);
                    product.setSubCategory(subcategory);
                    product.setPrice(prices);
                    productRepository.save(product);
                }
            }
        }
        return "redirect:/all";
    }


    @GetMapping("/share")
    public String shareProduct(@RequestParam("id") int productId, Model model) {
        Product product = productRepository.findById(productId); // Fetch product by ID
        model.addAttribute("product", product);
        return "Share";
    }


}
