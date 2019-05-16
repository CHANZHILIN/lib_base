package chen.baselib.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import chen.baselib.BuildConfig;
import chen.baselib.utils.LogUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Util.UTF_8;

/**
 * @author Administrator
 * @date 2017/9/19
 */

public class ApiEngine {
    private static volatile ApiEngine apiEngine;
    private Retrofit retrofit;


    private ApiEngine() {

        //日志拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                printRequestMessage(request);
                Response response = chain.proceed(request);
                printResponseMessage(response);
                return response;
            }

            /** * 打印请求消息 * * @param request 请求的对象 */
            private void printRequestMessage(Request request) {

                if (request == null) {
                    return;
                }

                LogUtil.error("==================================请求信息 start ===================================" + "\n"
                        + "Url : " + request.url().url().toString() + "\n"
                        + "Method: " + request.method() + "\n"
                        + "Heads : " + request.headers());

                RequestBody requestBody = request.body();
                if (requestBody == null) {
                    return;
                }
                try {
                    Buffer bufferedSink = new Buffer();
                    requestBody.writeTo(bufferedSink);
                    MediaType mediaType = requestBody.contentType();
                    if (mediaType != null) {
                        Charset charset = mediaType.charset();
                        charset = charset == null ? Charset.forName("utf-8") : charset;
                        LogUtil.error("Params: " + bufferedSink.readString(charset));
//                        Log.e(TAG, "Params: " + bufferedSink.readString(charset));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                LogUtil.error("==================================请求信息  end  ===================================");
            }

            /**
             *
             * 打印返回消息
             * @param response 返回的对象
             */
            private void printResponseMessage(Response response) {
                if (response == null || !response.isSuccessful()) {
                    return;
                }
                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();
                BufferedSource source = responseBody.source();
                try {
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Buffer buffer = source.buffer();
                Charset charset = UTF_8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset();
                }
                if (contentLength != 0) {
                    String result = buffer.clone().readString(charset);
                    LogUtil.error("==================================返回消息 start ===================================" + "\n"
                            + "Response: " + result + "\n"
                            + "==================================返回消息  end  ===================================");
                }
            }

        };


        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            //Tip：這裡key必須是String
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(interceptor);

        // 上线时，修改下面注释
        //当且仅当构建模式是debug的时候，才打印输出请求body信息
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public Api getApiService() {
        return retrofit.create(Api.class);
    }


    public static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        System.out.println("src: " + src);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();

    }
}
