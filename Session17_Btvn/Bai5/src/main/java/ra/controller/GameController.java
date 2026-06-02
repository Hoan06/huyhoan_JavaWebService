package ra.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    @PostMapping("/moderator/games")
    @Secured("ROLE_GAME_MODERATOR")
    public String createGame() {
        return "Game mới đã được thêm vào hệ thống.";
    }

    @DeleteMapping("/moderator/games/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_GAME_MODERATOR"})
    public String deleteGame(@PathVariable Long id) {
        return "Game mang ID " + id + " đã được xóa.";
    }
}
