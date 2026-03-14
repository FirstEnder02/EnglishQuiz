package English_Quiz.model;
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