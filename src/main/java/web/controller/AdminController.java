package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/admin/people")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String home(Model model){
        model.addAttribute("list",userService.getAllUsers());
        return "home";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("person", userService.getById(id));
        return "show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new User());
        return "new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") User user){
        userService.save(user);
        return "redirect:/admin/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id){
        model.addAttribute("person",userService.getById(id));
        return "edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") User user, @PathVariable("id") Long id){
        userService.edit(id, user);
        return "redirect:/admin/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin/people";
    }
}
