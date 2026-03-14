package English_Quiz.controller;
@Controller
public class levelController {

@GetMapping("/category/{id}")
public String levels(@PathVariable int id, Model model){

 model.addAttribute("levels", levelRepo.findByCategoryId(id));

 return "levels";
}
}