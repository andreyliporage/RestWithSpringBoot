package com.estudo.springrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.springrest.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {}