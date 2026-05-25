package ra.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "error_logs")
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(name = "exception_message", length = 1000)
    private String exceptionMessage;

    public ErrorLog() {}

    public ErrorLog(LocalDateTime timestamp, String methodName, String exceptionMessage) {
        this.timestamp = timestamp;
        this.methodName = methodName;
        this.exceptionMessage = exceptionMessage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }
    public String getExceptionMessage() { return exceptionMessage; }
    public void setExceptionMessage(String exceptionMessage) { this.exceptionMessage = exceptionMessage; }
}