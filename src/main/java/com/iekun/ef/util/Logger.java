package com.iekun.ef.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Logger
  implements Serializable
{
  public static final int ALL = 0;
  public static final int DEBUG = 1;
  public static final int INFO = 2;
  public static final int WARN = 3;
  public static final int ERROR = 4;
  public static final int NONE = 2147483647;
  private static final String[] LOG_LEVELS = { "ALL", "DEBUG", "INFO", "WARN", "ERROR" };
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private File file = null;
  private long MAX_FILE_SIZE = 20480000L;
  private PrintWriter fileWriter;
  private File logDir = null;
  private int CUR_DATE;
  private String logName = "syslog";
  private int logLevel = 0;
  private boolean toScreen = true;
  private static final HashMap<String, Logger> loggers = new HashMap<String, Logger>();
  
  public static synchronized Logger getLogger(String logName, int logLevel, boolean toScreen)
  {
    Logger logger = (Logger)loggers.get(logName);
    if (logger == null)
    {
      logger = new Logger(logName, logLevel, toScreen);
      loggers.put(logName, logger);
    }
    return logger;
  }
  
  private Logger(String logFileName, int logLevel, boolean toScreen)
  {
    this.logName = logFileName;
    if ((logLevel > 4) || (logLevel < 0)) {
      this.logLevel = 0;
    } else {
      this.logLevel = logLevel;
    }
    this.toScreen = toScreen;
    setLogDir();
    setNewWriter();
  }
  
  private void setLogDir()
  {
    String baseLogDir = new File(System.getProperty("user.dir")).getParent() + File.separator + "logs_db";
    

    this.logDir = new File(baseLogDir, this.logName);
    if (!this.logDir.exists()) {
      this.logDir.mkdirs();
    }
  }
  
  private static void releaseLogger(Logger logger)
  {
    if (logger != null)
    {
      logger.close();
      loggers.remove(logger.logName);
    }
  }
  
  private void close()
  {
    if (this.fileWriter != null) {
      this.fileWriter.close();
    }
  }
  
  private int getCurrDateDigital()
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String date = df.format(Calendar.getInstance().getTime());
    int curDate = 0;
    try
    {
      curDate = Integer.parseInt(date);
    }
    catch (NumberFormatException ex)
    {
      ex.printStackTrace();
    }
    return curDate;
  }
  
  private boolean isNewLogFileRequired()
  {
    if (this.file == null) {
      return false;
    }
    return (getCurrDateDigital() > this.CUR_DATE) || (this.file.length() > this.MAX_FILE_SIZE);
  }
  
  private void setNewWriter()
  {
    close();
    this.CUR_DATE = getCurrDateDigital();
    this.file = new File(this.logDir, this.CUR_DATE + ".log");
    try
    {
      this.fileWriter = new PrintWriter(new FileWriter(this.file, true));
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      this.fileWriter = new PrintWriter(System.out);
    }
  }
  
  private void log(Object message, int logLevel, boolean timeMark)
  {
    if ((logLevel > logLevel) || (this.fileWriter == null)) {
      return;
    }
    if (isNewLogFileRequired()) {
      setNewWriter();
    }
    String buff;
    if (timeMark == true) {
      buff = this.formatter.format(Calendar.getInstance().getTime()) + "[" + LOG_LEVELS[logLevel] + "]";
    } else {
      buff = "";
    }
    /*String*/ buff = buff + message;   /*Modified by JEFFMA 20151217*/
    if (this.toScreen) {
      System.out.println(buff);
    }
    this.fileWriter.println(buff);
    this.fileWriter.flush();
  }
  
  public void debugT(Object message)
  {
    log(message, 1, true);
  }
  
  public void debug(Object message)
  {
    log(message, 1, false);
  }
  
  public void infoT(Object message)
  {
    log(message, 2, true);
  }
  
  public void info(Object message)
  {
    log(message, 2, false);
  }
  
  public void warnT(Object message)
  {
    log(message, 3, true);
  }
  
  public void warn(Object message)
  {
    log(message, 3, false);
  }
  
  public void errorT(Object message)
  {
    log(message, 4, true);
  }
  
  public void error(Object message)
  {
    log(message, 4, false);
  }
  
  public void exception(Throwable throwable)
  {
    if ((this.fileWriter == null) || (this.logLevel == 2147483647)) {
      return;
    }
    if (isNewLogFileRequired()) {
      setNewWriter();
    }
    String buff = this.formatter.format(Calendar.getInstance().getTime());
    
    buff = buff + "[EXCEPTION] Thr:" + Thread.currentThread().getName();
    if (this.toScreen) {
      throwable.printStackTrace();
    }
    this.fileWriter.println(buff);
    throwable.printStackTrace(this.fileWriter);
    this.fileWriter.flush();
  }
  
  public void setToScreen(boolean toScreen)
  {
    this.toScreen = toScreen;
  }
  
  public void setLogLevel(int logLevel)
  {
    this.logLevel = logLevel;
  }
  
  public int getLogLevel()
  {
    return this.logLevel;
  }
}
