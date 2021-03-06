package br.com.fiap.medibox.service;

import java.util.List;


import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ResidenteMedicamentoService {

    @GET("residenteMedicamento/")
    Call<List<ResidenteMedicamentoModel>> findAll();

    @GET("residenteMedicamento/{id}")
    Call<ResidenteMedicamentoModel> findById(@Path("id") long id);

    @GET("residenteMedicamento/residente/{id}")
    Call<List<ResidenteMedicamentoModel>> findByIdResidente(@Path("id") long id);

    @GET("residenteMedicamento/cliente/{id}")
    Call<List<ResidenteMedicamentoModel>> findByIdCliente(@Path("id") long id);

    @GET("residenteMedicamento/medicamento/{id}")
    Call<List<ResidenteMedicamentoModel>> findByIdMedicamento(@Path("id") long id);

    @POST("residenteMedicamento/")
    Call<ResidenteMedicamentoModel> save(@Body ResidenteMedicamentoModel residenteMedicamentoModel);

    @PUT("residenteMedicamento/{id}")
    Call<ResidenteMedicamentoModel> update(@Path("id") long id, @Body ResidenteMedicamentoModel residenteMedicamentoModel);

    @DELETE("residenteMedicamento/{id}")
    Call<ResidenteMedicamentoModel> delete(@Path("id") long id);





}
