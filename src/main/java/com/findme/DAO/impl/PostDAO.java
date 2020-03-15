package com.findme.DAO.impl;

import com.findme.DAO.GenericDao;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.RelationshipType;
import com.findme.models.User;
import com.findme.models.UserTagged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class PostDAO implements GenericDao<Post> {

    private final String SQL_GETALLPOSTS = "From Post as rel where rel.userPagePosted.id = :idPage order by rel.datePosted ASC";
    private final String SQL_GETPOSTSOFUSER = "From Post as rel where rel.userPosted.id = :idUser order by rel.datePosted ASC";
    private final String SQL_GETPOSTOFFRIEND =
            "From Post as pos inner join Relationship as rel on rel.userFrom.id = :idUserFrom and rel.status = FRIENDS and pos.userPagePosted.id = :idOfUserPage " +
                    "order by pos.datePosted ASC";

    @Autowired
    private GeneralDAO<Post> generalDAO;

    @Autowired
    private GeneralDAO<UserTagged> userTaggedDAO;

    @Transactional
    public Post addPost(User userPosted, User userProfile, String text, List<User> userTagged) throws InternalServerError {
        Post post = new Post();
        post.setDatePosted(new Date());
        post.setMessage(text);
        post.setUserPosted(userPosted);
        post.setUserPagePosted(userProfile);

        try {
            save(post);

            for (User user : userTagged) {
                UserTagged userTagged1 = new UserTagged();
                userTagged1.setUserId(user.getId());
                userTagged1.setPostId(post.getId());
                userTaggedDAO.save(userTagged1);
            }

            return post;
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public List<Post> getAllpostsByQuery(Long pageId) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_GETALLPOSTS);
        query1.setParameter("idPage", pageId);
        return query1.getResultList();
    }

    public List<Post> getPostsByFriends(Long pageId) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_GETPOSTOFFRIEND);
        query1.setParameter("idOfUserPage", pageId);
        query1.setParameter("idUserFrom", pageId);
        return query1.getResultList();
    }

    public List<Post> getPostsByUser(Long userId) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_GETPOSTSOFUSER);
        query1.setParameter("idFrom", userId);
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
