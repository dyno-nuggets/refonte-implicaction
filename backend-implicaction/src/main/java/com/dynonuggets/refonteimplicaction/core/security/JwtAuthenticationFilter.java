package com.dynonuggets.refonteimplicaction.core.security;

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

import static com.dynonuggets.refonteimplicaction.core.dto.ExceptionResponse.from;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
            // Si une exception est lancée dans le filter, elle n'est pas traitée par le GlobalExceptionHandler
            // on force donc une erreur dans la réponse.
            final ExceptionResponse exceptionResponse = ex instanceof ImplicactionException ?
                    from(((ImplicactionException) ex).getErrorResult()) : ExceptionResponse.from(ex, INTERNAL_SERVER_ERROR);

            response.setStatus(exceptionResponse.getErrorCode());
            response.setContentType(ERROR_RESPONSE_CONTENT_TYPE);
            response.getWriter().write(exceptionResponse.toString());
        }
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HEADER_AUTH_PREFIX);
        // le header 'Authorization' est de la forme 'Bearer <token>', pour obtenir le token il suffit de supprimer 'Bearer '
        if (isNotBlank(bearerToken) && bearerToken.startsWith(AUTH_TYPE_PREFIX)) {
            return bearerToken.substring(AUTH_TYPE_PREFIX.length());
        }
        return bearerToken;
    }
}
