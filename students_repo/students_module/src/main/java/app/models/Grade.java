package app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Grade {

    private Integer id;
    private String name;
    private LocalDate startDate;
    private Boolean isActive;
}