import java.awt.*;
import java.awt.event.*;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Transforms2D extends JPanel {
    private class Display extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.translate(300,300);  // Moves (0,0) to the center of the display.
            int whichTransform = transformSelect.getSelectedIndex();
            ///////////////////////////////////

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHints(hints);
            Stroke stroke =  new BasicStroke(2) ;
            g2.setStroke(stroke);
            Paint paint = new GradientPaint(75, 75, Color.white,
                    95, 95, Color.gray, true);
            g2.setPaint(paint);

            var N = 12;

            int[] X = new int[N];
            int[] Y = new int[N];

            for (int i = 0; i < N; i++) {
                X[i] = (int) (150 * Math.cos(((Math.PI/2) + (2*Math.PI*i))/N));
                Y[i] = (int) (150 * Math.sin(((Math.PI/2) + (2*Math.PI*i))/N));
            }

            switch (whichTransform){
                case 1:
                    g2.scale(0.5, 0.5);
                    break;
                case 2:
                    g2.rotate(Math.toRadians(45));
                    break;
                case 3:
                    g2.rotate(Math.toRadians(180));
                    g2.scale(-0.5, 1);
                    break;
                case 4:
                    g2.shear(0.5,0);;
                    break;
                case 5:
                    g2.scale(1, 0.5);
                    g2.translate(0, -450 );
                    break;
                case 6:
                    g2.rotate(Math.toRadians(90));
                    g2.shear(0.5,0);
                    break;
                case 7:
                    g2.rotate(Math.toRadians(180));
                    g2.scale(0.5, 1);
                    break;
                case 8:
                    g2.translate(0, 100 );
                    g2.rotate(Math.toRadians(25));
                    g2.scale(1, 0.5);
                    break;
                case 9:
                    g2.translate(150,0);
                    g2.shear(0,0.5);
                    g2.rotate(Math.toRadians(180));
                    break;
                default:

                    break;
            }
            // TODO Apply transforms here, depending on the value of whichTransform!

            Polygon polygon = new Polygon(X,Y,N);

            g2.fill(polygon);
            g2.setPaint( Color.black);
            g2.drawPolygon(polygon);

            //g2.drawImage(pic, -200, -150, null); // Draw image with center at (0,0).
        }
    }


    private Display display;
    private BufferedImage pic;
    private JComboBox<String> transformSelect;

    public Transforms2D() throws IOException {
        pic = ImageIO.read(new FileInputStream("shuttle.jpg")); //Zmiana trybu pobierania bo nie działało
        display = new Display();
        display.setBackground(Color.YELLOW);
        display.setPreferredSize(new Dimension(600,600));
        transformSelect = new JComboBox<String>();
        transformSelect.addItem("None");
        for (int i = 1; i < 10; i++) {
            transformSelect.addItem("No. " + i);
        }
        transformSelect.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.repaint();
            }
        });
        setLayout(new BorderLayout(3,3));
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.GRAY,10));
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER));
        top.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        top.add(new JLabel("Transform: "));
        top.add(transformSelect);
        add(display,BorderLayout.CENTER);
        add(top,BorderLayout.NORTH);
    }


    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("2D Transforms");
        window.setContentPane(new Transforms2D());
        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( (screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2 );
        window.setVisible(true);
    }
}
