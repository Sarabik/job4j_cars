package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    @Column(name = "car_year")
    private int carYear;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "engine_size_id")
    private EngineSize engineSize;

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    private long mileage;

    @ManyToOne
    @JoinColumn(name = "car_colour_id")
    private CarColour carColour;

    private String number;

    private String vin;
}