package com.example.demo.controller;


import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.SubCategory;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Product> products = productRepository.findAll(); // Fetch all products
        model.addAttribute("products", products);
        List<Category> categories = categoryRepository.findAll(); // Fetch all products
        model.addAttribute("categories", categories);

        return "Component";  // Always return index.html
    }


    @PostMapping("/save-product")
    public String saveProduct(@RequestParam("product[]") List<String> productNames) {
        for (String name : productNames) {
            if (name != null && !name.trim().isEmpty()) {
                Product product = new Product();
                product.setName(name.trim());
                productRepository.save(product);
            }
        }
        return "redirect:/all";
    }

    @PostMapping("/save-category")
    public String saveCategory(@RequestParam("productId") int productId,
                               @RequestParam("categoryName[]") List<String> categoryNames) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            for (String categoryName : categoryNames) {
                if (categoryName != null && !categoryName.trim().isEmpty()) {
                    Category category = new Category();
                    category.setName(categoryName.trim());
                    category.setProduct(product);
                    categoryRepository.save(category);
                }
            }
        }
        return "redirect:/all";
    }
    @PostMapping("/save-subcategory")
    public String saveSubCategory(@RequestParam("productId") int productId,
                                  @RequestParam("categoryId") int categoryId,
                                  @RequestParam("subCategoryName[]") List<String> subCategoryNames) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

        if (productOpt.isPresent() && categoryOpt.isPresent()) {
            Product product = productOpt.get();
            Category category = categoryOpt.get();
            for (String subCategoryName : subCategoryNames) {
                if (subCategoryName != null && !subCategoryName.trim().isEmpty()) {
                    SubCategory subCategory = new SubCategory();
                    subCategory.setName(subCategoryName.trim());
                    subCategory.setProduct(product);
                    subCategory.setCategory(category);
                    subCategoryRepository.save(subCategory);
                }
            }
        }
        return "redirect:/all";
    }
}
