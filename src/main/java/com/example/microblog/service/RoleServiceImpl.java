package com.example.microblog.service;

import com.example.microblog.admin.Admin;
import com.example.microblog.admin.AdminServiceImpl;
import com.example.microblog.dao.RoleDaoImpl;
import com.example.microblog.entity.Role;

public class RoleServiceImpl implements RoleService{

    private final RoleDaoImpl roleDao;

    public RoleServiceImpl(){
        this.roleDao = new RoleDaoImpl();
    }

    public static RoleServiceImpl roleService(){
        return new RoleServiceImpl();
    }

    @Override
    public void saveRole(Role role) {
        this.roleDao.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        this.roleDao.updateRole(role);
    }

    public Role getRole(String email){
        Admin admin;
        Role role;
        if((admin = AdminServiceImpl.adminService().isAdmin(email)) != null){
            role = new Role("ADMIN");
        } else role = new Role("USER");

    return role;
    }
}
