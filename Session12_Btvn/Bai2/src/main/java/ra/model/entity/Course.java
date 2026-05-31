package ra.model.entity;

public class Course {
    private Long id;
    private String courseName;
    private String instructor;
    private Integer durationHours;
    private Double fee;

    public Course() {}

    public Course(Long id, String courseName, String instructor, Integer durationHours, Double fee) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.durationHours = durationHours;
        this.fee = fee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }

    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName='" + courseName + '\'' + ", instructor='" + instructor + '\'' + '}';
    }
}