package com.project.EStore.model.entity.enums;

public enum ProductSizeEnum {
    XS("33"),
    S("36"),
    M("39"),
    L("43"),
    XL("46");

    private String shoeSize;

    ProductSizeEnum(String shoeSize) {
        this.shoeSize = shoeSize;
    }

    public String getShoeSize() {
        return this.shoeSize;
    }
}
