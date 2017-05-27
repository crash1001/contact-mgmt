package com.example.contactmgr.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.contactmgr.model.User;
import com.example.contactmgr.service.UserService;
import com.example.contactmgr.web.FlashMessage;

@Controller
public class UserController {
    @Autowired 
    private UserService userService;
    
    //Home Page
    @RequestMapping("/")
    public String listUsers(Model model) {
	List<User> users = userService.findAll();
	model.addAttribute("users", users);
	return "user/index";	
    }
    
    //Single Contact details
    @RequestMapping("/user/{userId}")
    public String userDetails(@PathVariable Long userId, Model model) {
	User user = userService.findById(userId);
	model.addAttribute("user", user);
	return "user/details";
    }
    
    //GEt image data
    @RequestMapping("/users/{userId}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long userId) {
	return userService.findById(userId).getProfilePicture();
    }
    
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
	List<User> users = userService.findByFirstName(q);
	model.addAttribute("users", users);
	return "user/index";
    }
    
    //Upload a new User
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String addUser(@Valid User user, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
	userService.save(user, file);
	redirectAttributes.addFlashAttribute("flash", new FlashMessage("Contact Sucessfully Uploaded!!!", FlashMessage.Status.SUCCESS));
	
	return String.format("redirect:/user/%s", user.getId());	
    }
    
    //Form for uploading new user
    @RequestMapping("/upload")
    public String newUserForm(Model model) {
	if(!model.containsAttribute("user")) {
	    model.addAttribute("user", new User());
	}
	
	model.addAttribute("action", "/users");
	model.addAttribute("heading", "Upload");
	model.addAttribute("submit", "Add");
	return "user/form";
    }
    
    //Form for editing an existing user
    @RequestMapping("/user/{userId}/edit")
    public String newUserForm(@PathVariable Long userId, Model model) {
	if(!model.containsAttribute("user")) {
	    model.addAttribute("user",userService.findById(userId));
	}
	
	model.addAttribute("action", String.format("/user/%s", userId));
	model.addAttribute("heading", "Edit");
	model.addAttribute("submit", "Update");
	return "user/form";
    }
    
    //Update an exiting user
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
    public String updateGif(User user, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
         userService.save(user,file);

        redirectAttributes.addFlashAttribute("flash",new FlashMessage("User Contact successfully updated!", FlashMessage.Status.SUCCESS));

        return String.format("redirect:/user/%s",user.getId());
}
    
    //Delete a user
    @RequestMapping(value = "user/{userId}/delete", method = RequestMethod.POST)
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
	User user = userService.findById(userId);
	userService.delete(user);
	redirectAttributes.addFlashAttribute("flash",new FlashMessage("User Contact Deleted", FlashMessage.Status.SUCCESS));
	return "redirect:/";
    }
    
}
