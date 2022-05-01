package com.project.EStore.util.validation;

public class ProductIdTypeValidator {

    public static boolean isValid(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}