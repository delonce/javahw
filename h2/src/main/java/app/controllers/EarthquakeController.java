package app.controllers;

import app.models.entities.EarthquakeEntity;
import app.repositories.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/earthquakes")
public class EarthquakeController {

    private final EarthquakeRepository repository;

    @GetMapping("/mag")
    public List<EarthquakeEntity> getRecordsWithMagAfter(@RequestParam("mag") Double mag) {
        return repository.findByMagAfter(mag);
    }

    @GetMapping("/time")
    public List<EarthquakeEntity> getRecordsWithTimeBetween(
            @RequestParam("start")LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {

        return repository.findByTimeBetween(start, end);
    }
}