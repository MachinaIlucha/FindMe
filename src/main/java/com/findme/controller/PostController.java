package com.findme.controller;

import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;


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

    /****/
    public List<Post> getPostsOfUser(User user) throws InternalServerError {
        List<Post> posts;
        try {
            posts = postService.getPostsOfUser(user.getId());
        } catch (InternalServerError | BadRequestException e) {
            throw new InternalServerError("ServerError");
        }
        return posts;
    }
}
