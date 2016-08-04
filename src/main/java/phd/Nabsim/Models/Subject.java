package phd.Nabsim.Models;

/**
 * Class to describe the possible subject which could be chosen for the
 * notification. The subject "uplift" value varies depending on various 
 * aspects such as the sender, the dataset chosen.
 * @author kfraser
 *
 */
public class Subject {

	private String ground_truth;
	private String subject;
	private String dataset;
	
	public String getGround_truth() {
		return ground_truth;
	}
	public void setGround_truth(String ground_truth) {
		this.ground_truth = ground_truth;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	
	
	
}
