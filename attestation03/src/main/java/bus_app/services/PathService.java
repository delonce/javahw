package bus_app.services;

import bus_app.dto.paths.PathRequestDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Path;
import bus_app.models.PathStation;
import bus_app.repositories.PathRepository;
import bus_app.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PathService {

    private final PathRepository pathRepository;
    private final StationRepository stationRepository;

    public List<Path> readAllPaths() {
        return pathRepository.findAll();
    }

    public Path addPath(PathRequestDto path) {
        if (path.getStations().size() != path.getTimesFromStartToStations().size()) {
            throw new IncorrectBodyException("Stations array size is not equals a size of times array!");
        }

        try {
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
        }
    }

    public Path updatepath(PathRequestDto path) {
        Path updatablePath;

        try {
            updatablePath = pathRepository.findById(path.getNumber()).get();
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No path with number: " + path.getNumber() + "!");
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
        }
    }

    public void deletePath(String number) {
        try {
            Path path = pathRepository.findById(number).get();

            pathRepository.delete(path);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No path with number: " + number + "!");
        }
    }
}