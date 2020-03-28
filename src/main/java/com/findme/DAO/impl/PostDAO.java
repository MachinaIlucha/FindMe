package com.findme.DAO.impl;

import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.User;
import com.findme.models.UserTagged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class PostDAO extends GeneralDAO<Post> {

    private final String SQL_GETALLPOSTS = "From Post as rel where rel.userPagePosted.id = :idPage order by rel.datePosted ASC";
    private final String SQL_GETPOSTSOFUSER = "From Post as rel where rel.userPosted.id = :idUser order by rel.datePosted ASC";
    private final String SQL_GETPOSTOFFRIEND =
            "From Post as pos inner join Relationship as rel on rel.userFrom.id = :idUserFrom and rel.status = FRIENDS and pos.userPagePosted.id = :idOfUserPage " +
                    "order by pos.datePosted ASC";


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
        Query query1 = getEntityManager().createQuery(SQL_GETALLPOSTS);
        query1.setParameter("idPage", pageId);
        return query1.getResultList();
    }

    public List<Post> getPostsByFriends(Long pageId) {
        Query query1 = getEntityManager().createQuery(SQL_GETPOSTOFFRIEND);
        query1.setParameter("idOfUserPage", pageId);
        query1.setParameter("idUserFrom", pageId);
        return query1.getResultList();
    }

    public List<Post> getPostsByUser(Long userId) {
        Query query1 = getEntityManager().createQuery(SQL_GETPOSTSOFUSER);
        query1.setParameter("idFrom", userId);
        return query1.getResultList();
    }
}
