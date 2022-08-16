package iss.team5.vms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import iss.team5.vms.DTO.Account;
import iss.team5.vms.model.User;
import iss.team5.vms.services.AccountAuthenticateService;
import iss.team5.vms.services.UserSessionService;

@Controller
public class LoginController {
	@Autowired
	private AccountAuthenticateService accAuthService;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@GetMapping("/login")
    public String loadLoginPage(Model model) {
        Account account = new Account();
        model.addAttribute("account",account);
        model.addAttribute("repeatlogin", false);
        return "login";
    }
	
	@PostMapping(path = "/login/authenticate")
    public String login (@ModelAttribute("account") @Valid Account account, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "login";
        }
        User user = accAuthService.authenticateAccount(account);
        if(user == null) {
            model.addAttribute("account",account);
            model.addAttribute("repeatlogin", true);
            return "login";
        }
        userSessionService.setUserSession(user);        
        if(user.getRole().equals("ADMIN")) {
        	return "redirect:/admin/index";
        }
        else
        	return "redirect:/student/home";
    }

	@GetMapping("/logout")
    public String logout(Model model) {
		userSessionService.removeUserSession();
		Account account = new Account();
        model.addAttribute("account",account);
        return "login";
    }
}
