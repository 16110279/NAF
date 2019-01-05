package id.ac.amikom.jaka.nff.api;

import id.ac.amikom.jaka.nff.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RestApi {
    //inser
    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseModel> sendBiodata(@Field("nama") String nama,
                                    @Field("harga") String harga,
                                    @Field("foto") String foto);

    //read
    @GET("read.php")
    Call<ResponseModel> getData();

    //update menggunakan 3 parameter
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> updateData(@Field("id") String id,
                                   @Field("nama") String nama,
                                   @Field("harga") String harga);
    //delete menggunakan parameter id
    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> deleteData(@Field("id") String id);
}
