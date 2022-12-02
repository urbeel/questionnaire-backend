package by.urbel.questionnaireportal.filter;

import by.urbel.questionnaireportal.constants.Routes;
import by.urbel.questionnaireportal.constants.WebSockets;
import by.urbel.questionnaireportal.security.JwtTokenUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestUrl = request.getRequestURL().toString();
        if (isNoAuthUrl(requestUrl) || isCreateAnswerRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String token = authorizationHeader.substring(7);
        String email;
        try {
            email = jwtTokenUtil.validateTokenAndGetEmail(token);
        } catch (JWTVerificationException e) {
            logger.warn(e.getMessage());
            SecurityContextHolder.getContext().setAuthentication(null);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean isNoAuthUrl(String requestUrl) {
        return requestUrl.endsWith(Routes.SIGNUP)
                || requestUrl.endsWith(Routes.LOGIN)
                || requestUrl.endsWith(Routes.ACTIVE_FIELDS)
                || requestUrl.contains(WebSockets.WS_ENDPOINT);
    }

    private boolean isCreateAnswerRequest(HttpServletRequest request) {
        return request.getRequestURL().toString().endsWith(Routes.Q_ANSWERS)
                && request.getMethod().equalsIgnoreCase(HttpMethod.POST.name());

    }
}
