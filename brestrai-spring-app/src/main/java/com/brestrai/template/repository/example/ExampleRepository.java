package com.brestrai.template.repository.example;

import com.brestrai.template.domain.example.ExampleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleModel, Long> {
}
