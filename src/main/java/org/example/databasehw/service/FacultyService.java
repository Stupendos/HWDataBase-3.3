package org.example.databasehw.service;

import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    }

    public Set<Faculty> findByColor(String color) {
        return (Set<Faculty>) facultyRepository.findAllByColor(color);
    }
    public Set<Student> getFacultyStudents(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        return faculty.getStudents();
    }
}
