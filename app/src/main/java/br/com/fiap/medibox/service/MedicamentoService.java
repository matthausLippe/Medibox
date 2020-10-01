package br.com.fiap.medibox.service;

import java.util.List;

import br.com.fiap.medibox.model.MedicamentoModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicamentoService {

    @GET("medicamento/")
    Call<List<MedicamentoModel>> findAll();

    @GET("medicamento/{id}")
    Call<MedicamentoModel> findById(@Path("id") long id);

    @POST("medicamento/")
    Call<MedicamentoModel> save(@Body MedicamentoModel medicamentoModel);

    @PUT("medicamento/{id}")
    Call<MedicamentoModel> update(@Path("id") long id, @Body MedicamentoModel medicamentoModel);

    @DELETE("medicamento/{id}")
    Call<MedicamentoModel> delete(@Path("id") long id);
}
