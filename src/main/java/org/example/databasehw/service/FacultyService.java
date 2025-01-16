package org.example.databasehw.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.FacultyRepository;
import org.example.databasehw.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    public void deleteById(Long id) {
        facultyRepository.deleteById(id);
    }

    public Set<Faculty> findByColor(String color) {
        List<Faculty> faculties = facultyRepository.findAllByColor(color);
        return new HashSet<>(faculties);
    }

        public Set<Student> getFacultyStudents(Long facultyId) {
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(Collections.emptySet());
    }
}
