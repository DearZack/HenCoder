package io.github.dearzack.hencoder.activity2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.github.dearzack.hencoder.R;
import io.github.dearzack.hencoder.util.RetrofitService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http://blog.csdn.net/dd864140130/article/details/52625666
 * 这个demo没有对证书进行校验，实际实用还应该校验证书，不然还是存在被攻击危险
 * 详见以上地址。
 */
public class HTTPSActivity extends AppCompatActivity {

    private static Retrofit mRetrofit;
    private RetrofitService service;
    private OkHttpClient client;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        result = (TextView) findViewById(R.id.result);
        // 配置 Retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder build = new OkHttpClient.Builder();
        build.addNetworkInterceptor(interceptor);
        onHttps(build);
        client = build.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // 设置 Json 转换工具
                .build();
        service = mRetrofit.create(RetrofitService.class);
        Call<ResponseBody> call = service.getBook("1220562");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resultString = response.body().string();
                    Log.e("onResponse", resultString);
                    result.setText(resultString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.toString());
                result.setText(t.toString());
            }
        });
    }

    protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {

        }
        return null;
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.getSocketFactory();
    }


    //使用自定义SSLSocketFactory
    private void onHttps(OkHttpClient.Builder builder) {
        try {
            builder.sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void onHttpCertficates(OkHttpClient.Builder builder) {
//        int[] certficates = new int[]{R.raw.media};
//        builder.socketFactory(getSSLSocketFactory(this, certficates));
//    }


//    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
//        // Create a trust manager that does not validate certificate chains
//        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//            //证书中的公钥
//            public static final String PUB_KEY = "3082010a0282010100d52ff5dd432b3a05113ec1a7065fa5a80308810e4e181cf14f7598c8d553cccb7d5111fdcdb55f6ee84fc92cd594adc1245a9c4cd41cbe407a919c5b4d4a37a012f8834df8cfe947c490464602fc05c18960374198336ba1c2e56d2e984bdfb8683610520e417a1a9a5053a10457355cf45878612f04bb134e3d670cf96c6e598fd0c693308fe3d084a0a91692bbd9722f05852f507d910b782db4ab13a92a7df814ee4304dccdad1b766bb671b6f8de578b7f27e76a2000d8d9e6b429d4fef8ffaa4e8037e167a2ce48752f1435f08923ed7e2dafef52ff30fef9ab66fdb556a82b257443ba30a93fda7a0af20418aa0b45403a2f829ea6e4b8ddbb9987f1bf0203010001";
//
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType) throws CertificateException {
//
//
//            }
//
//            //客户端并为对ssl证书的有效性进行校验
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] chain,
//                    String authType) throws CertificateException {
//                if (chain == null) {
//                    throw new IllegalArgumentException("checkServerTrusted:x509Certificate array isnull");
//                }
//
//                if (!(chain.length > 0)) {
//                    throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
//                }
//
//                if (!(null != authType && authType.equalsIgnoreCase("RSA"))) {
//                    throw new CertificateException("checkServerTrusted: AuthType is not RSA");
//                }
//
//                // Perform customary SSL/TLS checks
//                try {
//                    TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
//                    tmf.init((KeyStore) null);
//                    for (TrustManager trustManager : tmf.getTrustManagers()) {
//                        ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
//                    }
//                } catch (Exception e) {
//                    throw new CertificateException(e);
//                }
//                // Hack ahead: BigInteger and toString(). We know a DER encoded Public Key begins
//                // with 0×30 (ASN.1 SEQUENCE and CONSTRUCTED), so there is no leading 0×00 to drop.
//                RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
//
//                String encoded = new BigInteger(1 /* positive */, pubkey.getEncoded()).toString(16);
//                // Pin it!
//                final boolean expected = PUB_KEY.equalsIgnoreCase(encoded);
//
//                if (!expected) {
//                    throw new CertificateException("checkServerTrusted: Expected public key: "
//                            + PUB_KEY + ", got public key:" + encoded);
//                }
//
//            }
//
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return new java.security.cert.X509Certificate[0];
//            }
//        }};
//
//        // Install the all-trusting trust manager
//        final SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, trustAllCerts,
//                new java.security.SecureRandom());
//        // Create an ssl socket factory with our all-trusting manager
//        return sslContext
//                .getSocketFactory();
//    }
}
