package ra.model.entity;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JacksonXmlRootElement(localName = "Item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
}