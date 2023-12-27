package com.later.core.configurer;

import com.later.common.converter.Converter;
import com.later.common.converter.IConverter;
import com.later.core.filters.CorsFilter;
import com.later.core.filters.TraceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class BeanConfigurer {

    private final CommonConfigurer commonConfigurer;

    BeanConfigurer(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("currentAuditor");
    }

    @Bean
    public IConverter iConverter() {
        return new Converter();
    }

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        return new FilterRegistrationBean<TraceFilter>() {{
            setFilter(new TraceFilter(commonConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        return new FilterRegistrationBean<CorsFilter>() {{
            setFilter(new CorsFilter(commonConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }


}
