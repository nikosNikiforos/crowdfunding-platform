package com.example.crowdfunding.controller;


import com.example.crowdfunding.entities.Contribution;
import com.example.crowdfunding.entities.Project;
import com.example.crowdfunding.entities.Supporter;
import com.example.crowdfunding.service.ContributionService;
import com.example.crowdfunding.service.ProjectService;
import com.example.crowdfunding.service.SupporterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;
    private SupporterService supporterService;
    private ContributionService contributionService;

    public ProjectController(ProjectService projectService, SupporterService supporterService, ContributionService contributionService) {
        this.projectService = projectService;
        this.supporterService = supporterService;
        this.contributionService = contributionService;
    }

    @GetMapping("")
    public String showProjects(Model model) {
        model.addAttribute("listProjects", projectService.getProjects());
        return "project/project";
    }


    @GetMapping("/new")
    public String addProject(Model model) {
        Project pro = new Project();
        model.addAttribute("emptyProject", pro);
        return "project/project_form";
    }

    @PostMapping("/new")
    public String saveProject(@ModelAttribute("pro") Project project, Model model) {
        System.out.println("List of projects before: " + projectService.getProjects());
        projectService.saveProject(project);
        System.out.println("List of projects after: " + projectService.getProjects());
        model.addAttribute("listProjects", projectService.getProjects());
        return "project/project";
    }

    @GetMapping("/details/{id}")
    public String projectDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("supList", supporterService.getSupporters());
        model.addAttribute("project", projectService.getProject(id));
        return "project/project_details";
    }


    @PostMapping("/support/{id}")
    public String supportProject(@PathVariable Integer id, @RequestParam("amount") double amount, @RequestParam("supId") Integer supId
            , Model model) {

        //We bring the project and the supporter based on their IDs
        Project project = projectService.getProject(id);
        Supporter supporter = supporterService.getSupporter(supId);

        //I must make sure that the support cannot support the same project twice
        if (supporter.validateSupport(id)){
            System.out.println ("The supporter already supports this project");
            return "index";
        }
        //I create a new contribution with the amount, the supporter and the project, and saves it
        Contribution contribution = new Contribution(amount, project, supporter);
        contributionService.saveContribution(contribution);

        //Adding the amount of that the supporter provided to the total amount of the project
        projectService.updateAmount(id, amount);

        //Adding the contribution to the project_contribution list
        projectService.updateProjectContributionList(project, contribution);

        //Adding the contribution to the supporter_contribution list
        supporterService.updateSupporterContributionList(supporter, contribution);

        //Adding the project to the list of the supporter_projects
        supporterService.updateSupporterProjectList(project, supporter);

        //Adding the supporter to the list of the project_supporter
        projectService.updateProjectSupporterList(project, supporter);

        model.addAttribute("listProjects", projectService.getProjects());

        return "project/project";
    }





}
