package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Opinions;

import java.util.Optional;



public interface OpinionsRepository extends JpaRepository <Opinions, Long>{

}
