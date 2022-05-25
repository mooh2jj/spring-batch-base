package com.dsg.springbatchbase.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dept2 {

    @Id
    private Long deptNo;
    private String dName;
    private String loc;

    @Builder
    public Dept2(Long deptNo, String dName, String loc) {
        this.deptNo = deptNo;
        this.dName = dName;
        this.loc = loc;
    }
}
