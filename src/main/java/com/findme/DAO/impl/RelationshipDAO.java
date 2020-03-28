package com.findme.DAO.impl;

import com.findme.DAO.GenericDao;
import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class RelationshipDAO implements GenericDao<Relationship> {

    private final String SQL_updateRelationship = "Update Relationship set status = :status where userFromId = :idFrom and userToId = :idTo";
    private final String SQL_getIncomeRequests = "From Relationship as rel inner join rel.userTo where rel.userTo.id = :idTo";
    private final String SQL_getOutcomeRequests = "From Relationship as rel inner join rel.userFrom where rel.userFrom.id = :idFrom";
    private final String SQL_checkRelationship = "From Relationship as rel where rel.userFrom.id = :idFrom and rel.userTo.id = :idTo";
    //    private final String SQL_getCountOfFriends = "Select count(*) FROM Relationship as rel" +
//            "where rel.userFrom.id = :idFrom and rel.status = :status";
    private final String SQL_getCountOfFriends = "Select count(*) FROM Relationship as rel" +
            " where rel.userFrom.id = :idFrom and rel.status = :status";

    @Autowired
    private GeneralDAO<Relationship> generalDAO;

    @Override
    public Relationship save(Relationship t) throws InternalServerError {
        try {
            return generalDAO.save(t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public Relationship read(Long id) throws InternalServerError {
        try {
            generalDAO.seteClass(Relationship.class);
            return generalDAO.read(id);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public Relationship update(Relationship t) throws InternalServerError {
        try {
            return generalDAO.update(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    @Override
    public void delete(Relationship t) throws InternalServerError {
        try {
            generalDAO.delete(t);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public Relationship addRelationship(User userFrom, User userTo) throws InternalServerError {
        Relationship relationship = new Relationship();
        relationship.setUserFrom(userFrom);
        relationship.setUserTo(userTo);
        relationship.setStatus(RelationshipType.WAITING);
        relationship.setDateCreated(new Date());
        relationship.setDateLastUpdated(new Date());

        try {
            return generalDAO.save(relationship);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public void updateRelationship(Long userIdFrom, Long userIdTo, RelationshipType status) throws InternalServerError {
        try {
            updateRelationshipStatusByQuery(userIdFrom, userIdTo, status);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public List<Relationship> getIncomeRequests(Long userIdTo) throws InternalServerError {
        try {
            return getIncomeRequestsByQuery(userIdTo);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public List<Relationship> getOutcomeRequests(Long userIdFrom) throws InternalServerError {
        try {
            return getOutcomeRequestsByQuery(userIdFrom);
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }

    public void checkRelationship(Long userIdFrom, Long userIdTo) throws InternalServerError {
        try {
            if (getRelationshipByQuery(userIdFrom, userIdTo) != null)
                throw new BadRequestException("BadRequestException");
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }


    /**
     * Query
     **/

    public Relationship getRelationshipByQuery(Long userIdFrom, Long userIdTo) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_checkRelationship);
        query1.setParameter("idFrom", userIdFrom);
        query1.setParameter("idTo", userIdTo);
        Relationship e;
        try {
            e = (Relationship) query1.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return e;
    }

    public void updateRelationshipStatusByQuery(Long userIdFrom, Long userIdTo, RelationshipType status) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_updateRelationship);
        query1.setParameter("status", status);
        query1.setParameter("idFrom", userIdFrom);
        query1.setParameter("idTo", userIdTo);
        query1.executeUpdate();
    }

    public List<Relationship> getIncomeRequestsByQuery(Long userIdTo) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_getIncomeRequests);
        query1.setParameter("idTo", userIdTo);
        return query1.getResultList();
    }

    public List<Relationship> getOutcomeRequestsByQuery(Long userIdTo) {
        Query query1 = generalDAO.getEntityManager().createQuery(SQL_getOutcomeRequests);
        query1.setParameter("idFrom", userIdTo);
        return query1.getResultList();
    }

    public Long countRelationships(Long userIdFrom, RelationshipType status) throws InternalServerError {
        try {
            Query query1 = generalDAO.getEntityManager().createQuery(SQL_getCountOfFriends);
            query1.setParameter("idFrom", userIdFrom);
            query1.setParameter("status", status);
            return (Long) query1.getSingleResult();
        } catch (Exception e) {
            throw new InternalServerError("InternalServerError");
        }
    }
}