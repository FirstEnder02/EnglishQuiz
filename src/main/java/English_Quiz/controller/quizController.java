package English_Quiz.controller;
@Controller
public class quizController {

@GetMapping("/quiz/{levelId}")
public String quiz(@PathVariable int levelId, Model model){

 model.addAttribute("questions", quizService.getQuiz(levelId));

 return "quiz";
}
}