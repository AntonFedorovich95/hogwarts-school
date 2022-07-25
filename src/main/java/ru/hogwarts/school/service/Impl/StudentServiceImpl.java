package ru.hogwarts.school.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.GetLastFiveStudents;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info ("Student Creation Method");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentId(Long studentId) {
        logger.info("Student search by ID");
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            logger.warn("There is no student with this name = " + student.getName());
            return null;
        }
        logger.info ("Method of changing student data");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) {
        logger.info ("Deleting a student by ID");
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<Student> getStudentsAge(Integer age) {
        logger.info("Search for students of the same post");
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Search for students of a certain age category ");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

@Override
    public Integer getStudentsQuantity() {
    logger.info ("Student counting Method");
        return studentRepository.getStudentsQuantity();
    }

    @Override
    public Long getStudentsAverageAge() {
        logger.info ("Method for determining the average return");
        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public List<GetLastFiveStudents> getLastFiveStudentsList() {
        logger.info("This method calls the last five students");
        return studentRepository.getLastFiveStudents();
    }

    public Collection<String> getStudentsByFirstName(String substring) {
        logger.info("Еhe method calls students by the beginning of the name ");
        return studentRepository
                .findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().startsWith(substring))
                .map(student -> student.getName())
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getStudentsAverageAgeByStreamMethod() {
        logger.info("The method determines the average age of students");
        return studentRepository
                .findAll()
                .stream()
                .parallel()
                .mapToInt(student->student.getAge())
                .average()
                .getAsDouble();
    }

    public void getStudentsThreadMethod() {
        logger.info("This method calls students by the flow method");
        List<String> students = getStudentsStreamMethod(studentRepository);
        int firstPoint = students.size() / 3;
        int secondPoint = firstPoint * 2;
        printStudentsNameToConsole(0, firstPoint, students);
        new Thread(() -> {
            printStudentsNameToConsole(firstPoint, secondPoint, students);
        }).start();
        new Thread(() -> {
            printStudentsNameToConsole(secondPoint, students.size(), students);
        }).start();
    }

    public void getStudentsSynchronizedThreadMethod() {
        logger.info("Method to get students list by synchronized thread method was invoked");
        List<String> students = getStudentsStreamMethod(studentRepository);
        int firstPoint = students.size() / 3;
        int secondPoint = firstPoint * 2;
        printStudentsNameToConsoleSynchronizedMethod(0, firstPoint, students);
        new Thread(() -> {
            printStudentsNameToConsoleSynchronizedMethod(firstPoint, secondPoint, students);
        }).start();
        new Thread(() -> {
            printStudentsNameToConsoleSynchronizedMethod(secondPoint, students.size(), students);
        }).start();
    }

    private List<String> getStudentsStreamMethod(StudentRepository studentRepository) {
        return studentRepository
                .findAll()
                .stream()
                .map(student -> student.getName())
                .collect(Collectors.toList());
    }

    private void printStudentsNameToConsole(Integer startPoint, Integer endPoint, List<String> students) {
        for (int i = startPoint; i < endPoint; i++) {
            System.out.println(students.get(i));
        }
    }

    private synchronized void printStudentsNameToConsoleSynchronizedMethod(Integer startPoint, Integer endPoint, List<String> students) {
        for (int i = startPoint; i < endPoint; i++) {
            System.out.println(students.get(i));
        }
    }
}