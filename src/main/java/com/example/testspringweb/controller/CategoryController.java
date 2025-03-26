package com.example.testspringweb.controller;

import com.example.testspringweb.models.Category;
import com.example.testspringweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/updateCategory")
    public ResponseEntity<Object> updateCategory(@RequestBody Category category, @RequestParam Long idCategory) {
        categoryRepository.findById(idCategory);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("deleteCategory")
    public ResponseEntity<Object> deleteCategory(@RequestParam Long idCategory) {
        categoryRepository.deleteById(idCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
