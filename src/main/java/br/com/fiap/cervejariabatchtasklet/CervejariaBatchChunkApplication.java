package br.com.fiap.cervejariabatchtasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableBatchProcessing
public class CervejariaBatchChunkApplication {

    Logger logger = LoggerFactory.getLogger(CervejariaBatchChunkApplication.class);

    @Bean
    public FlatFileItemReader<Pessoa> itemReader(@Value("file.input") Resource resource) {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .delimited().delimiter(";").names("nome", "cpf")
                .resource(resource)
                .targetType(Pessoa.class)
                .name("File Item Reader")
                .build();
    }

    @Bean
    public ItemProcessor<Pessoa, Pessoa> itemProcessor() {
        return pessoa -> {
            pessoa.setNome(pessoa.getNome().toUpperCase());
            pessoa.setCpf(pessoa.getCpf()
                    .replaceAll("\\.", "")
                    .replace("-", ""));
            return pessoa;
        };
    }

    @Bean
    public ItemWriter<Pessoa> itemWriter(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .beanMapped()
                .dataSource(dataSource)
                .sql("insert into tb_pessoa (nome, cpf) values (:nome, :cpf)")
                .build();
    }



    public static void main(String[] args) {
        SpringApplication.run(CervejariaBatchChunkApplication.class, args);
    }

}
