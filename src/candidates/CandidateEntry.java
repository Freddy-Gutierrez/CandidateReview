package candidates;
import java.util.ArrayList;

public class CandidateEntry {
	Integer id;
	String name;
	String specialties;
	String presentation;
	String operation;
	String rating; 
	ArrayList<Comments> comments = new ArrayList<Comments>();
	
	public CandidateEntry(int id, String name, String special, String pres, String rating) {
		this.id = id;
		this.name = name;
		this.specialties = special;
		this.presentation = pres;	
		this.operation = "edit";
		if(rating == null) {
			this.rating = "N/A";
		}
		else {
			this.rating = rating;
		}
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialties() {
		return specialties;
	}

	public void setSpecialties(String specialties) {
		this.specialties = specialties;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	public ArrayList<Comments> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comments> comment) {
		this.comments = comment;
	}

}