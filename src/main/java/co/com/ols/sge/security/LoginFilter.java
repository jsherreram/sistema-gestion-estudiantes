/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.security;

import co.com.ols.sge.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author jsherreram
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        // Obtenemos el body de la peticion que asumimos viene en formato JSON.
        InputStream body = req.getInputStream();

        // Asumimos que el body tendrá el siguiente JSON  {"username":"admin", "password":"123"}. Realizamos un 
        // mapeo a nuestra clase Usuario para tener ahi los datos.
        Usuario usuario = new ObjectMapper().readValue(body, Usuario.class);

        // Finalmente autenticamos, Spring comparará el user/password recibidos contra el que definimos en la clase
        // SecurityConfig.
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        usuario.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        // Si la autenticacion fue exitosa, agregamos el token a la respuesta.
        JwtUtil.addAuthentication(res, auth.getName());
    }

}
