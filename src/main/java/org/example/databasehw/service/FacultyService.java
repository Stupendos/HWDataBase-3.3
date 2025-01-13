package org.example.databasehw.service;

import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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
