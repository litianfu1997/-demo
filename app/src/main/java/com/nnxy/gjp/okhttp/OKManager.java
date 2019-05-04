package com.nnxy.gjp.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.nnxy.gjp.entity.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * 封装工具类
 */
public class OKManager {
    private OkHttpClient okHttpClient;
    private volatile static OKManager okManager;
    private final String TAG=OKManager.class.getSimpleName();
    private Handler handler;
    //提交json模式
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private OKManager(){
        okHttpClient=new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }
//    //采用单例模式获取对象
//    public static OKManager getInstance(){
//        OKManager instance = null;
//        if (okManager==null){
//            synchronized (OKManager.class){
//                if (instance==null){
//                    instance=new OKManager();
//                    okManager=instance;
//                }
//            }
//        }
//        return instance;
//    }
    public static OKManager getInstance(){
       return new OKManager();
    }

    /***
     * 向服务器发送字符串
     * @param url
     * @param content
     * @param callBack
     */

    public void sendStringByPostMethod(String url,String content,final Func4 callBack){
        Request request=new Request.Builder().url(url).post(RequestBody.create(JSON,content)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJosnObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }
    /***
     * 向服务器发送字符串，返回JsonArray
     * @param url
     * @param content
     * @param callBack
     */

    public void sendStringByPostMethod5(String url,String content,final Func5 callBack){
        Request request=new Request.Builder().url(url).post(RequestBody.create(JSON,content)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJosnObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }

    /**
     * 表单提交
     *  @param url
     * @param params
     * @param callBack
     */
    public void sendComplexForm(String url, Map<String, String> params, final Func4 callBack){
        FormBody.Builder form_builder=new FormBody.Builder();//表单对象，包含以input开始的对象，以html表单为主。
        if (params!=null&&!params.isEmpty()){
            for (Map.Entry<String,String> entry:params.entrySet()){
                form_builder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody request_Body=form_builder.build();
        Request request=new Request.Builder().url(url).post(request_Body).build();//以post的方式提交
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJosnObjectMethod(response.body().string(),callBack);


                }
            }
        });
    }

   public void sendComplexFormByJson(String url, String json,final Func4 callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJosnObjectMethod(response.body().string(),callBack);

                }
            }
        });
//        Response response = client.newCall(request).execute();
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
    }

    /***
     * 请求返回的是json对象
     * @param url
     * @param callBack
     */
    public void asyncJsonStringByURL(String url,final Func4 callBack){
        final Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJosnObjectMethod(response.body().string(),callBack);
                }

            }
        });
    }

    /***
     * 请求返回的结果是字节数组
     * @param url
     * @param callBack
     */
    public void asyncGetByteByURL(String url,final Func2 callBack){
        final Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessByteMethod(response.body().bytes(),callBack);
                }

            }
        });
    }

    /***
     * 请求返回的结果是imageView类型，bitmap类型
     * @param url
     * @param callBack
     */

    public void asyncDownLoadByURL(String url,final Func3 callBack){
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                   byte[] data=response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                    callBack.onResponse(bitmap);
                }

            }
        });
    }
    /***
     * 请求指定的url返回的结果是json字符串
     * @param url
     * @param callBack
     */
    public void asyncJsonStringByURL(String url,final Func1 callBack){
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {//失败的时候
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {//成功的时候
                if (response.isSuccessful()&&response!=null){
                    onSuccessJosnStringMethod(response.body().string(),callBack);
                }
            }
        });
    }


    /***
     * 同步请求，一般不用
     * @param url
     * @return
     */
    public String sycncGetByURL(String url){
        //构建一个request请求
        Request request=new Request.Builder().url(url).build();
        Response response=null;
        try{
            response=okHttpClient.newCall(request).execute();//同步请求数据
            if (response.isSuccessful()){
                return response.body().string();
            }
        }catch (Exception e){
         e.printStackTrace();
        }
        return null;
    }

    /***
     * 请求返回的结果是json字符串
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJosnStringMethod(final String jsonValue, final Func1 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try{
                        callBack.onResponse(jsonValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    /***
     * 请求返回的结果是字节数组
     * @param data
     * @param callBack
     */
    private void onSuccessByteMethod(final byte[] data, final Func2 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try{
                        callBack.onResponse(data);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    /***
     * 返回相应的结果是一个json对象
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJosnObjectMethod(final String jsonValue, final Func4 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try{
                        callBack.onResponse(new JSONObject(jsonValue));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    /***
     * 返回相应的结果是一个json数组
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJosnObjectMethod(final String jsonValue, final Func5 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try{
                        callBack.onResponse(new JSONArray(jsonValue));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public interface Func1{
        void onResponse(String result);
    }
    public interface Func2{
        void onResponse(byte[] result);
    }
     public interface Func3{
        void onResponse(Bitmap bitmap);
    }
    public interface Func4{
        void onResponse(JSONObject jsonObject);
    }
    public interface Func5{
        void onResponse(JSONArray jsonArray);
    }

}
