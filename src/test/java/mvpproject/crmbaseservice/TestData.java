package mvpproject.crmbaseservice;

import mvpproject.crmbaseservice.model.dto.ClientDTO;
import mvpproject.crmbaseservice.model.dto.ProductDTO;
import mvpproject.crmbaseservice.model.entity.ClientEntity;
import mvpproject.crmbaseservice.model.entity.ProductEntity;

import java.math.BigDecimal;

public class TestData {

    public static ClientDTO testClient() {
        return ClientDTO.builder()
                .clientName("Test")
                .address("CityTest")
                .payerAccountNumber("0")
                .bankDetails("BankTest")
                .build();
    }

    public static ClientDTO clientFromRequestWithEmptyFields() {
        return ClientDTO.builder()
                .clientName("")
                .address("Gomel")
                .payerAccountNumber("1")
                .bankDetails("Bank")
                .build();
    }


    public static ClientEntity ivanClient() {
        return ClientEntity.builder()
                .clientName("Ivan")
                .address("Gomel")
                .payerAccountNumber("1")
                .bankDetails("Bank")
                .build();
    }

    public static ClientEntity kiraClient() {
        return ClientEntity.builder()
                .clientName("Kira")
                .address("Minsk")
                .payerAccountNumber("2")
                .bankDetails("CleverBank")
                .build();
    }

    public static ProductDTO testProduct() {
        return ProductDTO.builder()
                .productName("Test")
                .price(BigDecimal.valueOf(1))
                .quantity(2)
                .unit("pieces")
                .build();
    }

    public static ProductEntity productOne() {
        return ProductEntity.builder()
                .productName("TestProductOne")
                .price(BigDecimal.valueOf(5))
                .quantity(10)
                .unit("pieces")
                .build();
    }

    public static ProductEntity productTwo() {
        return ProductEntity.builder()
                .productName("TestProductTwo")
                .price(BigDecimal.valueOf(4))
                .quantity(8)
                .unit("pieces")
                .build();
    }
}
