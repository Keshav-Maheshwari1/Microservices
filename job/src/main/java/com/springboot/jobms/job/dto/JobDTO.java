package com.springboot.jobms.job.dto;

import com.springboot.jobms.job.external.Company;
import com.springboot.jobms.job.external.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private int minSalary;
    private int maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
