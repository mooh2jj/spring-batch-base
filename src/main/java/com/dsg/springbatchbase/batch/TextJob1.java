package com.dsg.springbatchbase.batch;

import com.dsg.springbatchbase.dto.OneDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TextJob1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int chunkSize = 5;

    @Bean
    public Job textJob11() {
        return jobBuilderFactory.get("textJob11")
                .start(textStep1())
                .build();
    }

    @Bean
    public Step textStep1() {
        return stepBuilderFactory.get("textStep1")
                .<OneDto, OneDto>chunk(chunkSize)
                .reader(textJob1Reader())
                .writer(oneDto -> oneDto.stream().forEach(i -> {
                    log.info(i.toString());
                })).build();
    }

    @Bean
    public FlatFileItemReader<OneDto> textJob1Reader() {
        FlatFileItemReader<OneDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("sample/textJob1_input.txt"));
        flatFileItemReader.setLineMapper(((line, lineNumber) -> new OneDto(lineNumber + "_" + line)));
        return flatFileItemReader;
    }
}
