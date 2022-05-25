package com.dsg.springbatchbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneDto {

    private String one;

    public String toString() {
        return one;
    }
}
