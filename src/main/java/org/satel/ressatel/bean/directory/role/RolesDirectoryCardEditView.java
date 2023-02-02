package org.satel.ressatel.bean.directory.role;

import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.primefaces.PrimeFaces;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Component("rolesDirectoryCardEditView")
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
@Log4j2
public class RolesDirectoryCardEditView implements Serializable {
    private List<Role> roles;

    private Role selectedRole;

    private List<Role> selectedRoles;
    private final RoleService roleService;

    @Inject
    public RolesDirectoryCardEditView(RoleService roleService) {
        this.roleService = roleService;
    }

    public void onload() {
        this.init();
    }

    public void init() {
        this.roles = this.roleService.getAllRoles();
    }

    public void openNew() {
        this.selectedRole = new Role();
    }

    public void saveRole() {
        if (this.selectedRole.getId() == null) {
//            log.info("selectedRole name {} parent name {}",
//                    selectedRole.getName(), selectedRole.getParent().getName());
            //TODO здесь добавляем создание нового role в БД
            this.roles.add(this.selectedRole);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Role Added"));
        } else {
            //TODO здесь добавляем обновление role в БД
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Role Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageRoleDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
    }

    public void deleteRole() {
        //TODO здесь добавляем удаление role из БД
        this.roles.remove(this.selectedRole);
        this.selectedRoles.remove(this.selectedRole);
        this.selectedRole = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Role Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedRoles()) {
            int size = this.selectedRoles.size();
            if (size % 10 == 1 && size != 11) {
                return size + " роль выбрана";
            }
            if (size % 10 > 1 && size % 10 < 5 && (size < 5 || size > 20)) {
                return size + " роли выбрано";
            }
            return size + " ролей выбрано";
        }

        return "Удалить";
    }

    public boolean hasSelectedRoles() {
        return this.selectedRoles != null && !this.selectedRoles.isEmpty();
    }

    public void deleteSelectedRoles() {
        //TODO здесь добавляем удаление нескольких выбранных roles из БД
        this.roles.removeAll(this.selectedRoles);
        this.selectedRoles = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Roles Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-roles");
        PrimeFaces.current().executeScript("PF('dtRoles').clearFilters()");
    }
}
