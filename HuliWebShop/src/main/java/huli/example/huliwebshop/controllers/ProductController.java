package huli.example.huliwebshop.controllers;

import huli.example.huliwebshop.DTOs.ProductCreateDTO;
import huli.example.huliwebshop.DTOs.ProductUpdateDTO;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.services.ProductService;
import huli.example.huliwebshop.services.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
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
    public ResponseEntity getAllProductByCategory(@PathVariable Long id){
        try{
            return ResponseEntity.status(200).body(productService.getAllProduct());
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProduct(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(productService.getByAlone(id));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity createProduct(@RequestBody ProductCreateDTO productCreateDTO){
        try {
            return ResponseEntity.ok().body(productService.createNewProduct(productCreateDTO));
        } catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProductById(id));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("admin/{id}")
    public ResponseEntity editProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productService.editProductById(id,productUpdateDTO));
        } catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }
}

