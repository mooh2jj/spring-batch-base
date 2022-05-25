package com.dsg.springbatchbase.batch;

import com.dsg.springbatchbase.custom.CustomPassThroughLineAggregator;
import com.dsg.springbatchbase.dto.OneDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TextJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int chunkSize = 5;

    @Bean
    public Job textJob22() {
        return jobBuilderFactory.get("textJob22")
                .start(textStep2())
                .build();
    }

    @Bean
    public Step textStep2() {
        return stepBuilderFactory.get("textStep2")
                .<OneDto, OneDto>chunk(chunkSize)
                .reader(textJob2Reader())
                .writer(textJob2Writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<OneDto> textJob2Reader() {
        FlatFileItemReader<OneDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("sample/textJob2_input.txt"));
        flatFileItemReader.setLineMapper(((line, lineNumber) -> new OneDto(lineNumber + "_" + line)));
        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemWriter textJob2Writer() {
        return new FlatFileItemWriterBuilder<OneDto>()
                .name("textJob2Writer")
                .resource(new FileSystemResource("sample/textJob2_input.txt"))
                .lineAggregator(new CustomPassThroughLineAggregator<>())
                .build();
    }
}
