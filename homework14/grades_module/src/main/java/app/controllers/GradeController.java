package app.controllers;

import app.dto.GradeDto;
import app.exceptions.NoDataException;
import app.services.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService service;

    @GetMapping
    public List<GradeDto> readAllGrades() {
        return service.readAllGrades().stream()
                .map(GradeDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public GradeDto readGrade(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.readGrade(id));
    }

    @PutMapping("/{id}")
    public GradeDto makeGradeActive(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.makeGradeActive(id));
    }

    @DeleteMapping("/{id}")
    public GradeDto makeGradeNonActive(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.makeGradeNonActive(id));
    }
}
