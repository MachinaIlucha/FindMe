package com.findme.controller;

import com.findme.DAO.impl.UserDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserDAO userDAO;


    @RequestMapping(path = "/addPost", method = RequestMethod.POST, produces = "text/plain")
    public String addPost(HttpSession session, @RequestParam("id") Long profileId, @RequestParam("text") String post, @RequestParam("userTagged") String userTagged) {

        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                postService.addPost(userFrom.getId(), profileId, post, userTagged);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException | BadRequestException e) {
                return "400Error";
            }
        } else return "login";

        return "ok";
    }

    @RequestMapping(path = "/PostsFriends", method = RequestMethod.GET)
    public String getPostByFriends(Model model, HttpSession session, @RequestParam("id") Long profileId) {
        User user;

        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                List<Post> posts = postService.getPostByFriends(userFrom.getId());
                user = userDAO.read(profileId);
                model.addAttribute("posts", posts);
                model.addAttribute("user", user);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException | BadRequestException e) {
                return "400Error";
            }
        } else return "login";

        return "profile";
    }

    @RequestMapping(path = "/PostsOfUser", method = RequestMethod.GET)
    public String getPostsOfUser(Model model, HttpSession session, @RequestParam("id") Long userId) {
        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                List<Post> posts = postService.getPostsOfUser(userId);
                model.addAttribute("posts", posts);
                model.addAttribute("user", userFrom);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException | BadRequestException e) {
                return "400Error";
            }
        } else return "login";

        return "profile";
    }

    /****/
    public List<Post> getAllPosts(User user) throws InternalServerError {
        List<Post> posts;
        try {
            posts = postService.getAllposts(user.getId());
        } catch (InternalServerError | BadRequestException e) {
            throw new InternalServerError("ServerError");
        }
        return posts;
    }
}