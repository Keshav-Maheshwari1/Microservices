package com.springboot.jobms.job;

import com.springboot.jobms.job.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully",HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> jobById(@PathVariable Long id){
        JobDTO jobDTO = jobService.jobById(id);
        return jobDTO != null ? new ResponseEntity<>(jobDTO, HttpStatus.OK): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        JobDTO jobDTO = jobService.jobById(id);
        if(jobDTO != null) {
            jobService.deleteById(id);
            return ResponseEntity.ok("Successfully deleted");
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedjob){

        boolean isUpdated = jobService.updateJob(id,updatedjob);

        if(isUpdated){
            return ResponseEntity.ok("Successfully Updated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
