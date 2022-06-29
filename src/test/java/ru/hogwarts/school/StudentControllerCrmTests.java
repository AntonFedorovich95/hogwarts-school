package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.Impl.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerCrmTests {

    @LocalServerPort
    private int port;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private StudentServiceImpl studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        Student student = new Student(1L,"Harry",22);
        studentService.createStudent(student);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
    }

    @AfterEach
    void tearDown() {
        studentService.deleteStudent(1L);
    }

    @Test
    public void ContextLoads(){
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() throws JSONException {
        String expected = "{id:1,name:\"Harry\",age:22}";
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/student/" + 1L, String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }

    @Test
    void createStudent() throws JsonProcessingException, JSONException{
        Student student = new Student(2L,"Ron",23);
        studentService.createStudent(student);
        String expected = mapper.writeValueAsString(student);
        ResponseEntity<String> response = restTemplate.postForEntity("/student", student,String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected,response.getBody(),false);
    }

    @Test
    void updateStudent(){
        Student student = new Student(3L,"Hermione",23);
        when(studentRepository.save(any())).thenReturn(student);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        ResponseEntity<Student> response = this.restTemplate.exchange("/student", HttpMethod.PUT, entity, Student.class);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    void deleteStudent(){
        HttpEntity<String> entity = new HttpEntity<>(null,new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/student/2",HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}