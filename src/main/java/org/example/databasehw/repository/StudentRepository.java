package org.example.databasehw.repository;

import org.example.databasehw.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findAllByAgeBetween(Integer from, Integer to);

    @Query(value = "SELECT COUNT(id) FROM public.student", nativeQuery = true)
    Integer getNumberOfStudents();

    @Query(value = "SELECT AVG(student.age) from student", nativeQuery = true)
    Double getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();
}
