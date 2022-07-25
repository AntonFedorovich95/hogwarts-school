package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudentId(Long studentId);

    Student updateStudent(Student student);

    void deleteStudent(Long studentId);

    List<Student> getStudentsAge(Integer age);

    Collection<Student> getStudentByAgeBetween(Integer minAge, Integer maxAge);

    Integer getStudentsQuantity();

    Long getStudentsAverageAge();

    List<GetLastFiveStudents> getLastFiveStudentsList();

    Collection<String> getStudentsByFirstName(String substring);

    Double getStudentsAverageAgeByStreamMethod();

    void getStudentsThreadMethod();

    void getStudentsSynchronizedThreadMethod();
}