package gentjanahani.registro_elettronico_backend.runners;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.payloads.request.RegisterDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.RuoloDTO;
import gentjanahani.registro_elettronico_backend.services.RuoloService;
import gentjanahani.registro_elettronico_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MyRunner implements CommandLineRunner {

    private final RuoloService ruoloService;
    private final UserService userService;


    @Value("${admin.name}")
    private String adminName;
    @Value("${admin.lastname}")
    private String adminLastName;
    @Value("${admin.dateOfBirth}")
    private LocalDate adminDateOfBirth;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.pwd}")
    private String adminPwd;

    @Autowired
    public MyRunner(RuoloService ruoloService, UserService userService) {
        this.ruoloService = ruoloService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {


        // Crea ruolo ADMIN
        if (!this.ruoloService.existsByRuolo("ADMIN")) {
            Ruolo ruoloAdmin = this.ruoloService.addRuolo(new RuoloDTO("ADMIN"));
            System.out.println("Ruolo creato: " + ruoloAdmin);
        }

        // Crea  admin di default
        boolean utenteAdminExistFromDB = this.userService.existByEmail(adminEmail);
        if (!utenteAdminExistFromDB) {
            RegisterDTO admin = new RegisterDTO(adminName, adminLastName, adminDateOfBirth, adminEmail, adminPwd, "ADMIN", null, null, null);
            User userAdmin = this.userService.register(admin);
            System.out.println("Utente admin creato: " + userAdmin.getEmail());
        }
    }
}
