package topchef.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

@Component
public class CsrfTokenHeaderFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		
		CsrfToken token = (CsrfToken)req.getAttribute("_csrf");
		if(token != null) { 
			response.setHeader("X-CSRF-HEADER", token.getHeaderName());
			response.setHeader("X-CSRF-PARAM", token.getParameterName());
			response.setHeader("X-CSRF-TOKEN", token.getToken());
		}
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}
}
