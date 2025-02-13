package org.zaiekd.middleware.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * @author lhz
 * @version 1.0
 * @create 2/8/25 4:23 PM
 */
public class OpenAiCodeReview {

    public static void main(String[] args) {
        System.out.println("Test");

        // 1. 代码检出
        ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "HEAD~1", 'HEAD');
        processBuilder.directory(new File("."));

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        StringBuilder diffCode = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            diffCode.append(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Exited with code: " + exitCode);

        System.out.println("评审代码：" + diffCode.toString());
    }
}
