package com.shop.coffee;

import com.shop.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "coffee")
@Getter
@Setter
@ToString
public class Coffee extends BaseEntity {
    @Id
    @Column(name="coffee_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long coffeeId;

    @Column(nullable = false, length = 50, unique = true)
    private String coffeeNm;

    // Subjective features
    @Column(nullable = false)
    private double aroma;

    @Column(nullable = false)
    private double flavor;

    @Column(nullable = false)
    private double body;

    @Column(nullable = false)
    private double sweetness;

    @Column(nullable = false)
    private double acidity;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private double uniformity;

    @Column(nullable = false)
    private double aftertaste;

    // Objective features
    @Column(nullable = false)
    private String Origin;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String variety;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String ProcessingMethod;
    @Column(nullable = false)
    private int Category_one_defects;
    @Column(nullable = false)
    private int Category_two_defects;

    @Column(nullable = false)
    private double moisture;

    public Coffee() {

    }

    public Coffee(String Origin, String region, String variety, String color,String ProcessingMethod,double moisture) {
        this.Origin = Origin;
        this.region = region;
        this.variety = variety;
        this.ProcessingMethod = ProcessingMethod;
        this.moisture = moisture;
        this.color =    color;
    }
    public Coffee(double aroma, double flavor, double body, double sweetness, double acidity, double balance,
                  double uniformity, double aftertaste){
        this.aroma = aroma;
        this.flavor = flavor;
        this.body = body;
        this.sweetness = sweetness;
        this.acidity = acidity;
        this.balance = balance;
        this.uniformity = uniformity;
        this.aftertaste = aftertaste;
    }
}
