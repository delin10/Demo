package nil.ed.test.retrofit;

import nil.ed.test.comm.dto.TestDTO;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;

import java.io.IOException;

/**
 * @author lidelin.
 */
public class RetrofitQuickStart {
    public static final String API_URL = "http://127.0.0.1:12001";

    interface DemoInterface {
        /**
         * test get.
         * @return get.
         */
        @GET("/boot1/hello")
        Call<TestDTO> get();
    }

    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DemoInterface demoInterface = retrofit.create(DemoInterface.class);
        TestDTO dto = demoInterface.get().execute().body();
        System.out.println(dto);
    }

}
