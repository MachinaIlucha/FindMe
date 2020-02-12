package com.findme.relationship.add.impl;

import com.findme.DAO.impl.RelationshipDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.models.RelationshipType;
import com.findme.relationship.add.AddRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckOutComeRequests implements AddRelationship {

    private AddRelationship chain;

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Override
    public void setNextChain(AddRelationship nextChain) {
        this.chain = nextChain;
    }

    @Override
    public boolean dispense(Long userIdFrom, Long userIdTo) {
        try {
            if (relationshipDAO.countRelationships(userIdFrom, RelationshipType.WAITING) > 10)
                throw new BadRequestException("BadRequestException");
        } catch (Exception Ie) {
            return false;
        }
        return this.chain.dispense(userIdFrom, userIdTo);
    }
}
