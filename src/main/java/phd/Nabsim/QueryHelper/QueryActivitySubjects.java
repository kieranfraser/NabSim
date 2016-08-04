package phd.Nabsim.QueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import phd.Nabsim.Models.Subject;

public class QueryActivitySubjects {
	
	private static String selectQuery = 
			"select alone, friends, colleague, partner || family as family from activities "+
					"where id = ? ;";

	public static ArrayList<Subject> getActivitySubjects(String id, Connection c) throws SQLException{
		ArrayList<Subject> activityList = new ArrayList<Subject>();
		
		/**/
		
		PreparedStatement preparedStatement = c.prepareStatement(selectQuery);
    	preparedStatement.setString(1, id);
		
    	ResultSet rs = preparedStatement.executeQuery();
    	while (rs.next()) {
    		// stranger (alone - interest) 
    		Subject subject = new Subject();
    		subject.setDataset("activities");
    		subject.setGround_truth(rs.getString(1));
    		subject.setSubject("interest");
    		activityList.add(0, subject);
    		
    		// friends - social
    		subject = new Subject();
    		subject.setDataset("activities");
    		subject.setGround_truth(rs.getString(2));
    		subject.setSubject("social");
    		activityList.add(1, subject);
    		
    		// work - colleague - this is also social right now 
    		subject = new Subject();
    		subject.setDataset("activities");
    		subject.setGround_truth(rs.getString(3));
    		subject.setSubject("work (social)");
    		activityList.add(2, subject);
    		
    		// family 
    		subject = new Subject();
    		subject.setDataset("activities");
    		subject.setGround_truth(rs.getString(4));
    		subject.setSubject("family");
    		activityList.add(3, subject);
    	}
    	
		return activityList;
	}
}
