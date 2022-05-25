package com.dsg.springbatchbase.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeptRepositoryTest {

    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void given_when_then(){
        // given - precondition or setup
        LongStream.rangeClosed(1,101).forEach(i -> {
            Dept dept = Dept.builder()
                    .deptNo(i)
                    .dName("deptName_"+i)
                    .loc("loc_"+i)
                    .build();

            deptRepository.save(dept);
        });
        // when - action or the behaviour that we are going test
        List<Dept> all = deptRepository.findAll();
        log.info("all: {}", all);
        // then - verify the output
        assertThat(all.size()).isEqualTo(101);

    }

}