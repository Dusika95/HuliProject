package huli.example.huliwebshop.services;


import huli.example.huliwebshop.models.Category;
import huli.example.huliwebshop.models.User;
import huli.example.huliwebshop.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {
    private ICategoryRepository iCategoryRepository;
    private ProductService productService;

    @Autowired
    public CategoryServiceImpl(ICategoryRepository iCategoryRepository, ProductService productService) {
        this.iCategoryRepository = iCategoryRepository;
        this.productService= productService;
    }

    public List<Category> allCategories() {
        List<Category> allCategories = new ArrayList<>();
        allCategories = iCategoryRepository.findAll();

        return allCategories;

    }

    @Override
    public Category saveNewCategory(Category category) throws Exception {
        if (allCategories().size() != 0) {
            for (int i = 0; i < allCategories().size(); i++) {
                if (allCategories().get(i).getName().equals(category.getName())) {
                    throw new Exception("that category already exist");
                }
            }
        }
        return iCategoryRepository.save(category);
    }

    @Override
    public Category deleteCategoryById(long id) throws Exception {
        Category category = iCategoryRepository.findById(id).get();
        if (!iCategoryRepository.findById(id).isPresent()) {
            throw new Exception("this id not exist");
        } else {
            for (int i = 0; i < productService.getAllProduct().size(); i++) {
                if (productService.getAllProduct().get(i).getCategoryName().equals(category.getName())) {
                    throw new Exception("that category contains product, first need to remove all products from that category");
                }
            }
            iCategoryRepository.deleteById(id);
            return category;
        }
    }
}
