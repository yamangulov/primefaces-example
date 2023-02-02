package org.satel.ressatel.bean.directory.role;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.satel.ressatel.entity.Role;
import org.satel.ressatel.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("rolesDirectoryCardView")
@ViewScoped
@Getter
@Setter
public class RolesDirectoryCardView {
    private List<String> roleNames;
    private final RoleService roleService;

    @Inject
    public RolesDirectoryCardView(RoleService roleService) {
        this.roleService = roleService;
    }

    public void onload() {
        this.roleNames = roleService.getAllRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}
