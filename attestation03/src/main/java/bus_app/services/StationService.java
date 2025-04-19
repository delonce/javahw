package bus_app.services;

import bus_app.dto.stations.StationDto;
import bus_app.exceptions.NoDataException;
import bus_app.models.Station;
import bus_app.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public List<Station> readAllStations() {
        return stationRepository.findAll();
    }

    public Station addStation(StationDto station) {
        Station newStation = new Station(
                null,
                station.getName(),
                station.getDistrict(),
                false
        );

        return stationRepository.saveAndFlush(newStation);
    }

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
        }
    }

    public void deleteStation(Long id) {
        try {
            Station station = stationRepository.findById(id).get();

            stationRepository.delete(station);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No station with id: " + id + "!");
        }
    }
}