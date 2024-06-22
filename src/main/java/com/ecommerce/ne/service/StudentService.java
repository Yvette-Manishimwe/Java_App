package com.ecommerce.ne.service;

import com.ecommerce.ne.entity.Laptop;
import com.ecommerce.ne.entity.Student;
import com.ecommerce.ne.entity.UserData;
import com.ecommerce.ne.exception.ResourceNotFoundException;
import com.ecommerce.ne.repository.StudentRepository;
import com.ecommerce.ne.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;
    @Autowired
    private UserDataRepository userDataRepository;
    @Transactional
    public String addStudent(Student student) {
        // Fetch the UserData entity to ensure it is managed
        UserData createdBy = userDataRepository.findById(student.getCreated().getId())
                .orElseThrow(() -> new IllegalArgumentException("UserData not found"));

        // Set the managed UserData entity
        student.setCreated(createdBy);

        // Save the student entity
        repo.save(student);

        return "Student Added Successfully";
    }
    @Transactional
    public List<Student> getAllStudentsSortedByName() {
        // Fetch all students sorted by name
        return repo.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
    }
    @Transactional
    public Student updateStudent(int id, Student studentDetails) {
        Optional<Student> optionalStudent = repo.findById(id);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            return repo.save(student);
        } else {
            throw new ResourceNotFoundException("Laptop not found with id " + id);
        }
    }

    @Transactional
    public String deleteStudent(int studentId) {
        // Fetch the existing student entity
        Student studentToDelete = repo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Delete the student entity
        repo.delete(studentToDelete);

        return "Student Deleted Successfully";
    }
}


