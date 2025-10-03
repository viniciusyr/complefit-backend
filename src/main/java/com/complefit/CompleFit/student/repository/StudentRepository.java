package com.complefit.CompleFit.student.repository;

import com.complefit.CompleFit.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
