package com.example.microblog.admin;


public class AdminServiceImpl implements AdminService{

   private final AdminDaoImpl dao;

    public AdminServiceImpl (){
        this.dao = new AdminDaoImpl();
    }

    @Override
    public Admin isAdmin(String email) {
        return this.dao.isAdmin(email);
    }

    public static AdminServiceImpl adminService(){
        return new AdminServiceImpl();
    }
}
