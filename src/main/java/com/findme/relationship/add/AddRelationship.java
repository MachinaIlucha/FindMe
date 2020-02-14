package com.findme.relationship.add;

import org.springframework.stereotype.Component;

@Component
public abstract class AddRelationship {
    private AddRelationship next;

    public AddRelationship linkWith(AddRelationship next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(Long userIdFrom, Long userIdTo);

    public boolean checkNext(Long userIdFrom, Long userIdTo) {
        if (next == null) {
            return true;
        }
        return next.check(userIdFrom, userIdTo);
    }
}