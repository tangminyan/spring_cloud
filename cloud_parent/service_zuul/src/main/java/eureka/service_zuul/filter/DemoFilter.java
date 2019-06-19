package eureka.service_zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tangminyan on 2019/6/19.
 */
@Component
public class DemoFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(DemoFilter.class);

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
        String str = String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString());
        logger.info(str);
        return null;
    }
}
