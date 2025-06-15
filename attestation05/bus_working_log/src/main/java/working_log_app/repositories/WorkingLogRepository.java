package working_log_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import working_log_app.models.WorkingLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkingLogRepository extends JpaRepository<WorkingLog, Long> {

    List<WorkingLog> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<WorkingLog> findAllByDriverPhoneAndStartTimeBetween(String driverPhone,
                                                             LocalDateTime start, LocalDateTime end);
}