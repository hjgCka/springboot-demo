package com.hjg.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/22
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myJob;

    @RequestMapping(value = "/start")
    public void start() {
        try {
            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addString("personName", "James");
            builder.addString("fileName", "person.txt");
            JobExecution jobExecution = jobLauncher.run(myJob, builder.toJobParameters());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
