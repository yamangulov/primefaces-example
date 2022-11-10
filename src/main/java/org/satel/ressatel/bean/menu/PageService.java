package org.satel.ressatel.bean.menu;

import jakarta.enterprise.context.ApplicationScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.ManagedBean;

@ManagedBean
@ApplicationScoped
public class PageService {

    public TreeNode<Page> createPages() {
        TreeNode<Page> root = new DefaultTreeNode<Page>(new Page("Root", "folder", null), null);

        TreeNode<Page> ips = new DefaultTreeNode<Page>(new Page("Сотрудники", "folder", "employees.xhtml"), root);
        TreeNode<Page> contragents = new DefaultTreeNode<Page>(new Page("Субподрядчики", "document", null), root);

        //ИП/ГПХ
        TreeNode<Page> ip =
                new DefaultTreeNode<Page>( new Page("ИП/ГПХ", "document", "persons.xhtml"), contragents);
        TreeNode<Page> gph =
                new DefaultTreeNode<Page>( new Page("Юрлица", "document", "companies.xhtml"), contragents);

        return root;
    }

}
