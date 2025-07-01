package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }


    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String post(@PathVariable String id, @Valid @ModelAttribute("comment") Comment comment,
                       BindingResult bindingResult,
                       HttpSession httpSession,
                       Model model) {
        Post post;
        if (bindingResult.hasErrors()) {
            try {
                post = postService.findById(Long.parseLong(id));
            } catch (Exception e) {
                putMessage(httpSession, "No such post");
                return "redirect:";
            }
            model.addAttribute("post", post);
            return "PostPage";
        }

        try {
            post = postService.findById(Long.parseLong(id));
        } catch (Exception e) {
            putMessage(httpSession, "No such post");
            return "redirect:";
        }

        postService.writeComment(post, getUser(httpSession), comment);
        putMessage(httpSession, "You published new comment");
        return "redirect:/post/" + id;
    }

    @PostMapping("/post/")
    public String post(Model model) {
        model.addAttribute("post", null);
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id, Model model, HttpSession httpSession) {
        try {
            Post post = postService.findById(Long.valueOf(id));
            model.addAttribute("post", post);
            if (getUser(httpSession) != null) {
                model.addAttribute("comment", new Comment());
            }
        } catch (NumberFormatException e) {
            model.addAttribute("post", null);
        }
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/post/")
    public String postGet(Model model) {
        model.addAttribute("post", null);
        return "PostPage";
    }

}
