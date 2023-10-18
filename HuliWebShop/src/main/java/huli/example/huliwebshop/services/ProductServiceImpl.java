package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.*;
import huli.example.huliwebshop.models.Category;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.repository.ICartRepository;
import huli.example.huliwebshop.repository.ICategoryRepository;
import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private IProductRepository iProductRepository;
    private ICategoryRepository iCategoryRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository iProductRepository, ICategoryRepository iCategoryRepository) {
        this.iProductRepository = iProductRepository;
        this.iCategoryRepository = iCategoryRepository;
    }

    @Override
    public List<ProductGetToListDTO> getAllProduct() throws Exception {
        List<Product> allProducts = new ArrayList<>();
        allProducts = iProductRepository.findAll();
        if (allProducts.isEmpty()) {
            throw new Exception("no product yet");
        } else {
            List<ProductGetToListDTO> productGetToListDTOS = new ArrayList<>();
            for (int i = 0; i < allProducts.size(); i++) {
                ProductGetToListDTO productGetToListDTO = new ProductGetToListDTO();
                productGetToListDTO.setId(allProducts.get(i).getId());
                productGetToListDTO.setName(allProducts.get(i).getName());
                productGetToListDTO.setPicture(allProducts.get(i).getPicture());
                productGetToListDTO.setCategoryName(allProducts.get(i).getCategory().getName());
                productGetToListDTO.setPrice(allProducts.get(i).getPrice());
                if (allProducts.get(i).getQuantity() > 0) {
                    productGetToListDTO.setAvailable(true);
                } else {
                    productGetToListDTO.setAvailable(false);
                }
                productGetToListDTOS.add(productGetToListDTO);

            }
            return productGetToListDTOS;
        }
    }

    @Override
    public List<ProductGetToListDTO> getAllProductByCategoryId(Long id) throws Exception {
        List<Product> allProducts = new ArrayList<>();
        allProducts = iProductRepository.findAll();
        Category category = iCategoryRepository.findById(id).get();
        if (allProducts.isEmpty()) {
            throw new Exception("no product yet");
        } else if (!iCategoryRepository.findById(id).isPresent()) {
            throw new Exception("this category does not exist");
        } else {
            List<ProductGetToListDTO> productGetToListDTOS = new ArrayList<>();
            for (int i = 0; i < allProducts.size(); i++) {
                if (Objects.equals(allProducts.get(i).getCategory().getId(), category.getId())) {
                    ProductGetToListDTO productGetToListDTO = new ProductGetToListDTO();
                    productGetToListDTO.setId(allProducts.get(i).getId());
                    productGetToListDTO.setName(allProducts.get(i).getName());
                    productGetToListDTO.setPicture(allProducts.get(i).getPicture());
                    productGetToListDTO.setCategoryName(allProducts.get(i).getCategory().getName());
                    productGetToListDTO.setPrice(allProducts.get(i).getPrice());
                    if (allProducts.get(i).getQuantity() > 0) {
                        productGetToListDTO.setAvailable(true);
                    } else {
                        productGetToListDTO.setAvailable(false);
                    }
                    productGetToListDTOS.add(productGetToListDTO);

                }
            }
            return productGetToListDTOS;
        }
    }

    @Override
    public ProductGetByAloneDTO getByAlone(Long id) throws Exception {
        Product product = iProductRepository.findById(id).get();
        if (!iProductRepository.findById(id).isPresent()) {
            throw new Exception("that id not found");
        } else {
            ProductGetByAloneDTO productGetByAloneDTO = new ProductGetByAloneDTO();
            productGetByAloneDTO.setId(product.getId());
            productGetByAloneDTO.setName(product.getName());
            productGetByAloneDTO.setCategoryName(product.getCategory().getName());
            productGetByAloneDTO.setPicture(product.getPicture());
            productGetByAloneDTO.setPrice(product.getPrice());
            productGetByAloneDTO.setQuantity(product.getQuantity());
            productGetByAloneDTO.setDescription(product.getDescription());

            List<CommentsWithCreatorsDTO> comments = new ArrayList<>();
            for (int i = 0; i < product.getComments().size(); i++) {
                CommentsWithCreatorsDTO aComment = new CommentsWithCreatorsDTO();
                aComment.setComment(product.getComments().get(i).getComment());
                aComment.setUserId(product.getComments().get(i).getUser().getId());
                aComment.setUserName(product.getComments().get(i).getUser().getName());
                comments.add(aComment);
            }
            productGetByAloneDTO.setComment(comments);

            int allRatingNumb = 0;
            for (int i = 0; i < product.getRatings().size(); i++) {
                allRatingNumb += product.getRatings().get(i).getStar();
            }
            double average = (double) allRatingNumb / product.getRatings().size();
            productGetByAloneDTO.setStar(average);
            return productGetByAloneDTO;
        }
    }

    @Override
    public Product createNewProduct(ProductCreateDTO productCreateDTO) throws Exception {
        Product product = new Product();

        Category category = categoryValidator(productCreateDTO.getCategory());

        product.setCategory(category);
        product.setPrice(productCreateDTO.getPrice());
        product.setQuantity(productCreateDTO.getQuantity());
        product.setDescription(productCreateDTO.getDescription());
        product.setName(productCreateDTO.getName());
        product.setPicture(productCreateDTO.getPicture());
        iProductRepository.save(product);
        return product;
    }

    @Override
    public String deleteProductById(Long id) throws Exception {
        Product product = iProductRepository.findById(id).get();

        if (!iProductRepository.findById(id).isPresent()) {
            throw new Exception("that id not exist");
        } else {
            iProductRepository.deleteById(id);
            return product.getName();
        }
    }

    @Override
    public ProductUpdateDTO editProductById(Long id, ProductUpdateDTO productUpdateDTO) throws Exception {
        Product product = iProductRepository.findById(id).get();
        if (!iProductRepository.findById(id).isPresent()) {
            throw new Exception("that id not exist");
        } else {
            product.setName(productUpdateDTO.getName());
            product.setDescription(productUpdateDTO.getDescription());
            product.setPicture(productUpdateDTO.getPicture());
            product.setPrice(productUpdateDTO.getPrice());
            product.setQuantity(productUpdateDTO.getQuantity());

            Category category = categoryValidator(productUpdateDTO.getCategory());
            product.setCategory(category);

            iProductRepository.save(product);
            return productUpdateDTO;
        }
    }

    public Category categoryValidator(String categoryName) throws Exception {
        Category category = iCategoryRepository.findByName(categoryName);
        if (category == null) {
            throw new Exception("the given category not exist");
        }
        return category;
    }

}
