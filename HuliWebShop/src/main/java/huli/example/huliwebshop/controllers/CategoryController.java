package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.models.Category;
import huli.example.huliwebshop.services.CategoryNotFoundException;
import huli.example.huliwebshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.saveNewCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
            //return ResponseEntity.ok().body(categoryService.saveNewCategory(category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            //return ResponseEntity.status(400).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            //return  ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategoryById(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            //return ResponseEntity.status(400).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping
    public ResponseEntity getAllCategory() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.allCategories());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
