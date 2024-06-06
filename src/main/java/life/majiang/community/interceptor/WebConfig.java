package life.majiang.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sam
 * @create 2024-06-03 5:17 PM
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private SessionInterceptor sessionInterceptor;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    /** When you add @EnableWebMvc, you take full control of the MVC configuration, and
     * you must explicitly define where static resources are located.
     * registry.addResourceHandler("/css/**"): This line registers a resource handler and tells Spring MVC that any request pattern matching /css/** (which means any path under /css/) should be handled as a static resource.
     * .addResourceLocations("classpath:/static/css/"): This part specifies the location of
     * the static resources. It tells Spring where to look for the resources that match the
     * handler pattern. In this case, it’s saying that for requests matching /css/**, the
     * resources are located in the static/css directory within the classpath (typically inside
     * your src/main/resources directory).
     * */
    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");

    // Add other static resource handlers if needed
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    /** addPathPatterns对哪些路径进行拦截， excludePathPatterns对哪些路径进行略过
     * "/**" 表示所有地址都经过interceptor处理
     * */
    registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
  }
}
