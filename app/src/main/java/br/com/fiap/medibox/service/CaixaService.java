package br.com.fiap.medibox.service;

import java.util.List;

import br.com.fiap.medibox.model.CaixaModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CaixaService {

    @GET("caixa/")
    Call<List<CaixaModel>> findAll();

    @GET("caixa/{id}")
    Call<CaixaModel> findById(@Path("id") long id);

    @POST("caixa/")
    Call<CaixaModel> save(@Body CaixaModel caixaModel);

    @PUT("caixa/{id}")
    Call<CaixaModel> update(@Path("id") long id, @Body CaixaModel caixaModel);

    @DELETE("caixa/{id}")
    Call<CaixaModel> delete(@Path("id") long id);


}
