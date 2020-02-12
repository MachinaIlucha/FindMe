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
import com.findme.relationship.delete.CheckDaysToDelete;
import com.findme.relationship.delete.DeleteRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsRequestsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationshipDAO relationshipDAO;

    //Добавление
    public Relationship addRelationship(Long userIdFrom, Long userIdTo) throws BadRequestException, InternalServerError {
        /**Pattern Chain of Responsibility**/

        AddRelationship c1 = new CheckMaxListOfFriends();
        c1.setNextChain(new CheckOutComeRequests());

        if (!c1.dispense(userIdFrom, userIdTo))
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
    public void deleteRelationship(Long userIdFrom, Long userIdTo) throws BadRequestException, InternalServerError {
        /**Pattern Chain of Responsibility**/
        DeleteRelationship deleteRelationship = new CheckDaysToDelete();

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

    public void checkStatusRelationship(Long userIdFrom, Long userIdTo, RelationshipType status) throws InternalServerError {
        try {
            Relationship relationship = relationshipDAO.getRelationshipByQuery(userIdFrom, userIdTo);
            if (relationship != null) {
                if (relationship.getStatus() == RelationshipType.NEVERFRIENDS && status != RelationshipType.WAITING
                        || relationship.getStatus() == RelationshipType.NOFRIENDS && status != RelationshipType.WAITING
                        || relationship.getStatus() == RelationshipType.WAITING && status != RelationshipType.CANCELLED
                        || relationship.getStatus() == RelationshipType.FRIENDS && status != RelationshipType.NOFRIENDS
                        || relationship.getStatus() == RelationshipType.WAITING && status != RelationshipType.FRIENDS)
                    throw new BadRequestException("BadRequestException");
            }
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }
}
