package org.example.databasehw.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.FacultyRepository;
import org.example.databasehw.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty save(Faculty faculty) {
        logger.info("Faculty saved");
        return facultyRepository.save(faculty);
    }

    public List<Faculty> findAll() {
        logger.info("Get all faculty");
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findById(Long id) {
        logger.info("Get faculty by id");
        return facultyRepository.findById(id);
    }

    public void deleteById(Long id) {
        logger.info("Delete faculty by id");
        facultyRepository.deleteById(id);
    }

    public Set<Faculty> findByColor(String color) {
        logger.info("Get faculty by color");
        List<Faculty> faculties = facultyRepository.findAllByColor(color);
        return new HashSet<>(faculties);
    }

        public Set<Student> getFacultyStudents(Long facultyId) {
        logger.info("Get faculty students by faculty id");
        return facultyRepository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(Collections.emptySet());
    }
}
