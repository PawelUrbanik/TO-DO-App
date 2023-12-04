package pl.pawel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawel.model.ProjectSteps;
import pl.pawel.model.projection.ProjectWriteModel;

@Controller
@RequestMapping("projects")
public class ProjectController {

    @GetMapping
    String showProjects(Model model) {
        final ProjectWriteModel attributeValue = new ProjectWriteModel();
        attributeValue.setDescription("TEST");
        model.addAttribute("project", attributeValue);
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }

}

