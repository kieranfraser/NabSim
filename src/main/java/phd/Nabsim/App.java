package phd.Nabsim;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import phd.Nabsim.Excel.ExcelConversion;
import phd.Nabsim.Models.MobileApp;
import phd.Nabsim.Models.Notification;
import phd.Nabsim.Models.Personality;
import phd.Nabsim.Models.User;
import phd.Nabsim.QueryHelper.AppQueryHelper;
import phd.Nabsim.QueryHelper.BaseNotificationQueryHelper;
import phd.Nabsim.QueryHelper.Big5QueryHelper;
import phd.Nabsim.QueryHelper.QueryActivitySubjects;
import phd.Nabsim.QueryHelper.UniqueSendersQueryHelper;
import phd.Nabsim.Utilities.DateFormatUtility;

public class App 
{
    private static String selectSQL = "SELECT DISTINCT personA FROM call_log WHERE personA is not 'personA'"
    		+ "ORDER BY personA ASC;";
	
    public static void main( String[] args ) throws SQLException, IOException, ParseException
    {
        
        Connection c = null;
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:friends&family.db");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Opened database successfully");
        ArrayList<String> uniqueUsers = new ArrayList<String>();
        ArrayList<User> users = new ArrayList<User>();
        
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);
        
        while (rs.next()) {
        	uniqueUsers.add(rs.getString(1));
        }

        System.out.println("Number of users: "+ uniqueUsers.size());
                
        for(String user : uniqueUsers ){
            // Initialize the excel workbook
        	//ExcelConversion workbook = new ExcelConversion(user);
        	
        	       	
        	// get everything about the receiving user
        	User receivingUser = new User();
        	receivingUser.setId(user);
        	receivingUser.setFavoriteApps(AppQueryHelper.getUserApps(user, c));
        	receivingUser.setActivities(QueryActivitySubjects.getActivitySubjects(user, c));
        	receivingUser.setPersonality(Big5QueryHelper.getPersonality(user, c));
        	
        	
        	ArrayList<Notification> notifications = new ArrayList<Notification>();
        	ArrayList<String> senders = UniqueSendersQueryHelper.getUniqueSenders(c, user);
        	
        	if(senders != null && !senders.isEmpty()){
        		for(String userB : senders){
        			
        			// get everything about the sending user
        			User sendingUser = new User();
        			sendingUser.setId(userB);
        			sendingUser.setFavoriteApps(AppQueryHelper.getUserApps(userB, c));
        			sendingUser.setActivities(QueryActivitySubjects.getActivitySubjects(userB, c));
        			sendingUser.setPersonality(Big5QueryHelper.getPersonality(userB, c));
        			
        			notifications.addAll(BaseNotificationQueryHelper.getSenderAndRankingQuery(c, receivingUser, sendingUser));
        		}
        		//workbook.mapNotifications(user, notifications);
        	} 
            //workbook.closeWorkbook();
        	receivingUser.setNotifications(notifications);
            users.add(receivingUser);
            System.out.println("...");
        }       
        System.out.println("size of users: "+users.size());
        
        for(User user: users){

            System.out.println("...");
        	ExcelConversion workbook = new ExcelConversion(user.getId());
        	if(!user.getNotifications().isEmpty()){
        		workbook.mapNotifications(user.getId(), user.getNotifications());
        	}
        	workbook.closeWorkbook();
        }
    }
}
