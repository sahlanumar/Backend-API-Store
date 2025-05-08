package Group3.CourseApp.Service.ServiceImpl;


import Group3.CourseApp.Service.ServiceImpl.TaxServiceImpl;
import Group3.CourseApp.dto.request.TaxRequest;
import Group3.CourseApp.dto.response.TaxResponse;
import Group3.CourseApp.entity.Tax;
import Group3.CourseApp.exception.CustomException;
import Group3.CourseApp.exception.ErrorCode;
import Group3.CourseApp.mapper.TaxMapper;
import Group3.CourseApp.repository.TaxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxServiceImplTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private TaxServiceImpl taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTax_success() {
        //Arrange
        TaxRequest request = new TaxRequest("VAT", 10);
        Tax tax = new Tax();
        tax.setId("1");
        tax.setName("VAT");
        tax.setRate(10);

        when(taxRepository.existsByName("VAT")).thenReturn(false);
        when(taxRepository.save(any(Tax.class))).thenReturn(tax);

        //Act

        TaxResponse response = taxService.createTax(request);

        //Assert

        assertEquals("VAT", response.getName());
        assertEquals(10, response.getRate());
    }

    @Test
    void createTax_invalidData_throwsException() {
        //Arrange
        TaxRequest request = new TaxRequest("", -1);
        //Act
        CustomException exception = assertThrows(CustomException.class, () -> taxService.createTax(request));
        //Assert
        assertEquals(ErrorCode.INVALID_TAX_DATA, exception.getErrorCode());
    }

    @Test
    void createTax_alreadyExists_throwsException() {
        TaxRequest request = new TaxRequest("VAT", 10);
        when(taxRepository.existsByName("VAT")).thenReturn(true);
        CustomException exception = assertThrows(CustomException.class, () -> taxService.createTax(request));
        assertEquals(ErrorCode.TAX_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    void updateTax_success() {
        Tax tax = new Tax();
        tax.setId("1");
        tax.setName("OldName");
        tax.setRate(5);

        TaxRequest request = new TaxRequest("NewName", 15);

        when(taxRepository.findById("1")).thenReturn(Optional.of(tax));
        when(taxRepository.save(any(Tax.class))).thenReturn(tax);

        TaxResponse response = taxService.updateTax("1", request);

        assertEquals("NewName", response.getName());
        assertEquals(15, response.getRate());
    }

    @Test
    void updateTax_notFound_throwsException() {
        TaxRequest request = new TaxRequest("NewName", 15);
        when(taxRepository.findById("1")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taxService.updateTax("1", request));
        assertEquals(ErrorCode.TAX_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void deleteTax_success() {
        Tax tax = new Tax();
        tax.setId("1");

        when(taxRepository.findById("1")).thenReturn(Optional.of(tax));
        doNothing().when(taxRepository).delete(tax);

        assertDoesNotThrow(() -> taxService.deleteTax("1"));
    }

    @Test
    void deleteTax_notFound_throwsException() {
        when(taxRepository.findById("1")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taxService.deleteTax("1"));
        assertEquals(ErrorCode.TAX_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void findById_success() {
        Tax tax = new Tax();
        tax.setId("1");
        tax.setName("VAT");
        tax.setRate(10);

        when(taxRepository.findById("1")).thenReturn(Optional.of(tax));

        TaxResponse response = taxService.findById("1");

        assertEquals("VAT", response.getName());
        assertEquals(10, response.getRate());
    }

    @Test
    void findAll_success() {
        Tax tax1 = new Tax();
        tax1.setId("1");
        tax1.setName("A");
        tax1.setRate(5);

        Tax tax2 = new Tax();
        tax2.setId("2");
        tax2.setName("B");
        tax2.setRate(10);

        List<Tax> taxes = Arrays.asList(tax1, tax2);
        when(taxRepository.findAll()).thenReturn(taxes);

        List<TaxResponse> responseList = taxService.findAll();

        assertEquals(2, responseList.size());
    }
}

