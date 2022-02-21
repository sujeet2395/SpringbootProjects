package com.learn.springauthsecurity.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.learn.springauthsecurity.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByRole(String role);
}
