package br.com.fiap.medibox.service;

import java.util.List;


import br.com.fiap.medibox.model.ClienteModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteService {
    @GET("cliente/")
    Call<List<ClienteModel>> findAll();

    @GET("cliente/{id}")
    Call<ClienteModel> findById(@Path("id") long id);

    @POST("cliente/")
    Call<ClienteModel> save(@Body ClienteModel clienteModel);

    @PUT("cliente/{id}")
    Call<ClienteModel> update(@Path("id") long id, @Body ClienteModel clienteModel);

    @DELETE("cliente/{id}")
    Call<ClienteModel> delete(@Path("id") long id);
}
