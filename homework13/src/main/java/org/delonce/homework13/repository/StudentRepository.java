package org.delonce.homework13.repository;

import org.delonce.homework13.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
