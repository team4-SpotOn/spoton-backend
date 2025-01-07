package com.sparta.popupstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AlertManagerReplace implements CommandLineRunner {

    @Value("${SLACK_API_URL}")
    private String slackApiUrl;

    public void run(String... args) throws Exception {
        String template = new String(Files.readAllBytes(Paths.get("alertmanager/alertmanager.yml")));
        String resolved = template.replace("${SLACK_API_URL}", slackApiUrl);
        Files.write(Paths.get("alertmanager/alertmanager.yml"), resolved.getBytes());
    }
}
