package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.Impl.AvatarServiceImpl;
import ru.hogwarts.school.service.Impl.FacultyServiceImpl;
import ru.hogwarts.school.service.Impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarServiceImpl avatarService;

    @MockBean
    private StudentServiceImpl studentService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testStudents() throws Exception{
        final String name = "Draco Malfoy";
        final String color = "black";
        final long id = 4;
        Faculty faculty = new Faculty();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id",id);
        facultyObject.put("name",name);
        facultyObject.put("color",color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(id))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColor(eq(color))).thenReturn(List.of(faculty));
        when(facultyRepository.existsById(eq(id))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/filter/" + color)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[4].id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
