package EnglishQuiz.controller;

import EnglishQuiz.model.UserAccount;
import EnglishQuiz.repository.UserAccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthController {
    public static final String SESSION_USER_KEY = "currentUsername";

    private final UserAccountRepository userAccountRepository;

    public AuthController(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (isLoggedIn(session)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String redirect,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        String normalizedUsername = username == null ? "" : username.trim();
        if (normalizedUsername.isBlank() || password == null || password.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Username and password are required.");
            return buildLoginRedirect(redirect);
        }

        UserAccount user = userAccountRepository.findByUsernameIgnoreCase(normalizedUsername).orElse(null);
        if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return buildLoginRedirect(redirect);
        }

        session.setAttribute(SESSION_USER_KEY, user.getUsername());
        redirectAttributes.addFlashAttribute("success", "Login successful.");
        if (isSafeLocalRedirect(redirect)) {
            return "redirect:" + redirect;
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (isLoggedIn(session)) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        String normalizedUsername = username == null ? "" : username.trim();
        if (!isValidUsername(normalizedUsername)) {
            redirectAttributes.addFlashAttribute("error", "Username must be 3-30 chars and only letters, numbers, underscore.");
            return "redirect:/register";
        }
        if (password == null || password.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters.");
            return "redirect:/register";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Confirm password does not match.");
            return "redirect:/register";
        }
        if (userAccountRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists.");
            return "redirect:/register";
        }

        UserAccount account = new UserAccount();
        account.setUsername(normalizedUsername);
        account.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        userAccountRepository.save(account);

        session.setAttribute(SESSION_USER_KEY, account.getUsername());
        redirectAttributes.addFlashAttribute("success", "Account created and logged in.");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(SESSION_USER_KEY) != null;
    }

    private boolean isValidUsername(String username) {
        return username != null && username.matches("^[A-Za-z0-9_]{3,30}$");
    }

    private boolean isSafeLocalRedirect(String redirect) {
        return redirect != null && redirect.startsWith("/") && !redirect.startsWith("//");
    }

    private String buildLoginRedirect(String redirect) {
        if (isSafeLocalRedirect(redirect)) {
            String encoded = URLEncoder.encode(redirect, StandardCharsets.UTF_8);
            return "redirect:/login?redirect=" + encoded;
        }
        return "redirect:/login";
    }
}
