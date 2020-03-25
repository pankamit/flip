package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

	public Optional<Rating> findByRatingIdAndUserId(Long ratingId,String userId);
}
