package kz.iitu.spring_lab_01.web;

import kz.iitu.spring_lab_01.config.AppProperties;
import kz.iitu.spring_lab_01.config.EnvironmentBanner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(AppProperties props,
                          EnvironmentBanner banner,
                          Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> response = new HashMap<>();
        response.put("owner", props.owner());
        response.put("group", props.group());
        response.put("mailFrom", props.mail().from());
        response.put("retryCount", props.mail().retryCount());
        response.put("timeout", props.mail().timeout().toString());
        response.put("mailEnabled", props.mail().enabled());

        // NEW: individual assignment (variant 11)
        response.put("reportTimezone", props.report().timezone());
        response.put("reportRetention", props.report().retention().toString());
        response.put("reportIncludeCharts", props.report().includeCharts());

        response.put("banner", banner.describe());
        response.put("activeProfiles", List.of(environment.getActiveProfiles()));
        response.put("port", environment.getProperty("server.port"));
        return response;
    }
}