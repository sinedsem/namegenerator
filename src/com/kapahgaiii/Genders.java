package com.kapahgaiii;


enum Genders {
    MALE, FEMALE, BOTH;

    public String getSrc() {
        switch (this) {
            case MALE:
                return "male_names.txt";
            case FEMALE:
                return "female_names.txt";
            default:
                return null;
        }
    }
}