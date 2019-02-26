
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SUYOG
 *
 *
 */
public class GUI {

    static JFileChooser fc;
    static Thread gxthread;
    static JTextArea message = new JTextArea();
    public static File path;

    static Runnable doRun = new Runnable() {

        final class ChooseAction implements ActionListener {

            final ImagePane drawPan;

            ChooseAction(final ImagePane drawPan) {
                this.drawPan = drawPan;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                //JFileChooser navegador = new JFileChooser();
                if (fc.showOpenDialog(null) == 0) {
                    try {
                        BufferedImage imagenAbrir = ImageIO.read(fc.getSelectedFile());
                        path = fc.getSelectedFile();
                        //drawPan.paintComponents(imagenAbrir.getGraphics());
                        drawPan.drawImage(imagenAbrir);
                    } catch (IOException ie) {
                        JOptionPane.showMessageDialog(null, "Ocurriò un error al guardar la imàgen");
                    }
                }
            }

        }

        final class ImagePane extends JPanel {

            private static final long serialVersionUID = 1L;
            private BufferedImage myImage;

            public ImagePane(final BufferedImage myImage) {
                this.myImage = myImage;
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 500);
            }
            
            
           
            
            public void drawImage(BufferedImage img) {
                this.myImage = img;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (myImage != null) {
//                    Graphics2D g2d = (Graphics2D) g.create();
//                    int x = (getWidth() - myImage.getWidth()) / 2;
//                    int y = (getHeight() - myImage.getHeight()) / 2;
                    int h, w;
                    w = myImage.getWidth();
                    h = myImage.getHeight();
                    //BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    //bi.getGraphics();
                    g.drawImage(myImage, 0, 0, 600, 400, this);
                    //g2d.drawImage(myImage, x, y, this);
                    //g2d.drawImage(myImage, 600, 40, x, y, this);
                    //g2d.drawImage(myImage, 300, 300, this);
                    //g2d.dispose();
                }
            }

        }

        @Override
        public void run() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            final JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            panel.setBounds(600, 50, 300, 300);
            
          //  panel.setLayout(new BorderLayout());
            //panel.setLayout(null);
            JButton b1, b2;

            fc = new JFileChooser();

            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);
            JMenu menu = new JMenu("File");
            JMenu about = new JMenu("About");

            JMenuBar menuBar = new JMenuBar();

            JMenuItem i1 = new JMenuItem("Load Image");
            // JMenuItem i2 = new JMenuItem("Save Image");
            JMenuItem i3 = new JMenuItem("Exit");

            menu.setMnemonic('F');
            about.setMnemonic('A');

            menuBar.add(menu);
            menuBar.add(about);
            menu.add(i1);
            // menu.add(i2);
            menu.add(i3);

            about.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent me) {
                    JOptionPane.showMessageDialog(null,"This is ImageRecognizer", "About", JOptionPane.PLAIN_MESSAGE);
                }

                @Override
                public void menuDeselected(MenuEvent me) {
                    frame.repaint();
                }

                @Override
                public void menuCanceled(MenuEvent me) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

//        about.addItemListener(new ItemListener() {
//                 @Override
//                 public void itemStateChanged(ItemEvent ie) {
//                 
//                                       
//                 }
//                        
//                 
//        });
            class gx extends Thread {

                @Override
                public void run() {
                    path = fc.getSelectedFile();
                    System.out.println(path);
                    Mat img = imread(path.toString(), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
                    Mat gx;
                    gx = new Mat();
                    Imgproc.Sobel(img, gx, CvType.CV_8U, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
                    HighGui.namedWindow("DisplayWindow", HighGui.WINDOW_AUTOSIZE);
                    HighGui.resizeWindow("DisplayWindow", 700, 400);
                    Imgproc.resize(gx, gx, new Size(700, 400));
                    HighGui.imshow("DisplayWindow", gx);
                    HighGui.waitKey(0);
                    HighGui.destroyAllWindows();
                }

            }

            class gy extends Thread {

                @Override
                public void run() {
                    path = fc.getSelectedFile();
                    System.out.println(path);
                    Mat img = imread(path.toString(), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
                    Mat gy;
                    gy = new Mat();
                    Imgproc.Sobel(img, gy, CvType.CV_8U, 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);
                   
                    HighGui.namedWindow("DisplayWindow", HighGui.WINDOW_AUTOSIZE);
                    HighGui.resizeWindow("DisplayWindow", 700, 400);
                    Imgproc.resize(gy, gy, new Size(700, 400));
                    HighGui.imshow("DisplayWindow", gy);
                    HighGui.waitKey(0);
                    HighGui.destroyAllWindows();
                }

            }

            class detectP extends Thread {

                @Override
                public void run() {

                    HOGDescriptor vehDetector = new HOGDescriptor();
                    vehDetector.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());

                    Mat image = Imgcodecs.imread(path.toString());

                    MatOfRect vehDetections = new MatOfRect();
                    MatOfDouble foundWeights = new MatOfDouble();
                    //vehDetector.detectMultiScale(image, vehDetections, foundWeights);
                    vehDetector.detectMultiScale(image, vehDetections, foundWeights, 0, new Size(4, 4), new Size(32, 32), 1.1, 0, true);

                    System.out.println(String.format("Detected %s People", vehDetections.toArray().length));

                    for (Rect rect : vehDetections.toArray()) {
                        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

                        String filename = "PeopleDetectionGUI.jpg";
                        System.out.println(String.format("Writing" + filename));
                        Imgcodecs.imwrite(filename, image);

                    }
                    Mat gx = Imgcodecs.imread("PeopleDetectionGUI.jpg");
                    HighGui.namedWindow("DisplayWindow", HighGui.WINDOW_AUTOSIZE);
                    HighGui.resizeWindow("DisplayWindow", 700, 400);
                    HighGui.imshow("DisplayWindow", gx);

                    HighGui.waitKey(0);
                    HighGui.destroyAllWindows();
                }
            }

            ImagePane drawPan = new ImagePane(null);

            i1.addActionListener(new ChooseAction(drawPan));

//        i1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//           int returnVal = fc.showOpenDialog(frame);
//           
//            if(returnVal == JFileChooser.APPROVE_OPTION){
//               
//                   try {
//                       BufferedImage imagenAbrir = ImageIO.read(fc.getSelectedFile());
//                        
//                       
//                   } catch (IOException ex) {
//                       Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//                   }                                            
//                   
//                   
//                   
//               
//                   
//            }else{
//                System.out.println("File not Selected");
//            }
//            }
//        });
//        i2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//           int returnVal = fc.showSaveDialog(frame);
//            if(returnVal==JFileChooser.APPROVE_OPTION){
//               File files = fc.getSelectedFile();
//                 System.out.println(files);
//                
//                
//            }else{
//                System.out.println("File not Saved");
//            }
//            }
//        });
            
          JLabel background = new JLabel(new ImageIcon("background1.jpg"));
        
        
        JButton b = new JButton("Get GX");
            JButton a = new JButton("Get GY");
            Icon car = new ImageIcon("iconcar.png");
            Icon flower = new ImageIcon("iconflower.png");
            Icon people = new ImageIcon("iconpeople.png");
            Icon aero = new ImageIcon("aero.png");
            Icon dog = new ImageIcon("icondog.png");
            Icon bike = new ImageIcon("iconbike.png");

            JButton detectCar = new JButton("Is Car ?");
            JButton detectaero = new JButton("Is Areoplane ?", aero);
            JButton detectDog = new JButton("Is Dog?", dog);
            JButton detectBike = new JButton("Is Bike?", bike);
            detectCar.setIcon(car);
            //detectCar.setRolloverIcon(car);
            JButton detectFlower = new JButton("Is Flower?", flower);

            a.setBounds(40, 40, 100, 40);
            b.setBounds(40, 100, 100, 40);
            detectCar.setBounds(40, 400, 150, 50);
            detectaero.setBounds(40, 475, 150, 50);
            detectFlower.setBounds(40, 325, 150, 50);
            //detectDog.setBounds(210,575,100,50);
            detectBike.setBounds(40, 250, 150, 50);

            a.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    gy t2 = new gy();
                    t2.start();
                }
            });

            detectDog.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    new detectDog();
                }
            });

            JButton detectPeople = new JButton("Detect People", people);

            //  detectPeople.setBounds(40, 500, 200, 50);
            detectaero.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    new detectaricraft();
                }
            });
            detectFlower.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    new detectflowers();
                }
            });
            detectCar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    new detectVehicle();
                }
            });

            detectBike.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    new detectBike();
                }
            });
            frame.setContentPane(background);

            frame.add(a);
            frame.add(detectaero);
            frame.add(detectCar);
            frame.add(detectFlower);
            frame.add(detectBike);

//        j.add(a);
//        j.add(detectaero);
//        j.add(detectCar);
//        j.add(detectFlower);
//        j.add(detectBike);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    gx t1 = new gx();
                    t1.start();

                }
            });

            detectPeople.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    detectP t2 = new detectP();
                    t2.start();
                }
            });

            message.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            // message.setText("Hello World2");
            message.setEditable(false);
            message.setBackground(Color.lightGray);
            message.setForeground(Color.WHITE);
            Font font = new Font("Serif", Font.BOLD, 40);
            message.setFont(font);
            // message.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            frame.add(b);
            // j.add(b);
            //frame.add(detectPeople);
           
            //frame.add(detectDog);  j.add(detectDog);
            frame.add(message);
           
            frame.add(message);
            message.setBounds(600, 550, 500, 100);
            //message.setBackground(new Color(0, 0, 0, 50));
            //b.addActionListener(new ChooseAction(drawPan));
            //panel.add(b,BorderLayout.SOUTH);
            // panel.setBounds(600, 0, 600, 400);
            //  j.setBackground(Color.yellow);
            //frame.add(j,BorderLayout.WEST);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //drawPan.setBorder(BorderFactory.createLineBorder(Color.RED));
           drawPan.setBackground(new Color(0, 0, 0,50));
            //panel.add(drawPan);
            //frame.add(panel);
            
            frame.setJMenuBar(menuBar);
            drawPan.setBounds(600, 40, 600, 400);
            
            
            
            frame.add(drawPan);
            //frame.pack();
           frame.setLayout(null);
          
          //  frame.setLocationRelativeTo(null);
            frame.setSize(1300, 700);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            i3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    frame.dispose();
                }
            });
        }
    };

    public static void main(String[] args) {
        System.loadLibrary("opencv_java341");
        SwingUtilities.invokeLater(doRun);

    }

}
