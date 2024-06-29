package com.nbu.schoolbook.user;

import com.nbu.schoolbook.school.dto.SchoolDTO;
import com.nbu.schoolbook.user.dto.RegisterDTO;
import com.nbu.schoolbook.user.dto.UpdateUserDTO;
import com.nbu.schoolbook.user.dto.UserDTO;
import com.nbu.schoolbook.user.dto.UserDetailsDTO;
import com.nbu.schoolbook.user.parent.ParentEntity;
import com.nbu.schoolbook.user.student.StudentEntity;
import com.nbu.schoolbook.user.teacher.TeacherEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateUserDTO> updateUser(@PathVariable("id") String id,
                                                    @RequestBody UpdateUserDTO updateUserDTO) {
        UpdateUserDTO updatedUser = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User has been deleted successfully!");
    }

//    @GetMapping("/school")
//    public ResponseEntity<SchoolDTO> getSchoolForUser(Principal principal) {
//        String userId = principal.getName(); // Assuming the username is the user ID
//        SchoolDTO school = userService.getSchoolByUserId(userId);
//        return ResponseEntity.ok(school);
//    }


}
