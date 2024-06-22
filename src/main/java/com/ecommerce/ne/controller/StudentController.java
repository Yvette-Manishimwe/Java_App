package com.ecommerce.ne.controller;

import com.ecommerce.ne.entity.Laptop;
import com.ecommerce.ne.entity.Student;
import com.ecommerce.ne.entity.UserData;
import com.ecommerce.ne.exception.ResourceNotFoundException;
import com.ecommerce.ne.service.JwtService;
import com.ecommerce.ne.service.StudentService;
import com.ecommerce.ne.service.UserDataService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academics")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserDataService userServices;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/registration")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addStudent(@Valid @RequestBody Student student, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            // If validation errors exist, return Bad Request with error details
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                sb.append(error.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null) {
            UserData info = userServices.loadCurrentUser(username);
            student.setCreated(info);
        } else {
            return ResponseEntity.status(401).body("Unauthorized: Cannot extract user information");
        }

        try {
            studentService.addStudent(student);
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding the student: " + e.getMessage());
        }
    }



    @GetMapping("/sortedByName")
    public ResponseEntity<List<Student>> getAllStudentsSortedByName() {
        List<Student> students = studentService.getAllStudentsSortedByName();
        return ResponseEntity.ok(students);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails ) {
        try {
            return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        String message = studentService.deleteStudent(id);
        return ResponseEntity.ok(message);
    }
}

