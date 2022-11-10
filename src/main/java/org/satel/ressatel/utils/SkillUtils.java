package org.satel.ressatel.utils;

import jakarta.enterprise.context.ApplicationScoped;
import org.satel.ressatel.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component("skillUtils")
@ApplicationScoped
public class SkillUtils {
    public List<String> getHierarchicalSkillsRepresentation(List<Skill> skills) {
        List<String> strings = new ArrayList<>();
        skills.forEach(skill -> {
            if (skill.getParent() == null) {
                AtomicReference<Integer> level = new AtomicReference<>(0);
                addSkills(skill, strings, skills, level);
            }
        });
        List<String> hierarchy = new ArrayList<>();
        strings.forEach(s -> {
            String first = s.substring(0, 1);
            String other = s.substring(1);
            hierarchy.add("--".repeat(Integer.parseInt(first)) + other);
        });
        return hierarchy;
    }

    private void addSkills(Skill skill, List<String> strings, List<Skill> skills, AtomicReference<Integer> level) {
        strings.add(level.get() + skill.getName());
        skills.forEach(skl -> {
            if (skl.getParent() != null && skl.getParent().getId().intValue() == skill.getId().intValue()) {
                level.getAndSet(level.get() + 1);
                addSkills(skl, strings, skills, level);
                level.getAndSet(level.get() - 1);
            }
        });
    }
}
