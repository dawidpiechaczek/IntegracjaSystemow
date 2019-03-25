package com.example.dawid.visitwroclove.database.enums;

public enum Status {

    BLOCK("block"),
    DRAFT("draft"),
    PUBLISH("publish");

    private final String text;

    /**
     * @param text
     */
    private Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getValue(){
        return text;
    }

}