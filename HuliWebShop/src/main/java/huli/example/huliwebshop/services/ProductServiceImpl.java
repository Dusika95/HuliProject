package huli.example.huliwebshop.services;

import huli.example.huliwebshop.DTOs.CommentsWithCreatorsDTO;
import huli.example.huliwebshop.DTOs.ProductGetByAloneDTO;
import huli.example.huliwebshop.DTOs.ProductGetToListDTO;
import huli.example.huliwebshop.models.Product;
import huli.example.huliwebshop.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private IProductRepository iProductRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
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
                productGetToListDTOS.add(productGetToListDTO);
            }
            return productGetToListDTOS;
        }
    }

    @Override
    public ProductGetByAloneDTO getByAlone(Long id) throws Exception {
        Product product = iProductRepository.findById(id).get();
        //Optional product=iProductRepository.findById(id)
        //if(!product.isPresent()){
        if (product == null) {
            throw new Exception("that id not found");
        } else {
            ProductGetByAloneDTO productGetByAloneDTO = new ProductGetByAloneDTO();
            productGetByAloneDTO.setName(product.getName());
            productGetByAloneDTO.setCategoryName(product.getCategory().getName());
            productGetByAloneDTO.setPicture(product.getPicture());
            //IDE MÉG AZ USER IS KÉNE HOGY USER IS LÁTHATÓ LEGYENA COMMENTJE MELETT
            // SZVAL VMI COMMENTDTO LIST kéne
            List<CommentsWithCreatorsDTO> comments = new ArrayList<>();
            for (int i = 0; i < product.getComments().size(); i++) {
                CommentsWithCreatorsDTO aComment = new CommentsWithCreatorsDTO();
                aComment.setComment(product.getComments().get(i).getComment());
                aComment.setUserId(product.getComments().get(i).getUser().getId());
                aComment.setUserName(product.getComments().get(i).getUser().getFirstName()+" "+product.getComments().get(i).getUser().getLastName());
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

}
