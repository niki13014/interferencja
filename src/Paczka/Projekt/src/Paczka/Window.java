package Paczka;  
  
   
  
import java.awt.*;
 
import java.awt.event.*; 
import java.io.File; 
import java.io.IOException; 


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*; 

  
  
public class Window  extends JFrame implements LineListener {  
     
    private static final long serialVersionUID = 1L; 
// utworzenie pól klasy takich jak: panele, etykiety i przyciski  
  
    JLabel label;  
    JLabel label2;  
    JButton buttonDiagrams;  
    JButton buttonAnimation;  
    JPanel panel1;  
    JPanel panel2;  
    String audioFilePath = "";
    Clip audioClip = null;
    File audioFile = null;
    AudioInputStream audioStream = null;
   
    
   
   
    boolean playCompleted = false;
    final String inFileName = "inter.wav"; 
  
    public Window()  throws HeadlessException{ //utworzenie konstruktora  
  
        super("Projekt - Interferencja");  
  
        this.setSize(800,300); //ustawienie wymiarów okna głównego  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
  
        //utworzenie paneli, dobór koloru tła oraz utworzenie layoutów  
  
        panel1=new JPanel();  
        panel2=new JPanel();  
  
        panel1.setBackground(Color.white);  
        panel2.setBackground(Color.white);  
  
   
        this.add(panel1, BorderLayout.LINE_START);  
        this.add(panel2, BorderLayout.CENTER);  
  
   
        panel1.setLayout(new GridLayout(2,1));  
        panel2.setLayout(new GridLayout(2,1));  
         
        //utworzenie przycisku: Animacje – ustawianie rozmiaru, koloru  
  
        buttonAnimation=new JButton("ANIMACJE");  
        buttonAnimation.setPreferredSize(new Dimension(150, 100));  
        buttonAnimation.setBackground(new Color(218,112,214));  
  
  
        //utworzenie etykiety, ustawienie koloru tła, dobór czcionki, koloru  
  
        label = new JLabel("        Zjawisko fizyczne - Interferencja!");  
        panel1.add(label);  
        label.setForeground(new Color(128,0,128));  
        label.setFont(new Font("Baskerville Old Face", Font.ITALIC, 40));  
  
        panel1.addMouseListener(new MotionListener());  
  
        //utworzenie przycisku: Wykresy – ustawianie rozmiaru, koloru  
  
        buttonDiagrams=new JButton("WYKRESY");  
        buttonDiagrams.setPreferredSize(new Dimension(150, 100));  
        buttonDiagrams.setBackground(new Color(218,112,214));  
        buttonDiagrams.addActionListener(new ButtonListenerDiagrams()); 
        buttonAnimation.addActionListener(new ButtonListenerAnimation()); 
        
        //po wciśnięciu ESCAPE okno zamyka się   
  
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);  
        Action escapeAction = new AbstractAction() {  
  
          
        private static final long serialVersionUID = 1L; 
  
        public void actionPerformed(ActionEvent e) {  
  
            System.exit(0);  
  
          }  
  
        };  
  
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(  
  
            escapeKeyStroke, "ESCAPE");  
  
        getRootPane().getActionMap().put("ESCAPE", escapeAction); 
          
  
    }  
  
    //po kliknięciu myszką pierwsze okno zmienia się w drugie  
  
    public class MotionListener implements MouseListener {  
  
            @Override  
  
            public void mouseClicked(MouseEvent event) {  
  
                label.setText("             Interferencja               ");  
                panel2.add(buttonAnimation);  
                panel2.add(buttonDiagrams);  
                
                play(inFileName);
  
            }  
  
            @Override  
  
            public void mouseEntered(MouseEvent arg0) {  
  
                // TODO Auto-generated method stub  
  
            }  
  
            @Override  
  
            public void mouseExited(MouseEvent arg0) {  
  
                // TODO Auto-generated method stub  
  
            }  
  
            @Override  
  
            public void mousePressed(MouseEvent arg0) {  
  
                // TODO Auto-generated method stub  
  
            }  
  
            @Override  
  
            public void mouseReleased(MouseEvent arg0) {  
  
                // TODO Auto-generated method stub  
  
  
            }  
             
    }; 
    
  //klasa dzięki której po kliknięciu w przycisk Wykresy otwiera się okno z Wykresami 
    
            public class ButtonListenerDiagrams extends Diagrams implements ActionListener{ 
  
                 
                //private static final long serialVersionUID = 1L; 
  
                @Override 
                public void actionPerformed(ActionEvent arg0) { 
                	  
                    Diagrams  frame=new Diagrams(); 
                    frame.setVisible(true); 
                     
                    } 
                     
                 
             }; 
     
             //klasa dzięki której po kliknięciu w przycisk Animacja otwiera się okno z Animacją
             
             public class ButtonListenerAnimation extends Animation implements ActionListener{ 
  
                    @Override 
                    public void actionPerformed(ActionEvent arg0) { 
                    	  
                        Animation  frame=new Animation(); 
                        frame.setVisible(true); 
                        } 
                         
                     
                  
         
        };  
        
        void play(String audioFilePath) {
            
            try {
                audioFile = new File(audioFilePath);
                audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.addLineListener(this);
                audioClip.open(audioStream);
                
                /**
                 *  Play the audio clip in a new thread not to block the GUI.
                 *  It helps in this case, but is not really necessary. 
                 */
                Thread thread = new Thread(new Runnable() {



                    public void run() {
                         audioClip.start();
                         while(!playCompleted){              
                                                     
                         }
                         audioClip.close();



                         try {
                             audioStream.close();
                         } catch (IOException e) {
                                 e.printStackTrace();
                         }
                                
                    }
                            
                });
                thread.start();
                
                



            } catch (UnsupportedAudioFileException ex) {
                System.out.println("The specified audio file is not supported.");
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                System.out.println("Audio line for playing back is unavailable.");
                ex.printStackTrace();
            } catch (IOException e1) {
                System.out.println("Error playing the audio file.");
                e1.printStackTrace();
            } 
             
        }
  
     //wywołanie głónego okna  
  
     public static void main(String[] args) {  
  
         SwingUtilities.invokeLater(  
  
         new Runnable(){  
  
             public void run() {  
  
                    Window window=new Window();  
                    window.setVisible(true);  
  
             }  
  
          }); } 
     
  
public void update(LineEvent event) {
    // TODO Auto-generated method stub
   
     LineEvent.Type type = event.getType();
    
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
            
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
   
}}
