package id.ac.amikom.jaka.nff.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Server on 13/09/2017.
 */

public class RetroServer {

    private  static  final String base_url = "http://192.168.1.13/nafero/";
    public static final String img_url = "http://192.168.1.13/nafero/Uploads/";

    private static Retrofit retrofit;


    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retrofit;
    }
    // Mendeklarasikan Interface BaseApiService
    public static RestApi getAPIService(){
        return RetrofitClient.getClient(base_url).create(RestApi.class);
    }

}
