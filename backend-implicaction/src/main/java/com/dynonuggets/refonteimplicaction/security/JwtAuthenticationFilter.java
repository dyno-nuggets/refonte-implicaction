package com.dynonuggets.refonteimplicaction.security;

import com.dynonuggets.refonteimplicaction.dto.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ERROR_RESPONSE_CONTENT_TYPE = "application/json";
    private static final String HEADER_AUTH_PREFIX = "Authorization";
    private static final String AUTH_TYPE_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.isNotBlank(jwt) && jwtProvider.validateToken(jwt)) {
                String username = jwtProvider.getUsernameFromJwt(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            // Si une exception est lancée dans le filter, elle n'est pas traitée par le GlobalExceptionHandler
            // on force donc une erreur 401 (Unauthorized) dans la réponse.
            ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                    .errorMessage(ex.getMessage())
                    .errorCode(UNAUTHORIZED.value())
                    .timestamp(LocalDateTime.now())
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(ERROR_RESPONSE_CONTENT_TYPE);
            response.getWriter().write(exceptionResponse.toString());
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTH_PREFIX);
        // le header 'Authorization' est de la forme 'Bearer <token>', pour obtenir le token il suffit de supprimer 'Bearer '
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(AUTH_TYPE_PREFIX)) {
            return bearerToken.substring(AUTH_TYPE_PREFIX.length());
        }
        return bearerToken;
    }
}
