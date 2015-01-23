package com.alfredobarron.proyectofinal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokeball {

    @Expose
    private Integer id;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("pokemon_id")
    @Expose
    private Integer pokemonId;
    @Expose
    private String url;
    @Expose
    private String specie;
    @Expose
    private String image;
    @Expose
    private String alias;
    @Expose
    private String gender;
    @Expose
    private Integer level;
    @SerializedName("hit_points")
    @Expose
    private Integer hitPoints;
    @Expose
    private Integer attack;
    @Expose
    private Integer defense;
    @Expose
    private Integer speed;
    @Expose
    private Integer evasion;
    @Expose
    private Integer accuracy;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @Expose
    private String status;
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
     * The pokemonId
     */
    public Integer getPokemonId() {
        return pokemonId;
    }

    /**
     *
     * @param pokemonId
     * The pokemon_id
     */
    public void setPokemonId(Integer pokemonId) {
        this.pokemonId = pokemonId;
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
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The specie
     */
    public void setUrl(String url) {
        this.url = url;
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
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     *
     * @param level
     * The level
     */
    public void setLevel(Integer level) {
        this.level = level;
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
     * The attack
     */
    public Integer getAttack() {
        return attack;
    }

    /**
     *
     * @param attack
     * The attack
     */
    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    /**
     *
     * @return
     * The defense
     */
    public Integer getDefense() {
        return defense;
    }

    /**
     *
     * @param defense
     * The defense
     */
    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    /**
     *
     * @return
     * The speed
     */
    public Integer getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     * The evasion
     */
    public Integer getEvasion() {
        return evasion;
    }

    /**
     *
     * @param evasion
     * The evasion
     */
    public void setEvasion(Integer evasion) {
        this.evasion = evasion;
    }

    /**
     *
     * @return
     * The accuracy
     */
    public Integer getAccuracy() {
        return accuracy;
    }

    /**
     *
     * @param accuracy
     * The accuracy
     */
    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    /**
     *
     * @return
     * The statusId
     */
    public Integer getStatusId() {
        return statusId;
    }

    /**
     *
     * @param statusId
     * The status_id
     */
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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