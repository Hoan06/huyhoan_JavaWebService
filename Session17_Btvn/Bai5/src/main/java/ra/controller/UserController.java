package ra.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @PutMapping("/tasks/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GAME_MODERATOR') or @securityService.isCommentOwner(#commentId, authentication.name)")
    public String editComment(@PathVariable Long commentId) {
        return "Bình luận " + commentId + " đã được chỉnh sửa xử lý thành công.";
    }

    @PostMapping("/player/purchase")
    @PreAuthorize("hasRole('PLAYER') and @securityService.hasItemBalance(authentication.name, #itemCode)")
    public String purchaseItem(@RequestParam String itemCode) {
        return "Mua thành công vật phẩm mã: " + itemCode;
    }
}
