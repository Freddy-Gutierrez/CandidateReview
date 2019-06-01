package candidates;
import java.util.Date;

public class Comments {
	private int userId;
	private String name;
	private String rating;
	private String comment;
	private Date date;

	public Comments(int userId, String name, String rating, String comment, Date date) {
		this.userId = userId;
		this.name = name;
		this.rating = rating;
		this.comment = comment;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}