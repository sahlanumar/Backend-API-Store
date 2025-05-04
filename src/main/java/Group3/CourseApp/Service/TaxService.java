package Group3.CourseApp.Service;

import Group3.CourseApp.dto.request.TaxRequest;
import Group3.CourseApp.dto.response.TaxResponse;
import Group3.CourseApp.entity.Tax;

import java.util.List;

public interface TaxService {
    TaxResponse createTax(TaxRequest taxRequest);
    TaxResponse updateTax(String id, TaxRequest taxRequest);
    void deleteTax(String id);
    List<TaxResponse> findAll();
    TaxResponse findById(String id);
    Tax findTaxById(String id);
}
