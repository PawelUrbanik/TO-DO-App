package pl.pawel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawel.model.Project;
import pl.pawel.model.ProjectSteps;
import pl.pawel.model.projection.ProjectWriteModel;
import pl.pawel.service.ProjectService;

import java.util.List;

@Controller
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

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

    @PostMapping
    String addProject(@ModelAttribute("project") ProjectWriteModel current, Model model) {
        projectService.createProject(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message", "Dodano projekt");
        return "projects";
    }


    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.getAll();
    }

}

