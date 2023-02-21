package org.satel.ressatel.bean.menu;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import org.primefaces.PrimeFaces;

import javax.annotation.ManagedBean;
import java.io.Serializable;

@ManagedBean("topMenuView")
@ViewScoped
public class PageTopMenuView implements Serializable {
    private String listSrc;

    @Inject
    public PageTopMenuView() {
        this.listSrc = "employees.xhtml";
    }

    public void setSrc(String src) {
        listSrc = src;
        PrimeFaces.current().ajax().update("main-form:list");
    }

    public String getSrc() {
        return listSrc;
    }

}
