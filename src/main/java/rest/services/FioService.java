package rest.services;

import org.springframework.stereotype.Component;
import rest.models.Fio;
import rest.models.Query;

@Component
public class FioService {
    private final String defaultFio = "Test Testov";

    public Fio getFio(Query query) {
        Fio result = null;
        if (query != null && query.getId() == 1) {
            result = new Fio(defaultFio);
        }
        return result;
    }
}
