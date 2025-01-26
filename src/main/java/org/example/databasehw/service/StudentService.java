package org.example.databasehw.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.StudentRepository;
import org.example.databasehw.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        logger.info("Saving student");
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        logger.info("Finding all students");
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        logger.info("Finding student by id");
        return studentRepository.findById(id);
    }

    public void deleteById(Long id) {
        logger.info("Deleting student by id");
        studentRepository.deleteById(id);
    }

    public List<Student> findByAge(Integer age) {
        logger.info("Finding all students by age");
        return studentRepository.findAllByAge(age);
    }

    public List<Student> findByAgeBetween(Integer from, Integer to) {
        logger.info("Finding all students by age between");
        return studentRepository.findAllByAgeBetween(from, to);
    }

    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Finding student faculty by id");
        logger.error("Student faculty not found");
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Integer getNumberOfStudents() {
        logger.info("Number of students found");
        return studentRepository.getNumberOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Getting average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getFiveLastStudents() {
        logger.info("Getting five last students");
        return studentRepository.getFiveLastStudents();
    }
}
