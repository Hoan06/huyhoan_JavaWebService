package ra.model.entity;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class Student {
    private String stuId;
    private String stuName;
    private double score;

    public Student(String stuId, String stuName, double score) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.score = score;
    }

    public Student() {
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
