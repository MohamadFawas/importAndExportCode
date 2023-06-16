package com.defect.tracker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.defect.tracker.entities.ProjectStatus;
import com.defect.tracker.repositories.ProjectStatusRepository;

@SpringBootApplication
public class DefectTrackerSystemApplication implements CommandLineRunner{
 
	@Autowired
	ProjectStatusRepository projectStatusRepository;
	public static void main(String[] args) {
    SpringApplication.run(DefectTrackerSystemApplication.class, args);
  }

@Override
public void run(String... args) throws Exception {
	List<ProjectStatus> projectStatuses=new ArrayList<>();
	projectStatusRepository.saveAll(projectStatuses);
	
}

}
