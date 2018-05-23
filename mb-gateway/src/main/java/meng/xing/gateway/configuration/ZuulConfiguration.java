package meng.xing.gateway.configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import meng.xing.common.User.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class ZuulConfiguration extends ZuulFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZuulConfiguration.class);


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

    @Value("${zuul.prefix}")
    private String prefix;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("当前用户拥有的权限：{}", authentication.getAuthorities());

        if (request.getMethod().equals(HttpMethod.GET.toString()) && request.getRequestURI().startsWith(prefix + "/auth/roles")) {
            LOGGER.info("允许访问：{}", request.getRequestURL());
            return null;
        }
        if (request.getMethod().equals(HttpMethod.POST.toString()) && (request.getRequestURI().startsWith(prefix + "/auth/login/account") ||
                request.getRequestURI().startsWith(prefix + "/auth/register/account"))) {
            LOGGER.info("允许访问：{}", request.getRequestURL());
            return null;
        }
        // TODO: 2018/5/15 完善权限过滤
        LOGGER.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        LOGGER.info("无权访问：{}", request.getRequestURL());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(403);
        ctx.setResponseBody("{\"result\":\"没有权限!\"}");
        ctx.getResponse().setContentType("application.yml/json;charset=UTF-8");
        return null;
    }

    private boolean permit(Authentication authentication, RoleType roleType) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority(roleType.toString()));
    }
}
