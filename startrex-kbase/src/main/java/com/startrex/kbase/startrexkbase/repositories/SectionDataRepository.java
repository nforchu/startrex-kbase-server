package com.startrex.kbase.startrexkbase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.SectionData;

@Repository
public interface SectionDataRepository extends JpaRepository<SectionData, Integer>{

}
