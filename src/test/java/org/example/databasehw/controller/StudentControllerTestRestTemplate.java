package org.example.databasehw.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.FacultyRepository;
import org.example.databasehw.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    private Faculty testFaculty;
    private Student testStudent;
    private Student testStudent1;
    private Student testStudent2;
    private Student testStudent3;
    private Student testStudent4;
    private Student testStudent5;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        testFaculty = new Faculty();
        testFaculty.setName("Гриффиндор");
        testFaculty.setColor("Красный");
        facultyRepository.save(testFaculty);

        testStudent = new Student();
        testStudent.setName("John");
        testStudent.setAge(20);
        testStudent.setFaculty(testFaculty);
        testFaculty.getStudents().add(testStudent);
        studentRepository.save(testStudent);

        testStudent1 = new Student("Captain", 20, testFaculty);
        testStudent2 = new Student("Jack", 21, testFaculty);
        testStudent3 = new Student("Sparrow", 22, testFaculty);
        testStudent4 = new Student("David", 23, testFaculty);
        testStudent5 = new Student("Johns", 24, testFaculty);

        List.of(testStudent1, testStudent2, testStudent3, testStudent4, testStudent5)
                .forEach(student -> {
                    student.setFaculty(testFaculty);
                    testFaculty.getStudents().add(student);
                    studentRepository.save(student);
                });
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull();
    }

    @Transactional
    @Test
    void save() {
        Student newStudent = new Student();
        newStudent.setName("Harry");

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", newStudent, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry");
    }

    @Transactional
    @Test
    void findAll() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/student", List.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
                .isNotNull();
    }

    @Transactional
    @Test
    void findById() {
        Student newSaveStudent = new Student();
        newSaveStudent.setName("Ron");

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", newSaveStudent, Student.class);

        Long studentId = response.getBody().getId();

        ResponseEntity<Student> response1 = restTemplate.getForEntity("http://localhost:" + port + "/student/" + studentId, Student.class);

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isNotNull();
        assertThat(response1.getBody().getName()).isEqualTo("Ron");
    }

    @Transactional
    @Test
    void delete() throws Exception {
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/student/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
                .isNotNull();
    }

    @Transactional
    @Test
    void findByAge() throws Exception {
        ResponseEntity<List> response = restTemplate.exchange("http://localhost:" + port + "/student/age/eq?age=22",
                HttpMethod.GET, null, List.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
                .isNotNull();
    }

    @Transactional
    @Test
    void findByAgeBetween() {
        Student student = new Student();
        student.setName("Tom");
        student.setAge(18);
        student.setId(16L);

        ResponseEntity<List> response = restTemplate.exchange("http://localhost:" + port + "/student/age/between?from=18&to=22",
                HttpMethod.GET, null, List.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
                .isNotNull();
    }

    @Test
    void getStudentFaculty() {
        assertThat(testStudent).isNotNull();
        assertThat(testStudent.getFaculty()).isNotNull();
        assertThat(testStudent.getId()).isNotNull();

        System.out.println("факультет студента = " + testStudent.getFaculty().getName());
        System.out.println("айди факультета = " + testStudent.getFaculty().getId());
        System.out.println("айди студента = " + testStudent.getId());

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + testStudent.getId() + "/faculty", Faculty.class
        );

        System.out.println("Статус ответа: " + response.getStatusCode());
        System.out.println("Ответ: " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(testStudent.getFaculty().getId());
        assertThat(response.getBody().getName()).isEqualTo(testStudent.getFaculty().getName());
    }


    @Test
    void printParallel() throws Exception {
        String url = "http://localhost:" + port + "/student/print-parallel";
        restTemplate.getForObject(url, Void.class);

        Thread.sleep(1000);

        String output = outContent.toString();
        System.setOut(originalOut);

        Assertions.assertThat(output).contains("John");
        Assertions.assertThat(output).contains("Captain");
        Assertions.assertThat(output).contains("Jack");
        Assertions.assertThat(output).contains("Sparrow");
        Assertions.assertThat(output).contains("David");
        Assertions.assertThat(output).contains("Johns");

    }

    @Test
    void printStudentNameSynchronized() throws Exception {
        String url = "http://localhost:" + port + "/student/print-synchronized";
        restTemplate.getForObject(url, Void.class);

        Thread.sleep(1000);

        String output = outContent.toString();
        System.setOut(originalOut);

        Assertions.assertThat(output).contains("John");
        Assertions.assertThat(output).contains("Captain");
        Assertions.assertThat(output).contains("Jack");
        Assertions.assertThat(output).contains("Sparrow");
        Assertions.assertThat(output).contains("David");
        Assertions.assertThat(output).contains("Johns");
    }
}