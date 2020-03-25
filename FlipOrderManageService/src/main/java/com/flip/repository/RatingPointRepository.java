package com.flip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.RatingPoint;

@Repository
public interface RatingPointRepository extends JpaRepository<RatingPoint, Long>{

	Optional<RatingPoint> findByRatingLvlCd(String ratingLevelCd);
	
}
