package ra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class WishLog {
    private LocalDateTime timestamp;
    private String wishType;
    private String details;
    private String status;
    private String note;

    public WishLog(String wishType, String details, String status, String note) {
        this.timestamp = LocalDateTime.now();
        this.wishType = wishType;
        this.details = details;
        this.status = status;
        this.note = note;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getWishType() { return wishType; }
    public String getDetails() { return details; }
    public String getStatus() { return status; }
    public String getNote() { return note; }
}