package English_Quiz.model;
@Data
@Table("question")
public class question {

 @Id
 private Integer id;

 private Integer levelId;

 private String title;

 private String explaination;

 private String type;

 private String mediaUrl;
}