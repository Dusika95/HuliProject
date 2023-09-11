package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.services.ProductService;
import huli.example.huliwebshop.services.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {
    ProductService productService;
    public ProductController(ProductService productService){
        this.productService= productService;
    }
    @GetMapping("/all-product")
    public ResponseEntity getAllProduct(){
        try{
            return ResponseEntity.status(200).body(productService.getAllProduct());
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/all-product/{id}")
    public ResponseEntity editBuilding(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(productService.getByAlone(id));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}

