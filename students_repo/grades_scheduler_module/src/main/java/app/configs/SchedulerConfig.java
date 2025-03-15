package app.configs;

import app.clients.GradeClient;
import app.clients.StudentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Value("${grade.client.url}")
    private String gradeUrl;

    @Value("${student.client.url}")
    private String studentUrl;

    @Value("${google.email}")
    private String email;

    @Value("${google.password}")
    private String password;

    @Bean
    public GradeClient getGradeClient() {
        return new GradeClient(gradeUrl);
    }

    @Bean
    public StudentClient getStudentClient() {
        return new StudentClient(studentUrl);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}