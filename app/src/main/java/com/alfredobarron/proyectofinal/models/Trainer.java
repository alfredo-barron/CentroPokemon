package com.alfredobarron.proyectofinal.models;

import com.alfredobarron.proyectofinal.models.model_app.RegionId;
import com.alfredobarron.proyectofinal.models.model_app.RegionIdActual;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trainer {

    @Expose
    private Integer id;
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @Expose
    private String image;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String birthday;
    @SerializedName("region_id")
    @Expose
    public RegionId regionId;
    @Expose
    private String gender;
    @Expose
    private Boolean leader;
    @SerializedName("region_id_actual")
    @Expose
    public RegionIdActual regionIdActual;
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     * The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public RegionId getRegionId() {
        return regionId;
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
     * The leader
     */
    public Boolean getLeader() {
        return leader;
    }

    /**
     *
     * @param leader
     * The leader
     */
    public void setLeader(Boolean leader) {
        this.leader = leader;
    }


    public RegionIdActual getRegionIdActual() {
        return regionIdActual;
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
