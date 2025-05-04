package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.request.TaxRequest;
import Group3.CourseApp.dto.response.TaxResponse;
import Group3.CourseApp.entity.Tax;

import java.util.ArrayList;
import java.util.List;

public class TaxMapper {
    public static Tax toTax(TaxRequest taxRequest) {
        return Tax.builder()
                .name(taxRequest.getName())
                .rate(taxRequest.getRate())
                .build();
    }

    public static TaxResponse toTaxResponse(Tax tax) {
        List<String> productIds = new ArrayList<>();
        if(tax.getProducts() != null){
            productIds = tax.getProducts().stream().map(Product -> Product.getId()).toList();
        }

        return TaxResponse.builder()
                .id(tax.getId())
                .name(tax.getName())
                .rate(tax.getRate())
                .productIds(productIds)
                .build();
    }
    public static List<TaxResponse> toTaxResponseList(List<Tax> taxes) {
        List<TaxResponse> taxResponseList = taxes.stream().map(Tax -> toTaxResponse(Tax) ).toList();
        return taxResponseList;
    }
}
