package meng.xing.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import meng.xing.common.User.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器，实现权限控制
 */
@Component
public class AuthFilter extends ZuulFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("当前用户拥有的权限：{}", authentication.getAuthorities());

        if (request.getMethod().equals(HttpMethod.GET.toString()) && request.getRequestURI().startsWith("/mb-user/role"))
            return null;
        if (request.getMethod().equals(HttpMethod.POST.toString()) && (request.getRequestURI().startsWith("/mb-user/user/login") ||
                request.getRequestURI().startsWith("/mb-user/user/register")))
            return null;
        // TODO: 2018/5/15 完善权限过滤 
        LOGGER.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        LOGGER.info("无权访问：{}", request.getRequestURI());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(403);
        ctx.setResponseBody("{\"result\":\"没有权限!\"}");
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        return null;
    }

    private boolean permit(Authentication authentication, RoleType roleType) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(roleType.toString()));
    }
}
