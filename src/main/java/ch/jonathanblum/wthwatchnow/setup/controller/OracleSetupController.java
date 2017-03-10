/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.setup.controller;

import ch.jonathanblum.wthwatchnow.core.controller.AbstractCoreController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class OracleSetupController extends AbstractCoreController {
    private static String script_location = "";
    private static String file_extension = ".sql";
    private static ProcessBuilder processBuilder= null;
    
    @Override
    public void Do() {
        try {
            File file = new File("./scripts/oracle");
            File[] list_files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(file_extension);
                }
            });
            for(int i = 0; i<list_files.length;i++){
                script_location = "@" + list_files[i].getAbsolutePath();
                processBuilder = new ProcessBuilder("sqlplus", String.format("%s/%s@%s", getConf().getDbuser(), getConf().getDbpassword(), getConf().getDbname()), script_location);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String currentLine = null;
                while ((currentLine = in.readLine()) != null) {
                    System.out.println(" " + currentLine);
                }
            }
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot Open SQL scripts");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unsupported Exception during SQL Generation: {0}", ex.getMessage());
        }
    }

    @Override
    protected void Display() {
        LOGGER.log(Level.WARNING, "Trying to display OracleSetupController but there's no display");
    }
    
}
