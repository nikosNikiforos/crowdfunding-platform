package com.example.crowdfunding.service;

import com.example.crowdfunding.entities.Contribution;
import com.example.crowdfunding.entities.Project;
import com.example.crowdfunding.entities.Supporter;
import com.example.crowdfunding.repository.ProjectRepository;
import com.example.crowdfunding.entities.Creator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    @Transactional
    public void saveProject (Project project){
        projectRepository.save(project);
    }

    @Transactional
    public Project getProject(Integer projectId){
        return projectRepository.findById(projectId).get();
    }

    @Transactional
    public void saveProject(Project project, Creator creator) {
        project.setCreator(creator);
        projectRepository.save(project);
    }
    @Transactional
    public void updateAmount(Integer projectId, double amount){
        Project pro = projectRepository.findById(projectId).get();
        pro.addAmount(amount);
        projectRepository.save(pro);
    }

    @Transactional
    public void updateProjectSupporterList(Project project, Supporter supporter){
        project.addSupporter(supporter);
        projectRepository.save(project);
    }

    @Transactional
    public void updateProjectContributionList(Project project, Contribution contribution){
        project.addContribution(contribution);
        projectRepository.save(project);
    }
    @Transactional
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }


}
