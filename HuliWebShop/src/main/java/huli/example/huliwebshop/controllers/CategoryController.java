package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.models.Category;
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
    @PostMapping("/admin/create")
    public ResponseEntity createCategory(@RequestBody Category category){
        try {
            return ResponseEntity.ok().body(categoryService.saveNewCategory(category));
        } catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/all-categories")
    public ResponseEntity getAllCategory() {
        try {
            return ResponseEntity.status(200).body(categoryService.allCategories());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}


