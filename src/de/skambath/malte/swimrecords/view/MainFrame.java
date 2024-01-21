package de.skambath.malte.swimrecords.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.skambath.malte.swimrecords.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class contains the main window of the application. 
 * It also contains the static main method to start the application.
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 6185194290594049297L;
	private JMenuBar menuBar;
    private JTree recordTree;
    private JTable recordTable;
    private JLabel statusBar;
    private DSVSession dsvsession;
    private RecordDB recordDB;

    /**
     * Constructs the MainFrame with a specified title.
     * This constructor initializes the frame with various settings
     * such as size, close operation, and the initial layout.
     *
     * @param title The title of the frame.
     */
    public MainFrame(String title) {
        super(title);
        initializeUI();
    }

    private void initializeUI() {
    	List<Image> imgList = new ArrayList<>();
    	//imgList.add((new ImageIcon(getClass().getResource("/images/AppIcon16x16.png"))).getImage());
    	//imgList.add((new ImageIcon(getClass().getResource("/images/AppIcon32x32.png"))).getImage());
    	imgList.add((new ImageIcon(getClass().getResource("/images/AppIcon64x64.png"))).getImage());
    	imgList.add((new ImageIcon(getClass().getResource("/images/AppIcon128x128.png"))).getImage());
    	imgList.add((new ImageIcon(getClass().getResource("/images/AppIcon512x512.png"))).getImage());

        this.setIconImages(imgList);
    	
    	this.dsvsession = new DSVSession();
    	
        // Create the menu bar
        createMenuBar();

        // A tree for to list all available record tables in a hierarchy
        recordTree = new JTree();
        
        // A table to show the data of one selected record table
        recordTable = new JTable(0, 7);
       
        // Create the status bar
        statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);

        // Layout-Manager for the main window
        setLayout(new BorderLayout());
        
        // Create a JSplitPane to split the main content area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250); // Initial width of the left panel
        
        // Add the left and right panels to the split pane
        splitPane.setLeftComponent(new JScrollPane(recordTree));
        splitPane.setRightComponent(recordTable);

        // Add the split pane to the frame's content pane
        getContentPane().add(splitPane);
        add(statusBar, BorderLayout.SOUTH);

        // Set the size and visibility of the window
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        recordTree.setModel(null);
        
    }
    
    /**
     * Displays the record table and its associated individual time entries.
     * This method updates the GUI to show details of the selected record table along
     * with the list of individual time entries (such as rankings) associated with it.
     *
     * @param table The RecordTable to display. This contains information about the age class,
     *              sex category, and course type.
     * @param entries The list of IndividualTimeEntry objects associated with the record table.
     *                These entries typically include details like rank, name, club, time, etc.
     */
    private void displayRecordTable(RecordTable table, List<IndividualTimeEntry> entries) {
        String[] columnNames = { "", "Name", "Birth Year", "Club", "Time",  "Location", "Date" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (IndividualTimeEntry recordEntry : entries) {
        	// TODO: Design and implement the whole table creation process
            model.addRow(new Object[]{             
            		recordEntry.getName(),
            });
        }

        recordTable.setModel(model);
    }

    
    /**
     * Sets up a tree selection listener for the record tree.
     * This listener responds to user selections in the tree. When a leaf node (representing an event)
     * is selected, it fetches and displays the rankings associated with that event.
     */
    private void setupTreeSelectionListener() {
        recordTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) recordTree.getLastSelectedPathComponent();

            if (selectedNode != null && selectedNode.isLeaf()) {
            	// Assume the node's user object contains necessary event info
                // You might need to adjust this based on your tree's data structure
                RecordTable table = (RecordTable) selectedNode.getUserObject();
                try {
                    // TODO: get records from database
                	List<IndividualTimeEntry> entries = null; //
                	// TODO: show records in table
                	displayRecordTable(table, entries);
                	System.err.println("Not implemented yet");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Handle exceptions (e.g., show an error message)
                }
            }
        });
    }
    
    /**
     * Initializes the record tree hierarchy.
     * This method creates a tree structure where each record table is categorized
     * under its corresponding sex and course type.
     * 
     * The tree hierarchy is structured as follows:
     * Root Node ("Rekordtabellen")
     *   |
     *   |-- Sex Node
     *        |
     *        |-- Course Type Node
     *             |
     *             |-- Record Table Node
     *
     * This allows for an organized view of record tables, grouped first by sex and then by course type.
     */
    private void initializeRecordTableTree() {
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rekordtabellen");
        List<RecordTable> tables = recordDB.getAllRecordTables();

        Map<Sex, DefaultMutableTreeNode> sexNodes = new HashMap<>();
        Map<CourseType, DefaultMutableTreeNode> courseTypeNodes = new HashMap<>();

        for (RecordTable table : tables) {
            Sex sex = table.getSex();
            CourseType courseType = table.getCourseType();

            // Create or get the node for the specific sex
            DefaultMutableTreeNode sexNode = sexNodes.get(sex);
            if (sexNode == null) {
                sexNode = new DefaultMutableTreeNode(sex);
                root.add(sexNode);
                sexNodes.put(sex, sexNode);
            }

            // Create or get the node for the specific course type under this sex
            DefaultMutableTreeNode courseNode = courseTypeNodes.get(courseType);
            if (courseNode == null || courseNode.getParent() != sexNode) {
                courseNode = new DefaultMutableTreeNode(courseType);
                sexNode.add(courseNode);
                courseTypeNodes.put(courseType, courseNode);
            }

            // Add the record table node under the correct course type node
            DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table);
            courseNode.add(tableNode);
        }

        recordTree.setModel(new DefaultTreeModel(root));
        setupTreeSelectionListener();
    }

    

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menü "Datei"
        JMenu fileMenu = new JMenu("Datei");
        
        JMenuItem openDatabaseItem = new JMenuItem("Open Database");
        JMenuItem closeDatabaseItem = new JMenuItem("Close Database");
        JMenuItem createNewDatabaseItem = new JMenuItem("Create New Database");

        fileMenu.add(openDatabaseItem);
        fileMenu.add(closeDatabaseItem);
        fileMenu.add(createNewDatabaseItem);

        // Untermenü "Import"
        JMenu importMenu = new JMenu("Import");
        JMenuItem dsvSeiteItem = new JMenuItem("DSV-Seite Scannen");
        //JMenuItem ewk4Item = new JMenuItem(".ewk4");
        JMenuItem dsvItem = new JMenuItem(".dsv7");
        //JMenuItem csvItem = new JMenuItem(".csv");

        importMenu.add(dsvSeiteItem);
        //importMenu.add(ewk4Item);
        //importMenu.add(csvItem);
        importMenu.add(dsvItem);
        //fileMenu.add(importMenu);
        
        
        JMenuItem seasonRankingItem = new JMenuItem("Online-Bestenlisten");
        JMenuItem exitItem = new JMenuItem("Beenden");

        fileMenu.add(seasonRankingItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(importMenu);
        setJMenuBar(menuBar);

        // Ereignisbehandlung für Menüpunkte
        dsvSeiteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementieren Sie hier den Import aus der DSV-Seite
            	YearSelectionDialog yearChooser = new YearSelectionDialog(MainFrame.this);
                if(yearChooser.showModal()) {                	
                	Integer selectedYear = yearChooser.getSelectedYear();
                	System.out.println("Selected Year: " + selectedYear);
                }

            }
        });
        
        seasonRankingItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unused")
				JFrame frame = (new DSVSeasonRankingFrame("DSV Online-Bestenliste"));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        openDatabaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Database Files", "*.sqlite"));
                int result = fileChooser.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    // Open the selected database file
                    recordDB = new RecordDB(selectedFile.getAbsolutePath());
                    initializeRecordTableTree();
                }
            }
        });
        
        createNewDatabaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Database Files", "*.sqlite"));
                int result = fileChooser.showSaveDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file for the new database
                    File selectedFile = fileChooser.getSelectedFile();
                    // Create the new database using the selected file
                    // Implement the logic to create the database here
                    recordDB = new RecordDB(selectedFile.getAbsolutePath());
                    initializeRecordTableTree();
                }
            }
        });
        
        closeDatabaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to close the current database here
            	recordDB.closeConnection();
            	recordDB = null;
            }
        });
    }
   
    /**
     * The entry point of the application.
     * This method initializes and displays the main window (or other components) of the application.
     * @param args Command-line arguments passed to the application. These are typically not used in a GUI application.
     */
    public static void main(String[] args) {
        // Set the look and feel here
        try {
        	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame("Schwimmrekord-Verwaltung");
            }
        });
    }
}
