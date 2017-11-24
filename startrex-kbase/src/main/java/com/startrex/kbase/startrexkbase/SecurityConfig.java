package com.startrex.kbase.startrexkbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.startrex.kbase.startrexkbase.services.JwtAuthenticationEntryPoint;
import com.startrex.kbase.startrexkbase.services.JwtAuthenticationTokenFilter;

@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

   // @Autowired
   // private UserDetailsService userDetailsService;
    
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/course/**").permitAll()		
				.antMatchers("/students/**").hasRole("USER")			
				.and()
			.formLogin()
				.loginPage("/login").failureUrl("/login-error");	
		http.csrf().disable(); 
	}*/ 	

   /* @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		// we don't need CSRF because our token is invulnerable
        http.csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        // don't create session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // allow anonymous resource requests
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .antMatchers(
                HttpMethod.GET, 
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"                
        ).permitAll()    
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/openportal/**").permitAll()
        .anyRequest().authenticated();

		// Custom JWT based security filter
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		http.headers().cacheControl();

		/*http.csrf().disable();
		http.authorizeRequests()
        	.antMatchers("/**")  // Disable authentication for all requests.
        	.permitAll()
        	.and()
        	.formLogin()
        	.loginPage("/user/login").failureUrl("/login-error");*/
       
	} 
	
//	@Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }
	

	
//	 @Bean
//	  public ViewResolver viewResolver() {
//	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//	    //templateResolver.setTemplateMode("XHTML");
//	    //templateResolver.setPrefix("views/");
//	    templateResolver.setSuffix(".html");
//
//	    SpringTemplateEngine engine = new SpringTemplateEngine();
//	    engine.setTemplateResolver(templateResolver);
//
//	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	    viewResolver.setTemplateEngine(engine);
//	    return viewResolver;
//	  }
	

}

