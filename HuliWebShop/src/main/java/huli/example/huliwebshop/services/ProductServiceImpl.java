package huli.example.huliwebshop.services;

import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    private IProductRepository iProductRepository;
    @Autowired
    public ProductServiceImpl(IProductRepository iProductRepository){
        this.iProductRepository=iProductRepository;
    }

}
