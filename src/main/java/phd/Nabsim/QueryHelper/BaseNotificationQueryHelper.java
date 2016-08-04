package phd.Nabsim.QueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import phd.Nabsim.Models.Notification;
import phd.Nabsim.Models.User;
import phd.Nabsim.Utilities.DateFormatUtility;

public class BaseNotificationQueryHelper {

	/**
	 * Query to get the sender and the corresponding current ranking for that sender (at the time
	 * of the notification).
	 */
	static String senderAndRankingQuery = 
			
		"select notificationDate, "+
		"max((substr(friendshipDate, 7, 4) || '-' || substr(friendshipDate, 4, 2) || '-' || substr(friendshipDate, 1, 2)))  "+
		"as 'friendshipDate', ranking  from "+
		"( "+
			"select notificationDate, friendshipDate, personA, personB, ranking from ( "+
				"select notifications.personA as 'personA', notifications.personB as 'personB', "+
				 "notifications.date as 'notificationDate', senderFriendship.friendshipRanking as 'ranking', friendshipDate from  "+
				"( "+
					"select * from sms_log  "+
					"where  "+
						"personA = ?  "+
						"and personB = ?	 "+
						"and personB is not ''  "+
						"and (type = 'incoming' or type = 'incoming+')  "+
						
						"UNION "+
						
					"select call_log.id, call_log.personA, call_log.personB, call_log.date, call_log.type, call_log.hash, call_log.cat from call_log  "+
					"where  "+
						"personA = ?  "+
						"and personB = ? 	 "+
						"and personB is not ''  "+
						"and (type = 'incoming' or type = 'incoming+') "+
				") as notifications JOIN "+
		
				"( "+
					"select friendshipDate, weight as 'friendshipRanking' from ( "+
					"SELECT DISTINCT call_log.personA, friendship.personA, friendship.personB, friendship.weight, friendship.date as 'friendshipDate' FROM friendship "+ 
					"JOIN call_log  "+
					"ON call_log.personA = friendship.personA "+
					"WHERE call_log.personA = ? "+
					"and friendship.personB = ?) "+
				") as senderFriendship  "+
			") as rankingTable "+
			"where  "+
			"(substr(friendshipDate, 7, 4) || '-' || substr(friendshipDate, 4, 2) || '-' || substr(friendshipDate, 1, 2))  "+
			"<  "+
			"(substr(notificationDate, 7, 4) || '-' || substr(notificationDate, 4, 2) || '-' || substr(notificationDate, 1, 2))  "+
			"ORDER BY notificationDate "+
		") GROUP BY notificationDate ORDER BY notificationDate;";
	
	 /**
     * Set the individual notification values with results from the query
     * @param c
     * @param userA
     * @param userB
     * @return
     * @throws SQLException
     * @throws ParseException 
     */
    public static ArrayList<Notification> getSenderAndRankingQuery(Connection c, User userA, User userB) throws SQLException, ParseException {
    	PreparedStatement preparedStatement = c.prepareStatement(senderAndRankingQuery);
    	
    	ArrayList<Notification> notifications = new ArrayList<Notification>();
    	
    	preparedStatement.setString(1, userA.getId());
    	preparedStatement.setString(2, userB.getId());
    	preparedStatement.setString(3, userA.getId());
    	preparedStatement.setString(4, userB.getId());
    	preparedStatement.setString(5, userA.getId());
    	preparedStatement.setString(6, userB.getId());
    	ResultSet rs = preparedStatement.executeQuery();
    	while (rs.next()) {
    		Notification n = new Notification();
    		
    		String sender = checkSender(userA.getId(), userB.getId(), rs.getInt(3), c);
    		int rank = rs.getInt(3);
    		
    		int scaledRank = (rank * 10)/ 7;
    		
    		n.setSendingUser(userB);
    		
    		n.setSender(sender);
    		n.setSenderRank(scaledRank);
    		
    		Date notificationDate = null;
    		notificationDate = DateFormatUtility.convertStringToDate(rs.getString(1));
    		n.setDate(DateFormatUtility.convertDateToStringUTC(notificationDate));
    		n.setSubject(userA.getSubject(sender));
    		
    		notifications.add(n);
    	}
    	return notifications;
    }
    
    /**
     * Helper function to determine the relationship between the receiver and sender of the notification.
     * @param userA
     * @param userB
     * @param friendRanking
     * @param c
     * @return
     * @throws SQLException
     */
    private static String checkSender(String userA, String userB, int friendRanking, Connection c) throws SQLException{
    	
    	String coupleQuery = 
    			"select couple.coupleId from couple where couple.personId = ?;";
    	
    	PreparedStatement preparedStatement = c.prepareStatement(coupleQuery);
    	preparedStatement.setString(1, userA);
    	ResultSet rs = preparedStatement.executeQuery();
    	
    	if(rs.next() == true){
    		int coupleIdA = rs.getInt(1);
    		String personBCouple = 
        			"select couple.coupleId from couple where couple.personId = ?;";
        	
        	preparedStatement = c.prepareStatement(personBCouple);
        	preparedStatement.setString(1, userB);
        	rs = preparedStatement.executeQuery();
        	
        	if(rs.next() == true){
        		int coupleIdB = rs.getInt(1);
            	if(coupleIdA == coupleIdB){
            		return "partner";
            	}
            	else {
            		if(friendRanking > 0){
                		return "friend";
            		}
            		else{
            			return "stranger";
            		}
            	}
        	}
        	else {
        		if(friendRanking > 0){
        			return "friend";
        		}
        		else{
        			return "stranger";
        		}
        	}
    	}
    	else{
	    	if(friendRanking > 0){
	    		return "friend";
	    	}
	    	else {
	    		return "stranger";
	    	}
    	}
    }
    
}
