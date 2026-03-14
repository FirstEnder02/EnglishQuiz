package English_Quiz.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("level")
public class level {
    @Id
    private Integer id;
    private Integer categoryId;
    private String level;
}