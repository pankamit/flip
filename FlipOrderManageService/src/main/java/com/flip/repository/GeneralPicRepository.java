package com.flip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.GeneralPic;
import com.flip.entity.OrderInfo;

@Repository
public interface GeneralPicRepository extends JpaRepository<GeneralPic, Long>{

}
