package com.springboot.jobms.job;

import com.springboot.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO jobById(Long id);

    void deleteById(Long id);

    boolean updateJob(Long id,Job updatedjob);
}
