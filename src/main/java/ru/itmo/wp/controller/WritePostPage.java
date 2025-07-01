package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;

    public WritePostPage(UserService userService) {
        this.userService = userService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") Post post,
                                @ModelAttribute("myTags") String myTags,
                                BindingResult bindingResult,
                                HttpSession httpSession,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }
        String[] newTags = myTags.split("\\s+");
        List<Tag> list = new ArrayList<>();
        for (String tag : newTags) {
            if (userService.findTagByName(tag) == null) {
                userService.addTag(tag);
            }
            if (!list.contains(userService.findTagByName(tag))) {
                list.add(userService.findTagByName(tag));
            }
        }
        userService.writePost(getUser(httpSession), post, list);
        putMessage(httpSession, "You published new post");

        return "redirect:/myPosts";
    }
}
