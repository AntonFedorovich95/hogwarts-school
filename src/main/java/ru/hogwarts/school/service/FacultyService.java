package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long facultyId = 1L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(facultyId);
        faculties.put(facultyId, faculty);
        facultyId++;
        return faculty;
    }

    public Faculty getFacultyById(Long facultyId) {
        if (!faculties.containsKey(facultyId)) {
            return null;
        }
        return faculties.get(facultyId);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            return null;
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long facultyId) {
        if (!faculties.containsKey(facultyId)) {
            return null;
        }
        return faculties.remove(facultyId);
    }

    public List<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(e -> e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
