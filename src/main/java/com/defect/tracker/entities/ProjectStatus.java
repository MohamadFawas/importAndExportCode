package com.defect.tracker.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String color;
public ProjectStatus() {
	super();
	// TODO Auto-generated constructor stub
}
public ProjectStatus(String name, String color) {
	super();
	this.name = name;
	this.color = color;
}

}
