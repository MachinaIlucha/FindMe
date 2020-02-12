package com.findme.relationship.delete;

public abstract class DeleteRelationship {
    private DeleteRelationship next;

    public DeleteRelationship linkWith(DeleteRelationship next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(Long userIdFrom, Long userIdTo);

    protected boolean checkNext(Long userIdFrom, Long userIdTo) {
        if (next == null) {
            return true;
        }
        return next.check(userIdFrom, userIdTo);
    }
}
