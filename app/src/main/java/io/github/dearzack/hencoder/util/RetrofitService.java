package io.github.dearzack.hencoder.util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhouxiong on 2017-7-25.
 */

public interface RetrofitService {
    @GET("book/{id}")
    Call<ResponseBody> getBook(@Path("id") String id);
}
