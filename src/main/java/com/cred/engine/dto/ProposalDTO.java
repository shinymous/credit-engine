package com.cred.engine.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProposalDTO {

    private String name;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String uf;
    private Integer dependents;
    private BigDecimal income;
    private Boolean approved;
    private String limit;
}
