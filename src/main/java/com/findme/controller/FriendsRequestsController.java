package com.findme.controller;

import com.findme.Exceptions.BadRequestException;
import com.findme.Exceptions.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import com.findme.models.User;
import com.findme.service.FriendsRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FriendsRequestsController {

    @Autowired
    private FriendsRequestsService friendsRequestsService;

    /**
     * RELATIONSHIPS
     **/
    @RequestMapping(path = "/sendFriendRequest", method = RequestMethod.POST, produces = "text/plain")
    public String addRelationship(HttpSession session, @RequestParam("id") Long idTo) {

        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                friendsRequestsService.addRelationship(userFrom.getId(), idTo);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException | BadRequestException e) {
                return "400Error";
            }
        } else return "login";

        return "ok";
    }

    @RequestMapping(path = "/updateRelationship/{status}", method = RequestMethod.GET)
    public String updateRelationship(HttpSession session, @PathVariable String status, @RequestParam("id") Long idTo) {

        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                RelationshipType relationshipType = RelationshipType.valueOf(status);
                friendsRequestsService.updateRelationship(userFrom.getId(), idTo, relationshipType);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException | BadRequestException e) {
                return "400Error";
            }
        } else return "login";

        return "ok";
    }

    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.GET)
    public String deleteRelationship(HttpSession session, @RequestParam("id") Long idTo) {

        if (session.getAttribute("login") != null) {
            try {
                User userFrom = (User) session.getAttribute("user");
                friendsRequestsService.deleteRelationship(userFrom.getId(), idTo);
            } catch (InternalServerError e) {
                return "500Error";
            } catch (NumberFormatException e) {
                return "400Error";
            }
        } else return "login";

        return "ok";
    }


    public List<Relationship> getIncomeRequests(HttpSession session) throws InternalServerError {
        List<Relationship> incomeRequests;
        try {
            User sessionUser = (User) session.getAttribute("user");
            incomeRequests = friendsRequestsService.getIncomeRequests(sessionUser.getId());
        } catch (InternalServerError | BadRequestException e) {
            throw new InternalServerError("ServerError");
        }

        return incomeRequests;
    }


    public List<Relationship> getOutcomeRequests(HttpSession session) throws InternalServerError, BadRequestException {
        List<Relationship> outcomeRequests;
        try {
            User sessionUser = (User) session.getAttribute("user");
            outcomeRequests = friendsRequestsService.getOutcomeRequests(sessionUser.getId());
        } catch (InternalServerError | BadRequestException e) {
            throw new InternalServerError("ServerError");
        }
        return outcomeRequests;
    }
}
