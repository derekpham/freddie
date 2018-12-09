package com.derek.freddie.entity.relationship;

public final class RelationshipType {
    private RelationshipType() {
        throw new RuntimeException("not instantiable");
    }

    public static final String OF_GENRE = "OF_GENRE";
    public static final String WAS_RECOMMENDED = "WAS_RECOMMENDED";
    public static final String LISTENED = "LISTENED";
    public static final String GAVE_PREFERENCE = "GAVE_PREFERENCE";
    public static final String SINGS = "SINGS";

    public static boolean isUserSongRelationship(String relationship) {
        return relationship.equals(WAS_RECOMMENDED) || relationship.equals(LISTENED)
                || relationship.equals(GAVE_PREFERENCE);
    }
}
