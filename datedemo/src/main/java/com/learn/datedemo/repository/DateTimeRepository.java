package com.learn.datedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.datedemo.entity.DateTimeWrapper;

public interface DateTimeRepository extends JpaRepository<DateTimeWrapper, Long>{

}
