package com.github.denislavpetkov.simplerestclientserver.DTO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "sum")
public class Sum {
    double num1;

    double num2;
    double result;

    public Sum() {
    }

    public Sum(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.result = num1+num2;
    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
