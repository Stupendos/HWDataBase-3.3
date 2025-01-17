package org.example.databasehw.controller;

import net.minidev.json.JSONObject;
import org.example.databasehw.model.Faculty;
import org.example.databasehw.model.Student;
import org.example.databasehw.service.FacultyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    private Faculty facultyTest;
    private JSONObject facultyJson;

    @BeforeEach
    void setUp() {
        facultyTest = new Faculty();
        facultyTest.setName("Gryffindor");
        facultyTest.setColor("Red");
        facultyTest.setId(22L);

        facultyJson = new JSONObject();
        facultyJson.put("name", "Gryffindor");
        facultyJson.put("color", "Red");
        facultyJson.put("id", 22L);
    }

    @Test
    void save() throws Exception {
        when(facultyService.save(any(Faculty.class))).thenReturn(facultyTest);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Gryffindor\", \"color\":\"Red\"}"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.id").value(facultyTest.getId()));
    }

    @Test
    void findAll() throws Exception {
        when(facultyService.findAll()).thenReturn(Arrays.asList(facultyTest));

        ResultActions resultActions = mockMvc.perform(get("/faculty")
                .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyTest.getId()))
                .andExpect(jsonPath("$[0].name").value(facultyTest.getName()))
                .andExpect(jsonPath("$[0].color").value(facultyTest.getColor()));
    }

    @Test
    void findById() throws Exception {
        when(facultyService.findById(any(Long.class))).thenReturn(Optional.of(facultyTest));

        ResultActions resultActions = mockMvc.perform(get("/faculty/" + facultyTest.getId())
                        .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyTest.getId()))
                .andExpect(jsonPath("$.name").value(facultyTest.getName()));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(facultyService).deleteById(facultyTest.getId());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + facultyTest.getId())
                        .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    void findByColor() throws Exception {
        when(facultyService.findByColor(any(String.class))).thenReturn(Set.of(facultyTest));

        ResultActions resultActions = mockMvc.perform(get("/faculty/color")
                        .param("color", String.valueOf(facultyTest.getColor()))
                        .accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(facultyTest.getId()))
                .andExpect(jsonPath("$[0].name").value(facultyTest.getName()))
                .andExpect(jsonPath("$[0].color").value(facultyTest.getColor()));
    }

    @Test
    void getFacultyStudents() throws Exception {
        Set<Student> students = new HashSet<>();
        Student st1 = new Student();
        Student st2 = new Student();
        st1.setName("Harry");
        st2.setName("Hermione");
        st1.setFaculty(facultyTest);
        st2.setFaculty(facultyTest);
        students.add(st1);
        students.add(st2);

        when(facultyService.getFacultyStudents(22L)).thenReturn(students);

        ResultActions resultActions = mockMvc.perform(get("/faculty/{facultyId}/students", 22L));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(st1.getId()))
                .andExpect(jsonPath("$[0].name").value(st1.getName()))
                .andExpect(jsonPath("$[1].id").value(st2.getId()))
                .andExpect(jsonPath("$[1].name").value(st2.getName()));
    }
}