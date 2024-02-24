package com.springboot.companyms.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;


//    // relationship between the job and the company
//    @JsonIgnore
//    @OneToMany(mappedBy = "company")
//    List<Job> jobs;

//    @OneToMany(mappedBy = "company")
//    List<Review> reviews;
}
