package app.services;

import app.exceptions.NoDataException;
import app.models.Grade;
import app.repositories.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository repository;

    public List<Grade> readAllGrades() {
        return repository.readAllGrades();
    }

    public Grade makeGradeActive(Integer id) throws NoDataException {
        repository.readGrade(id);

        return repository.makeGradeActive(id);
    }

    public Grade makeGradeNonActive(Integer id) throws NoDataException {
        repository.readGrade(id);

        return repository.makeGradeNonActive(id);
    }

    public Grade readGrade(Integer id) throws NoDataException {
        return repository.readGrade(id);
    }
}
