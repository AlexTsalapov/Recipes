package recipes.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.dto.UserDetailsImpl;
import recipes.entity.User;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
@AllArgsConstructor
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity registry(@Valid @RequestBody User user) {
        try {
            if (userService.registry(user)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}
