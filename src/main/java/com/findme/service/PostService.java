package com.findme.service;


import com.findme.DAO.impl.GeneralDAO;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private GeneralDAO generalDAO;

    public Post save(Post post) {
        return (Post) generalDAO.save(post);
    }

    public Post read(Long id) {
        return (Post) generalDAO.read(id);
    }

    public Post update(Post post) {
        return (Post) generalDAO.update(post);
    }

    public void delete(Post post) {
        generalDAO.delete(post);
    }
}
