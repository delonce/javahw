package bus_app.services;

import bus_app.dto.drivers.DriverDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Driver;
import bus_app.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, предоставляющий интерфейс для обработки полученных через MVC данных от клиента
 * и получения / модификации записей о водителях
 */
@Service
@RequiredArgsConstructor
public class DriverService {

    /**
     * Экземпляр класса для общения со схемой водителей в БД
     */
    private final DriverRepository driverRepository;

    /**
     * Метод получения списка всех водителей
     * @return список всех записей о водителях
     */
    public List<Driver> readAllDrivers() {
        return driverRepository.findAll();
    }

    /**
     * Метод создания новой записи о водителе
     * @param driver объект, содержащий основную информацию для создания новой записи
     * @return объект с информацией о новом водителе из БД
     */
    public Driver addDriver(DriverDto driver) {
        try {
            Driver newDriver = new Driver(
                    null,
                    driver.getName(),
                    driver.getAge(),
                    driver.getPhone(),
                    false
            );

            return driverRepository.saveAndFlush(newDriver);
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод модификации записи о водителе в БД
     * @param id номер водителя
     * @param driver объект, содержащий основную информацию для модификации записи
     * @return объект с информацией о модифицированном водителе из БД
     */
    public Driver updateDriver(Long id, DriverDto driver) {
        try {
            Driver updatableDriver = driverRepository.findById(id).get();

            if (driver.getName() != null) {
                updatableDriver.setName(driver.getName());
            }
            if (driver.getAge() != null) {
                updatableDriver.setAge(driver.getAge());
            }
            if (driver.getPhone() != null) {
                updatableDriver.setPhone( driver.getPhone());
            }

            return driverRepository.saveAndFlush(updatableDriver);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No driver with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод удаления записи о водителе по его номеру
     * @param id номер водителя, информацию о котором нужно удалить / скрыть
     */
    public void deleteDriver(Long id) {
        try {
            Driver driver = driverRepository.findById(id).get();

            driverRepository.delete(driver);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No driver with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}