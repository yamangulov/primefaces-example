package org.satel.ressatel.bean.menu;

import java.io.Serializable;

public class Page implements Serializable, Comparable<Page> {
    private String name;
    private String type;
    private String link;

    public Page(String name, String type, String link) {
        this.name = name;
        this.type = type;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Page other = (Page) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (link == null) {
            if (other.link != null) {
                return false;
            }
        }
        else if (!link.equals(other.link)) {
            return false;
        }
        if (type == null) {
            return other.type == null;
        }
        else return type.equals(other.type);
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Page Page) {
        return this.getName().compareTo(Page.getName());
    }
}
