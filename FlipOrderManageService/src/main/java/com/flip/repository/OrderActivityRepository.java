package com.flip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flip.entity.OrderActivity;

@Repository
public interface OrderActivityRepository extends JpaRepository<OrderActivity, Long>{

}
