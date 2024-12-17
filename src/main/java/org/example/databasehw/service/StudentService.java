package org.example.databasehw.service;

import lombok.AllArgsConstructor;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.StudentRepository;
import org.example.databasehw.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

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
    }

    public List<Student> findByAge(Integer age) {
        return studentRepository.findAllByAge(age);
    }

    public List<Student> findByAgeBetween(Integer from, Integer to) {
        return studentRepository.findAllByAgeBetween(from, to);
    }
}
