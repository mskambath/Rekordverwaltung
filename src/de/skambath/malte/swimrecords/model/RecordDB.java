package de.skambath.malte.swimrecords.model;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class RecordDB {
    private String dbFilePath;
    private Connection connection;
    private boolean transactionActive;

    /**
     * Constructor for RecordDB.
     * @param dbFilePath Path to the SQLite database file.
     */
    public RecordDB(String dbFilePath) {
        this.dbFilePath = dbFilePath;
        initializeDatabase();
    }

    /**
     * Initializes the database by establishing a connection and setting up tables.
     */
    private void initializeDatabase() {
    	try {
            // Check if the database file already exists
            boolean isNewDatabase = !((new File(dbFilePath)).exists());

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

            if (connection != null && isNewDatabase) {
                // Create tables as this is a new database
                createTables();
                fillTables();
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        } finally {
            // Ensure the connection is closed
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing database connection: " + e.getMessage());
                }
            }
        }
    }
    
    private void fillTables() {
    	// Add standard tables
        
        for (CourseType courseType : CourseType.values()) {
        	if(courseType == CourseType.LONG_COURSE_YARDS 
            			|| courseType == CourseType.SHORT_COURSE_YARDS
            			|| courseType == CourseType.OPEN_WATER_YARDS
            			|| courseType == CourseType.OPEN_WATER_METRIC
            			) {
        		break;
        	}
            	for (Sex sex : Sex.values()) {
                	if(sex == Sex.Diverse || sex == Sex.Mixed) {
                		break;
                	}
                // Add age-specific tables
            	insertRecordTable(new AgeClass(-1,1000), sex, courseType, "Landesrekorde " + courseType.toLongString());
            	
                for (int age = 8; age <= 19; age++) {
                	AgeClass cls = new AgeClass(age);
                    insertRecordTable(cls, sex, courseType, "Jahrgangsrekorde " + cls.toString()  
                    + " " + sex.toLongLabelString() 
                    + " " + courseType.toLongString() + ""
                    );
                }

                // Add age range tables
                /*for (int age = 20; age <= 100; age += 5) {
                	AgeClass cls = new AgeClass(age, age + 4);
                    insertRecordTable(cls, sex, courseType, "Jahrgangsrekorde " + cls.toString() 
                    + " " + sex.toLongLabelString() 
                    + " " + courseType.toLongString() + ""
                    );
                }*/
            }
        }    
		
	}

	/**
     * Inserts a new record table into the database.
     * This method creates an entry in the RecordTables table with the specified age class, sex, and course type.
     * It uses a PreparedStatement to safely insert the values, preventing SQL injection.
     *
     * @param ageClass The AgeClass object representing the minimum and maximum age for the record table.
     * @param sex The Sex enum value representing the gender category for the record table.
     * @param courseType The CourseType enum value representing the course type for the record table.
     */
    private void insertRecordTable(AgeClass ageClass, Sex sex, CourseType courseType, String name) {

        String sql = "INSERT INTO RecordTables (ageClassMinAge, ageClassMaxAge, sex, courseType, name) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, ageClass.getMinAge());
            pstmt.setInt(2, ageClass.getMaxAge());
            pstmt.setString(3, sex.toString());
            pstmt.setString(4, courseType.toString());
            pstmt.setString(5, name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record table: " + e.getMessage());
        }    
    }
    
    
    /**
     * Creates the necessary tables in the database.
     * 
     * @param connection The database connection.
     * @throws SQLException If an SQL error occurs.
     */
    private void createTables() throws SQLException {
        // SQL statement for creating a new table for RecordTable
        String sqlCreateRecordTable = "CREATE TABLE IF NOT EXISTS RecordTables (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                      "ageClassMinAge INTEGER," +
                                      "ageClassMaxAge INTEGER," +
                                      "sex TEXT NOT NULL," +
                                      "courseType TEXT NOT NULL," +
                                      "name TEXT NOT NULL" +
                                      ");";
        
     // SQL statement for creating IndividualEventClasses
        String sqlCreateIndividualEventClasses = "CREATE TABLE IF NOT EXISTS IndividualEventClasses (" +
                                                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                 "length INTEGER NOT NULL," +
                                                 "swimStyle TEXT NOT NULL," +
                                                 "sex TEXT NOT NULL," +
                                                 "courseType TEXT NOT NULL," +
                                                 "recordTableId INTEGER," +
                                                 "FOREIGN KEY (recordTableId) REFERENCES RecordTables(id)" +
                                                 ");";

        // SQL statement for creating IndividualEntries
        String sqlCreateIndividualEntries = "CREATE TABLE IF NOT EXISTS IndividualEntries (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "rank INTEGER NOT NULL," +
                                            "name TEXT NOT NULL," +
                                            "birthYear INTEGER NOT NULL," +
                                            "yearAge INTEGER NOT NULL," +
                                            "club TEXT NOT NULL," +
                                            "time TEXT NOT NULL," +
                                            "location TEXT NOT NULL," +
                                            "date TEXT NOT NULL," +
                                            "eventClassId INTEGER," +
                                            "recordTableId INTEGER," +
                                            "FOREIGN KEY (eventClassId) REFERENCES IndividualEventClasses(id)," +
                                            "FOREIGN KEY (recordTableId) REFERENCES RecordTables(id)" +
                                            ");";

        // Execute the SQL statement
        try (Statement stmt = connection.createStatement()) {
        	stmt.execute(sqlCreateRecordTable);
            stmt.execute(sqlCreateIndividualEventClasses);
            stmt.execute(sqlCreateIndividualEntries);
        }
        
        
    }

    /**
     * Starts a new database transaction.
     */
    public void beginTransaction() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not established.");
        }
        connection.setAutoCommit(false);
        transactionActive = true;
    }

    /**
     * Commits the current transaction.
     */
    public void commitTransaction() throws SQLException {
        if (transactionActive) {
            connection.commit();
            connection.setAutoCommit(true);
            transactionActive = false;
        }
    }

    /**
     * Retrieves all RecordTables from the database.
     * @return List of RecordTables.
     */
    public List<RecordTable> getAllRecordTables() {
        List<RecordTable> recordTables = new ArrayList<>();
        String sql = "SELECT ageClassMinAge, ageClassMaxAge, sex, courseType, name FROM RecordTables";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AgeClass ageClass = new AgeClass( rs.getInt("ageClassMinAge"), rs.getInt("ageClassMaxAge"));
                Sex sex = Sex.fromSexId(rs.getString("sex"));
                CourseType courseType = CourseType.fromCourseTypeId(rs.getString("courseType"));
                String name = rs.getString("name");

                recordTables.add(new RecordTable(ageClass, sex, courseType, name));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching record tables: " + e.getMessage());
        }
        return recordTables;
    }
    
    /**
     * Stores a new record table entry in the database.
     * @param recordTable The record table to store.
     */
    public void storeRecordTable(RecordTable recordTable) {
        String sql = "INSERT INTO RecordTables (ageClass, sex, courseType) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recordTable.getAgeClass().toString());
            pstmt.setString(2, recordTable.getSex().toString());
            pstmt.setString(3, recordTable.getCourseType().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error storing record table: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}

