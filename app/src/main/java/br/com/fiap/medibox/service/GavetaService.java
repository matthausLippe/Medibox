package br.com.fiap.medibox.service;

import java.util.List;

import br.com.fiap.medibox.model.GavetaModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GavetaService {

    @GET("gaveta/")
    Call<List<GavetaModel>> findAll();

    @GET("gaveta/caixa/{id}")
    Call<List<GavetaModel>> findByIdCaixa(@Path("id") long id);

    @GET("gaveta/{id}")
    Call<GavetaModel> findById(@Path("id") long id);

    @POST("gaveta/")
    Call<GavetaModel> save(@Body GavetaModel gavetaModel);

    @PUT("gaveta/{id}")
    Call<GavetaModel> update(@Path("id") long id, @Body GavetaModel gavetaModel);

    @DELETE("gaveta/{id}")
    Call<GavetaModel> delete(@Path("id") long id);
}
