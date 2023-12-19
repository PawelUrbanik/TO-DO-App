package pl.pawel.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pawel.model.Project;
import pl.pawel.model.ProjectSteps;
import pl.pawel.model.projection.ProjectWriteModel;
import pl.pawel.service.ProjectService;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "projects";
        }
        projectService.createProject(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProjects());
        model.addAttribute("message", "Dodano projekt");
        return "projects";
    }


    @PostMapping("/{projectId}")
    public String createGroup(
            Model model,
            @ModelAttribute("project") ProjectWriteModel current,
            @PathVariable(name = "projectId") int projectId,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline){

        try {
            projectService.createGroup(projectId, deadline);
            model.addAttribute("message", "Dodano grupę!");
        } catch (IllegalStateException | IllegalArgumentException exception) {
            model.addAttribute("message", "Wystąpił błąd podczas dodawania grupy");
        }
        return "projects";
    }
    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.getAll();
    }

}

