package com.findme.service;

import com.findme.DAO.impl.RelationshipDAO;
import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import com.findme.models.User;
import com.findme.relationship.add.AddRelationship;
import com.findme.relationship.add.impl.CheckMaxListOfFriends;
import com.findme.relationship.add.impl.CheckOutComeRequests;
import com.findme.relationship.checkStatusRelationship.CheckStatus;
import com.findme.relationship.checkStatusRelationship.impl.CheckStatusFRIENDS;
import com.findme.relationship.checkStatusRelationship.impl.CheckStatusNEVERFRIENDS;
import com.findme.relationship.checkStatusRelationship.impl.CheckStatusNOFRIENDS;
import com.findme.relationship.checkStatusRelationship.impl.CheckStatusWAITING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FriendsRequestsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Autowired
    private CheckMaxListOfFriends c2;

    @Autowired
    private CheckOutComeRequests c3;

    //Добавление
    public Relationship addRelationship(Long userIdFrom, Long userIdTo) throws BadRequestException, InternalServerError {
        /**Pattern Chain of Responsibility**/

        c2.linkWith(c3);

        if (!c2.check(userIdFrom, userIdTo))
            throw new BadRequestException("BadRequestException");

        relationshipDAO.checkRelationship(userIdFrom, userIdTo);
        User userFrom = userService.read(userIdFrom);
        User userTo = userService.read(userIdTo);
        if (userService.read(userIdFrom) == null || userService.read(userIdTo) == null)
            throw new BadRequestException("BadRequestException");
        return relationshipDAO.addRelationship(userFrom, userTo);
    }

    //Обновление
    public void updateRelationship(Long userIdFrom, Long userIdTo, RelationshipType status) throws BadRequestException, InternalServerError {
        checkStatusRelationship(userIdFrom, userIdTo, status);
        if (userService.read(userIdFrom) == null || userService.read(userIdTo) == null)
            throw new BadRequestException("BadRequestException");
        relationshipDAO.updateRelationship(userIdFrom, userIdTo, status);
    }

    //Удаление
    public void deleteRelationship(Long userIdFrom, Long userIdTo) throws InternalServerError {
        Relationship relationship = relationshipDAO.getRelationshipByQuery(userIdFrom, userIdTo);
        int days = (int) ((new Date().getTime() - relationship.getDateCreated().getTime()) / (1000 * 60 * 60 * 24));
        try {
            if (userService.read(userIdFrom) == null || userService.read(userIdTo) == null)
                if (relationship == null)
                    if (days < 3)
                        throw new BadRequestException("BadRequestException");
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
        relationshipDAO.delete(relationshipDAO.getRelationshipByQuery(userIdFrom, userIdTo));
    }

    //Входящие
    public List<Relationship> getIncomeRequests(Long userIdTo) throws BadRequestException, InternalServerError {
        if (userService.read(userIdTo) != null)
            return relationshipDAO.getIncomeRequests(userIdTo);
        else throw new BadRequestException("BadRequestException");
    }

    //Исходящие
    public List<Relationship> getOutcomeRequests(Long userIdTo) throws BadRequestException, InternalServerError {
        if (userService.read(userIdTo) != null)
            return relationshipDAO.getOutcomeRequests(userIdTo);
        else throw new BadRequestException("BadRequestException");
    }

    private void checkStatusRelationship(Long userIdFrom, Long userIdTo, RelationshipType status) throws InternalServerError {
        try {
            Relationship relationship = relationshipDAO.getRelationshipByQuery(userIdFrom, userIdTo);

            CheckStatus checkStatus = new CheckStatusFRIENDS();
            checkStatus.linkWith(new CheckStatusNEVERFRIENDS())
                    .linkWith(new CheckStatusNOFRIENDS())
                    .linkWith(new CheckStatusWAITING());

            if (!checkStatus.check(status, relationship))
                throw new BadRequestException("BadRequestException");
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }
}
