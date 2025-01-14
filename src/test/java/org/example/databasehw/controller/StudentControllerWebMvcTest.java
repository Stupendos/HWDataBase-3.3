package org.example.databasehw.controller;

import net.minidev.json.JSONObject;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.repository.StudentRepository;
import org.example.databasehw.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerWebMvcTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student1;
    private Student student;
    private Student student2;
    private JSONObject userObject;
    private JSONObject userObject1;
    private JSONObject userObject2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setAge(15);
        student.setName("Bob");
        userObject = new JSONObject();
        userObject.put("name", student.getName());


        student1 = new Student();
        student1.setId(2L);
        student1.setAge(16);
        student1.setName("Dobby");
        userObject1 = new JSONObject();
        userObject1.put("name", student1.getName());

        student2 = new Student();
        student2.setId(2L);
        student2.setAge(18);
        student2.setName("Dobby");
        userObject2 = new JSONObject();
        userObject2.put("name", student2.getName());

    }

    @Test
    void save() throws Exception {

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }

    @Test
    void findAll() throws Exception {

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student, student1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[1].id").value(student1.getId()))
                .andExpect(jsonPath("$[1].name").value(student1.getName()));
    }

    @Test
    void findById() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()));
    }

    @Test
    void delete() throws Exception {
        Long studentId = student.getId();

        doNothing().when(studentRepository).deleteById(studentId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByAge() throws Exception {
        int age = 16;
        when(studentRepository.findAllByAge(age)).thenReturn(Arrays.asList(student, student1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/eq")
                        .param("age", String.valueOf(age))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()))
                .andExpect(jsonPath("$[1].id").value(student1.getId()))
                .andExpect(jsonPath("$[1].name").value(student1.getName()))
                .andExpect(jsonPath("$[1].age").value(student1.getAge()));
    }

    @Test
    void findByAgeBetween() throws Exception {
        int fromAge = 15;
        int toAge = 19;
        when(studentRepository.findAllByAgeBetween(fromAge, toAge)).thenReturn(Arrays.asList(student, student1, student2));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/age/between")
                .param("from", String.valueOf(fromAge))
                .param("to", String.valueOf(toAge))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()))
                .andExpect(jsonPath("$[1].id").value(student1.getId()))
                .andExpect(jsonPath("$[1].name").value(student1.getName()))
                .andExpect(jsonPath("$[1].age").value(student1.getAge()))
                .andExpect(jsonPath("$[2].id").value(student2.getId()))
                .andExpect(jsonPath("$[2].name").value(student2.getName()))
                .andExpect(jsonPath("$[2].age").value(student2.getAge()));
    }

    @Test
    void getStudentFaculty() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setColor("Бирюзовый");
        newFaculty.setName("Когтевран");

        student.setFaculty(newFaculty);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/" + student.getId() + "/faculty")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newFaculty.getId()))
                .andExpect(jsonPath("$.name").value(newFaculty.getName()));
    }
}