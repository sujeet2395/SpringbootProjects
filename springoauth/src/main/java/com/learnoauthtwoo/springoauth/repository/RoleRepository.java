package com.learnoauthtwoo.springoauth.repository;

import org.springframework.data.repository.CrudRepository;

import com.learnoauthtwoo.springoauth.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}