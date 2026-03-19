package English_Quiz.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Table("question")
public class Question {
    @Id
    private Integer id;
    private Integer levelId;
    private String title;
    private String explaination;
    private String type;
    private String mediaUrl;

    @Transient
    private List<Answer> answers;

    public void setAnswers(List<Answer> answers) {
        if (answers == null) {
            this.answers = Collections.emptyList();
        } else {
            this.answers = new ArrayList<>(answers);
        }
    }
}
