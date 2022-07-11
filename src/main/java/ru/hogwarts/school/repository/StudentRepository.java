package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.GetLastFiveStudents;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT(id) FROM student", nativeQuery = true)
    Integer getStudentsQuantity();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Long getStudentsAverageAge();

    @Query(value = "SELECT id, name, age FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<GetLastFiveStudents> getLastFiveStudents();
}
