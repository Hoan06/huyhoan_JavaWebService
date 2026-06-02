package ra.service;

import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    public boolean isCommentOwner(Long commentId, String username) {
        return "player1".equals(username) && commentId == 99L;
    }

    public boolean hasItemBalance(String username, String itemCode) {
        return "player1".equals(username) && "SWORD_01".equals(itemCode);
    }
}