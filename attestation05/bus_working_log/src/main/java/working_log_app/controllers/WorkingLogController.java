package working_log_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import working_log_app.dto.SalaryResponseDto;
import working_log_app.dto.WorkingLogRequestDto;
import working_log_app.dto.WorkingLogResponseDto;
import working_log_app.service.WorkingLogService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/working_log")
@RequiredArgsConstructor
public class WorkingLogController {

    private final WorkingLogService service;

    @GetMapping
    public List<WorkingLogResponseDto> getAllLogs(@RequestBody WorkingLogRequestDto request) {
        if (request.getPhone() != null) {
            return service.getAllLogsByPhone(request);
        }
        return service.getAllLogs(request);
    }

    @GetMapping("/salary")
    public List<SalaryResponseDto> getAllSalaries(@RequestBody WorkingLogRequestDto request) {
        if (request.getPhone() != null) {
            return List.of(service.getSalaryByPhone(request));
        }
        return service.getAllSalaries(request);
    }
}