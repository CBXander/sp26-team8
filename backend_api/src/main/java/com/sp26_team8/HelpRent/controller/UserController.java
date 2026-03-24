    package com.sp26_team8.HelpRent.controller;
    import java.util.List;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import com.sp26_team8.HelpRent.entity.User;
    import com.sp26_team8.HelpRent.service.UserService;

    @RestController
    @RequestMapping("/api/users")
    public class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        }

        @PutMapping("/{userId}")
        public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
            return ResponseEntity.ok(userService.updateUser(userId, updatedUser));
        }

        @GetMapping("/{userId}")
        public ResponseEntity<User> getUserById(@PathVariable Long userId) {
            return ResponseEntity.ok(userService.getUserById(userId));
        }

        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @DeleteMapping("/{userId}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        }
    }
