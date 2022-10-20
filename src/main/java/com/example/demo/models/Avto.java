package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "avto")
public class Avto {
    public Avto(String name, String marka, String nomer, Double probeg, Integer horse) {
        this.name = name;
        this.marka = marka;
        this.nomer = nomer;
        this.probeg = probeg;
        this.horse = horse;
    }

    public Avto() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 2, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
    private String name;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 3, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
    private String marka;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 6, max = 6, message = "Поле должно быть размером 6 симовлов")
    private String nomer;

    @DecimalMin(value = "0.0", message = "Пробег должен быть больше 0")
    @Positive(message = "Пробег не может быть отрицательным")
    @NotNull(message = "Поле не должно быть пустым")
    private Double probeg;

    @DecimalMin(value = "0", message = "Колвичество л.с. должен быть больше 0")
    @Positive(message = "Колвичество л.с. не может быть отрицательным")
    @NotNull(message = "Поле не должно быть пустым")
    private Integer horse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public Double getProbeg() {
        return probeg;
    }

    public void setProbeg(Double probeg) {
        this.probeg = probeg;
    }

    public Integer getHorse() {
        return horse;
    }

    public void setHorse(Integer horse) {
        this.horse = horse;
    }

}
