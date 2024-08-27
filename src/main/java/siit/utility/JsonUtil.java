package siit.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import siit.config.json.adapter.LocalDateTimeTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class JsonUtil {

    public static JSONObject getJsonObjFrom(HttpServletRequest request) throws IOException {
        String jsonString = "";
        String line;
        BufferedReader reader = request.getReader();

        while ((line = reader.readLine()) != null) {
            jsonString += line;
        }
        return new JSONObject(jsonString);
    }

    public static String convertToJsonString(Object obj){
        Gson jsonMapper = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        return jsonMapper.toJson(obj);
    }
}
