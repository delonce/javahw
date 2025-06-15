package bus_app.services;

import bus_app.dto.paths.PathRequestDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Path;
import bus_app.models.PathStation;
import bus_app.repositories.PathRepository;
import bus_app.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, предоставляющий интерфейс для обработки полученных через MVC данных от клиента
 * и получения / модификации записей о путях следования
 */
@Service
@RequiredArgsConstructor
public class PathService {

    /**
     * Экземпляр класса для общения со схемой путей следования в БД
     */
    private final PathRepository pathRepository;

    /**
     * Экземпляр класса для общения со схемой остановок в БД
     */
    private final StationRepository stationRepository;

    /**
     * Метод получения списка всех путей
     * @return список всех записей о путях
     */
    public List<Path> readAllPaths() {
        return pathRepository.findAll();
    }

    /**
     * Метод создания новой записи о путе следования
     * @param path объект, содержащий основную информацию для создания новой записи
     * @return объект с информацией о новом пути следования из БД
     */
    public Path addPath(PathRequestDto path) {
        try {
            pathRepository.returnToListById(path.getNumber());

            if (pathRepository.findById(path.getNumber()).isPresent()) {
                return updatePath(path);
            }

            if (path.getStations().size() != path.getTimesFromStartToStations().size()) {
                throw new IncorrectBodyException("Stations array size is not equals a size of times array!");
            }

            Path newPath = new Path(
                    path.getNumber(),
                    stationRepository.findById(path.getBeginStation()).get(),
                    stationRepository.findById(path.getEndStation()).get(),
                    null,
                    path.getDuration(),
                    false
            );

            List<PathStation> pathsStations = new ArrayList<>();

            for (int i = 0; i < path.getStations().size(); i++) {
                pathsStations.add(new PathStation(
                        newPath,
                        stationRepository.findById(path.getStations().get(i)).get(),
                        path.getTimesFromStartToStations().get(i)
                ));
            }

            newPath.setStations(pathsStations);

            return pathRepository.saveAndFlush(newPath);
        } catch (NoSuchElementException ex) {
            throw new IncorrectBodyException("Wrong create data!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод модификации записи о пути следования в БД
     * @param path объект, содержащий основную информацию для модификации записи
     * @return объект с информацией о модифицированном департаменте из БД
     */
    public Path updatePath(PathRequestDto path) {
        Path updatablePath;

        try {
            updatablePath = pathRepository.findById(path.getNumber()).get();
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No path with number: " + path.getNumber() + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }

        try {
            if (path.getBeginStation() != null) {
                updatablePath.setBeginStation(
                        stationRepository.findById(path.getBeginStation()).get());
            }
            if (path.getEndStation() != null) {
                updatablePath.setEndStation(
                        stationRepository.findById(path.getEndStation()).get());
            }
            if (path.getStations() == null && path.getTimesFromStartToStations() != null
                    || path.getStations() != null && path.getTimesFromStartToStations() == null) {
                throw new IncorrectBodyException("Need to provide stations and times info!");
            }
            if (path.getStations() != null) {
                if (path.getStations().size() != path.getTimesFromStartToStations().size()) {
                    throw new IncorrectBodyException("Stations array size is not equals a size of times array!");
                }

                List<PathStation> pathsStations = new ArrayList<>();

                for (int i = 0; i < path.getStations().size(); i++) {
                    pathsStations.add(new PathStation(
                            updatablePath,
                            stationRepository.findById(path.getStations().get(i)).get(),
                            path.getTimesFromStartToStations().get(i)
                    ));
                }

                updatablePath.setStations(pathsStations);
            }
            if (path.getDuration() != null) {
                updatablePath.setDuration(path.getDuration());
            }

            return pathRepository.saveAndFlush(updatablePath);
        } catch (NoSuchElementException ex) {
            throw new IncorrectBodyException("Wrong update data!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }


    /**
     * Метод удаления записи о пути по его номеру
     * @param number номер пути, информацию о котором нужно удалить / скрыть
     */
    public void deletePath(String number) {
        try {
            Path path = pathRepository.findById(number).get();

            pathRepository.delete(path);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No path with number: " + number + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}