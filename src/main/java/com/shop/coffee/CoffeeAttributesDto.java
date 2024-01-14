package com.shop.coffee;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CoffeeAttributesDto {

    private double aroma;
    private double flavor;
    private double body;
    private double sweetness;
    private double acidity;
    private double balance;
    private double uniformity;
    private double aftertaste;


    public List<String> toList() {
        return Arrays.asList(
                Double.toString(aroma),
                Double.toString(flavor),
                Double.toString(body),
                Double.toString(sweetness),
                Double.toString(acidity),
                Double.toString(balance),
                Double.toString(uniformity),
                Double.toString(aftertaste)
        );
        }
}


