package app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {

    private Integer id;
    private Integer studentId;
    private Integer gradeId;
    private String text;
}