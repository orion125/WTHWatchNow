/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.plugins;

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;

/**
 * Custom policies for plugins
 * @author Jonathan Blum
 */
public class PluginPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(CodeSource cs) {
        return super.getPermissions(cs); //To change body of generated methods, choose Tools | Templates.
    }
    
}
