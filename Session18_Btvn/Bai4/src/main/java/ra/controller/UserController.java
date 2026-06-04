package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.User;
import ra.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/role")
    public ResponseEntity<User> changeUserRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newRole = request.get("role");
        User updatedUser = userService.changeUserRole(id, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        return ResponseEntity.ok(currentUser);
    }
}