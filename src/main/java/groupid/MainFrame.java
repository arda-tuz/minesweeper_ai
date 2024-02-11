package groupid;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.chrono.MinguoChronology;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;


public class MainFrame extends JFrame implements WindowListener{

    public Color lightBlue = new Color(100, 200, 255);
    public Color greyBlue = new Color(70, 130, 180);
    public Color lighterGreyBlue = new Color(90, 150, 200);
    public Color orange = new Color(255, 165, 0);
    public String currentPanel;
    public ImagePanel clPanel; // card layout'a sahip olan ve diger tum panelleri tasiyan panel 
    public HashMap<String, ImagePanel> clPanelMap;
    public CardLayout cl;

    public ImagePanel menu_north_panel;

    public Game game = null;
    
    public String fixPath(String path){

        return path.replace("\\", "//");
    }

    public MainFrame(){

        addWindowListener(this);
        this.clPanel = new ImagePanel(this);
        this.cl = new CardLayout();
        clPanel.setLayout(cl);

        //     "7" tane sliding panel var 
        ImagePanel cl_menu  = new ImagePanel(this);
        ImagePanel cl_custom = new ImagePanel(this);
        ImagePanel cl_win7 = new ImagePanel(this);
        ImagePanel cl_number = new ImagePanel(this);
        ImagePanel cl_mouse = new ImagePanel(this);
        ImagePanel cl_close = new ImagePanel(this);

        clPanel.add(cl_menu, "cl_menu");
         clPanel.add(cl_custom,"cl_custom");
        clPanel.add(cl_win7, "cl_win7");
        clPanel.add(cl_number,"cl_number");
        clPanel.add(cl_mouse, "cl_mouse");
        clPanel.add(cl_close, "cl_close");


        this.clPanelMap = new HashMap<>(7);
        
        clPanelMap.put("cl_menu", cl_menu);
         clPanelMap.put("cl_custom", cl_custom);
        clPanelMap.put("cl_win7", cl_win7);
        clPanelMap.put("cl_number", cl_number);
        clPanelMap.put("cl_mouse", cl_mouse);
        clPanelMap.put("cl_close", cl_close);

        initialize_cl_panels(this); // initialize everything related to all of the 7 sliding panels

        initializeMainFrame();  // initialize  everything that will be displayed initially 
    }

    private void initializeMainFrame(){     //? MAIN FRAME INITIALIZER METHOD

        this.addWindowListener(this);

        this.add(clPanel);
        cl.show(clPanel, "cl_menu");
        this.currentPanel = "cl_menu";

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 720;
        int height = 840;
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        this.setLocation(x, y);
        this.setResizable(false);  // Disable resizing

        this.setSize(720, 780);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        this.setVisible(true);
    }

///?====================================================================================================================================
///?====================================================================================================================================
//?                         INITIALIZER METHOD()'S
///?====================================================================================================================================
///?====================================================================================================================================
///?====================================================================================================================================
    private void initialize_cl_panels(MainFrame mainFrame){    

       initialize_cl_menu(mainFrame);

       initialize_cl_win7(mainFrame);

       initialize_cl_close(mainFrame);
    }
///?====================================================================================================================================
    private void initialize_cl_menu(MainFrame mainFrame){

        ImagePanel cl_menu = clPanelMap.get("cl_menu");
        cl_menu.setLayout(new BorderLayout());

        ImagePanel menu_south_panel = new ImagePanel(mainFrame, "whiteBlue");
        menu_south_panel.setLayout(new FlowLayout());
        cl_menu.panelMap.put("menu_south_panel", menu_south_panel);
        cl_menu.add(menu_south_panel, BorderLayout.SOUTH);

        ImagePanel menu_north_panel = new ImagePanel(mainFrame);


        //!     main menu'de GIF gosterme burada yapiliyor
        ImageIcon originalIcon = new ImageIcon(this.fixPath("src\\main\\resources\\MainMenuGif.gif"));
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(706, 684, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel mainMenuLabel = new JLabel(resizedIcon);
        menu_north_panel.setLayout(new BorderLayout());
        menu_north_panel.add(mainMenuLabel, BorderLayout.CENTER);
        this.menu_north_panel = menu_north_panel;

        cl_menu.panelMap.put("menu_north_panel", menu_north_panel);
        cl_menu.add(menu_north_panel, BorderLayout.CENTER);

        ImagePanel windowsButtonPanel = new ImagePanel(mainFrame, "windowsIcon1");
        ImagePanel customButtonPanel = new ImagePanel(mainFrame, "customIcon1");

        PanelButtonListener windowsButtonPanelListener = new PanelButtonListener(mainFrame, windowsButtonPanel, null, "windows");
        PanelButtonListener customButtonPanelListener = new PanelButtonListener(mainFrame, null, customButtonPanel, "custom");

        windowsButtonPanel.addMouseListener(windowsButtonPanelListener); windowsButtonPanel.addMouseMotionListener(windowsButtonPanelListener);
        customButtonPanel.addMouseListener(customButtonPanelListener); customButtonPanel.addMouseMotionListener(customButtonPanelListener);

        Dimension preferredSizeWindows = new Dimension(200, 125); 
        windowsButtonPanel.setPreferredSize(preferredSizeWindows);

        Dimension preferredSizeCustom = new Dimension(200, 125); 
        customButtonPanel.setPreferredSize(preferredSizeCustom);

        ImagePanel araPanel = new ImagePanel(mainFrame);
        Dimension preferredSizeAraPanel = new Dimension(200, 125); 
        araPanel.setPreferredSize(preferredSizeAraPanel);

        menu_south_panel.add(windowsButtonPanel);  menu_south_panel.add(araPanel); menu_south_panel.add(customButtonPanel);
    }
///?====================================================================================================================================    
    private void initialize_cl_win7(MainFrame mainFrame){

        ImagePanel cl_win7 = clPanelMap.get("cl_win7");
        cl_win7.setLayout(new GridLayout(3,1));    

        ImagePanel upPanel = new ImagePanel(mainFrame);
        ImagePanel middlePanel = new ImagePanel(mainFrame);
        ImagePanel downPanel = new ImagePanel(mainFrame);

        upPanel.setLayout(new BorderLayout());
        middlePanel.setLayout(new BorderLayout());
        downPanel.setLayout(new BorderLayout());

        cl_win7.add(upPanel);       
        cl_win7.add(middlePanel);
        cl_win7.add(downPanel);

        cl_win7.panelMap.put("up", upPanel);
        cl_win7.panelMap.put("middle", middlePanel);
        cl_win7.panelMap.put("down", downPanel);

        MyJbutton upButton = new MyJbutton(mainFrame, "<html><u>BEGINNER</u>~  9x9 board with 10 mines</html>");
        upButton.setBackground(mainFrame.greyBlue);
        upButton.setActionCommand("beginner");
        cl_win7.buttonMap.put("win7Button", upButton);
        upButton.setFont(new Font("Serif", Font.BOLD, 35));

        MyJbutton middleButton = new MyJbutton(mainFrame, "<html><u>INTERMEDIATE</u>~  16x16 board with 40 mines</html>");
        middleButton.setBackground(mainFrame.greyBlue);
        middleButton.setActionCommand("intermediate");
        cl_win7.buttonMap.put("numberButton", middleButton);
        middleButton.setFont(new Font("Serif", Font.BOLD, 33));

        MyJbutton downButton = new MyJbutton(mainFrame, "<html><u>EXPERT</u>~  30x16 board with 99 mines</html>");
        downButton.setBackground(mainFrame.greyBlue);
        downButton.setActionCommand("expert");
        cl_win7.buttonMap.put("mouseButton", downButton);
        downButton.setFont(new Font("Serif", Font.BOLD, 35));

        upButton.boyansinMi = true; middleButton.boyansinMi = true; downButton.boyansinMi = true;
        upPanel.add(upButton); middlePanel.add(middleButton); downPanel.add(downButton);
    }
///?====================================================================================================================================
    private void initialize_cl_close(MainFrame mainFrame){

        ImagePanel cl_close = clPanelMap.get("cl_close");
        cl_close.setLayout(new BorderLayout());

        ImagePanel cl_close_south_panel = new ImagePanel(mainFrame);
        cl_close_south_panel.setLayout(new FlowLayout());
        cl_close.panelMap.put("cl_close_south_panel", cl_close_south_panel);
        cl_close.add(cl_close_south_panel, BorderLayout.SOUTH);

        ImagePanel cl_close_north_panel = new ImagePanel(mainFrame, "quitMessage");
        cl_close.panelMap.put("cl_close_north_panel", cl_close_north_panel);
        cl_close.add(cl_close_north_panel, BorderLayout.CENTER);

        MyJbutton yesButton = new MyJbutton(mainFrame, "YES");
        yesButton.setBackground(this.greyBlue);
        yesButton.setActionCommand("quitYes");
        cl_close.buttonMap.put("yesButton", yesButton);
        yesButton.setFont(new Font("Serif", Font.BOLD, 90));

        // ImagePanel araPanel = new ImagePanel(mainFrame);   
        // Dimension preferredSize = new Dimension(200, araPanel.getPreferredSize().height); 
        // araPanel.setPreferredSize(preferredSize);

        MyJbutton noButton = new MyJbutton(mainFrame, "NO");
        noButton.setBackground(this.greyBlue);
        noButton.setActionCommand("quitNo");
        cl_close.buttonMap.put("noButton", noButton);
        noButton.setFont(new Font("Serif", Font.BOLD, 90));

        yesButton.setForeground(Color.black);
        noButton.setForeground(Color.black);
        yesButton.boyansinMi = true;
        noButton.boyansinMi = true;

        cl_close_south_panel.add(yesButton);
        // cl_close_south_panel.add(araPanel);

        ImagePanel menuPanel = new ImagePanel(mainFrame, "menuIcon");

        PanelButtonListener panelButtonListener4  = new PanelButtonListener(mainFrame, null, null, null, 
        menuPanel   ,"menu");
        menuPanel.addMouseListener(panelButtonListener4);
        menuPanel.addMouseMotionListener(panelButtonListener4);
  
        Dimension preferredSize = new Dimension(200, 125); 
        menuPanel.setPreferredSize(preferredSize);

        cl_close_south_panel.add(menuPanel);

        cl_close_south_panel.add(noButton);
    }
///?====================================================================================================================================
///?====================================================================================================================================
///?====================================================================================================================================
    public void initializeGame(MainFrame mainFrame, int mineNumber, int satir, int sutun){

        this.game = new Game(mainFrame, mineNumber, satir, sutun);      //? cl_game panelinin herseyi game icinde initialize ediliyor
        
        //TODO later initializations will go on ...
    }
///?====================================================================================================================================
    public static void main(String[] args){

        MainFrame mainFrame = new MainFrame();

        //TODO later initializetin about the mainFrame will go on 
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
        
    }

    @Override
    public void windowClosing(WindowEvent e) {  //?     cl_close paneli ilgilendiren yer burasi 
        
        cl.show(clPanel, "cl_close");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
}
