package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.ProductService;
import Group3.CourseApp.Service.TaxService;
import Group3.CourseApp.dto.request.ProductRequest;
import Group3.CourseApp.dto.response.ProductRespose;
import Group3.CourseApp.entity.Product;
import Group3.CourseApp.entity.Tax;
import Group3.CourseApp.exception.CustomException;
import Group3.CourseApp.exception.ErrorCode;
import Group3.CourseApp.mapper.ProductMapper;
import Group3.CourseApp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TaxService taxService;


    @Override
    public ProductRespose addProduct(ProductRequest productRequest) {
        Product product = ProductMapper.toProduct(productRequest);
        Set<Tax> taxes =  productRequest.getTaxesId().stream()
                .map(taxService::findTaxById)
                .collect(Collectors.toSet());
        product.setTaxes(taxes);
        productRepository.save(product);
        return ProductMapper.toProductRespose(product);
    }

    @Override
    public ProductRespose updateProductById(String id, ProductRequest productRequest) {
        Product product = findProductById(id);
        if(productRequest.getName() != null){
            product.setName(productRequest.getName());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        productRepository.save(product);
        return ProductMapper.toProductRespose(product);
    }

    @Override
    public void deleteProductById(String id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public List<ProductRespose> findAllProduct() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.toProductResposeList(products);
    }

    @Override
    public ProductRespose findById(String id) {
        Product product = findProductById(id);
        return ProductMapper.toProductRespose(product);
    }

    @Override
    public ProductRespose addTaxToProduct(String productId, String taxId) {
        Product product = findProductById(productId);
        Tax tax = taxService.findTaxById(taxId);
        product.getTaxes().add(tax);
        productRepository.save(product);
        return ProductMapper.toProductRespose(product);
    }

    @Override
    public ProductRespose removeTaxFromProduct(String productId, String taxId) {
        Product product = findProductById(productId);
        Tax tax = taxService.findTaxById(taxId);
        product.getTaxes().remove(tax);
        productRepository.save(product);
        return ProductMapper.toProductRespose(product);
    }

    @Override
    public Product findProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return product;
    }
}
