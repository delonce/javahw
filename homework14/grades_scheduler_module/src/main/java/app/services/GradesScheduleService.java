package app.services;

import app.clients.GradeClient;
import app.models.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GradesScheduleService {

    private static final Logger LOGGER =
            Logger.getLogger(GradesScheduleService.class.getName());

    private final GradeClient gradeClient;
    private final StudentMessageService studentService;

    @Scheduled(fixedDelayString = "P1D")
    public void checkGrades() {
        LocalDate currentDate = LocalDate.now();

        List<Grade> startedGrades = gradeClient.getGrades().stream()
                .filter(grade -> grade.getIsActive() &&
                        (grade.getStartDate().isBefore(currentDate) || grade.getStartDate().isEqual(currentDate)))
                .toList();

        List<Integer> ids = startedGrades.stream().map(Grade::getId).toList();

        LOGGER.info("Today grades: " + ids);

        ids.forEach(gradeClient::makeGradeActive);

        studentService.sendMessagesAbout(startedGrades);
    }
}