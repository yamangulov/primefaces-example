package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.repository.SkillRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ApplicationScoped
@Log4j2
public class SkillService {
    private final SkillRepository skillRepository;

    @Inject
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public static Set<Skill> getSkillsWithParents(Set<Skill> skills) {
        Set<Skill> parentsSet = new HashSet<>();
        skills.forEach(skill -> {
            List<Skill> skillList = new ArrayList<>();
            parentsSet.addAll(getParents(skill, skillList));
        });
        Set<Skill> newSet = new HashSet<>(skills);
        newSet.addAll(parentsSet);
        return newSet;
    }

    private static List<Skill> getParents(Skill skill, List<Skill> skills) {
        if (skill.getParent() != null) {
            skills.add(skill.getParent());
            getParents(skill.getParent(), skills);
        }
        return skills;
    }

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    private Skill getByName(String name) {
        return skillRepository.findByName(name);
    }

    public List<Skill> getSkillListByNames(List<String> names) {
        List<Skill> skills = new ArrayList<>();
        names.forEach(name -> skills.add(getByName(name)));
        return skills;
    }

    public Set<Skill> getSkillsByNames(List<String> names) {
        return new HashSet<>(getSkillListByNames(names));
    }

    public List<String> getSkillNames() {
        List<Skill> skills = getSkills();
        List<String> names = new ArrayList<>();
        skills.forEach(skill -> names.add(skill.getName()));
        return names;
    }

    public DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> getTreeNodeOfSkills() {
        DefaultTreeNode<org.satel.ressatel.bean.list.skill.Skill> root = new DefaultTreeNode<>(new org.satel.ressatel.bean.list.skill.Skill(0, "Компетенции", "Folder"), null);
        List<Skill> skills = getSkills();
        Map<Integer, Skill> parents = new HashMap<>();
        skills.forEach(skill -> parents.put(skill.getId(), skill.getParent() == null ? null : skill.getParent()));
        Map<Integer, TreeNode<org.satel.ressatel.bean.list.skill.Skill>> nodes = new HashMap<>();
        skills.forEach(skill -> {
            nodes.put(
                    skill.getId(),
                    new DefaultTreeNode<>(
                            new org.satel.ressatel.bean.list.skill.Skill(skill.getId(), skill.getName(), "Folder")
                    )
            );
        });
        new ArrayList<>(nodes.values()).forEach(node -> {
            Skill parent = parents.get(node.getData().getId());
            TreeNode<org.satel.ressatel.bean.list.skill.Skill> parentNode
                    = parent == null ? root : nodes.get(parent.getId());
            node.setParent(parentNode);
            parentNode.getChildren().add(node);
        });
        return root;
    }

    public Skill getById(Integer skillId) {
        return skillRepository.findById(skillId).orElse(null);
    }
}
