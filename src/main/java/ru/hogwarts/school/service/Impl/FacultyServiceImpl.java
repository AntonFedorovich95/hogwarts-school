package ru.hogwarts.school.service.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

@Override
    public Faculty createFaculty(Faculty faculty) {
    return facultyRepository.save(faculty);
}
@Override
    public Faculty getFacultyId(Long facultyId) {
    return facultyRepository.findById(facultyId).get();
}

@Override
    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId()) != true) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

@Override
    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

@Override
    public List<Faculty> getFacultyColor(String color) {
        return facultyRepository.findByColor(color);
    }

@Override
    public Collection<Faculty> getFacultyColorOrName (String color, String name){
        return facultyRepository.findByColorAndName(color, name);
}
}
