package bus_app.services;

import bus_app.dto.stations.StationDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Station;
import bus_app.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, предоставляющий интерфейс для обработки полученных через MVC данных от клиента
 * и получения / модификации записей об остановках
 */
@Service
@RequiredArgsConstructor
public class StationService {

    /**
     * Экземпляр класса для общения со схемой остановок в БД
     */
    private final StationRepository stationRepository;

    /**
     * Метод получения списка всех остановок
     * @return список всех записей об остановках
     */
    public List<Station> readAllStations() {
        return stationRepository.findAll();
    }

    /**
     * Метод создания новой записи об остановке
     * @param station объект, содержащий основную информацию для создания новой записи
     * @return объект с информацией о новой остановке из БД
     */
    public Station addStation(StationDto station) {
        try {
            Station newStation = new Station(
                    null,
                    station.getName(),
                    station.getDistrict(),
                    false
            );

            return stationRepository.saveAndFlush(newStation);
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод модификации записи об остановке в БД
     * @param id номер остановки
     * @param station объект, содержащий основную информацию для модификации записи
     * @return объект с информацией о модифицированной остановке из БД
     */
    public Station updateStation(Long id, StationDto station) {
        try {
            Station updatableStation = stationRepository.findById(id).get();

            if (station.getName() != null) {
                updatableStation.setName(station.getName());
            }
            if (station.getDistrict() != null) {
                updatableStation.setDistrict(station.getDistrict());
            }

            return stationRepository.saveAndFlush(updatableStation);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No station with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод удаления записи об остановке по его номеру
     * @param id номер остановки, информацию о котором нужно удалить / скрыть
     */
    public void deleteStation(Long id) {
        try {
            Station station = stationRepository.findById(id).get();

            stationRepository.delete(station);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No station with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}