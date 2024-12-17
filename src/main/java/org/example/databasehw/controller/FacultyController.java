package org.example.databasehw.controller;

import lombok.AllArgsConstructor;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.repository.FacultyRepository;
import org.example.databasehw.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@AllArgsConstructor
public class FacultyController {

    private FacultyService facultyService;

    @PostMapping
    public Faculty save(@RequestBody Faculty faculty) {
        return facultyService.save(faculty);
    }

    @GetMapping
    public List<Faculty> findAll() {
        return facultyService.findAll();
    }

    @GetMapping("/{id}")
    public Faculty findById(@PathVariable Long id) {
        return facultyService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facultyService.deleteById(id);
    }

    @GetMapping("/color")
    public List<Faculty> findByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }
}
