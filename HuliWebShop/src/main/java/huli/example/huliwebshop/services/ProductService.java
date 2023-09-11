package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.ProductCreateDTO;
import huli.example.huliwebshop.DTOs.ProductGetByAloneDTO;
import huli.example.huliwebshop.DTOs.ProductGetToListDTO;
import huli.example.huliwebshop.models.Product;

import java.util.List;

public interface ProductService {
    public List<ProductGetToListDTO> getAllProduct() throws Exception;
    public ProductGetByAloneDTO getByAlone(Long id) throws Exception;
    public Product createNewProduct(ProductCreateDTO productCreateDTO) throws Exception;
}
