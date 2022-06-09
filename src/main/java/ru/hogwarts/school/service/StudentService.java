package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long studentId = 1L;

    public Student createStudent(Student student) {
        student.setId(studentId);
        students.put(studentId, student);
        studentId++;
        return student;
    }

    public Student getStudentById(Long studentId) {
        if (!students.containsKey(studentId)) {
            return null;
        }
        return students.get(studentId);
    }

    public Student updateStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        if (!students.containsKey(studentId)) {
            return null;
        }
        return students.remove(studentId);
    }

    public List<Student> getStudentsByAge(Integer age) {
        return students.values().stream()
                .filter(e -> e.getAge().equals(age))
                .collect(Collectors.toList());
    }
}