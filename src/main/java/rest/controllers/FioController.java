package rest.controllers;

import com.solidfire.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import rest.models.Fio;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.models.Query;
import rest.services.FioService;

@RestController
public class FioController {
    private final FioService fioService;

    @Autowired
    public FioController(FioService fioService) {
        this.fioService = fioService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Fio getFio(@RequestBody String query) {
        Gson gson = new Gson();
        return fioService.getFio(gson.fromJson(query, Query.class));
    }
}
