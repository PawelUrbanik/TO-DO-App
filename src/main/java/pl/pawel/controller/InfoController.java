package pl.pawel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawel.TaskConfigurationProperties;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private DataSourceProperties properties;
    private TaskConfigurationProperties configurationProperties;

    public InfoController(DataSourceProperties properties, TaskConfigurationProperties configurationProperties) {
        this.properties = properties;
        this.configurationProperties = configurationProperties;
    }

    @GetMapping("/url")
    public String url(){
        return properties.getUrl();
    }
    @GetMapping("/prop")
    public boolean prop()
    {
        return configurationProperties.getTemplte().isAllowMultipleTasks();
    }
}
