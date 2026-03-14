package English_Quiz.controller;
@Controller
public class categoryController {

 @Autowired
 categoryRepository repo;

 @GetMapping("/")
 public String categories(Model model){

  model.addAttribute("categories", repo.findAll());

  return "index";
 }
}