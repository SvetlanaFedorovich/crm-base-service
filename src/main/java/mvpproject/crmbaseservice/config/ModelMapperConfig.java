package mvpproject.crmbaseservice.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Slf4j
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        return mapper;
    }
}
