package EnglishQuiz.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "quiz_level")
public class Level {
    @Id
    private Integer id;
    private Integer categoryId;
    @Column(name = "level_name")
    private String levelName;
}