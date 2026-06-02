package ra.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProjectManagementController {

    @PostMapping("/projects")
    @PreAuthorize("hasRole('PO')")
    public String createProject() {
        return "Dự án mới đã được tạo bởi Product Owner.";
    }

    @PutMapping("/sprints/{id}")
    @PreAuthorize("hasRole('SM')")
    public String manageSprint(@PathVariable UUID id) {
        return "Sprint " + id + " đã được quản lý bởi Scrum Master.";
    }

    @PutMapping("/tasks/{taskId}/status")
    @PreAuthorize("(hasRole('DEV') or hasRole('QA')) and @taskService.isTaskOwner(#taskId, authentication.name)")
    public String updateTaskStatus(@PathVariable UUID taskId) {
        return "Trạng thái tác vụ " + taskId + " đã được cập nhật.";
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasRole('PO') or @taskService.getTaskCreator(#taskId) == authentication.name")
    public String deleteTask(@PathVariable UUID taskId) {
        return "Tác vụ " + taskId + " đã được xóa.";
    }
}