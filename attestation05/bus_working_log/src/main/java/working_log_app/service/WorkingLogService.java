package working_log_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import working_log_app.dto.SalaryResponseDto;
import working_log_app.dto.WorkingLogRequestDto;
import working_log_app.dto.WorkingLogResponseDto;
import working_log_app.kafka.KafkaMessageDto;
import working_log_app.repositories.WorkingLogRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class WorkingLogService {

    @Value("${salary}")
    private Double salaryByHour;

    private final WorkingLogRepository repository;

    public List<WorkingLogResponseDto> getAllLogs(WorkingLogRequestDto request) {
        return repository.findAllByStartTimeBetween(request.getFrom(), request.getTo())
                .stream()
                .map(WorkingLogResponseDto::new)
                .toList();
    }

    public List<WorkingLogResponseDto> getAllLogsByPhone(WorkingLogRequestDto request) {
        return repository.findAllByDriverPhoneAndStartTimeBetween(request.getPhone(),
                        request.getFrom(), request.getTo())
                .stream()
                .map(WorkingLogResponseDto::new)
                .toList();
    }

    public List<SalaryResponseDto> getAllSalaries(WorkingLogRequestDto request) {
        Map<String, List<WorkingLogResponseDto>> logsByPhone = this.getAllLogs(request).stream()
                .collect(groupingBy(WorkingLogResponseDto::getDriverPhone));

        List<SalaryResponseDto> result = new ArrayList<>();

        for (var driverData: logsByPhone.entrySet()) {
            result.add(new SalaryResponseDto(
                    driverData.getKey(),
                    findSalary(driverData.getValue())
            ));
        }

        return result;
    }

    public SalaryResponseDto getSalaryByPhone(WorkingLogRequestDto request) {
        return new SalaryResponseDto(
                request.getPhone(),
                findSalary(this.getAllLogsByPhone(request))
        );
    }

    public void createNewLog(KafkaMessageDto message) {
        System.out.println("Получено сообщение ON");
    }

    public void closeLog(KafkaMessageDto messageDto) {
        System.out.println("Получено сообщение OFF");
    }

    private String findSalary(List<WorkingLogResponseDto> logs) {
        Optional<Double> result =  logs.stream()
                .filter(elem -> elem.getEndTime() != null)
                .map(elem ->
                        Duration.between(elem.getStartTime(), elem.getEndTime()))
                .reduce(Duration::plus)
                .map(Duration::toMinutes)
                .map(elem -> elem / 60.);

        return String.format("%.2f", result.orElse(0.) * salaryByHour);
    }
}