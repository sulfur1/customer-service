package com.iprody08.e2e;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberReportRunner extends BlockJUnit4ClassRunner {
    private static final String PROJECT_NAME = "Customer Service";
    private static final String BUILD_NUMBER = "0.0.1";
    private static final String BRANCH_NAME = "main";

    public CucumberReportRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        generateReport();
    }

    public static void generateReport() {
        File reportOutputDirectory = new File("target/report/cucumber/");

        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/report/cucumber/cucumber-report.json");

        Configuration configuration = new Configuration(reportOutputDirectory, PROJECT_NAME);
        configuration.setBuildNumber(BUILD_NUMBER);
        configuration.addClassifications("Build Number", configuration.getBuildNumber());
        configuration.addClassifications("Branch Name", BRANCH_NAME);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
