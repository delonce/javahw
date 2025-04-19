package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Класс web - контроллера получения клиентов html - страницы tasks.html
 * @author Danma
 * */
@Controller
public class PagesController {

    /**
     * Процедура, возвращающаяя динамическую страницу tasks.html с помощью инструментов thymeleaf
     * @return tasks.html - страницы, отображающая установаленные задания
     * */
    @GetMapping("/tasks")
    public String getProfilePage() {
        return "tasks";
    }
}