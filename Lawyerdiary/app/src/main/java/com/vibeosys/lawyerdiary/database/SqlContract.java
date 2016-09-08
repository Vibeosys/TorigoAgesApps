package com.vibeosys.lawyerdiary.database;

/**
 * Created by akshay on 08-09-2016.
 */
public class SqlContract {

    public final static String DATABASE_NAME = "LawyerDiary";

    public abstract class Client {
        public final static String TABLE_NAME = "Client";
        public final static String ID = "ID";
        public final static String NAME = "Name";
        public final static String EMAIL = "Email";
        public final static String PH_NUMBER = "PhNumber";
        public final static String ADDRESS = "Address";
    }

    public abstract class Case {
        public final static String TABLE_NAME = "Case";
        public final static String ID = "ID";
        public final static String CASE_NAME = "CaseName";
        public final static String CLIENT_ID = "ClientId";
        public final static String AGAINST = "Against";
        public final static String SITUATION = "Situation";
        public final static String CASE_DATE = "CaseDate";
        public final static String COURT_LOCATION = "CourtLocation";
        public final static String DESCRIPTION = "Description";
        public final static String KEY_POINTS = "KeyPoints";
    }

    public abstract class Document {
        public final static String TABLE_NAME = "Document";
        public final static String ID = "ID";
        public final static String DOCUMENT_NAME = "Name";
        public final static String FILE_PATH = "FilePath";
        public final static String CASE_ID = "CaseId";
        public final static String LAST_UPDATED_DATE = "LastUpdatedDate";
    }

    public abstract class Reminder {
        public final static String TABLE_NAME = "Reminder";
        public final static String ID = "ID";
        public final static String REMINDER_NAME = "Name";
        public final static String START_DATE_TIME = "StartDateTime";
        public final static String END_DATE_TIME = "EndDateTime";
        public final static String LOCATION = "Location";
        public final static String NOTE = "Note";
        public final static String COLOUR = "Colour";
        public final static String CASE_ID = "CaseId";
    }


}
