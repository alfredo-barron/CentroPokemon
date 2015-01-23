package com.alfredobarron.proyectofinal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @Expose
    private Integer id;
    @SerializedName("date_start")
    @Expose
    private String dateStart;
    @SerializedName("date_end")
    @Expose
    private Object dateEnd;
    @SerializedName("regenerator_id")
    @Expose
    private Integer regeneratorId;
    @SerializedName("hit_points")
    @Expose
    private Integer hitPoints;
    @SerializedName("pokeball_id")
    @Expose
    private Integer pokeballId;
    @Expose
    private String specie;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @Expose
    private Boolean available;

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
     * The dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     *
     * @param dateStart
     * The date_start
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     *
     * @return
     * The dateEnd
     */
    public Object getDateEnd() {
        return dateEnd;
    }

    /**
     *
     * @param dateEnd
     * The date_end
     */
    public void setDateEnd(Object dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     *
     * @return
     * The regeneratorId
     */
    public Integer getRegeneratorId() {
        return regeneratorId;
    }

    /**
     *
     * @param regeneratorId
     * The regenerator_id
     */
    public void setRegeneratorId(Integer regeneratorId) {
        this.regeneratorId = regeneratorId;
    }

    /**
     *
     * @return
     * The hitPoints
     */
    public Integer getHitPoints() {
        return hitPoints;
    }

    /**
     *
     * @param hitPoints
     * The hit_points
     */
    public void setHitPoints(Integer hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     *
     * @return
     * The pokeballId
     */
    public Integer getPokeballId() {
        return pokeballId;
    }

    /**
     *
     * @param pokeballId
     * The pokeball_id
     */
    public void setPokeballId(Integer pokeballId) {
        this.pokeballId = pokeballId;
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
     * The trainerId
     */
    public Integer getTrainerId() {
        return trainerId;
    }

    /**
     *
     * @param trainerId
     * The trainer_id
     */
    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    /**
     *
     * @return
     * The available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

}