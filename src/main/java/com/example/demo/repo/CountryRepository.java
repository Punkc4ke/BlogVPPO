package com.example.demo.repo;

import com.example.demo.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {

        Country findByName(String name);
}