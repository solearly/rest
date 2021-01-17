package rest.models;

import com.solidfire.gson.Gson;

import java.io.Serializable;

public class Fio extends BaseModel {
    private String fio;

    public Fio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
