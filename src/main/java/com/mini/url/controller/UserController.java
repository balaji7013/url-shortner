import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    // @GetMapping("/{id}")
    // public ResponseEntity<User> getUser(@PathVariable Long userId)
    // {
    //     return ResponseEntity.status(HttpStatus.OK).body(user);
    // }
}
