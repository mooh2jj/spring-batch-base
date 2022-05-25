package com.dsg.springbatchbase.batch;

import com.dsg.springbatchbase.domain.Dept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPageJob1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job jpaPageJob() {
        return jobBuilderFactory.get("jpaPageJob")
                .start(jpaPageStep1())
                .build();
    }

    @Bean
    public Step jpaPageStep1() {
        return stepBuilderFactory.get("jpaPageStep1")
                .<Dept, Dept>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .writer(printItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Dept> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Dept>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d from Dept d ORDER BY d.deptNo asc")
                .build();
    }

    @Bean
    public ItemWriter<Dept> printItemWriter() {
        return list -> {
            for (Dept dept : list) {
                log.debug("dept: {}", dept.toString());
            }
        };
    }

}
