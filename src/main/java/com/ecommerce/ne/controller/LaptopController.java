package com.ecommerce.ne.controller;

import com.ecommerce.ne.dto.LaptopDTO;
import javax.validation.Valid;

import com.ecommerce.ne.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ne.entity.Laptop;
import com.ecommerce.ne.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.ne.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping
    public List<Laptop> getAllLaptops() {
        return laptopService.getAllLaptops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptopById(@PathVariable int id) {
        return laptopService.getLaptopById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping("/add")
    public ResponseEntity<?> createLaptop(@Valid @RequestBody LaptopDTO laptopDTO, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                sb.append(error.getDefaultMessage()).append(", ");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }

        try {
            // Create and save the laptop
            Laptop createdLaptop = laptopService.createLaptop(laptopDTO);

            // Return the saved laptop with HTTP status CREATED
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLaptop);
        } catch (ResourceNotFoundException e) {
            // Handle specific exception (e.g., student not found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle generic exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add laptop: " + e.getMessage());
        }
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<Laptop> updateLaptop(@PathVariable int id, @RequestBody Laptop laptopDetails) {
        try {
            return ResponseEntity.ok(laptopService.updateLaptop(id, laptopDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLaptop(@PathVariable int id) {
        laptopService.deleteLaptop(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok()
                .body(new MessageResponse("You've been signed out!"));
    }
}
