package rest.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidfire.gson.Gson;

import java.io.Serializable;

public class Query extends BaseModel {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
