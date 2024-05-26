package com.nbu.schoolbook.user;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<RegisterDTO> createUser(@RequestBody RegisterDTO registerDTO) {
        RegisterDTO savedUser = userService.createUser(registerDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//
//    }


}
