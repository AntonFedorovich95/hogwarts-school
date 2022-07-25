package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.Impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFacultyId(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getFacultyId(facultyId);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/filter/{color}")
    public List<Faculty> getFacultyColor(@PathVariable String color) {
        return facultyService.getFacultyColor(color);
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> getFacultyColorOrName(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.getFacultyColorOrName(nameOrColor));
    }

    @GetMapping("/findTheLongestNameOfTheFaculty")
    public ResponseEntity<Collection> getFacultiesWithTheLongestName() {
        return ResponseEntity.ok(facultyService.getFacultyWithTheMaxNameLength());
    }
}