package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Contact;
import com.bfs.logindemo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ContactUsController {
    private final ContactService contactService;

    @Autowired
    public ContactUsController(ContactService contactUsService) {
        this.contactService = contactUsService;
    }

    @GetMapping("/contact")
    public String getContactpage() {
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String subject, @RequestParam String email,
                                @RequestParam String message, RedirectAttributes redirectAttributes) {
       if(subject.isEmpty() || email.isEmpty() || message.isEmpty()){
           redirectAttributes.addFlashAttribute("errorMessage", "All fields are required");
           return "redirect:/contact";
       }
       Contact contact = new Contact(subject, email, message);
       contactService.saveContact(contact);
       redirectAttributes.addFlashAttribute("successMessage", "Your message has been submitted successfully.");
       return "redirect:/home";
    }

    @GetMapping("/contact/view/{id}")
    public String viewContactMessage(@PathVariable("id") int id, Model model) {
        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact);
        return "view-contact-message";
    }

}
