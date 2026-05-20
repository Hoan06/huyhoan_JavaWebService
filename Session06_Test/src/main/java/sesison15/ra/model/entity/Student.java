package sesison15.ra.model.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long stuId;
    @Column(name = "full_name" , length = 100 , nullable = false)
    private String fullName;
    @Column(length =  100 , nullable = false , unique = true)
    private String email;
    @Column(nullable = false)
    private Double gpa;
}
