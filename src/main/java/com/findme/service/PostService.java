package com.findme.service;

import com.findme.DAO.impl.PostDAO;
import com.findme.DAO.impl.RelationshipDAO;
import com.findme.DAO.impl.UserDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Autowired
    private UserDAO userDAO;

    public Post addPost(Long userPosted, Long userProfile, String text, String userTagged) throws InternalServerError, BadRequestException {
        Relationship relationship = relationshipDAO.getRelationshipByQuery(userPosted, userProfile);

        List<User> usersTagged = userDAO.getUserTagged(userTagged);

        if (relationship != null && relationship.getStatus() == RelationshipType.FRIENDS)
            return postDAO.addPost(userDAO.read(userPosted), userDAO.read(userProfile), text, usersTagged);
        else throw new BadRequestException("BadRequestException");
    }

    public List<Post> getPostsOfUser(Long pageId) throws BadRequestException, InternalServerError {
        if (userDAO.read(pageId) != null)
            return postDAO.getPostsOfUserByQuery(pageId);
        else throw new BadRequestException("BadRequestException");
    }
}
