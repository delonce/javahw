package app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto - класс, через который осуществляется поступление информации
 * о новом задании
 * @author Danma
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAddRequestDto {

    /** Поле, содержащее имя нового задания */
    private String name;

    /** Поле, содержащее описание нового задания */
    private String description;
}