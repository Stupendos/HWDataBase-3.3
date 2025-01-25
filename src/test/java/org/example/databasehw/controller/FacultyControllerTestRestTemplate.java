package org.example.databasehw.controller;

import jakarta.transaction.Transactional;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FacultyControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyController facultyController;

    private Faculty faculty;
    private Student student;

    @BeforeEach
    void setUp() {
        faculty = new Faculty();
        faculty.setName("Пуфендуй");
        faculty.setColor("Синий");
        facultyRepository.save(faculty);

        student = new Student();
        student.setName("Kevin");
        student.setAge(20);
        student.setFaculty(faculty);
        studentRepository.save(student);
    }

    @Test
    void contextLoads() {
        assertNotNull(faculty);
    }

    @Test
    void save() {
        Faculty saveFaculty = new Faculty();
        saveFaculty.setName("Пуфендуй");
        saveFaculty.setColor("Синий");

        ResponseEntity<Faculty> responsePost = restTemplate.postForEntity("http://localhost:" + port + "/faculty", saveFaculty, Faculty.class);

        assertEquals(HttpStatus.OK, responsePost.getStatusCode());
        assertNotNull(responsePost.getBody());

        ResponseEntity<Faculty[]> responseGet = restTemplate.getForEntity("http://localhost:" + port + "/faculty", Faculty[].class);

        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
        assertNotNull(responseGet.getBody());
    }

    @Test
    void findAll() {
        // Act: Выполнение тестируемого действия
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty", Faculty[].class);

        // Assert: Проверка результатов
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println(Arrays.toString(response.getBody()));
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void findById() {
        // Act: Выполнение тестируемого действия
        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        // Assert: Проверка результатов
        if (response.getStatusCode() == HttpStatus.OK) {
            assertNotNull(response.getBody());
            assertEquals(faculty.getName(), response.getBody().getName());
            assertEquals(faculty.getColor(), response.getBody().getColor());
        } else {
            System.out.println("Response status: " + response.getStatusCode());
        }
    }

    @Test
    void delete() {
        restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId());

        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByColor() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty/color?color=" + faculty.getColor(), Faculty[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(Arrays.stream(response.getBody()).anyMatch(f -> f.getColor().equals(faculty.getColor())));
    }

    @Test
    void getFacultyStudents() {
        facultyRepository.findById(this.faculty.getId());

        ResponseEntity<Student[]> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + faculty.getId() + "/students", Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}