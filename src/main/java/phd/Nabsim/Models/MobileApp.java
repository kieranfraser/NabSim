package phd.Nabsim.Models;

/**
 * App class which describes an application used by a receiver of
 * a notification.
 * @author kfraser
 *
 */
public class MobileApp {
	private String name;
	private String category;
	private int rank;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
}
