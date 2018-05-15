package meng.xing.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 自定义的Filter
 * 通过jwt实现无状态的api保护
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);
    @Autowired
    private UserDetailsServiceIml userDetailsService;


    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(this.tokenHeader);
        if (token != null && token.startsWith(tokenHead)) {
            Optional<UserDetails> optionalUserDetails = userDetailsService.loadUserByToken(token);
            if (optionalUserDetails.isPresent()) {
                UserDetails userDetails = optionalUserDetails.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("权限验证成功,authentication：{},request:{}", authentication, request);
            } else {
                LOGGER.warn("token验证失败：{},request：{}", token, request);
                chain.doFilter(request, response);
            }
        } else {
            LOGGER.warn("token为空或者格式错误：{},request：{}", token, request);
            chain.doFilter(request, response);
        }

    }
}

