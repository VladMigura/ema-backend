package com.itechart.ema.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechart.ema.util.VersionUtil;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SwaggerApiController {

    private static final String CONFIG_UI_FILE_PATH = "classpath:/swagger/configuration-ui.json";
    private static final String CONFIG_SECURITY_FILE_PATH = "classpath:/swagger/configuration-security.json";
    private static final String CONFIG_FILE_PATH = "classpath:/swagger/configuration.json";
    private static final String SWAGGER_FILE_PATH = "classpath:/swagger/swagger.yaml";

    public static final String SWAGGER_UI_REDIRECT_PATH = "/";
    public static final String GET_UI_CONFIGURATION_PATH = "/swagger-resources/configuration/ui";
    public static final String GET_SECURITY_CONFIGURATION_PATH = "/swagger-resources/configuration/security";
    public static final String GET_CONFIGURATION_PATH = "/swagger-resources";
    public static final String GET_SWAGGER_YML_PATH = "/swagger.yml";
    public static final String GET_SWAGGER_YAML_PATH = "/swagger.yaml";
    public static final String GET_SWAGGER_JSON_PATH = "/swagger.json";

    private final String uiConfiguration;
    private final String securityConfiguration;
    private final String configuration;
    private final String swaggerYaml;
    private final String swaggerJson;

    @SneakyThrows
    public SwaggerApiController(final ResourceLoader resourceLoader,
                                final ObjectMapper objectMapper,
                                final Yaml yaml) {
        var configurationUiJson = loadJson(objectMapper, resourceLoader, CONFIG_UI_FILE_PATH);
        var configurationSecurityJson = loadJson(objectMapper, resourceLoader, CONFIG_SECURITY_FILE_PATH);
        var configurationJson = loadJson(objectMapper, resourceLoader, CONFIG_FILE_PATH);
        var swaggerYaml = populateVersion(loadYaml(yaml, resourceLoader, SWAGGER_FILE_PATH));
        this.uiConfiguration = objectMapper.writeValueAsString(configurationUiJson);
        this.securityConfiguration = objectMapper.writeValueAsString(configurationSecurityJson);
        this.configuration = objectMapper.writeValueAsString(configurationJson);
        this.swaggerYaml = yaml.dump(swaggerYaml);
        this.swaggerJson = objectMapper.writeValueAsString(swaggerYaml);
    }

    @GetMapping(SWAGGER_UI_REDIRECT_PATH)
    public String swaggerUiRedirect() {
        return "redirect:swagger-ui.html";
    }

    @ResponseBody
    @GetMapping(GET_UI_CONFIGURATION_PATH)
    public String uiConfiguration() {
        return uiConfiguration;
    }

    @ResponseBody
    @GetMapping(GET_SECURITY_CONFIGURATION_PATH)
    public String securityConfiguration() {
        return securityConfiguration;
    }

    @ResponseBody
    @GetMapping(GET_CONFIGURATION_PATH)
    public String configuration() {
        return configuration;
    }

    @ResponseBody
    @GetMapping(value = {GET_SWAGGER_YAML_PATH, GET_SWAGGER_YML_PATH}, produces = "text/yaml")
    public String swaggerYaml() {
        return swaggerYaml;
    }

    @ResponseBody
    @GetMapping(GET_SWAGGER_JSON_PATH)
    public String swaggerJson() {
        return swaggerJson;
    }

    @SneakyThrows
    private Map<String, Object> loadYaml(final Yaml yaml,
                                         final ResourceLoader resourceLoader,
                                         final String location) {
        return yaml.load(resourceLoader.getResource(location).getInputStream());
    }

    @SneakyThrows
    private JsonNode loadJson(final ObjectMapper objectMapper,
                              final ResourceLoader resourceLoader,
                              final String location) {
        return objectMapper.readTree(resourceLoader.getResource(location).getInputStream());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> populateVersion(final Map<String, Object> swaggerYaml) {
        if (!swaggerYaml.containsKey("info")) {
            swaggerYaml.put("info", new HashMap<String, Object>());
        }
        var info = (Map<String, Object>) swaggerYaml.get("info");
        var description = "**App version**: `" + "0.0.1-SNAPSHOT"
                + "`\n\n**Spring Boot version**: `" + VersionUtil.springBootVersion()
                + "`\n\n**Java version**: `" + VersionUtil.javaVersion()
                + "`\n\n**Java vendor**: `" + VersionUtil.javaVendor() + "`";
        info.put("description", description);
        return swaggerYaml;
    }

}
