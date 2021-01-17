package rest.helpers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.spi.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rest.Application;

@Component
public class Log {
    private final Crypto crypto;
    private Logger log;

    private final String logFileName = "/Users/a/IdeaProjects/rest.log";
    private final String logPattern = "%d %m%n";

    @Autowired
    public Log(Crypto crypto) {
        log = null;
        this.crypto = crypto;
    }

    private Logger getLog() {
        if (this.log == null)
        {
            try {
                log = initLogger(logFileName, logPattern);
            } catch (Exception e) {
                System.out.print(e.getMessage());
                log = LogManager.getLogger(Application.class.getName());
            }
        }
        return log;
    }

    private Logger initLogger(String fileName, String pattern) {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        builder.setStatusLevel(Level.INFO);
        builder.setConfigurationName("RestLogger");

        AppenderComponentBuilder appenderBuilder = builder.newAppender("Console", "CONSOLE").addAttribute("target",
                ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern", pattern));
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("Console"));

        builder.add(appenderBuilder);

        LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
                .addAttribute("pattern", pattern);
        appenderBuilder = builder.newAppender("LogToFile", "File")
                .addAttribute("fileName", fileName)
                .add(layoutBuilder);
        builder.add(appenderBuilder);
        rootLogger = builder.newRootLogger(Level.INFO);
        rootLogger.add(builder.newAppenderRef("LogToFile"));

        builder.add(rootLogger);

        Configuration config = builder.build();
        LoggerContext context = Configurator.initialize(config);
        Configurator.reconfigure(config);

        return context.getLogger("RestLogger");
    }

    private void writeCryptoLog(Logger log, String message) {
        String encryptedText = crypto.encrypt(message);
        log.info("======= encryption: " + encryptedText);
        log.info("======= decryption: " + crypto.decrypt(encryptedText));
    }

    public void writeLog(String prefix, String message, boolean encrypt) {
        getLog().info(prefix + message);
        if (encrypt) {
            writeCryptoLog(getLog(), message);
        }
    }
}
