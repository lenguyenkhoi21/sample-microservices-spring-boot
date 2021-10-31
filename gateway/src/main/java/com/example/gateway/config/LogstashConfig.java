package com.example.gateway.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class LogstashConfig {
    private final Logstash logstash;
    private final AppInfo appInfo;

    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";
    private static final String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH";
    private final Logger logger = LoggerFactory.getLogger(Logstash.class);

    public LogstashConfig(Logstash logstash, AppInfo appInfo) {
        this.logstash = logstash;
        this.appInfo = appInfo;
        LoggerContext CONTEXT = (LoggerContext) LoggerFactory.getILoggerFactory();
        addLogstashAppender(CONTEXT);
    }


    private void addLogstashAppender(LoggerContext context) {
        logger.info("Initializing Logstash logging");
        LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
        logstashAppender.setName(LOGSTASH_APPENDER_NAME);
        logstashAppender.setContext(context);
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        String customFields = "{\"servicename\":\"" + appInfo.getName() + "\"}";
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.addDestinations(
                new InetSocketAddress(logstash.getHost(), logstash.getPort())
        );
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.setEncoder(logstashEncoder);

        logstashAppender.start();
        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName(ASYNC_LOGSTASH_APPENDER_NAME);
        asyncLogstashAppender.setQueueSize(logstash.getQueuesize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();
        context.getLogger("ROOT").addAppender(asyncLogstashAppender);
    }

}
