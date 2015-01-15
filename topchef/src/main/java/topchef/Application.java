package topchef;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;

import topchef.listeners.SessionListener;

@Configuration
@ComponentScan(basePackages={"topchef.repositories", "topchef.controllers", "topchef.filters", "topchef.security", "topchef.services", "topchef.exceptions"}) 
@EnableAutoConfiguration
@Import({MongoFactoryConfig.class, SecurityConfiguration.class})
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
                
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {    	
        return application.sources(applicationClass);
    }
    
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
    	super.onStartup(sc);
        sc.addListener(new SessionListener());    	
    }
        
    private static Class<Application> applicationClass = Application.class;   
}
