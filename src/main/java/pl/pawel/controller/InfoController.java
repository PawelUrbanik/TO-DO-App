package pl.pawel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.TaskConfigurationProperties;

@RestController
public class InfoController {

    @Autowired
    private DataSourceProperties properties;
    private TaskConfigurationProperties configurationProperties;

    public InfoController(DataSourceProperties properties, TaskConfigurationProperties configurationProperties) {
        this.properties = properties;
        this.configurationProperties = configurationProperties;
    }

    @GetMapping("/info/url")
    public String url(){
        return properties.getUrl();
    }
    @GetMapping("/info/prop")
    public boolean prop()
    {
        return configurationProperties.getTemplte().isAllowMultipleTasks();
    }
}
