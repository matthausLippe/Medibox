package br.com.fiap.medibox.service;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ResidenteService {

    @GET("residente/")
    Call<List<ResidenteModel>> findAll();

    @GET("residente/cliente/{id}")
    Call<List<ResidenteModel>> findByIdCliente(@Path("id") long id);

    @GET("residente/{id}")
    Call<ResidenteModel> findById(@Path("id") long id);

    @POST("residente/")
    Call<ResidenteModel> save(@Body ResidenteModel residenteModel);

    @PUT("residente/{id}")
    Call<ResidenteModel> update(@Path("id") long id, @Body ResidenteModel residenteModel);

    @DELETE("residente/{id}")
    Call<ResidenteModel> delete(@Path("id") long id);
}
