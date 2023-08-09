package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "engine_sizes")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EngineSize {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "engine_size")
    private float size;
}
