/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.users.model;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class User {
    
    protected final int id;
    protected String username;
    protected String password;
    protected Access access;

    public User(int id, String username, String password, Access access) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.access = access;
    }
    
    public User(int id, String username, String password, String access) {
        this.id = id;
        this.username = username;
        this.password = password;
        setAccess(access);
    }
    public User(int id, String username, String password) {
        this(id, username, password, Access.Guest);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Access getAccess() {
        return access;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
    
    public final void setAccess(String access) {
        switch(access) {
            case "admin":
                this.access = Access.Admin;
                break;
            case "user":
                this.access = Access.User;
                break;
            default:
                this.access = Access.Guest;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }
    
    public enum Access {
        Admin,
        User,
        Guest
    }
}
