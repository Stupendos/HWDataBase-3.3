package org.example.databasehw.repository;

import org.example.databasehw.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findAllByAgeBetween(Integer from, Integer to);
}
