package com.dynonuggets.refonteimplicaction.auth.filter;

import com.dynonuggets.refonteimplicaction.auth.service.JwtProvider;
import com.dynonuggets.refonteimplicaction.core.dto.ExceptionResponse;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
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
import java.util.List;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.getPublicUris;
import static com.dynonuggets.refonteimplicaction.core.dto.ExceptionResponse.from;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final List<String> JWT_DEACTIVATED_URIS = getPublicUris();

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return JWT_DEACTIVATED_URIS.contains(request.getRequestURI());
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain) {
        try {
            final String jwt = getJwtFromRequest(request);

            if (isNotBlank(jwt) && jwtProvider.validateToken(jwt)) {
                final String username = jwtProvider.getUsernameFromJwt(jwt);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (final RuntimeException ex) {
            // Si une exception est lancée dans le filter, elle n’est pas traitée par le GlobalExceptionHandler, on force donc une erreur dans la réponse.
            final ExceptionResponse exceptionResponse = ex instanceof ImplicactionException ?
                    from(((ImplicactionException) ex).getErrorResult()) : from(ex, INTERNAL_SERVER_ERROR);

            response.setStatus(exceptionResponse.getErrorCode());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.getWriter().write(exceptionResponse.toString());
        }
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HEADER_AUTHORIZATION_KEY);
        // le header 'Authorization' est de la forme 'Bearer <token>', pour obtenir le token il suffit de supprimer 'Bearer '
        if (isNotBlank(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return bearerToken;
    }
}
