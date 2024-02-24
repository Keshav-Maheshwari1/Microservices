package com.springboot.jobms.job;


import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(name = "job_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int minSalary;
    private int maxSalary;
    private String location;
    private Long companyId;

//    @JsonIgnore

}
