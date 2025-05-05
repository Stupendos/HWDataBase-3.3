package org.example.databasehw.controller;

import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.StudentRepository;
import org.example.databasehw.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
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
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(student -> ResponseEntity.ok(student))
                .orElse(ResponseEntity.notFound().build());
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

    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("name-starting-with-a")
    public ResponseEntity<List<String>> findByNameStartingWithA() {
        return ResponseEntity.ok(studentService.findByNameStartingWithA());
    }

    @GetMapping("average-age")
    public ResponseEntity<Double> averageAge() {
        return ResponseEntity.ok(studentService.averageAge());
    }

    public long calculateSum() {
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    @GetMapping("/sum")
    public ResponseEntity<Long> getSum() {
        long sum = calculateSum();
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        List<Student> students = studentService.findAll();
        for (int i = 0; i < 2 && i < students.size(); i++) {
            System.out.println(students.get(i).getName());
        }
        if (students.size() > 3) {
            new Thread(() -> {
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
            }).start();
        }

        if (students.size() > 5) {
            new Thread(() -> {
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            }).start();
        }
    }

    private synchronized void printStudentName(String name) {
        System.out.println(name);
    }

    @GetMapping("/print-synchronized")
    public void printStudentNameSynchronized() {
        List<Student> students = studentService.findAll();

        for (int i = 0; i < 2 && i < students.size(); i++) {
            printStudentName(students.get(i).getName());
        }
        if (students.size() > 3) {
            new Thread(() -> {
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
            }).start();
        }
        if (students.size() > 5) {
            new Thread(() -> {
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            }).start();
        }
    }
}

