package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.request.ProductRequest;
import Group3.CourseApp.dto.response.ProductRespose;
import Group3.CourseApp.entity.Product;
import Group3.CourseApp.entity.Tax;

import java.util.List;
import java.util.Set;

public class ProductMapper {
    public static ProductRespose toProductRespose(Product product) {
        return ProductRespose.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImgUrl())
                .taxRate(product.getTaxes().stream().mapToInt(Tax -> Tax.getRate()).sum())
                .build();
    }
    public static Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
    }
    public static List<ProductRespose> toProductResposeList(List<Product> products) {
        List<ProductRespose> productResposeList = products.stream().map(Product -> toProductRespose(Product) ).toList();
        return productResposeList;
    }
}
