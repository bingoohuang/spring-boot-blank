package com.github.bingoohuang.springbootbank.demo;

import com.github.bingoohuang.blackcat.javaagent.annotations.BlackcatCreateTransformedClassFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@BlackcatCreateTransformedClassFile
public class MessageController {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping("/")
    public ModelAndView list() {
        System.err.println("Nano:" + System.currentTimeMillis());
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "messages/hello";
    }

    @RequestMapping("/id/{id}")
    public ModelAndView view(@PathVariable("id") Message message) {
        return new ModelAndView("messages/view", "message", message);
    }

    @RequestMapping(value = "/", params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Message message) {
        return "messages/form";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView create(@Valid Message message, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("messages/form", "formErrors", result.getAllErrors());
        }
        message = this.messageRepository.save(message);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
        return new ModelAndView("redirect:/id/{message.id}", "message.id", message.getId());
    }

    @ResponseBody
    @RequestMapping("/rest")
    public String rest() {
        return "rest response body";
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

}