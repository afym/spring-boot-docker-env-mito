package http;

import com.google.gson.Gson;
import entity.Student;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddStudentHttp {
    private String url;
    private String path;
    private String method;
    private Response response;
    private List<String> students;
    private List<Integer> responseStatusCodes;

    public AddStudentHttp() {
        this.students = new ArrayList<>();
        this.responseStatusCodes = new ArrayList<>();
    }

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

    public void addStudent(Student student) throws IOException {
        Gson gson = new Gson();
        this.students.add(gson.toJson(student, Student.class));
    }

    public void make() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        for (int i = 0; i < this.students.size(); i++) {
            RequestBody body = RequestBody.create(this.students.get(i), mediaType);

            Request request = new Request.Builder()
                    .url(this.buildUrl())
                    .addHeader("Content-Type", "application/json")
                    .method(this.method, body)
                    .build();

            this.response = client.newCall(request).execute();
            this.responseStatusCodes.add(this.response.code());
        }
    }

    public List<Integer> getResponseStatusCodes() {
        return this.responseStatusCodes;
    }

    private String buildUrl() {
        return this.url + this.path;
    }
}
