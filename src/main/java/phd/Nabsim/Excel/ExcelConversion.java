package phd.Nabsim.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import phd.Nabsim.Models.Notification;

public class ExcelConversion {
	
	HSSFWorkbook workbook;
	String user;
	
	/**
	 * Default constructor which opens a new excel workbook 
	 *  - this must be called first.
	 */
	public ExcelConversion(){
		workbook = new HSSFWorkbook();
	}
	
	public ExcelConversion(String user){
		workbook = new HSSFWorkbook();
		this.user = user;
	}

	/**
	 * Maps notifications from an array list of user notifications to a sheet
	 * in the final excel spreadsheet.
	 * @param user - the user to whom the notifications belong
	 * @param notifications - the array list of notification objects
	 * @throws IOException
	 */
	public void mapNotifications(String user, ArrayList<Notification> notifications) throws IOException{
		
		HSSFSheet sheet = workbook.createSheet(user);

		int rownum = 0;
		Cell cell;
		Row row; /* = sheet.createRow(rownum++);

		Cell cell = row.createCell(1);
		cell.setCellValue("Sender");
		cell = row.createCell(3);
		cell.setCellValue("App");
		cell = row.createCell(5);
		cell.setCellValue("Subject");
		cell = row.createCell(7);
		cell.setCellValue("Body");
		cell = row.createCell(8);
		cell.setCellValue("Date");
		cell = row.createCell(9);
		cell.setCellValue("Date Importance");*/
		
		for (Notification notif : notifications) {
			
			row = sheet.createRow(rownum++);
			cell = row.createCell(0);
			cell.setCellValue(notif.getSenderRank());
			cell = row.createCell(1);
			cell.setCellValue(notif.getSender());
			
			cell = row.createCell(4);
			cell.setCellValue(notif.getSubject().getGround_truth());
			
			cell = row.createCell(5);
			cell.setCellValue(notif.getSubject().getSubject());
			
			// body may have to be the same as subject
			cell = row.createCell(7);
			cell.setCellValue("body");

			cell = row.createCell(8);
			cell.setCellValue(notif.getDate());

			cell = row.createCell(9);
			cell.setCellValue("not significant");
			// ... other columns when known
		}
	}
	
	/**
	 *  Close the workbook when finished.
	 */
	public void closeWorkbook(){
		try {
			FileOutputStream out = 
					new FileOutputStream(new File("workbooks/"+user+".xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
