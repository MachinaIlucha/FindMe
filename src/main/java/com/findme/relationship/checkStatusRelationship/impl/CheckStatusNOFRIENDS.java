package com.findme.relationship.checkStatusRelationship.impl;

import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import com.findme.relationship.checkStatusRelationship.CheckStatus;

public class CheckStatusNOFRIENDS extends CheckStatus {

    @Override
    public CheckStatus linkWith(CheckStatus next) {
        return super.linkWith(next);
    }

    @Override
    public boolean check(RelationshipType status, Relationship relationship) {
        if (relationship != null) {
            if (relationship.getStatus() == RelationshipType.NOFRIENDS && status != RelationshipType.WAITING)
                return false;
        }
        return checkNext(status, relationship);
    }

    @Override
    public boolean checkNext(RelationshipType status, Relationship relationship) {
        return super.checkNext(status, relationship);
    }
}
