package com.example.demo.repo;

import com.example.demo.models.Avto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvtoRepository extends CrudRepository<Avto, Long> {

    List<Avto> findByNameContains(String name);

    List<Avto> findByNameLike(String name);
}