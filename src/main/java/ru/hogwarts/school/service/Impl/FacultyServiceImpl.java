package ru.hogwarts.school.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("The method of creating a faculty ");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyId(Long facultyId) {
        logger.info("Calling the id search method");
        return facultyRepository.findById(facultyId).get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId()) != true) {
            logger.warn("The faculty with this name does not exist =  " + faculty.getName());
            return null;
        }
        logger.info("Method causes faculty name change");
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long facultyId) {
        logger.info ("Faculty Removal Method");
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public List<Faculty> getFacultyColor(String color) {
        logger.info ("Color search method");
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> getFacultyColorOrName(String nameOrColor) {
        logger.info ("Search method by color or name");
        return facultyRepository.findByColorAndName(nameOrColor, nameOrColor);
    }
}

