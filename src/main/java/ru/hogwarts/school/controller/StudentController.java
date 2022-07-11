package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.Impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentId(@PathVariable Long studentId) {
        Student student = studentService.getStudentId(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/filter/{studentAge}")
    public List<Student> getStudentAge(@PathVariable Integer studentAge) {
        return studentService.getStudentsAge(studentAge);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity <Collection<Student>> getStudentByAgeBetween (@RequestParam Integer minAge,
                                                                       @RequestParam Integer maxAge) {
        if (minAge > maxAge){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getStudentByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/studentsQuantityInfo")
    public ResponseEntity getStudentsQuantity() {
        return ResponseEntity.ok(studentService.getStudentsQuantity());
    }

    @GetMapping("/studentsAverageAgeInfo")
    public ResponseEntity getStudentsAverageAge() {
        return ResponseEntity.ok(studentService.getStudentsAverageAge());
    }

    @GetMapping("/listOfLastFiveStudents")
    public ResponseEntity getLastFiveStudentsList() {
        return ResponseEntity.ok(studentService.getLastFiveStudentsList());
    }

}
