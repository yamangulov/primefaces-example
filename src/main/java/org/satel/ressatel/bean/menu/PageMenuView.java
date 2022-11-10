package org.satel.ressatel.bean.menu;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.TreeNode;

import javax.annotation.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean("treeContextMenuView")
@ViewScoped
public class PageMenuView implements Serializable {
    private final TreeNode<Page> root;

    private TreeNode<Page> selectedNode;
    private final String DEFAULT_LIST = "employees.xhtml";
    private String listSrc;

    @Inject
    public PageMenuView(PageService service) {
        root = service.createPages();
        listSrc = DEFAULT_LIST;
    }


    public TreeNode<Page> getRoot() {
        return root;
    }

    public TreeNode<Page> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<Page> selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void setSrc() {
        if (selectedNode.getData().getLink() != null) {
            listSrc = selectedNode.getData().getLink();

            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            List<UIComponent> uiComponents = view.getChildren();
            UIComponent uiComponent = uiComponents.get(2).getChildren().get(0).getChildren().get(3).getChildren().get(1)
                    .getChildren().get(0);
            PrimeFaces.current().ajax().update(uiComponent.getClientId());
        }
    }

    public String getSrc() {
        return listSrc;
    }

}
