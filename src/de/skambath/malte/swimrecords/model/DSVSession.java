package de.skambath.malte.swimrecords.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Manages sessions and interactions with the DSV website.
 */
public class DSVSession {
    private final String refererUrl  = "https://www.dsv.de/schwimmen/wettkampf-regional/landesverbaende/";
    private final String stateBaseUrl = "https://dsvdaten.dsv.de/Modules/Clubs/Index.aspx?StateID=";
    private final String stateListUrl = "https://dsvdaten.dsv.de/Modules/Clubs/States.aspx";
    
    private Map<Integer, Document> stateDocumentMap = new HashMap<>();

    /**
     * Constructor for DSVSession.
     */
    public DSVSession() {

    }

    /**
     * Downloads the HTML content of the page listing all states.
     * @return The HTML content of the state list page.
     * @throws IOException If an I/O error occurs.
     */
    private String downloadStateListPage() throws IOException {
        URL url = new URL(stateListUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Referer", refererUrl);

        // Check for successful response code or throw error
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        // Read the response content
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }

    /**
     * Downloads and parses the state list page to extract the list of states.
     * @return A list of State objects representing each state.
     * @throws IOException If an I/O error occurs.
     */
    public List<State> downloadStatesList() throws IOException {
        String htmlContent = downloadStateListPage();
        Document doc = Jsoup.parse(htmlContent);
        List<State> states = new ArrayList<>();

        // Select the rows in the table with the id 'ContentSection_table'
        Elements stateRows = doc.select("table#ContentSection_table tr");

        for (Element row : stateRows) {
            Elements cols = row.select("td");
            if (cols.size() >= 2) {
                try {
                    // Assuming first column has the state ID and second column has the state name
                    int id = Integer.parseInt(cols.get(0).text().trim());
                    String name = cols.get(1).text().trim();
                    states.add(new State(id, name));
                } catch (NumberFormatException e) {
                    // Handle potential parsing errors for state IDs
                    System.err.println("Error parsing state ID: " + e.getMessage());
                }
            }
        }

        return states;
    }

    /**
     * Downloads and stores a state-specific page.
     * @param stateId The ID of the state to download.
     * @throws IOException If an I/O error occurs.
     */
    private void downloadStatePage(int stateId) throws IOException {
        String stateUrl = stateBaseUrl + stateId;

        URL url = new URL(stateUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Referer", stateBaseUrl + stateId);

        // Check for successful response code
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        // Parse and store the document
        Document statePage = Jsoup.parse(connection.getInputStream(), "UTF-8", stateUrl);
        stateDocumentMap.put(stateId, statePage);
    }
    
    private Map<String, String> extractHiddenFields(Document doc) {
        Map<String, String> formData = new HashMap<>();
        Elements inputElements = doc.select("input[type=hidden]");
        for (Element inputElement : inputElements) {
            formData.put(inputElement.attr("name"), inputElement.attr("value"));
        }
        return formData;
    }
    
    /**
     * Converts a CourseType enum into a corresponding course code string.
     * This method translates the course type into a code understood by the form submission process.
     * For example, it converts SHORT_COURSE_METRIC  to "S", and 
     * LONG_COURSE_METRIC  to "L".
     * 
     * @param courseType The CourseType enum representing the type of the swimming course.
     * @return A string representing the course code.
     */
    private String getCourseCode(CourseType courseType) {
        if(courseType == CourseType.SHORT_COURSE_METRIC) {
        	return "S";
        } else if(courseType == CourseType.LONG_COURSE_METRIC) {
        	return "L";
        } else {
        	return "";
        }
    }
    
    private Document postFormData(Map<String, String> formData, int stateId) throws IOException {
        // State-specific URL for the POST request
        String stateUrl = stateBaseUrl + stateId;

        // Construct the POST data string
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            sj.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        byte[] postDataBytes = sj.toString().getBytes("UTF-8");

        // Create and configure the POST request
        HttpURLConnection conn = (HttpURLConnection) new URL(stateUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("Referer", stateBaseUrl + stateId);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        

        // Read and return the response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            
            Document doc = Jsoup.parse(response.toString());
            this.stateDocumentMap.put(stateId, doc);
            return doc;
        }
    }

    
    /**
     * Extracts the selected value from the points dropdown list in the state-specific page.
     * @param statePage The Document object representing the state-specific page.
     * @return The value of the selected option in the points dropdown list.
     */
    private String getSelectedPointsValue(Document statePage) {
        Element pointsDropdown = statePage.selectFirst("select[name=ctl00$ContentSection$_pointsDropDownList]");
        if (pointsDropdown != null) {
            Elements options = pointsDropdown.select("option");
            if (!options.isEmpty()) {
                return options.first().attr("value");
            }
        }
        return null; // or a default value if necessary
    }
    
    /**
     * Extract the ranking and create a list of Ranking objects.
     * @param doc The HTML-DOM content of a rankings page (of a state).
     * @return A list of Ranking objects.
     */
    private List<Ranking> extractRankings(Document doc) {
        List<Ranking> rankings = new ArrayList<>();

        // Assuming the rankings are in a table with a specific ID
        Elements rows = doc.select("div#rankings table.dataTables-example tbody tr");
        int lastRank = 0;
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() >= 8) { // Replace with the actual number of columns
                try {
                    // Extract and parse each column's data
                	String rankStr = cols.get(0).text().trim();
                	int rank;
                	if(rankStr.isBlank()) {
                		rank = lastRank;
                	} else {
                		rank = Integer.parseInt(rankStr);
                	}
                    
                    lastRank = rank;
                    String name = cols.get(1).text().trim();
                    int birthYear = Integer.parseInt(cols.get(2).text().trim());
                    String club = cols.get(3).text().trim();
                    String time = cols.get(4).text().trim();
                    int points = Integer.parseInt(cols.get(5).text().trim());
                    String location = cols.get(6).text().trim();
                    String date = cols.get(7).text().trim();

                    // Create a new Ranking object and add it to the list
                    Ranking ranking = new Ranking(rank, name, birthYear, club, time, points, location, date);
                    rankings.add(ranking);
                } catch (NumberFormatException e) {
                    // Handle potential number format exceptions
                    System.err.println("Error parsing ranking data: " + e.getMessage());
                }
            }
        }

        return rankings;
    }
    
    private List<Meet> extractMeeting(Document doc) {
    	List<Meet> meets = new ArrayList<>();
    	return meets;
    }
    
    private List<Event> extractEvent(Document doc) {
    	List<Event> events = new ArrayList<>();
    	return events;
    }
    
    /**
     * Extract the clubs and create a list of ClubRef objects.
     * @param doc The HTML-DOM content of a rankings page (of a state).
     * @return A list of ClubRef objects.
     */
    private List<ClubRef> extractClubs(Document doc) {
        List<ClubRef> clubs = new ArrayList<>();
        
        // Assuming the clubs are in a table with a specific ID
        Elements rows = doc.select("div#clubs table tbody tr");
        int clubCounter = 0;
        for (Element row : rows) {
        	if(clubCounter == 0) {
        		// skip the headline row
        		clubCounter++;
        		continue;
        	}
        	
        	// Extract the club
            Elements cols = row.select("td");
            
            try {
                // Extract and parse each column's data
            	String name = cols.get(0).select("a").text();
            	String clubLink = cols.get(0).select("a").attr("href");
            	String clubLinkId = clubLink.split("?")[1].split("=")[1];
            	String zip = cols.get(1).text().trim();

                String city = cols.get(2).text().trim();
                String region = cols.get(3).text().trim();
                
                // Create a new ClubRef object and add it to the list
                ClubRef clubRef = new ClubRef(name, "", clubLinkId, zip, city, region);
                clubs.add(clubRef);
            } catch (NumberFormatException e) {
                // Handle potential number format exceptions
                System.err.println("Error parsing ranking data: " + e.getMessage());
            }
            
        }

        return clubs;
    }

    
    /**
     * Retrieves the rankings for a given state and event.
     * @param stateId The ID of the state.
     * @param event The event for which rankings are to be retrieved.
     * @return A list of Ranking objects representing the rankings.
     * @throws IOException If an I/O error occurs.
     */
    public List<Ranking> getRankings(int stateId, Event event, BirthClass birthClass, Season season) throws IOException {
        // Check if the state page has already been downloaded
        //if (!stateDocumentMap.containsKey(stateId)) {
            downloadStatePage(stateId);
        //}

        // Get the stored Document for the state
        Document statePage = stateDocumentMap.get(stateId);

        // Extract hidden form fields
        Map<String, String> formData = extractHiddenFields(statePage);

        // Add form data
        formData.put("__EVENTTARGET", "ctl00$ContentSection$_timerangeDropDownList");
        formData.put("__EVENTARGUMENT", "");
        formData.put("__LASTFOCUS", "");
        
        // Add additional form data based on the event
        formData.put("ctl00$ContentSection$_genderRadioButtonList", event.getSex().toString());
        formData.put("ctl00$ContentSection$_courseRadioButtonList", getCourseCode(event.getCourseType()));
        formData.put("ctl00$ContentSection$_timerangeDropDownList", season.getSeason());
        formData.put("ctl00$ContentSection$_ageDropDownList", birthClass.getFirstYear() + "|" + birthClass.getSecondYear());
        formData.put("ctl00$ContentSection$_pointsDropDownList", getSelectedPointsValue(statePage));
        formData.put("ctl00$ContentSection$hiddenTab", "#rankings");
        formData.put("ctl00$ContentSection$_regionsDropDownList", ""+ stateId);
        formData.put("ctl00$ContentSection$_eventDropDownList", event.getLength() + event.getSwimStyle().toString() + "|GL");
        
        // Perform a POST request with the form data
        Document rankingsDoc = postFormData(formData, stateId);
        
        formData.putAll(extractHiddenFields(statePage));
        formData.put("__EVENTTARGET", "ctl00$ContentSection$_timerangeDropDownList");
        rankingsDoc = postFormData(formData, stateId);
        
        formData.putAll(extractHiddenFields(rankingsDoc));
        formData.put("__EVENTTARGET", "ctl00$ContentSection$_rankingsButton");
        rankingsDoc = postFormData(formData, stateId);
        

        // Parse the HTML to extract rankings
        return extractRankings(rankingsDoc);
    }
}
