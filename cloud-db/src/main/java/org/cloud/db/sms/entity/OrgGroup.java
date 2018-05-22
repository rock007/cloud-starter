package org.cloud.db.sms.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "org_group")
public class OrgGroup {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    
    @Column(name = "link_man")
    private String linkMan;

    @Column(name = "link_phone")
    private String linkPhone;

    @Column(name = "parent_id")
    private Long parentId;

    @Transient
    private List<OrgGroup> child;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public List<OrgGroup> getChild() {
		return child;
	}

	public void setChild(List<OrgGroup> child) {
		this.child = child;
	}
	
	
}
