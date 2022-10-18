package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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

    private String name, marka, nomer;

    private Double probeg;

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
