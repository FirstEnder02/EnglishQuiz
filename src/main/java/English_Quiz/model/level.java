package English_Quiz.model;
@Data
@Table("level")
public class level {

 @Id
 private Integer id;

 private Integer categoryId;

 private String level;
}