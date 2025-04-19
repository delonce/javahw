package bus_app.repositories;

import bus_app.models.BusUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusUserRepository extends JpaRepository<BusUser, String> {
}