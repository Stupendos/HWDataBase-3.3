package org.example.databasehw.controller;

import lombok.AllArgsConstructor;
import org.example.databasehw.model.Student;
import org.example.databasehw.service.StudentService;
import org.example.databasehw.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student save(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable Long id) {
        return studentService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/age/eq")
    public List<Student> findByAge(@RequestParam Integer age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/age/between")
    public List<Student> findByAgeBetween(@RequestParam Integer from, @RequestParam Integer to) {
        return studentService.findByAgeBetween(from, to);
    }
}
