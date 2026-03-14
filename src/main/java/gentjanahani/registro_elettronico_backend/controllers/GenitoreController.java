package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.sevices.GenitoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genitori")
public class GenitoreController {

    private final GenitoreService genitoreService;

    @Autowired
    public GenitoreController(GenitoreService genitoreService) {
        this.genitoreService = genitoreService;
    }

    //    endpoint per giustificare un assenza
    //    endpoint per visualizzare i profili dei propri figli
    //    endpoint per visualizzare il profilo di un figlio
}
