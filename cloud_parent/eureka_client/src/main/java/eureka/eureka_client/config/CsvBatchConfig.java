package eureka.eureka_client.config;

import eureka.eureka_client.entity.DomainData;
import eureka.eureka_client.validator.CsvItemProcessor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by tangminyan on 2019/7/12.
 */
@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

    @Bean
    public ItemReader<DomainData> reader() throws Exception{
        FlatFileItemReader<DomainData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data.csv"));
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",") {
            @Override
            public void setNames(String[] names) {
                super.setNames(new String[]{"enterName", "domain", "type"});
            }
        };
        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper() {
            @Override
            public void setTargetType(Class type) {
                super.setTargetType(DomainData.class);
            }
        };
        reader.setLineMapper(new DefaultLineMapper<DomainData>() {
            @Override
            public void setLineTokenizer(LineTokenizer tokenizer) {
                super.setLineTokenizer(delimitedLineTokenizer);
            }

            @Override
            public void setFieldSetMapper(FieldSetMapper<DomainData> fieldSetMapper) {
                super.setFieldSetMapper(beanWrapperFieldSetMapper);
            }
        });
        return reader;
    }

    /*@Bean
    public ItemProcessor<DomainData, DomainData> processor() {
        CsvItemProcessor processor = new CsvItemProcessor();
        processor.setValidator();
    }*/
}















