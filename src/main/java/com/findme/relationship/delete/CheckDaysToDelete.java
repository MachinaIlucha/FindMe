package com.findme.relationship.delete;

import com.findme.DAO.impl.RelationshipDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class CheckDaysToDelete extends DeleteRelationship {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Override
    public boolean check(Long userIdFrom, Long userIdTo) {
        Relationship relationship = relationshipDAO.getRelationshipByQuery(userIdFrom, userIdTo);
        int days = (int) ((new Date().getTime() - relationship.getDateCreated().getTime()) / (1000 * 60 * 60 * 24));
        try {
            if (userService.read(userIdFrom) == null || userService.read(userIdTo) == null)
                if (relationship == null)
                    if (days < 3)
                        throw new BadRequestException("BadRequestException");
        } catch (Exception e) {
            return false;
        }
        return checkNext(userIdFrom, userIdTo);
    }

}
