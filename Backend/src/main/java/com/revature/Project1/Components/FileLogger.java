package com.revature.Project1.Components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FileLogger {

    public Logger log;

    public FileLogger() {
        this.log = LogManager.getLogger(FileLogger.class);
    }
}

