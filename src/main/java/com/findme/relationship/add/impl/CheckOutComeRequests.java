package com.findme.relationship.add.impl;

import com.findme.DAO.impl.RelationshipDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.models.RelationshipType;
import com.findme.relationship.add.AddRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckOutComeRequests extends AddRelationship {

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Override
    public AddRelationship linkWith(AddRelationship next) {
        return super.linkWith(next);
    }

    @Override
    public boolean check(Long userIdFrom, Long userIdTo) {
        try {
            if (relationshipDAO.countRelationships(userIdFrom, RelationshipType.WAITING) > 10)
                throw new BadRequestException("BadRequestException");
        } catch (Exception Ie) {
            return false;
        }
        return checkNext(userIdFrom, userIdTo);
    }

    @Override
    public boolean checkNext(Long userIdFrom, Long userIdTo) {
        return super.checkNext(userIdFrom, userIdTo);
    }
}
