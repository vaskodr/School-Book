package com.nbu.schoolbook.user;

import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateUserDTO> updateUser(@PathVariable("id") Long id,
                                                    @RequestBody UpdateUserDTO updateUserDTO) {
        UpdateUserDTO updatedUser = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User has been deleted successfully!");
    }

}
