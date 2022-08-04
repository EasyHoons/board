package com.ex.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.board.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{
	Optional<SiteUser> findByUsername(String username);
	

}
