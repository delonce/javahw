package working_log_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import working_log_app.models.WorkingLog;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingLogResponseDto {

    private String driverPhone;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public WorkingLogResponseDto(WorkingLog log) {
        this.driverPhone = log.getDriverPhone();
        this.startTime = log.getStartTime();
        this.endTime = log.getEndTime();
    }
}