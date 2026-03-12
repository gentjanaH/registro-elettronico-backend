package gentjanahani.registro_elettronico_backend.security;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.UnauthorizedException;
import gentjanahani.registro_elettronico_backend.sevices.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools ;
    private final UserService userService;

@Autowired
    public JWTCheckerFilter(JWTTools jwtTools, UserService userService) {
        this.jwtTools = jwtTools;
    this.userService = userService;
}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //verifico che la richiesta contenga il token e che sia nel formato corretto
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("errore nell'inserimento del token");
        //estraggo il token dall'header
        String accessToken = authHeader.substring(7);
        //verifico che il token sia valido
        jwtTools.verifyToken(accessToken);
        //se tutto ok, andiamo avanti

        //------------------------AUTORIZZAZIONE------------------
        //Prima leggiamo l'id
        UUID idUtente = jwtTools.extractIdFromToken(accessToken);
        User utenteAutenticato = this.userService.findById(idUtente);

        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteAutenticato, null, utenteAutenticato.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/login", request.getServletPath()) ||
                new AntPathMatcher().match("/auth/register", request.getServletPath());
    }
}
