package com.findme.relationship.checkStatusRelationship;

import com.findme.models.Relationship;
import com.findme.models.RelationshipType;
import org.springframework.stereotype.Component;

@Component
public abstract class CheckStatus {
    private CheckStatus next;

    public CheckStatus linkWith(CheckStatus next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(RelationshipType status, Relationship relationship);

    public boolean checkNext(RelationshipType status, Relationship relationship) {
        if (next == null) {
            return true;
        }
        return next.check(status, relationship);
    }
}
