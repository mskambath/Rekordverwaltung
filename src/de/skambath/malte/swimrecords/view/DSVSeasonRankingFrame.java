package de.skambath.malte.swimrecords.view;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.skambath.malte.swimrecords.model.*;
import de.skambath.malte.swimrecords.model.Event;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class DSVSeasonRankingFrame extends JFrame {
    private static final long serialVersionUID = 1214354376L;
    private JTree recordTree;
    private JTable recordTable;
    private JLabel statusBar;
    private DSVSession dsvsession;

    public DSVSeasonRankingFrame(String title) {
        super(title);
        initializeUI();
    }

    private void initializeUI() {
    	this.dsvsession = new DSVSession();
    	
        // Baumansicht für Rekordlisten
        recordTree = new JTree();
        // Tabelle für Rekordinformationen
        //recordTable = new JTable(0, 4);
        recordTable = new JTable(0, 7);
       
        // Create the status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);

        // Layout-Manager für das Hauptfenster
        setLayout(new BorderLayout());
        
        // Create a JSplitPane to split the main content area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250); // Initial width of the left panel

        // Add the left and right panels to the split pane
        splitPane.setLeftComponent(new JScrollPane(recordTree));
        splitPane.setRightComponent(new JScrollPane(recordTable));

        // Add the split pane to the frame's content pane
        getContentPane().add(splitPane);
        add(statusBar, BorderLayout.SOUTH);

        // Setzen der Größe und Sichtbarkeit des Fensters
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        createRecordTree();
        
    }
    
    private void updateTableWithRankings(List<Ranking> rankings) {
        String[] columnNames = { "Rank", "Name", "Birth Year", "Club", "Time",  "Location", "Date" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Ranking ranking : rankings) {
            model.addRow(new Object[]{
                ranking.getRank(),
                ranking.getName(),
                ranking.getBirthYear(),
                ranking.getClub(),
                ranking.getTime(),
                ranking.getLocation(),
                ranking.getDate()
            });
        }

        recordTable.setModel(model);
    }

    
    
    private void setupTreeSelectionListener() {
        recordTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) recordTree.getLastSelectedPathComponent();

            if (selectedNode != null && selectedNode.isLeaf()) {
            	// Assume the node's user object contains necessary event info
                // You might need to adjust this based on your tree's data structure
            	
            	Integer year = (Integer) selectedNode.getUserObject();
            	DefaultMutableTreeNode eventNode = (DefaultMutableTreeNode) (selectedNode.getParent());
            	Event event = (Event) eventNode.getUserObject();
            	DefaultMutableTreeNode ageClassNode = (DefaultMutableTreeNode) (eventNode.getParent());
                AgeClass ac = (AgeClass) (ageClassNode).getUserObject();
                try {
                    // Fetch rankings for stateId 14 (as an example) and the selected event
                    List<Ranking> rankings = dsvsession.getRankings(14, event, ac.getBirthClass(year), new Season(year,false) );
                    updateTableWithRankings(rankings);
                } catch (IOException ex) {
                	List<Ranking> rankings = new ArrayList<>();
                	rankings.add(new Ranking(-1, "ERR", 0, "ERR", "", 0 , ex.getLocalizedMessage(), ""));
                    updateTableWithRankings(rankings);
                    ex.printStackTrace();
                    // Handle exceptions (e.g., show an error message)
                }
            }
        });
    }
    
    /**
     * Creates and initializes the record tree with the specific swim style structure.
     * The tree structure includes nodes for "Kurzbahn" and "Langbahn",
     * and under each, the valid combinations of lengths, swim styles, and sexes
     * based on the rules of swimming competitions.
     */
    private void createRecordTree() {


        // Create nodes for Kurzbahn and Langbahn
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Events");


        // Add all combinations for Kurzbahn
        addCombinations(root);


        recordTree.setModel(new DefaultTreeModel(root));
        setupTreeSelectionListener();
    }

    /**
     * Adds valid combinations of lengths, swim styles, and sexes to a given course node.
     * The combinations are added based on whether it is a short course (Kurzbahn) or a long course (Langbahn).
     * For Kurzbahn, all combinations are allowed, while for Langbahn, combinations are limited to specific rules.
     * 
     * @param courseNode The tree node representing either Kurzbahn or Langbahn.
     * @param sexes Array of sexes (e.g., "männlich", "weiblich").
     * @param swimStyles Array of swim styles (e.g., "S", "R", "B", "F", "L").
     * @param isShortCourse Boolean flag indicating whether the course is a short course. 
     *                     True for Kurzbahn, False for Langbahn.
     */
    private void addCombinations(DefaultMutableTreeNode rootNode) {
        int[] lengths = new int[]{50, 100, 200, 400, 800, 1500};
        List<CourseType> cts = new ArrayList<CourseType>( );
        cts.add(CourseType.SHORT_COURSE_METRIC);
        cts.add(CourseType.LONG_COURSE_METRIC);
        
        List<Sex> sexes = new ArrayList<Sex>( );
        sexes.add(Sex.Male);
        sexes.add(Sex.Female);
        
        List<AgeClass> ageClasses = new ArrayList<>();
        ageClasses.add(new AgeClass(-1, 1000));
        for(int i = 8; i < 20; i++) {
        	ageClasses.add(new AgeClass(i, i));
        }
        /*for(int i = 20; i <= 100; i+=5) {
        	ageClasses.add(new AgeClass(i, i+4));
        }*/
        
     
		//          Builds a hierarchical tree structure representing various swimming events. 
		//          The tree is organized first by course type, then by swim style, followed by event length,
		//          age classes, and finally individual events characterized by a combination of these attributes.
		//          
		//          The hierarchy is constructed as follows:
		//          - The top level of the tree consists of nodes representing different course types.
		//          - Under each course type node, swim style nodes are added.
		//          - For each swim style, event length nodes are created based on predefined lengths.
		//            Only valid combinations of swim style and length are considered:
		//              - Events with a length less than 400 meters, excluding medley styles.
		//              - Freestyle events for all lengths.
		//              - Medley events for lengths between 100 and 400 meters, including specific
		//                conditions for short course metrics.
		//          - Under each length node, age class nodes are added.
		//          - For each age class, event nodes are created for each sex category.
		//          
		//          Each event node is an instance of the Event class, representing a specific swimming
		//          event with a distinct combination of length, course type, sex category, and swim style.
         
        // Iterate over all course types
        for(CourseType courseType : cts) {
        	DefaultMutableTreeNode courseTypeNode = new DefaultMutableTreeNode(courseType);
        	rootNode.add(courseTypeNode);
        	
        	// Iterate over all swim styles
        	for (SwimStyle swimStyle : SwimStyle.values()) {
        		DefaultMutableTreeNode swimStyleNode = new DefaultMutableTreeNode(swimStyle);
        		courseTypeNode.add(swimStyleNode);
        		
        		// Iterate over all lengths
	        	for (int length : lengths) {
	            	DefaultMutableTreeNode lengthNode = new DefaultMutableTreeNode(length);
	                if (
	                	length < 400 && !swimStyle.equals(SwimStyle.Medley)
	                   || swimStyle.equals(SwimStyle.Freestyle)
	                   || (length > 100 && length <= 400 && swimStyle.equals(SwimStyle.Medley))
	                   || (length == 100 && courseType == CourseType.SHORT_COURSE_METRIC &&  swimStyle.equals(SwimStyle.Medley))
	                   ) {
	                	// Add only valid combinations of swim style, length, and the course type.
                		
	                	swimStyleNode.add(lengthNode);

                		for(AgeClass ageClass : ageClasses) { 
                			DefaultMutableTreeNode acNode = new DefaultMutableTreeNode(ageClass);
                			lengthNode.add(acNode);
                			for (Sex sex : sexes) {
                				Event event = new Event(length, courseType, sex, swimStyle);
                				DefaultMutableTreeNode eventNode = new DefaultMutableTreeNode(event);
                				acNode.add(eventNode);
                				for (int year = 2000; year <= LocalDateTime.now().getYear(); year++) {
                					DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode(year);
                					eventNode.add(yearNode);
                				}
	                    	}
	                    }
	                }
            	}
            }
        }
    }
}
