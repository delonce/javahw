package org.delonce.homework12.service;

import org.delonce.homework12.model.Student;
import org.delonce.homework12.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public static boolean isEmail(String s) {
        return EMAIL.matcher(s).matches();
    }
}
