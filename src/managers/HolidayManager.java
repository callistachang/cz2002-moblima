package managers;

import java.util.ArrayList;
import java.time.LocalDate;

import handlers.DatabaseHandler;
import models.Holiday;
import serializers.AbstractSerializer;
import serializers.HolidaySerializer;

public class HolidayManager {
	private static final String DATABASE_NAME = "holidaydata";
	protected static ArrayList<Holiday> records = null;

	public HolidayManager() {
		if (records == null)
			initializeDatabase();
	}
	
	public void listAll() {
		for (Holiday h: records) {
			System.out.println("Name: " + h.getHolidayName() + " Date: " + h.getHolidayDate());
		}
		
	}
	
	public Boolean isHoliday(LocalDate movieDate) {
		for (Holiday h: records) {
			LocalDate date = h.getHolidayDate();
			if(movieDate == date) return true;
		}
		return false;
	}
	
	private static void initializeDatabase() {
		ArrayList<String> data = DatabaseHandler.readDatabase(DATABASE_NAME);
		AbstractSerializer serializer = new HolidaySerializer();
		records = serializer.deserialize(data);
	}
	
	private void updateDatabase() {
		AbstractSerializer serializer = new HolidaySerializer();
		ArrayList<String> updatedRecords = serializer.serialize(records);
		DatabaseHandler.writeToDatabase(DATABASE_NAME, updatedRecords);
	}

}
