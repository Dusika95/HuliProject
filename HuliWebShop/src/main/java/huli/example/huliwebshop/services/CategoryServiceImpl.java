package huli.example.huliwebshop.services;


import huli.example.huliwebshop.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {
    private ICategoryRepository iCategoryRepoRepository;

    @Autowired
    public CategoryServiceImpl(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepoRepository = iCategoryRepository;
    }
}
