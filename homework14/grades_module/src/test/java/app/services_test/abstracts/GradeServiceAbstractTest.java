package app.services_test.abstracts;

import app.repositories.GradeRepository;
import app.services.GradeService;
import org.mockito.Mockito;

public abstract class GradeServiceAbstractTest {

    protected final GradeRepository repository;
    protected final GradeService service;

    public GradeServiceAbstractTest() {
        repository = Mockito.mock(GradeRepository.class);
        service = new GradeService(repository);
    }
}
