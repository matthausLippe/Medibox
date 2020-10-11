package br.com.fiap.medibox.service;

import java.util.List;

import br.com.fiap.medibox.model.TimeLineModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TimeLineService {

    @GET("timeLine/")
    Call<List<TimeLineModel>> findAll();

    @GET("timeline/cliente/{id}")
    Call<List<TimeLineModel>> findByIdCliente(@Path("id") long id);

    @GET("timeline/residenteMedicamento/{id}")
    Call<List<TimeLineModel>> findByIdResidenteMedicamento(@Path("id") long id);

    @GET("timeLine/{id}")
    Call<TimeLineModel> findById(@Path("id") long id);

    @POST("timeLine/")
    Call<TimeLineModel> save(@Body TimeLineModel timeLineModel);

    @PUT("timeLine/{id}")
    Call<TimeLineModel> update(@Path("id") long id, @Body TimeLineModel timeLineModel);

    @DELETE("timeLine/{id}")
    Call<TimeLineModel> delete(@Path("id") long id);
}
