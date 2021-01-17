package rest.models;

import com.solidfire.gson.Gson;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
