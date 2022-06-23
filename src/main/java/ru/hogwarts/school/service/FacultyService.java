package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyId(Long facultyId);

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(Long facultyId);

    List<Faculty> getFacultyColor(String color);

    Collection<Faculty> getFacultyColorOrName(String nameOrColor);
}
