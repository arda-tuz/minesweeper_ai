package groupid;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.xml.transform.Source;

import org.w3c.dom.html.HTMLParamElement;
import org.xml.sax.SAXNotSupportedException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.concurrent.Flow;



public class CardLayoutTutorial {

    JFrame frame = new JFrame();
	JPanel panelCont = new JPanel();
	JPanel panelFirst = new JPanel();
	JPanel panelSecond = new JPanel();
	JButton buttonOne = new JButton("Switch to second panel/workspace");
	JButton buttonSecond = new JButton("Switch to first panel/workspace");

    JButton closingYesButton = new JButton("YES");
    JButton closingNoButton = new JButton("NO");

    JButton button4 = new JButton("TRUE");
	CardLayout cl = new CardLayout();
    boolean flagButton4 = true;

    public String currentPanel = "1";
    public String beforePanel = "2";


    public class ImagePanel extends JPanel implements MouseInputListener{

        public ArrayList<Image> imageList = new ArrayList<>(20); 
        public String imageHash = "default";

        public ImagePanel(String imageHash){

            super();
            this.imageHash = imageHash;
            addMouseListener(this);
            addMouseMotionListener(this);

            initImageList();
        }

        public ImagePanel(){

            super();
            this.imageHash = "default";
            addMouseListener(this);
            addMouseMotionListener(this);

            initImageList();
        }

        private void initImageList(){

            try {

                Image image0 = new ImageIcon(fixPath("src\\main\\resources\\tile_0.png")).getImage();
                Image image1 = new ImageIcon(fixPath("src\\main\\resources\\tile_1.png")).getImage();
                Image image2 = new ImageIcon(fixPath("src\\main\\resources\\tile_2.png")).getImage();
                Image image3 = new ImageIcon(fixPath("src\\main\\resources\\tile_3.png")).getImage();
                Image image4 = new ImageIcon(fixPath("src\\main\\resources\\tile_4.png")).getImage();
                Image image5 = new ImageIcon(fixPath("src\\main\\resources\\tile_5.png")).getImage();
                Image image6 = new ImageIcon(fixPath("src\\main\\resources\\tile_6.png")).getImage();
                Image image7 = new ImageIcon(fixPath("src\\main\\resources\\tile_7.png")).getImage();
                Image image8 = new ImageIcon(fixPath("src\\main\\resources\\tile_8.png")).getImage();
                Image imageClosed = new ImageIcon(fixPath("src\\main\\resources\\tile_closed.png")).getImage();
                Image imageFlag = new ImageIcon(fixPath("src\\main\\resources\\tile_flag.png")).getImage();
                Image imageMine = new ImageIcon(fixPath("src\\main\\resources\\tile_mine.png")).getImage();
                Image imagePurple = new ImageIcon(fixPath("src\\main\\resources\\Purple.png")).getImage();
                Image imageCyan = new ImageIcon(fixPath("src\\main\\resources\\cyan.jpg")).getImage();
                Image mineCounterImage = new ImageIcon(fixPath("src\\main\\resources\\mineCounterImage.png")).getImage();
                Image mineCounterSmall = new ImageIcon(fixPath("src\\main\\resources\\mineCounterSmall.jpg")).getImage();
                Image mineCounterBig= new ImageIcon(fixPath("src\\main\\resources\\mineCounterBig.jpg")).getImage();

                imageList.add(image0);
                imageList.add(image1);
                imageList.add(image2);
                imageList.add(image3);
                imageList.add(image4);
                imageList.add(image5);
                imageList.add(image6);
                imageList.add(image7);
                imageList.add(image8);
                imageList.add(imageClosed);
                imageList.add(imageFlag);
                imageList.add(imageMine);
                imageList.add(imagePurple);
                imageList.add(imageCyan);
                imageList.add(mineCounterImage);
                imageList.add(mineCounterSmall);
                imageList.add(mineCounterBig);
            
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            if(this.imageHash.equals("0")){

                g.drawImage(imageList.get(0), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("1")){

                g.drawImage(imageList.get(1), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("2")){

                g.drawImage(imageList.get(2), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("3")){

                g.drawImage(imageList.get(3), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("4")){

                g.drawImage(imageList.get(4), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("5")){

                g.drawImage(imageList.get(5), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("6")){

                g.drawImage(imageList.get(6), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("7")){

                g.drawImage(imageList.get(7), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("8")){

                g.drawImage(imageList.get(8), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("closed")){

                g.drawImage(imageList.get(9), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("flag")){

                g.drawImage(imageList.get(10), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("mine")){

                g.drawImage(imageList.get(11), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("purple")){

                g.drawImage(imageList.get(12), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("default")){

                g.drawImage(imageList.get(13), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("mineCounter")){

                g.drawImage(imageList.get(14), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("mineCounterSmall")){

                g.drawImage(imageList.get(15), 0, 0, getWidth(), getHeight(), this);
            }
            else if(this.imageHash.equals("mineCounterBig")){

                g.drawImage(imageList.get(16), 0, 0, getWidth(), getHeight(), this);
            }
            else{

                // ELSE
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
           
            
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON3) {  //! SAG BUTON 

                imageHash = "flag";                
            }
            else{

                if(this.imageHash.equals("0")){

                    imageHash = "1";
                }
                else if(this.imageHash.equals("1")){
    
                    imageHash = "2";
                }
                else if(this.imageHash.equals("2")){
    
                    imageHash = "3";
                }
                else if(this.imageHash.equals("3")){
    
                    imageHash = "4";
                }
                else if(this.imageHash.equals("4")){
    
                    imageHash = "5";
                }
                else if(this.imageHash.equals("5")){
    
                    imageHash = "6";
                }
                else if(this.imageHash.equals("6")){
    
                    imageHash = "7";
                }
                else if(this.imageHash.equals("7")){
    
                    imageHash = "8";
                }
                else if(this.imageHash.equals("8")){
    
                    imageHash = "closed";
                }
                else if(this.imageHash.equals("closed")){
    
                    imageHash = "flag";
                }
                else if(this.imageHash.equals("flag")){
    
                    imageHash = "mine";
                }
                else if(this.imageHash.equals("mine")){
    
                    imageHash = "0";
                }
            }

            this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
    }

    public String fixPath(String path){

        return path.replace("\\", "//");
    }

	public CardLayoutTutorial() {

		panelCont.setLayout(cl);


        ImagePanel panel1ButtonPanel = new ImagePanel("purple");
        panel1ButtonPanel.setBackground(Color.blue);

        panelFirst.setLayout(new BorderLayout());
        panelFirst.add(panel1ButtonPanel, BorderLayout.SOUTH);

        //-----------------------------------------------------------
        panelSecond.setLayout(new BorderLayout());
        

        JPanel gridPanel = new JPanel();
        // gridPanel.setBackground(Color.yellow);
        gridPanel.setLayout(new GridLayout(16, 16));

        ImagePanel specialPanel1 = new ImagePanel();
        specialPanel1.setBackground(Color.red);

        for (int i = 1; i <= 16 * 16; i++) {
            
            ImagePanel newObj = new ImagePanel("closed");
            newObj.setBackground((Color.green));

            gridPanel.add(newObj); 
        }

        panelSecond.add(gridPanel, BorderLayout.CENTER);
        //-----------------------------------------------------------

        panel1ButtonPanel.add(buttonOne);

        button4.setBackground(Color.CYAN);
        panel1ButtonPanel.add(this.button4);
        //!-----------------------------------------------------------------------
		// panelSecond.add(buttonSecond, BorderLayout.SOUTH);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.green);  
        southPanel.setLayout(new GridLayout(1,3));

        JPanel expPanel1 = new JPanel();
        expPanel1.setBackground(Color.black);
        southPanel.add(expPanel1);
        expPanel1.setLayout(new GridLayout(1,2));

        ImagePanel exPanel1_mineImage = new ImagePanel("mineCounterBig");
        ImagePanel exPanel1_mineText = new ImagePanel("default");

        expPanel1.add(exPanel1_mineImage); expPanel1.add(exPanel1_mineText);



        JPanel expPanel2 = new JPanel();
        expPanel2.setBackground(Color.blue);
        southPanel.add(expPanel2);

        JButton expButton1 = new JButton("southComp2");
        expButton1.setBackground(Color.lightGray);
        southPanel.add(expButton1);
        expButton1.setFont(new Font("Serif", Font.BOLD, 25));

        panelSecond.add(southPanel, BorderLayout.SOUTH);
        //!-----------------------------------------------------------------------
		panelFirst.setBackground(Color.BLUE);
		panelSecond.setBackground(Color.GREEN);

		panelCont.add(panelFirst, "1");
		panelCont.add(panelSecond, "2");
		cl.show(panelCont, "1");
		
		buttonOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelCont, "2");
                currentPanel = "2";
                beforePanel = "1";
			}
		});
		
		buttonSecond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(panelCont, "1");
                currentPanel = "1";
                beforePanel = "2";
			}
		});

        button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                
                if(flagButton4){

                    flagButton4 = false;
                    button4.setText("FALSE");
                }
                else{

                    flagButton4 = true;
                    button4.setText("TRUE");
                }
			}
		});
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 720;
        int height = 840;
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        frame.setLocation(x, y);
        frame.setResizable(false);  // Disable resizing

		frame.add(panelCont);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
        frame.setSize(720, 780);
		frame.setVisible(true);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new CardLayoutTutorial();
			}
		});


        // CardLayoutTutorial.AnaFrame anaFrame = new CardLayoutTutorial().new AnaFrame("AnaFrame");
	}

}