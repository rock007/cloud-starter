package org.cloud.db.sys.entity;

import javax.persistence.*;

/**
 * Created by sam on 2017/7/7.
 */
@Entity
@Table(name = "sys_role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "role_permission_id")
    private Long rolePermissionId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permission_id")
    private Long permissionId;

    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
