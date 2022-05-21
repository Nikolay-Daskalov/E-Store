package com.project.EStore.model.entity.enums;

public enum ProductSizeEnum {
    XXS("34"),
    XS("36"),
    S("38"),
    M("40"),
    L("42"),
    XL("44"),
    XXL("46");

    private final String shoeSize;

    ProductSizeEnum(String shoeSize) {
        this.shoeSize = shoeSize;
    }

    public String getShoeSize() {
        return shoeSize;
    }
}
