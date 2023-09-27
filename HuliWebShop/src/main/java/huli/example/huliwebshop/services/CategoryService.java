package huli.example.huliwebshop.services;

import huli.example.huliwebshop.models.Category;

import java.util.List;

public interface CategoryService {
    Category saveNewCategory(Category category) throws Exception;
    Category deleteCategoryById(long id) throws Exception;
    List<Category> allCategories();
}
