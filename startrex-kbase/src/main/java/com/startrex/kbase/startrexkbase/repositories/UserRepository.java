package com.startrex.kbase.startrexkbase.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.startrex.kbase.startrexkbase.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findOneById(long id);
	User findByUsername(String username);
	Optional<User> findOneByUsernameAndPassword(String username, String password);
}
