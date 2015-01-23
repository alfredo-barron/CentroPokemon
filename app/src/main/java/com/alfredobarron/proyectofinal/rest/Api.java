package com.alfredobarron.proyectofinal.rest;

import com.alfredobarron.proyectofinal.models.Notice;
import com.alfredobarron.proyectofinal.models.Pokeball;
import com.alfredobarron.proyectofinal.models.Pokemon;
import com.alfredobarron.proyectofinal.models.Region;
import com.alfredobarron.proyectofinal.models.Register;
import com.alfredobarron.proyectofinal.models.Trainer;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface Api {

   @GET("/login/{username}/{password}")
   void getLogin(@Path("username") String username, @Path("password") String password, Callback<Trainer> callback);

   @GET("/inicio/{id}")
   void getNotices(@Path("id") int id, Callback<List<Notice>> callback);

   @FormUrlEncoded
   @POST("/entrenadores")
   void getEntrenador(@Field("name") String name, @Field("last_name") String last_name, @Field("image") String image,
                      @Field("username") String username, @Field("password") String password, @Field("birthday") String birthday,
                      @Field("region_id") int region_id, @Field("gender") String gender, @Field("leader") boolean leader,
                      @Field("region_id_actual") int region_id_actual, Callback<Trainer> callback);

   @GET("/entrenadores/{id}")
   void getEntrenadorVista(@Path("id") int id, Callback<Trainer> callback);

   @GET("/regiones/{id}")
   void getRegion(@Path("id") int id, Callback<Region> callback);

   @GET("/pokemon")
   void pokeList(Callback<List<Pokemon>> callback);

   @GET("/pokemon/{id}")
   void getPokemon(@Path("id") int id, Callback<Pokemon> callback);

   @GET("/pokebolas/{id}")
   void pokeballsList(@Path("id") int id, Callback<List<Pokeball>> callback);

   @FormUrlEncoded
   @POST("/pokebola")
   void getPokebola(@Field("trainer_id") int trainer_id, @Field("pokemon_id") int pokemon_id, Callback<Pokeball> callback);

   @FormUrlEncoded
   @PUT("/entrenadores/{id}")
   void updateEntrenador(@Path("id") int id, @Field("name") String name, @Field("last_name") String last_name, @Field("username")
                         String username, @Field("password") String password, Callback<Trainer> callback);

   @FormUrlEncoded
   @POST("/registro")
   void getRegenerador(@Field("pokeball_id") int pokeball_id, @Field("trainer_id") int trainer_id, Callback<Register> callback);

   @GET("/registros/{id}")
   void getRegistros(@Path("id") int id, Callback<List<Register>> callback);

   @DELETE("/registros/{id}")
   void getDeleteRe(@Path("id") int id, Callback<Pokeball> callback);

   @PUT("/batalla/{id}")
   void getBatalla(@Path("id") int id, Callback<Pokeball> callback);

   @DELETE("/pokebola/{id}")
   void getSoltar(@Path("id") int id, Callback<Trainer> callback);
}
