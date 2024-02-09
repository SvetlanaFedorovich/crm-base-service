package mvpproject.crmbaseservice;

import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.dto.ProductDTO;
import mvpproject.crmbaseservice.model.dto.SalesDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.entity.ProductEntity;
import mvpproject.crmbaseservice.model.entity.SalesEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestData {

    public static ClientDTO testClient() {
        return ClientDTO.builder()
                .id(1L)
                .clientName("Test")
                .address("CityTest")
                .payerAccountNumber("0")
                .bankDetails("BankTest")
                .build();
    }

    public static ClientEntity ivanClient() {
        return ClientEntity.builder()
                .clientId(2L)
                .clientName("Ivan")
                .address("Gomel")
                .payerAccountNumber("1")
                .bankDetails("Bank")
                .build();
    }

    public static ClientEntity kiraClient() {
        return ClientEntity.builder()
                .clientId(3L)
                .clientName("Kira")
                .address("Minsk")
                .payerAccountNumber("2")
                .bankDetails("CleverBank")
                .build();
    }

    public static ProductDTO testProduct() {
        return ProductDTO.builder()
                .id(1L)
                .productName("Test")
                .price(BigDecimal.valueOf(1))
                .quantity(2)
                .unit("pieces")
                .build();
    }

    public static ProductEntity productOne() {
        return ProductEntity.builder()
                .productId(2L)
                .productName("TestProductOne")
                .price(BigDecimal.valueOf(5))
                .quantity(10)
                .unit("pieces")
                .build();
    }

    public static ProductEntity productTwo() {
        return ProductEntity.builder()
                .productId(3L)
                .productName("TestProductTwo")
                .price(BigDecimal.valueOf(4))
                .quantity(8)
                .unit("pieces")
                .build();
    }


    public static SalesDTO salesDto() {
        return SalesDTO.builder()
                .id(1L)
                .clientId(1L)
                .productId(1L)
                .quantity(10)
                .salesDate(LocalDate.now())
                .build();
    }

    public static SalesDTO salesDto2() {
        return SalesDTO.builder()
                .clientId(2L)
                .productId(2L)
                .quantity(20)
                .salesDate(LocalDate.now())
                .build();
    }

    public static SalesEntity buildTestSalesEntity(Long id, int quantity, int daysAgo) {
        return SalesEntity.builder()
                .id(id)
                .quantity(quantity)
                .salesDate(LocalDate.now().minusDays(daysAgo))
                .build();
    }

    public static SalesDTO buildTestSalesDTO(Long id, int quantity, int daysAgo) {
        return SalesDTO.builder()
                .id(id)
                .quantity(quantity)
                .salesDate(LocalDate.now().minusDays(daysAgo))
                .build();
    }
}
