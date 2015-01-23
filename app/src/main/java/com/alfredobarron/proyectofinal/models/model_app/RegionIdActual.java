package com.alfredobarron.proyectofinal.models.model_app;

import com.google.gson.annotations.Expose;

public class RegionIdActual {

    @Expose
    private Integer id;
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}