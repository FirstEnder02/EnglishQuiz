package English_Quiz.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("answer")
public class answer {
    @Id
    private Integer id;
    private Integer questionId;
    private String label;
    private String text;
    private boolean isCorrect;
}