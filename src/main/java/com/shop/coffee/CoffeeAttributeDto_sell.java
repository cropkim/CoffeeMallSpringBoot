package com.shop.coffee;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CoffeeAttributeDto_sell {
    private String Origin;
    private String region;
    private String variety;
    private String color;
    private String ProcessingMethod;
    private int Category_one_defects;
    private int Category_two_defects;
    private double moisture;

    public List<String> toList() {
        return Arrays.asList(
                (Origin),
                (region), (variety),(color),
                Integer.toString(Category_one_defects),
                Integer.toString(Category_two_defects),(ProcessingMethod),
                Double.toString(moisture)
        );
    }
}
