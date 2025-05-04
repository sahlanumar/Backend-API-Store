package Group3.CourseApp.controller;

import Group3.CourseApp.Service.ProductService;
import Group3.CourseApp.constant.ApiEndpoint;
import Group3.CourseApp.dto.request.ProductRequest;
import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.dto.response.ProductRespose;
import Group3.CourseApp.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductRespose>> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductRespose response = productService.addProduct(productRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Product created successfully", response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductRespose>> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest productRequest) {
        ProductRespose response = productService.updateProductById(id, productRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Product updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Product deleted successfully", null);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductRespose>>> getAllProducts() {
        List<ProductRespose> responseList = productService.findAllProduct();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Product list retrieved successfully", responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductRespose>> getProductById(@PathVariable String id) {
        ProductRespose response = productService.findById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Product retrieved successfully", response);
    }

    @PostMapping("/{productId}/tax/{taxId}")
    public ResponseEntity<CommonResponse<ProductRespose>> addTaxToProduct(
            @PathVariable String productId,
            @PathVariable String taxId) {
        ProductRespose response = productService.addTaxToProduct(productId, taxId);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Tax added to product successfully", response);
    }

    @DeleteMapping("/{productId}/tax/{taxId}")
    public ResponseEntity<CommonResponse<ProductRespose>> removeTaxFromProduct(
            @PathVariable String productId,
            @PathVariable String taxId) {
        ProductRespose response = productService.removeTaxFromProduct(productId, taxId);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Tax removed from product successfully", response);
    }
}
