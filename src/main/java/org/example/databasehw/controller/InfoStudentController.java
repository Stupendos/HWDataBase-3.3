package org.example.databasehw.controller;

import org.example.databasehw.model.Student;
import org.example.databasehw.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/info-student")
@RestController
public class InfoStudentController {

    private final StudentService studentService;

    public InfoStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/nums-of-students")
    public Integer getNumOfStudents() {
        return studentService.getNumberOfStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/last-five-students")
    public List<Student> getLastFiveStudents() {
        return studentService.getFiveLastStudents();
    }
}
