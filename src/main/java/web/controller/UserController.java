package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.UserDetailsServiceImpl;

import java.security.Principal;


@Controller
@RequestMapping("")
public class UserController {
	private UserDetailsServiceImpl userService;

	@Autowired
	public void setUserService(UserDetailsServiceImpl userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/login")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/admin")
	public String pageForAdmin(Principal principal){
		return "admin";
	}

	@GetMapping("/user")
	public String pageForUser(Principal principal, Model model){
		UserDetails user = userService.loadUserByUsername(principal.getName());
		model.addAttribute("person",user);
		return "show"  ;
	}

}
