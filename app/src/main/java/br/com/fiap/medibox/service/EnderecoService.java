package br.com.fiap.medibox.service;

import java.util.List;


import br.com.fiap.medibox.model.EnderecoModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EnderecoService {

    @GET("endereco/")
    Call<List<EnderecoModel>> findAll();

    @GET("endereco/{id}")
    Call<EnderecoModel> findById(@Path("id") long id);

    @POST("endereco/")
    Call<EnderecoModel> save(@Body EnderecoModel enderecoModel);

    @PUT("endereco/{id}")
    Call<EnderecoModel> update(@Path("id") long id, @Body EnderecoModel enderecoModel);

    @DELETE("endereco/{id}")
    Call<EnderecoModel> delete(@Path("id") long id);
}
