//package mvpproject.crmbaseservice.service;
//
//import mvpproject.crmbaseservice.TestData;
//import mvpproject.crmbaseservice.model.dto.ProductDTO;
//import mvpproject.crmbaseservice.model.entity.ProductEntity;
//import mvpproject.crmbaseservice.model.mapper.ProductConverter;
//import mvpproject.crmbaseservice.repository.ProductRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest extends TestData {
//
//    @Mock
//    private ProductRepository productRepository;
//    @Mock
//    private ProductConverter productConverter;
//    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    void init() {
//        productConverter = new ProductConverter(MODEL_MAPPER);
//        productService = new ProductService(productRepository, productConverter);
//    }
//
//    @Test
//    void whenGetByIdInvokedThenExpectClientIsReturned() {
//        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productOne()));
//        Optional<ProductDTO> productDto = productService.getById(anyLong());
//        Assertions.assertThat(productDto).isNotEmpty();
//        Assertions.assertThat(productDto.get().getProductName()).isEqualTo(productOne().getProductName());
//        verify(productRepository).findById(anyLong());
//    }
//
//
//    @Test
//    void whenGetAllInvokedThenAllTheClientsAreReturned() {
//        when(productRepository.findAll()).thenReturn(List.of(productTwo(), productOne()));
//        productService.getAll().forEach(ProductDTO::getProductName);
//        String[] expected = new String[]{productTwo().getProductName(), productOne().getProductName()
//        };
//        Assertions.assertThat(expected).containsExactlyInAnyOrder(productTwo().getProductName(), productOne().getProductName());
//    }
//
//    @Test
//    void whenCreatedInvokedWithClientThenClientIsSaved() {
//        when(productRepository.findAll()).thenReturn(List.of(productConverter.convertProductEntityFromDto(testProduct())));
//        when(productRepository.save(productConverter.convertProductEntityFromDto(testProduct())))
//                .thenReturn(productConverter.convertProductEntityFromDto(testProduct()));
//
//        productService.create(testProduct());
//        List<String> allUserEmail = productRepository.findAll().stream()
//                .map(ProductEntity::getProductName)
//                .toList();
//        Assertions.assertThat(allUserEmail).contains(testProduct().getProductName());
//    }
//
//    @Test
//    void whenUpdateByIdThenSavedClientUpdate() {
//        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productOne()));
//        when(productRepository.save(productOne())).thenReturn(productOne());
//
//        productService.update(anyLong(), productConverter.convertFromProductEntityToDto(productOne()));
//        Assertions.assertThat(productOne().getProductName()).contains(productOne().getProductName());
//    }
//}
//
