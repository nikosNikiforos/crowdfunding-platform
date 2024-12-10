package com.example.crowdfunding.controller;

import com.example.crowdfunding.entities.Creator;
import com.example.crowdfunding.entities.Project;
import com.example.crowdfunding.service.CreatorService;
import com.example.crowdfunding.service.ProjectService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/creator")
public class CreatorController {

        private final CreatorService creatorService;
        private final ProjectService projectService;

        public CreatorController(CreatorService creatorService, ProjectService projectService) {
                this.creatorService = creatorService;
                this.projectService = projectService;
        }

        // **1. Εγγραφή νέου Creator**
        @GetMapping("/register")
        public String showRegisterForm(Model model) {
                model.addAttribute("creator", new Creator());
                return "creator/register";
        }

        @PostMapping("/register")
        public String registerCreator(@ModelAttribute("creator") Creator creator) {
                creatorService.saveCreator(creator);
                return "redirect:/creator/login";
        }

        // **2. Login για Creator**
        @GetMapping("/login")
        public String showLoginForm() {
                return "creator/login";
        }

        @PostMapping("/login")
        public String processLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
                Creator creator = creatorService.findByUsername(username);
                if (creator != null && creator.getPassword().equals(password)) {
                        session.setAttribute("loggedInCreator", creator);
                        return "redirect:/creator/my-projects";
                }
                model.addAttribute("error", "Invalid username or password");
                return "creator/login";
        }

        // **3. Προβολή των έργων του δημιουργού**
        @GetMapping("/my-projects")
        public String viewMyProjects(HttpSession session, Model model) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                creator = creatorService.getCreatorById(creator.getId());
                Hibernate.initialize(creator.getProjects());
                model.addAttribute("projects", creator.getProjects());
                model.addAttribute("username", creator.getUsername());
                return "creator/my_projects";
        }

        // **4. Δημιουργία νέου έργου**
        @GetMapping("/new-project")
        public String newProjectForm(HttpSession session, Model model) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                model.addAttribute("project", new Project());
                model.addAttribute("username", creator.getUsername());
                return "creator/new_project_form";
        }

        @PostMapping("/new-project")
        public String createProject(@ModelAttribute("project") Project project, HttpSession session) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                project.setCreator(creator);
                projectService.saveProject(project);
                return "redirect:/creator/my-projects";
        }

        // **5. Επεξεργασία έργου**
        @GetMapping("/edit-project/{id}")
        public String editProjectForm(@PathVariable("id") Integer id, HttpSession session, Model model) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                Project project = projectService.getProject(id);
                model.addAttribute("project", project);
                model.addAttribute("username", creator.getUsername());
                return "creator/edit_project_form";
        }

        @PostMapping("/edit-project/{id}")
        public String updateProject(@PathVariable("id") Integer id, @ModelAttribute("project") Project updatedProject, HttpSession session) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                Project existingProject = projectService.getProject(id);
                existingProject.setTitle(updatedProject.getTitle());
                existingProject.setDescription(updatedProject.getDescription());
                existingProject.setRequiredFunding(updatedProject.getRequiredFunding());
                projectService.saveProject(existingProject);
                return "redirect:/creator/my-projects";
        }

        // **6. Διαγραφή έργου**
        @GetMapping("/delete-project/{id}")
        public String deleteProject(@PathVariable("id") Integer id, HttpSession session) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                projectService.deleteProject(id);
                return "redirect:/creator/my-projects";
        }

        // **7. Logout**
        @GetMapping("/logout")
        public String logout(HttpSession session) {
                session.invalidate();
                return "redirect:/creator/login";
        }
        @GetMapping("/menu")
        public String showMenu(HttpSession session, Model model) {
                Creator creator = (Creator) session.getAttribute("loggedInCreator");
                if (creator == null) {
                        return "redirect:/creator/login";
                }
                model.addAttribute("username", creator.getUsername());
                return "creator/menu"; // Το αντίστοιχο HTML template
        }

}
