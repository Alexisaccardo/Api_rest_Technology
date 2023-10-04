package com.example.demo;

public class Technology {

    public String code;
    public String name;
    public String type;
    public String characteristics;
    public String amount;
    public String value;

    public Technology(String code, String name, String type, String characteristics, String amount, String value) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.characteristics = characteristics;
        this.amount = amount;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
