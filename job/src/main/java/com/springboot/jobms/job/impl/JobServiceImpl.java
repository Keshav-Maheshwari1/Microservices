package com.springboot.jobms.job.impl;
import com.springboot.jobms.job.Job;
import com.springboot.jobms.job.JobService;
import com.springboot.jobms.job.clients.CompanyClient;
import com.springboot.jobms.job.clients.ReviewClient;
import com.springboot.jobms.job.dto.JobDTO;
import com.springboot.jobms.job.external.Company;
import com.springboot.jobms.job.external.Review;
import com.springboot.jobms.job.mapper.JobMapper;
import com.springboot.jobms.job.repo.JobRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    JobRepository jobRepository;
    CompanyClient companyClient;

    ReviewClient reviewClient;
//    @Autowired
//    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOs = new ArrayList<>();
//        RestTemplate restTemplate = new RestTemplate();
        for (Job job : jobs) {
//            // GetForObject is used when we know that the return type will be a single object
//            Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(),Company.class);
            // exchange is used when we know that the return type will be of Collection
//             ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                     "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
//            });
//             List<Review> reviews = reviewResponse.getBody();
            Company company = companyClient.getCompany(job.getCompanyId());
            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
            JobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job,company,reviews);
            jobDTOs.add(jobDTO);
        }
        return jobDTOs;
    }
    public List<String> companyBreakerFallback(Exception e){
        List<String> companyBreakerFallback = new ArrayList<String>();
        companyBreakerFallback.add("Service is Down");
        return companyBreakerFallback;
    }
    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    @CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    public JobDTO jobById(Long id) {
        Job job =  jobRepository.findById(id).orElse(null);
        if(job != null){
//            Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(),Company.class);
//            ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                    "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
//            });
//            List<Review> reviews = reviewResponse.getBody();
            Company company = companyClient.getCompany(job.getCompanyId());
            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
            return JobMapper.mapToJobWithCompanyDTO(job,company,reviews);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public boolean updateJob(Long id , Job updatedjob) {

        Optional<Job> jobOptional = jobRepository.findById(id);
        System.out.println(jobOptional.isPresent());
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedjob.getTitle());
            job.setDescription(updatedjob.getDescription());
            job.setMinSalary(updatedjob.getMinSalary());
            job.setMaxSalary(updatedjob.getMaxSalary());
            job.setLocation(updatedjob.getLocation());
            jobRepository.save(job);
            return true;
        }

        return false;
    }
}

