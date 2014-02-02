package com.example.fitnessnotes;

import java.util.Date;

public class Serie {
	
	private int id;
	private String name;
	private int repetition;
	private int weight;
	private Date date; 
	private int trainingId; 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRepetition() {
		return repetition;
	}

	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public Date getDate() { 
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
	
 	public Serie(){
		
	}
	
	public Serie(String name, int repetition, int weight){
		this.name=name;
		this.repetition=repetition;
		this.weight=weight;
		this.setDate(new Date());
		this.setTrainingId(0);
	}


	
	
}
