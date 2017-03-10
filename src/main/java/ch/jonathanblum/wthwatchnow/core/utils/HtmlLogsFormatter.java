/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.jonathanblum.wthwatchnow.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 *
 * @author Jonathan Blum
 */
public class HtmlLogsFormatter extends Formatter {

    @Override
    public String format(LogRecord lr) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<tr>\n");
        
        // Colorize levels
        if(lr.getLevel().intValue() >= Level.SEVERE.intValue()) {
            sb.append(String.format("\t<td class=\"severe\">%s</td>", lr.getLevel()));
        } else if(lr.getLevel().intValue() >= Level.WARNING.intValue()) {
            sb.append(String.format("\t<td class=\"warning\">%s</td>", lr.getLevel()));
        } else if (lr.getLevel().intValue() >= Level.INFO.intValue()) {
            sb.append(String.format("\t<td class=\"info\">%s</td>", lr.getLevel()));
        } else {
            sb.append(String.format("\t<td class=\"debug\">%s</td>", lr.getLevel()));
        }
        
        sb.append(String.format("\t<td>%s</td>\n",lr.getMillis()));
        sb.append(String.format("\t<td>%s.%s</td>\n",lr.getSourceClassName(),lr.getSourceMethodName()));
        sb.append(String.format("\t<td>%s</td>\n",formatMessage(lr)));
        sb.append("</tr>\n");   

        return sb.toString();
    }  
    
    private String calcDate(long millisecs) {
      SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
      Date resultdate = new Date(millisecs);
      return date_format.format(resultdate);
    }
    
  // this method is called just after the handler using this
  // formatter is created

    @Override
  public String getHead(Handler h) {

      return "<!DOCTYPE html>\n<head>\n<style>\n"
          + "table { width: 100%; }\n"
          + "th { font:bold 10pt Tahoma; }\n"
          + "td { font:normal 10pt Tahoma; }\n"
          + "h1 { font:normal 11pt Tahoma;}\n"
          + ".debug { background-color: grey; }\n"
          + ".info { background-color: green; }\n"
          + ".warning { background-color: pink; font-weight: bold; }\n"
          + ".severe { background-color: red; font-weight: bold; }\n"
          + "</style>\n"
          + "</head>\n"
          + "<body>\n"
          + "<h1>" + (new Date()) + "</h1>\n"
          + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
          + "<tr align=\"left\">\n"
          + "\t<th style=\"width:5%\">Loglevel</th>\n"
          + "\t<th style=\"width:10%\">Time</th>\n"
          + "\t<th style=\"width:15%\">Caller</th>\n"
          + "\t<th style=\"width:60%\">Log Message</th>\n"
          + "</tr>\n";
    }
  
  // this method is called just after the handler using this
  // formatter is closed
    @Override
  public String getTail(Handler h) {
    return "</table>\n</body>\n</html>";
  }    
}
