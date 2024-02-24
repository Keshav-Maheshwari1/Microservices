package com.springboot.reviewms.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review){
        Boolean present = reviewService.addReview(companyId,review);
        if(present){
            return new ResponseEntity<>("Review added Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        Review review = reviewService.getReviewById(reviewId);
        if(review != null){
            return new ResponseEntity<>(review,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview){
        Boolean updated = reviewService.updateReview(reviewId,updatedReview);
        if(updated){
            return new ResponseEntity<>("Review Updated Successfully!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Updated",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId){
        Boolean deleted = reviewService.deleteReviewById(reviewId);
        if(deleted){
            return new ResponseEntity<>("Review Deleted Successfully!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review Not Deleted",HttpStatus.BAD_REQUEST);
    }



}
