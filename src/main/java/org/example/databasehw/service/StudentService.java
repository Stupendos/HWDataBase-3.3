package org.example.databasehw.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.StudentRepository;
import org.example.databasehw.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findByAge(Integer age) {
        return studentRepository.findAllByAge(age);
    }

    public List<Student> findByAgeBetween(Integer from, Integer to) {
        return studentRepository.findAllByAgeBetween(from, to);
    }

    public Faculty getStudentFaculty(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Integer getNumberOfStudents() {
        return studentRepository.getNumberOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }
}
