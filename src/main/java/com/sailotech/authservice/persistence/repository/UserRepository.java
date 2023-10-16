package com.sailotech.authservice.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.sailotech.authservice.persistence.entity.User;

public interface UserRepository extends ListCrudRepository<User, Integer> {

	Optional<User> findUserByUserName(String userName);
}
