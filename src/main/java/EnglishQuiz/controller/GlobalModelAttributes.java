package EnglishQuiz.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addAuthAttributes(HttpSession session, Model model) {
        Object currentUsername = session.getAttribute(AuthController.SESSION_USER_KEY);
        model.addAttribute("loggedIn", currentUsername != null);
        model.addAttribute("currentUsername", currentUsername);
    }
}
