package app.dto;

import app.models.Grade;

public class GradeDto {

    public Integer id;
    public String name;
    public String startDate;
    public Boolean isActive;

    public GradeDto(Grade grade) {
        this.id = grade.getId();
        this.name = grade.getName();
        this.startDate = grade.getStartDate().toString();
        this.isActive = grade.getIsActive();
    }
}
