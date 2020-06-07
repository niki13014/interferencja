package Paczka; 

import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 
import java.awt.event.WindowEvent; 
import java.awt.event.WindowListener; 
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.*; 
import java.net.*; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import javax.imageio.*; 
import javax.sound.sampled.LineListener; 
import javax.swing.*; 
import javax.swing.event.*; 

 
	public class Animation extends JFrame{ 

		//stworzenie pól: stałych, paneli, przycisków, pól do wpisywania oraz elementów do menu
		private static final long serialVersionUID = 1L;
		static final int SLIDER_MIN = 500; 
		static final int SLIDER_MAX = 700; 
		static final int SLIDER_INIT = 500; 
		
		static final int SLIDER_MIN_DISTANCE = 0; 
		static final int SLIDER_MAX_DISTANCE = 200; 
		static final int SLIDER_INIT_DISTANCE = 0; 
		
		JPanel panelAnimation1; 
		AnimationPanel panelAnimation2; 
		JPanel panelAnimation3; 
		JPanel panelAnimation4; 
		JPanel panelAnimation5; 
		JButton buttonON; 
		JButton buttonRESET; 
		JSlider sliderDistance; 
		JLabel labelLambda; 
		JLabel labelDistance; 
		JSlider sliderLambda; 
		SpinnerNumberModel model1; 
		BufferedImage image; 
		JTextField fieldLambda; 
		JTextField fieldDistance; 

		JMenuBar menuBar; 
		JMenu menu; 
		JMenuItem menuItem1; 
		JMenuItem menuItem2; 
		JMenuItem menuItem3; 
		
		
		public static int lambda; 
		public static int distance; 

     

		public  Animation()  throws HeadlessException{ 

			super("Animacja"); 
			this.setSize(1000,600); 
			setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
			
			//funkcja dzięki której po wciśnięciu ESCAPE zamykają się wszystkie okna 
			KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false); 
			Action escapeAction = new AbstractAction() { 
				
				public void actionPerformed(ActionEvent e) { 
					System.exit(0); 

				} 

			}; 
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( 
					escapeKeyStroke, "ESCAPE"); 
			getRootPane().getActionMap().put("ESCAPE", escapeAction); 
			
			//tworzenie menu 
			menuBar = new JMenuBar(); 
			this.setJMenuBar(menuBar); 
			menu = new JMenu("Menu"); 
			menuBar.add(menu); 


			menuItem1 = new JMenuItem("Zamknięcie okna"); 
			menu.add(menuItem1); 
			menuItem1.addActionListener(new ActionListener(){ 

			@Override 
			public void actionPerformed(ActionEvent arg0) { 
				dispose(); 
				}}); 

			menuItem2 = new JMenuItem("Wybierz kolor tła"); 
			menu.add(menuItem2); 
			menuItem2.addActionListener(new Colors()); 

			menuItem3 = new JMenuItem("Wybierz kolor fal"); 
			menu.add(menuItem3); 
			ActionListener AnimationColorListener = new ActionListener() 
			  { 
			  	public void actionPerformed(ActionEvent arg0) { 

  				Color newColor = JColorChooser.showDialog(null,"Choose Background Color",panelAnimation2.getBackground()); 
  				panelAnimation2.changeColor(newColor); 
  				} 
  				}; 
  				
  				
  			menuItem3.addActionListener(AnimationColorListener); 
  			

            //(bedzie rozwiniete potem) 
			
			//tworzenie paneli i ich umiejscowienia w oknie
			panelAnimation1=new JPanel(); 
			panelAnimation2=new AnimationPanel(); 
			panelAnimation3=new JPanel(); 
			panelAnimation4=new JPanel(); 
			panelAnimation5=new JPanel(); 
			
			this.add(panelAnimation1, BorderLayout.PAGE_START); 
			this.add(panelAnimation2, BorderLayout.CENTER); 
			this.add(panelAnimation3, BorderLayout.PAGE_END); 
			this.add(panelAnimation4, BorderLayout.LINE_START); 
			this.add(panelAnimation5, BorderLayout.LINE_END); 

			//stworzenie etykiety odleglosc, a pozniej odpowiedniego spinnera  który umozliwia zmiane odleglosci 
			panelAnimation4.setLayout(new GridLayout(4,1)); 
			labelDistance = new JLabel("odległość:"); 
			panelAnimation1.add(labelDistance); 
			
			labelDistance.setHorizontalAlignment(JLabel.CENTER); 
			sliderDistance = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN_DISTANCE, SLIDER_MAX_DISTANCE, SLIDER_INIT_DISTANCE); 
			sliderDistance.addChangeListener(new SliderChangeListener()); 
			sliderDistance.addChangeListener(new SliderDistanceChangeListener()); 
			sliderDistance.setPaintTicks(true); 
			sliderDistance.setPaintTrack(true); 
			sliderDistance.setMajorTickSpacing(50); 
			sliderDistance.setMinorTickSpacing(0); 
			sliderDistance.setPaintLabels(true); 
			
			sliderDistance.addChangeListener(new ChangeListener() { 

				@Override 
				public void stateChanged(ChangeEvent e) { 
					distance= (int)sliderDistance.getValue(); 
            } 
				}); 
			
			panelAnimation1.add(sliderDistance); 
			fieldDistance = new JTextField("       "); 
			panelAnimation1.add(fieldDistance); 
			
			
			//stworzenie etykiety dlugosc fali, a pozniej odpowiedniego spinnera  który umozliwia zmiane odleglosci 
			labelLambda=new JLabel("Długość fali λ: ");
			panelAnimation1.add(labelLambda); 
			sliderLambda=new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT); 
			panelAnimation1.add(sliderLambda); 
			sliderLambda.addChangeListener(new SliderChangeListener()); 
			sliderLambda.setPaintTicks(true); 
			sliderLambda.setPaintTrack(true); 


			sliderLambda.setMajorTickSpacing(50); 
			sliderLambda.setMinorTickSpacing(0); 
			sliderLambda.setPaintLabels(true); 
			fieldLambda = new JTextField("       "); 
			panelAnimation1.add(fieldLambda); 
			
			sliderLambda.addChangeListener(new ChangeListener() { 

	            @Override 
	            public void stateChanged(ChangeEvent e) { 
	
	            	lambda = (int)sliderLambda.getValue()-470;
	            	} 
	            }); 
			
			//stworzenie przycisku ON, który będzie odpowiedzialny za rozpoczecie animacji, jednak funkcja ta zostanie dodana pozniej  
			buttonON=new JButton("ON"); 
			panelAnimation4.add(buttonON); 
			
			ActionListener AnimationONListener = new ActionListener(){ 
			 
			 public void actionPerformed(ActionEvent arg0) { 
			 	panelAnimation2.active=true; 
			 	go(); 
			 	} 
			 	}; 
			 	
			 buttonON.addActionListener(AnimationONListener);
			
			//stworzenie przycisku STOP/RESET, który będzie odpowiedzialny za zakonczenie animacji, jednak funkcja ta zostanie dodana pozniej 
			buttonRESET=new JButton("STOP/RESET"); 
			panelAnimation4.add(buttonRESET); 
			
			ActionListener AnimationRESETListener = new ActionListener() {
			  
			 	public void actionPerformed(ActionEvent arg0) { 
			 	
			 	stop(); 
			 	} 
			 	}; 
			 
			buttonRESET.addActionListener(AnimationRESETListener);
			 panelAnimation2.setBackground(Color.white); 
}  

			public void go(){ 
			
			ExecutorService exec = Executors.newFixedThreadPool(1); 
			exec.execute(panelAnimation2); 
			exec.shutdown(); 
			
			} 
			
			public void stop(){ 
		
		
				panelAnimation2.n=0; 
				panelAnimation2.f=0; 
				panelAnimation2.z=0; 
				panelAnimation2.active=false; 
			
			}
			
		//klasy pozwalająca na działanie sliderów 
			public class SliderChangeListener implements ChangeListener{ 
			
			@Override 
			public void stateChanged(ChangeEvent arg0) { 
				
				String value = String.format("%d", sliderLambda.getValue()); 
				fieldLambda.setText(value); 
			
			}
			
			} 
			
			public class SliderDistanceChangeListener implements ChangeListener{ 
				
				@Override 
				public void stateChanged(ChangeEvent arg0) { 
					
					String value = String.format("%d", sliderDistance.getValue()); 
					fieldDistance.setText(value); 
			
			  } 
			
			} 
			
			 
			//stworzenie klasy która umozliwia zmiane koloru tla animacji 
			public class Colors implements ActionListener{  
				
				public void actionPerformed(ActionEvent e) { 
					
					Color newColor = JColorChooser.showDialog(null,"Choose Background Color",panelAnimation2.getBackground());
					panelAnimation2.setBackground(newColor); 
			
			        }  
			
			} 
		
			} 
			
			//public class ImageComponent  { 
			
		//	public void paintComponent(Graphics g) { 
			
			    //    Image image;
					//g.drawImage(image, 0, 0, null); 
			
			   // } 
			
		//	}
