/*package huli.example.huliwebshop.security.jwt;

import huli.example.huliwebshop.security.jwt.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // we can intercept requests and extract data from them
            HttpServletResponse response, // with response, we can provide new data
            FilterChain filterChain // contains the list of other filters that we might need to execute - can call filters in order of responsibility
    ) {

        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwtToken;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwtToken = authHeader.substring(7);
            userEmail = jwtUtil.extractUsername(jwtToken); // extract email from jwt token

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // we check if the username is not null & check if the user already was authenticated - if he was, we don't need to go through all this again
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                    // this object is needed by spring and the security context holder to update security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // here we need to pass more details out of our http request
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // here we update security context holder with our authToken
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // we need always to call our filterChain to pass to the next filters to get executed
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }
}
*/