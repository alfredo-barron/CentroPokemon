package com.alfredobarron.proyectofinal.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    @Expose
    private Integer id;
    @Expose
    private String species;
    @Expose
    private String image;
    @SerializedName("region_id")
    @Expose
    private Integer regionId;
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
    @Expose
    private String url;
    @Expose
    private List<Type> types = new ArrayList<Type>();
    @Expose
    private List<Power> powers = new ArrayList<Power>();

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
     * The species
     */
    public String getSpecies() {
        return species;
    }

    /**
     *
     * @param species
     * The species
     */
    public void setSpecies(String species) {
        this.species = species;
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
     * The regionId
     */
    public Integer getRegionId() {
        return regionId;
    }

    /**
     *
     * @param regionId
     * The region_id
     */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
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
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The image
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The types
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     *
     * @param types
     * The types
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    /**
     *
     * @return
     * The powers
     */
    public List<Power> getPowers() {
        return powers;
    }

    /**
     *
     * @param powers
     * The powers
     */
    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }


}
