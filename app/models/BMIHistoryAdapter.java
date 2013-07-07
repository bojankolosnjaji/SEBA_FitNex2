package models;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BMIHistoryAdapter implements JsonSerializer<BMIHistory> {

    @Override
    public JsonElement serialize(BMIHistory history, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", history.id);
        jsonObject.addProperty("y", history.BMIValue);
        return jsonObject;      
    }
}