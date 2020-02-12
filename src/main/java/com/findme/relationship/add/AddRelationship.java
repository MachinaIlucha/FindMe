package com.findme.relationship.add;

public interface AddRelationship {

    void setNextChain(AddRelationship nextChain);

    boolean dispense(Long userIdFrom, Long userIdTo);
}
