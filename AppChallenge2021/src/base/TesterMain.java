package base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JLabel;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * http://stackoverflow.com/questions/25027060/running-swing-application-in-javafx
 */
public class TesterMain extends Application {

    static class MyCanvas extends Canvas { 

        private FXGraphics2D g2;

        private TeXIcon icon;
        
        private final TeXFormula formula;
        
        public MyCanvas() {
            this.g2 = new FXGraphics2D(getGraphicsContext2D());

            // create a formula
            this.formula = new TeXFormula("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}");

            // render the formla to an icon of the same size as the formula.
            this.icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

            // Redraw canvas when size changes. 
            widthProperty().addListener(evt -> draw()); 
            heightProperty().addListener(evt -> draw()); 
        }  

        private void draw() {
        	System.out.printf("Entered draw()%n");
            double width = getWidth(); 
            double height = getHeight();
            System.out.printf("\t(width, height) = (%.1f, %.1f)%n", width, height);
            getGraphicsContext2D().clearRect(0, 0, width, height);

            // ideally it should be possible to draw directly to the FXGraphics2D
            // instance without creating an image first...but this does not generate
            // good output
            //this.icon.paintIcon(new JLabel(), g2, 50, 50);

            // now create an actual image of the rendered equation
            if(width > 1.0 && height > 1.0) {
            	int imgWidth = Math.max(1, (int) width); //icon.getIconWidth();
    			int imgHeight = Math.max(1, (int) width); //icon.getIconHeight();
    			updateIcon(imgWidth, imgHeight);
    			BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gg = image.createGraphics();
                gg.setColor(Color.WHITE);
                gg.fillRect(0, 0, imgWidth, imgHeight);
                Color textColor = new Color(0, 0, 0); //color of the text in the TeX formula.
                JLabel jl = new JLabel();
    			jl.setForeground(textColor);
                icon.paintIcon(jl, gg, 0, 0);
                // at this point the image is created, you could also save it with ImageIO

                this.g2.drawImage(image, 0, 0, null);
            }
            
        }
        
        private void updateIcon(int imgWidth, int imgHeight) {
        	this.icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, Math.max(imgWidth, imgHeight) / 20);
        }

        @Override 
        public boolean isResizable() { 
            return true;
        }  

        @Override 
        public double prefWidth(double height) { return getWidth(); }  

        @Override 
        public double prefHeight(double width) { return getHeight(); } 
    } 

    
    public static void main(String[] args) {
    	Application.launch(args);
	}
    
    @Override 
    public void start(Stage stage) throws Exception {
        MyCanvas canvas = new MyCanvas();
        StackPane stackPane = new StackPane(); 
        stackPane.getChildren().add(canvas);  
        // Bind canvas size to stack pane size. 
        canvas.widthProperty().bind( stackPane.widthProperty()); 
        canvas.heightProperty().bind( stackPane.heightProperty());  
        stage.setScene(new Scene(stackPane)); 
        stage.setTitle("FXGraphics2DDemo3.java"); 
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show(); 

    }
}