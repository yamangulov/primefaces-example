package org.satel.ressatel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CardController {

    @GetMapping("/company/{id}")
    public String getCompanyCard(@PathVariable String id) {
        return "/company.xhtml?id=" + id;
    }

    @GetMapping("/company/edit/{id}")
    public String getCompanyEditCard(@PathVariable String id) {
        return "/company_edit.xhtml?id=" + id;
    }

    @GetMapping("/person/{id}")
    public String getPersonCard(@PathVariable String id) {
        return "/person.xhtml?id=" + id;
    }

    @GetMapping("/person/edit/{id}")
    public String getPersonEditCard(@PathVariable String id) {
        return "/person_edit.xhtml?id=" + id;
    }

    @GetMapping("/person/skills/{id}")
    public String getPersonSkillsCard(@PathVariable String id) {
        return "/person_skills.xhtml?id=" + id;
    }

    @GetMapping("/person/skills/edit/{id}")
    public String getPersonSkillsEditCard(@PathVariable String id) {
        return "/person_skills_edit.xhtml?id=" + id;
    }

    @GetMapping("/person/occupation/{id}")
    public String getPersonOccupationCard(@PathVariable String id) {
        return "/person_occupation.xhtml?id=" + id;
    }

    @GetMapping("/person/occupation/edit/{id}")
    public String getPersonOccupationEditCard(@PathVariable String id) {
        return "/person_occupation_edit.xhtml?id=" + id;
    }

    @GetMapping("/person/command/{id}")
    public String getPersonCommandCard(@PathVariable String id) {
        return "/person_command.xhtml?id=" + id;
    }

    @GetMapping("/person/command/edit/{id}")
    public String getPersonCommandEditCard(@PathVariable String id) {
        return "/person_command_edit.xhtml?id=" + id;
    }

    @GetMapping("/person/specialties/{id}")
    public String getPersonSpecialtiessCard(@PathVariable String id) {
        return "/person_specialties.xhtml?id=" + id;
    }

    @GetMapping("/person/specialties/edit/{id}")
    public String getPersonSpecialtiesEditCard(@PathVariable String id) {
        return "/person_specialties_edit.xhtml?id=" + id;
    }

    @GetMapping("/employee/{id}")
    public String getEmployeeCard(@PathVariable String id) {
        return "/employee.xhtml?id=" + id;
    }

    @GetMapping("/employee/edit/{id}")
    public String getEmployeeEditCard(@PathVariable String id) {
        return "/employee_edit.xhtml?id=" + id;
    }

    @GetMapping("/employee/skills/edit-main-skills/{id}/{roleId}")
    public String getEmployeeSkillsEditCard(@PathVariable String id, @PathVariable String roleId) {
        return "/employee_skills_edit.xhtml?id=" + id + "&roleId=" + roleId;
    }

    @GetMapping("/employee/skills/edit-extra-skills/{id}/{roleId}")
    public String getEmployeeExtraSkillsEditCard(@PathVariable String id, @PathVariable String roleId) {
        return "/employee_extra_skills_edit.xhtml?id=" + id + "&roleId=" + roleId;
    }

    @GetMapping("/employee/roles/edit/{id}")
    public String getEmployeeRolesEditCard(@PathVariable String id) {
        return "/employee_roles_edit.xhtml?id=" + id;
    }
}
