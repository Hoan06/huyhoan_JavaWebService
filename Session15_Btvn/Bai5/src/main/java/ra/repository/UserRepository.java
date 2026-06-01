package ra.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, String> database = new HashMap<>();

    public boolean existsByUsername(String username) {
        return database.containsKey(username);
    }

    public void saveUser(String username, String encodedPassword) {
        database.put(username, encodedPassword);
    }

    public String findPasswordByUsername(String username) {
        return database.get(username);
    }
}