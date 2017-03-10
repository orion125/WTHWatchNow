/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.controller;

import ch.jonathanblum.wthwatchnow.WTHWatchNow;
import ch.jonathanblum.wthwatchnow.core.model.Configuration;
import ch.jonathanblum.wthwatchnow.core.utils.CustomLogger;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.cli.*;
/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 * @version 0.0.1
 */
public final class FrontController extends AbstractCoreController {
    
    private static final String OPT_SERVER = "s";
    private static final String OPT_SERVER_LONG = "server";
    private static final String OPT_DEBUG = "d";
    private static final String OPT_DEBUG_LONG = "debug";
    private static final String OPT_NOGUI = "c";
    private static final String OPT_NOGUI_LONG = "no-gui";
    private static final String OPT_VERSION_LONG = "version";
    
    private final Dispatcher dispatcher = new Dispatcher();
    
    private Options getCLIOptions() {
        Options opt = new Options();
        opt.addOption(OPT_SERVER, OPT_SERVER_LONG, false, "Launch WTHWatchNow as server");
        opt.addOption(OPT_DEBUG, OPT_DEBUG_LONG, false, "Enable Debug Mode");
        opt.addOption(OPT_NOGUI, OPT_NOGUI_LONG, false, "Launch app without GUI (Use with -s)");
        opt.addOption(OptionBuilder.withLongOpt(OPT_VERSION_LONG)
                                       .withDescription("Show program version")
                                       .create());
        return opt;
    }
    
    public void ParseCLIArgumentsAction() {
        LOGGER.info("Parsing CLI Arguments");
       CommandLineParser parser = new DefaultParser();
       String[] args = WTHWatchNow.getArgs();
       Configuration conf = getCM().getConf();
       
        try {
            CommandLine cmd = parser.parse(getCLIOptions(), args);
            if(cmd.hasOption(OPT_VERSION_LONG)) {
                System.out.println("WTHWatchNow version " + conf.getVersion());
                return;
            }
            if(cmd.hasOption(OPT_DEBUG) && !conf.isDebugMode()) {
                conf.setDebugMode(true);    
                LOGGER.info("WTHWatchNow launched with debug mode On.");
            }
            if(cmd.hasOption(OPT_SERVER) && !conf.isServerMode()) {
                conf.setServerMode(true);
                LOGGER.info("WTHWatchNow launched in server mode.");
                if(cmd.hasOption(OPT_NOGUI) && !conf.isNoGUI()) {
                    conf.setNoGUI(true);
                    LOGGER.info("WTHWatchNow launched without GUI.");
                }
            }
           
        } catch (ParseException ex) {
            LOGGER.log(Level.WARNING, "Cannot Parse CLI arguments : {0}", ex.getMessage());
        }          
    }

    @Override
    public void Do() {
        try {
            CustomLogger.setup();
            LOGGER.setLevel(Level.FINEST);
        } catch (IOException ex) {
            throw new RuntimeException("Problems with creating the log file");
        }
        // Welcome Message
        System.out.println("What The Hell Should I Watch Now ?");
        System.out.println("Version : " + Configuration.getInstance().getVersion());
        LOGGER.log(Level.INFO, "Version : {0}", Configuration.getInstance().getVersion());
        // Check possible arguments
        ParseCLIArgumentsAction();
        // Check existing conf

        // Routing
        try {
            if(!getCM().checkConfig())
                dispatcher.dispatch(Dispatcher.ROUTE_SETUP);
            else
                dispatcher.dispatch(Dispatcher.ROUTE_DEFAULT);
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Unreported Exception : {0}", ex.getMessage());
        }
    }

    @Override
    protected void Display() {
        LOGGER.warning("Trying to display FrontController's view, who doesn't exist");
    }
    
}
