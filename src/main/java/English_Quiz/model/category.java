package English_Quiz.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("category")
public class Category {
    @Id
    private Integer id;
    private String title;
}