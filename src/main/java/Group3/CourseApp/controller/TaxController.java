package Group3.CourseApp.controller;

import Group3.CourseApp.Service.TaxService;
import Group3.CourseApp.constant.ApiEndpoint;
import Group3.CourseApp.dto.request.TaxRequest;
import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.dto.response.TaxResponse;
import Group3.CourseApp.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.TAX)
@RequiredArgsConstructor
public class TaxController {
    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<CommonResponse<TaxResponse>> createTax(@Valid @RequestBody TaxRequest taxRequest) {
        TaxResponse taxResponse = taxService.createTax(taxRequest);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Success", taxResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TaxResponse>> updateTax(
            @PathVariable String id,
            @Valid @RequestBody TaxRequest taxRequest) {
        TaxResponse taxResponse = taxService.updateTax(id, taxRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Tax updated successfully", taxResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTax(@PathVariable String id) {
        taxService.deleteTax(id);
        return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Tax deleted successfully", null);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TaxResponse>>> findAllTaxes() {
        List<TaxResponse> taxes = taxService.findAll();
        return ResponseUtil.buildResponse(HttpStatus.OK, "Tax list retrieved successfully", taxes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TaxResponse>> findTaxById(@PathVariable String id) {
        TaxResponse taxResponse = taxService.findById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Tax retrieved successfully", taxResponse);
    }

}
