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

    public static ProductEntity productThree() {
        return ProductEntity.builder()
                .productId(4L)
                .productName("TestProductThree")
                .price(BigDecimal.valueOf(3))
                .quantity(3)
                .unit("pieces")
                .build();
    }

    //Тестовые продажи///////////////////////////////////////////////////////////
    public static SalesDTO salesDto() {
        return SalesDTO.builder()
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

    public static SalesEntity salesOne() {
        return SalesEntity.builder()
                .client(ivanClient())
                .product(productOne())
                .quantity(1)
                .salesDate(LocalDate.now().minusDays(3))
                .build();
    }

    public static SalesEntity salesTwo() {
        return SalesEntity.builder()
                .client(kiraClient())
                .product(productTwo())
                .quantity(2)
                .salesDate(LocalDate.now().minusDays(15))
                .build();
    }

    public static SalesEntity salesThree() {
        return SalesEntity.builder()
                .client(kiraClient())
                .product(productThree())
                .quantity(3)
                .salesDate(LocalDate.now().minusDays(60))
                .build();
    }

    public static SalesEntity salesToday() {
        return SalesEntity.builder()
                .client(kiraClient())
                .product(productThree())
                .quantity(3)
                .salesDate(LocalDate.now())
                .build();
    }

    ///////////////////////////////////////////////////


    public static ClientEntity clientEntity() {
        return ClientEntity.builder()
                .clientId(1L)
                .clientName("TestClient")
                .address("TestAddress")
                .payerAccountNumber("TestPayerAccountNumber")
                .bankDetails("TestBankDetails")
                .build();
    }

    public static ProductEntity productEntity() {
        return ProductEntity.builder()
                .productId(1L)
                .productName("TestProduct")
                .price(BigDecimal.valueOf(100))
                .quantity(20)
                .unit("TestUnit")
                .build();
    }

    public static SalesEntity salesEntity() {
        return SalesEntity.builder()
                .client(clientEntity())
                .product(productEntity())
                .quantity(salesDto().getQuantity())
                .salesDate(salesDto().getSalesDate())
                .build();
    }

    /////для Update/////
    public static ClientEntity createClientEntity() {
        ClientEntity client = new ClientEntity();
        client.setAddress("42 Main St");
        client.setBankDetails("Bank Details");
        client.setClientId(1L);
        client.setClientName("Dr Jane Doe");
        client.setPayerAccountNumber("42");
        return client;
    }

    public static ProductEntity createProductEntity() {
        ProductEntity product = new ProductEntity();
        product.setPrice(new BigDecimal("2.3"));
        product.setProductId(1L);
        product.setProductName("Product Name");
        product.setQuantity(1);
        product.setUnit("Unit");
        return product;
    }

    public static SalesEntity createSalesEntity(ClientEntity client, ProductEntity product) {
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setClient(client);
        salesEntity.setId(1L);
        salesEntity.setProduct(product);
        salesEntity.setQuantity(1);
        salesEntity.setSalesDate(LocalDate.of(1970, 1, 1));
        return salesEntity;
    }

    public static SalesDTO createSalesDTO() {
        SalesDTO.SalesDTOBuilder quantityResult = SalesDTO.builder().clientId(1L).id(1L).productId(1L).quantity(1);
        return quantityResult.salesDate(LocalDate.of(1970, 1, 1)).build();
    }

    public static SalesEntity createSalesEntity1() {
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setId(1L);
        salesEntity.setQuantity(1);
        salesEntity.setSalesDate(LocalDate.now().minusDays(1));
        return salesEntity;
    }

    public static SalesEntity createSalesEntity2() {
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setId(2L);
        salesEntity.setQuantity(2);
        salesEntity.setSalesDate(LocalDate.now().minusDays(15));
        return salesEntity;
    }

    public static SalesEntity createSalesEntity3() {
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setId(3L);
        salesEntity.setQuantity(3);
        salesEntity.setSalesDate(LocalDate.now().minusDays(100));
        return salesEntity;
    }

    public static SalesDTO createSalesDto1() {
        SalesDTO salesDto = new SalesDTO();
        salesDto.setId(1L);
        salesDto.setQuantity(1);
        salesDto.setSalesDate(LocalDate.now().minusDays(1));
        return salesDto;
    }

    public static SalesDTO createSalesDto2() {
        SalesDTO salesDto = new SalesDTO();
        salesDto.setId(2L);
        salesDto.setQuantity(2);
        salesDto.setSalesDate(LocalDate.now().minusDays(15));
        return salesDto;
    }

    public static SalesDTO createSalesDto3() {
        SalesDTO salesDto = new SalesDTO();
        salesDto.setId(3L);
        salesDto.setQuantity(3);
        salesDto.setSalesDate(LocalDate.now().minusDays(100));
        return salesDto;
    }
}

