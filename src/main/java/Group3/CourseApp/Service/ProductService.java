package Group3.CourseApp.Service;

import Group3.CourseApp.dto.request.ProductRequest;
import Group3.CourseApp.dto.response.ProductRespose;
import Group3.CourseApp.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductRespose addProduct(ProductRequest productRequest, MultipartFile file);
    ProductRespose updateProductById(String id,ProductRequest productRequest, MultipartFile file);
    void deleteProductById(String id);
    List<ProductRespose> findAllProduct();
    ProductRespose findById(String id);
    ProductRespose addTaxToProduct(String productId,String taxId);
    ProductRespose removeTaxFromProduct(String productId,String taxId);
    Product findProductById(String id);
}
