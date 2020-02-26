package com.findme.DAO.impl;

import com.findme.DAO.GenericDao;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class PostDAO implements GenericDao<Post> {

    private final String SQL_getPostsOfUser = "From Post as rel where rel.userPagePosted.id = :idPage";

    @Autowired
    private GeneralDAO<Post> generalDAO;

    public Post addPost(User userPosted, User userProfile, String text, List<User> userTagged) throws InternalServerError {
        Post post = new Post();
        post.setDatePosted(new Date());
        post.setMessage(text);
        post.setUserPosted(userPosted);
        post.setUserPagePosted(userProfile);
        post.setUsersTagged(userTagged);

        try {
            return generalDAO.save(post);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public List<Post> getPostsOfUserByQuery(Long pageId) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_getPostsOfUser);
        query1.setParameter("idPage", pageId);
        return query1.getResultList();
    }

    @Override
    public Post save(Post t) throws InternalServerError {
        try {
            return generalDAO.save(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public Post read(Long id) throws InternalServerError {
        try {
            generalDAO.seteClass(Post.class);
            return generalDAO.read(id);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public Post update(Post t) throws InternalServerError {
        try {
            return generalDAO.update(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public void delete(Post t) throws InternalServerError {
        try {
            generalDAO.delete(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }
}
