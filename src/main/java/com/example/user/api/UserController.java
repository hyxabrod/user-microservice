package com.example.user.api;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.ErrorResponse;
import com.example.user.UserDto;
import com.example.user.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@NonNull @PathVariable Long id) {
        try {
            return repository.findById(id)
                    .<ResponseEntity<?>>map(user -> ResponseEntity.ok(UserDto.fromEntity(user)))
                    .orElseGet(()
                            -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse("User not found"))
                    );
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Database access error"));
        }
    }
}
