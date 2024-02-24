package com.springboot.jobms.job.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private Long Id;
    private String title;
    private String description;
    private Double rating;
}
