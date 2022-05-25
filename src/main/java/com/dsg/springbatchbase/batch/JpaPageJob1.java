package com.dsg.springbatchbase.batch;

import com.dsg.springbatchbase.domain.Dept;
import com.dsg.springbatchbase.domain.Dept2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
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
                .<Dept, Dept2>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .processor(itemProcessor())
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
    public ItemProcessor<Dept, Dept2> itemProcessor() {
        return dept -> {
            return Dept2.builder()
                    .deptNo(dept.getDeptNo())
                    .dName("New_" + dept.getDName())
                    .loc("New_" + dept.getLoc())
                    .build();
        };
    }

    @Bean
    public JpaItemWriter<Dept2> printItemWriter() {
        JpaItemWriter<Dept2> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
