package Group3.CourseApp.Service.ServiceImpl;

import Group3.CourseApp.Service.TaxService;
import Group3.CourseApp.dto.request.TaxRequest;
import Group3.CourseApp.dto.response.TaxResponse;
import Group3.CourseApp.entity.Tax;
import Group3.CourseApp.exception.CustomException;
import Group3.CourseApp.exception.ErrorCode;
import Group3.CourseApp.mapper.TaxMapper;
import Group3.CourseApp.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Override
    public TaxResponse createTax(TaxRequest taxRequest) {
        if(taxRequest.getName() == null||taxRequest.getRate() == null||taxRequest.getRate() < 0||taxRequest.getRate() > 100||taxRequest.getName().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_TAX_DATA);
        }
        if(taxRepository.existsByName(taxRequest.getName())) {
            throw new CustomException(ErrorCode.TAX_ALREADY_EXISTS);
        }
        Tax tax = TaxMapper.toTax(taxRequest);
        taxRepository.save(tax);
        return TaxMapper.toTaxResponse(tax);
    }

    @Override
    @Transactional
    public TaxResponse updateTax(String id, TaxRequest taxRequest) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TAX_NOT_FOUND));
        if (taxRequest.getName() != null) {
            tax.setName(taxRequest.getName());
        }

        if (taxRequest.getRate() != null) {
            tax.setRate(taxRequest.getRate());
        }
        taxRepository.save(tax);
        return TaxMapper.toTaxResponse(tax);
    }

    @Override
    public void deleteTax(String id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TAX_NOT_FOUND));
        taxRepository.delete(tax);
    }

    @Override
    public List<TaxResponse> findAll() {
        List<Tax> taxes = taxRepository.findAll();
        return TaxMapper.toTaxResponseList(taxes);
    }

    @Override
    public TaxResponse findById(String id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TAX_NOT_FOUND));
        return TaxMapper.toTaxResponse(tax);
    }

    public  Tax findTaxById(String id) {
        Tax tax = taxRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TAX_NOT_FOUND));
        return tax;
    }
}
