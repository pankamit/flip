package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.MenFootWearInfo;

@Repository
public interface MenFootWearInfoRepository extends JpaRepository<MenFootWearInfo, Long>{
	
	public MenFootWearInfo getById(Long id);

}
