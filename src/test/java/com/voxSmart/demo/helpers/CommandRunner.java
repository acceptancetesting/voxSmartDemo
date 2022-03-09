package com.voxSmart.demo.helpers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class CommandRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CommandRunner.class);

    public static synchronized Integer runCommandAndWait(List<String> commandParams) {

        ProcessBuilder processBuilder = new ProcessBuilder(commandParams);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            String output = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());

            LOG.debug(
                    "Running command '{}' with output '{}'",
                    String.join(" ", commandParams),
                    output);

            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(
                    "Error running command '" + String.join(" ", commandParams) + "'", e);
        }
    }
}
