package app.services;

import app.clients.StudentClient;
import app.models.Grade;
import app.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentMessageService {

    private final StudentClient studentClient;
    private final JavaMailSender sender;

    public void sendMessagesAbout(List<Grade> grades) {
        for (Grade grade: grades) {
            studentClient.getStudentsById(grade.getId()).forEach(student ->
                    sendMessageToStudent(student, grade));
        }
    }

    public void sendMessageToStudent(Student student, Grade grade) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("student_test_app");
        message.setTo(student.getEmail());
        message.setSubject("Grade was started today!");
        message.setText(String.format("""
                        Greetings, %s!
                        Today is %s and we are informing you about the start of the new grade: '%s'!
                        Good luck and have a good learning process!""",
                student.getFullName(), LocalDate.now(), grade.getName()));

        sender.send(message);
    }
}