package com.alfredobarron.proyectofinal.models;

import com.google.gson.annotations.Expose;

public class Notice {

    @Expose
    private Integer id;
    @Expose
    private String specie;
    @Expose
    private String image;
    @Expose
    private String alias;
    @Expose
    private String status;
    @Expose
    private String trainer;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The specie
     */
    public String getSpecie() {
        return specie;
    }

    /**
     *
     * @param specie
     * The specie
     */
    public void setSpecie(String specie) {
        this.specie = specie;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     *
     * @param alias
     * The alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The trainer
     */
    public String getTrainer() {
        return trainer;
    }

    /**
     *
     * @param trainer
     * The trainer
     */
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

}

