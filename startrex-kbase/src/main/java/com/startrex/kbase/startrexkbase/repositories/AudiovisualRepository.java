package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.Audiovisual;

@Repository
public interface AudiovisualRepository extends JpaRepository<Audiovisual, Integer>{
	Optional<Audiovisual> findOneById(int id);
	Page<Audiovisual> findAll(Pageable pageable);
	@Query("SELECT au.id, au.title, au.link, au.code FROM Audiovisual au WHERE au.deleted = 'NO' ORDER BY weight")
	Page<Audiovisual> getAudiovisualList(Pageable pageable); 
	Optional<Audiovisual> findTop1ByTopicIdOrderByWeightAsc(int topicId);
}
