package http;

import com.google.gson.Gson;
import entity.Student;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ListStudentHttp {
    private String url;
    private String path;
    private String method;
    private Response response;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void make() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(this.buildUrl())
                .method(this.method, null)
                .build();

        this.response = client.newCall(request).execute();
    }

    public List<Student> getStudents() throws IOException {
        Gson gson = new Gson();
        ResponseBody responseBody = this.response.body();
        return Arrays.asList(gson.fromJson(responseBody.string(), Student[].class));
    }

    private String buildUrl(){
        return this.url + this.path;
    }
}
