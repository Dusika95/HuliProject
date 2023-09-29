package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.ProductCreateDTO;
import huli.example.huliwebshop.DTOs.ProductGetByAloneDTO;
import huli.example.huliwebshop.DTOs.ProductGetToListDTO;
import huli.example.huliwebshop.DTOs.ProductUpdateDTO;
import huli.example.huliwebshop.models.Product;

import java.util.List;

public interface ProductService {
    List<ProductGetToListDTO> getAllProduct() throws Exception;

    ProductGetByAloneDTO getByAlone(Long id) throws Exception;

    Product createNewProduct(ProductCreateDTO productCreateDTO) throws Exception;

    Product deleteProductById(Long id) throws Exception;

    Product editProductById(Long id, ProductUpdateDTO productUpdateDTO) throws Exception;
    List<ProductGetToListDTO> getAllProductById(Long id) throws Exception;
}
