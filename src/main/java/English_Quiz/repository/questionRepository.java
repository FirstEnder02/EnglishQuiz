package English_Quiz.repository;
@Repository
public interface questionRepository extends crudRepository<Question,Integer> {

 List<Question> findByLevelId(Integer levelId);

}