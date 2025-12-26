import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class SimplePaint extends Frame implements MouseListener, MouseMotionListener {
   static Rectangle red, blue, yellow, green, black, pencil, brush, roller, spray, eraser, clear, line, circle, oval, custom, polygon,star;
   static int numColor, numTool, xClick, yClick;
   static JComponent component;
   private BufferedImage canvas;
   private boolean isDrawing = false;
   static int prevX, prevY;
   static int currentX, currentY;
   static boolean isDraggingShape = false; // For temporary drawing feedback
   static Color customColor;
   static int shapeChoice, openOrClosed;
   static int starPoints, starOpenOrClosed, brushSize, lineSize;


   static Graphics2D g2d;

   public static void main(String[] args) {
      numTool = 1;
      JFrame frame = new JFrame();
      final int FRAME_WIDTH = 1050;
      final int FRAME_HEIGHT = 700;

      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      SimplePaint sp = new SimplePaint();
      frame.addMouseListener(sp);
      frame.addMouseMotionListener(sp);

      component = 
         new JComponent() {
            public void paintComponent(Graphics graph) {
               super.paintComponent(graph); // Ensure proper painting order
               Graphics2D g2 = (Graphics2D) graph;

            // Draw the canvas (BufferedImage) onto the screen
               g2.drawImage(sp.canvas, 0, 0, null); 

            // Draw the temporary shape while dragging
               if (sp.isDraggingShape) {
                  g2.setColor(Color.black); // Use black for feedback

               // Draw the temporary shape based on the selected tool
                  if (numTool == 7) { // Line tool
                     g2.setStroke(new BasicStroke(lineSize));
                     g2.drawLine(prevX, prevY, currentX, currentY);
                     //g2.setStroke(new BasicStroke(1));

                  } else if (numTool == 8) { // Circle tool
                     int diameter = Math.min(Math.abs(sp.currentX - sp.prevX), Math.abs(sp.currentY - sp.prevY));
                     g2.drawOval(Math.min(sp.prevX, sp.currentX), Math.min(sp.prevY, sp.currentY), diameter, diameter);
                  } else if (numTool == 9) { // Oval tool
                     g2.drawOval(Math.min(sp.prevX, sp.currentX), Math.min(sp.prevY, sp.currentY), 
                              Math.abs(sp.currentX - sp.prevX), Math.abs(sp.currentY - sp.prevY));
                  }
                  else if(numTool == 10){//System.out.println(shapeChoice);
                     int startX = Math.min(sp.prevX, sp.currentX); int startY = Math.min(sp.prevY, sp.currentY);
                     int endX = Math.max(sp.prevX, sp.currentX); int endY = Math.max(sp.prevY, sp.currentY);
                     Polygon shape = new Polygon();
                     if(shapeChoice ==0){//System.out.println(startX + " " + startY);
                        shape.addPoint(startX+(endX-startX)/2, startY);
                        shape.addPoint(startX, endY); shape.addPoint(endX, endY); shape.addPoint(startX+(endX-startX)/2, startY); 
                     } //System.out.println(shape);
                     else if(shapeChoice == 1){
                        shape.addPoint(startX, startY); shape.addPoint(endX, startY); shape.addPoint(endX, endY);
                        shape.addPoint(startX, endY); shape.addPoint(startX, startY);
                     }
                     else if(shapeChoice ==2){
                        int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
                        int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
                        double twoPI = Math.PI * 2;
                        for(int i = 0; i < 6; i++){
                           int tempX = (int) Math.round(Math.cos(twoPI/-20+twoPI * (i%6)/5) * xRadius) + xCenter;
                           int tempY = (int) Math.round(Math.sin(twoPI/-20+twoPI * (i%6)/5) * yRadius) + yCenter;
                           shape.addPoint(tempX, tempY);
                        }


                     }
                     else if(shapeChoice == 3){
                        int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
                        int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
                        double twoPI = Math.PI * 2;
                        for (int k = 0; k < 6; k++){
                           shape.addPoint((int) Math.round(Math.cos(twoPI * k/6) * xRadius) + xCenter,
                              (int) Math.round(Math.sin(twoPI * k/6) * yRadius) + yCenter);
                        }

                     }
                     else if(shapeChoice == 4){
                        int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
                        int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
                        double twoPI = Math.PI * 2;
                        for (int k = 0; k < 8; k++){
                           shape.addPoint((int) Math.round(Math.cos(twoPI/16 +twoPI * k/8) * xRadius) + xCenter,
                              (int) Math.round(Math.sin(twoPI/16 +twoPI * k/8) * yRadius) + yCenter);
                        }

                     }




                     if (openOrClosed == 0){ //System.out.println("ok");
                        g2.drawPolygon(shape);
                     }
                     else{
                        g2.fillPolygon(shape);
                     }
                  }
                  else if(numTool == 11){
                     int startX = Math.min(sp.prevX, sp.currentX); int startY = Math.min(sp.prevY, sp.currentY);
                     int endX = Math.max(sp.prevX, sp.currentX); int endY = Math.max(sp.prevY, sp.currentY);
                     Polygon shape = new Polygon();
                     int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
                     int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
                     double twoPI = Math.PI * 2;

                     for(int i = 0; i <= starPoints; i++){
                        int tempX = (int) Math.round(Math.cos(twoPI/(100/starPoints)+twoPI * i/starPoints) * xRadius/2) + xCenter;
                        int tempY = (int) Math.round(Math.sin(twoPI/(100/starPoints)+twoPI * i/starPoints) * yRadius/2) + yCenter;
                        shape.addPoint(tempX, tempY);
                        /*shape.addPoint((int) Math.round(Math.cos(twoPI/16 +twoPI * k/8) * xRadius) + xCenter,
                              (int) Math.round(Math.sin(twoPI/16 +twoPI * k/8) * yRadius) + yCenter);*/
                        tempX = (int) Math.round(Math.cos(twoPI/(100/starPoints)+twoPI * (i+0.5)/starPoints) * xRadius) + xCenter;
                        tempY = (int) Math.round(Math.sin(twoPI/(100/starPoints)+twoPI * (i+0.5)/starPoints) * yRadius) + yCenter;
                        shape.addPoint(tempX, tempY);
                     }

                     if (starOpenOrClosed == 0){ 
                        g2.drawPolygon(shape);
                     }
                     else{
                        g2.fillPolygon(shape);
                     }
                  }

               }
            }
         };

      frame.add(component);
      frame.setVisible(true);
   }

   public SimplePaint() {
      red = new Rectangle(5, 5, 42, 42);
      blue = new Rectangle(5, 52, 42, 42);
      yellow = new Rectangle(5, 99, 42, 42);
      green = new Rectangle(5, 146, 42, 42);
      black = new Rectangle(5, 193, 42, 42);
      pencil = new Rectangle(5, 240, 42, 42);
      brush = new Rectangle(5, 287, 42, 42);
      roller = new Rectangle(5, 334, 42, 42);
      spray = new Rectangle(5, 381, 42, 42);
      eraser = new Rectangle(5, 428, 42, 42);
      clear = new Rectangle(5, 475, 42, 42);
      line = new Rectangle(5, 522, 42, 42);
      circle = new Rectangle(5, 569, 42, 42);
      oval = new Rectangle(5, 616, 42, 42);
      custom = new Rectangle(57, 5, 42, 42);
      polygon = new Rectangle(57, 52, 42, 42);
      star = new Rectangle(57, 99, 42, 42);

      canvas = new BufferedImage(1050, 700, BufferedImage.TYPE_INT_RGB);
      g2d = (Graphics2D) canvas.getGraphics();
      g2d.setColor(Color.white);
      g2d.fillRect(0, 0, 1050, 700);
      drawPalette(g2d); // Draw the initial color palette
   }

   // Updated draw method
   public void draw() {
      g2d = (Graphics2D) canvas.getGraphics();
      if(xClick<102){
         xClick = prevX; yClick = prevY;
      }

    // Set the selected color
      switch (numColor) {
         case 1: g2d.setColor(Color.red); 
            break;
         case 2: g2d.setColor(Color.blue); 
            break;
         case 3: g2d.setColor(Color.yellow); 
            break;
         case 4: g2d.setColor(Color.green); 
            break;
         case 5: g2d.setColor(Color.black); 
            break;
         case 6: g2d.setColor(customColor); 
            break;
      }

    // Perform the drawing action based on the selected tool
      switch (numTool) {
         case 1: g2d.drawLine(prevX, prevY, xClick, yClick); 
            break; // Pencil
         case 2: g2d.fillOval(prevX, prevY, brushSize, brushSize); 
            break; // Brush
         case 3: g2d.fillRect(xClick, yClick, 20, 30); 
            break; // Roller
         case 4: 
            for (int i = 0; i < 200; i++) { // Spray
               g2d.fillRect(xClick + (int) (Math.random() * 30), yClick + (int) (Math.random() * 30), 2, 2);
            }
            break;
         case 5: g2d.setColor(Color.white); // Eraser
            g2d.fillOval(xClick, yClick, 10, 10);
            break;
         case 6: // Clear the canvas
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 1050, 700);
            drawPalette(g2d); // Redraw the palette after clearing
            break;
         case 7: // Line tool
            g2d.setStroke(new BasicStroke(lineSize));
            g2d.drawLine(prevX, prevY, xClick, yClick);
            break;
         case 8: // Circle tool
            int diameter = Math.min(Math.abs(xClick - prevX), Math.abs(yClick - prevY));
            g2d.drawOval(Math.min(prevX, xClick), Math.min(prevY, yClick), diameter, diameter);
            break;
         case 9: // Oval tool
            g2d.drawOval(Math.min(prevX, xClick), Math.min(prevY, yClick), 
                        Math.abs(xClick - prevX), Math.abs(yClick - prevY));
            break;
         case 10:
            //System.out.println(shapeChoice);
            int startX = Math.min(prevX, xClick); int startY = Math.min(prevY, yClick);
            int endX = Math.max(prevX, xClick); int endY = Math.max(prevY, yClick);
            Polygon shape = new Polygon();
            if(shapeChoice ==0){//System.out.println("ok");
               shape.addPoint(startX+(endX-startX)/2, startY);
               shape.addPoint(startX, endY); shape.addPoint(endX, endY); shape.addPoint(startX+(endX-startX)/2, startY); 
            }
            else if(shapeChoice == 1){
               shape.addPoint(startX, startY); shape.addPoint(endX, startY); shape.addPoint(endX, endY);
               shape.addPoint(startX, endY); shape.addPoint(startX, startY);
            }
            else if(shapeChoice ==2){
               int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
               int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
               double twoPI = Math.PI * 2;
               for(int i = 0; i < 6; i++){
                  int tempX = (int) Math.round(Math.cos(twoPI/-20+twoPI * (i%6)/5) * xRadius) + xCenter;
                  int tempY = (int) Math.round(Math.sin(twoPI/-20 + twoPI * (i%6)/5) * yRadius) + yCenter;
                  shape.addPoint(tempX, tempY);
               }

            }
            else if(shapeChoice == 3){
               int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
               int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
               double twoPI = Math.PI * 2;
               for (int k = 0; k < 6; k++){
                  shape.addPoint((int) Math.round(Math.cos(twoPI * k/6) * xRadius) + xCenter,
                           (int) Math.round(Math.sin(twoPI * k/6) * yRadius) + yCenter);
               }

            }
            else if(shapeChoice == 4){
               int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
               int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
               double twoPI = Math.PI * 2;
               for (int k = 0; k < 8; k++){
                  shape.addPoint((int) Math.round(Math.cos(twoPI/16 +twoPI * k/8) * xRadius) + xCenter,
                           (int) Math.round(Math.sin(twoPI/16 +twoPI * k/8) * yRadius) + yCenter);
               }

            }



            if (openOrClosed == 0){
               g2d.drawPolygon(shape);
            }
            else{
               g2d.fillPolygon(shape);
            }


            break;
         case 11:
            startX = Math.min(prevX, xClick); startY = Math.min(prevY, yClick);
            endX = Math.max(prevX, xClick); endY = Math.max(prevY, yClick);
            shape = new Polygon();
            int xRadius = (endX-startX)/2; int yRadius = (endY-startY)/2;
            int xCenter = startX + (endX-startX)/2; int yCenter = startY + (endY-startY)/2;
            double twoPI = Math.PI * 2;

            for(int i = 0; i <= starPoints; i++){
               int tempX = (int) Math.round(Math.cos(twoPI/(100/starPoints)+twoPI * i/starPoints) * xRadius/2) + xCenter;
               int tempY = (int) Math.round(Math.sin(twoPI/(100/starPoints)+twoPI * i/starPoints) * yRadius/2) + yCenter;
               shape.addPoint(tempX, tempY);
                        /*shape.addPoint((int) Math.round(Math.cos(twoPI/16 +twoPI * k/8) * xRadius) + xCenter,
                              (int) Math.round(Math.sin(twoPI/16 +twoPI * k/8) * yRadius) + yCenter);*/
               tempX = (int) Math.round(Math.cos(twoPI/(100/starPoints)+twoPI * (i+0.5)/starPoints) * xRadius) + xCenter;
               tempY = (int) Math.round(Math.sin(twoPI/(100/starPoints)+twoPI * (i+0.5)/starPoints) * yRadius) + yCenter;
               shape.addPoint(tempX, tempY);
            }

            if (starOpenOrClosed == 0){ 
               g2d.drawPolygon(shape);
            }
            else{
               g2d.fillPolygon(shape);
            }
            break;
      }

      component.repaint(); // Repaint the component to display the updated canvas
   }
   // Handle mouse click to select color or tool
   public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();

      // Color palette selection
      if (red.contains(x, y)){ numColor = 1;}
      else if (blue.contains(x, y)){ numColor = 2;}
      else if (yellow.contains(x, y)){ numColor = 3;}
      else if (green.contains(x, y)){ numColor = 4;}
      else if (black.contains(x, y)){ numColor = 5;}
      else if (custom.contains(x,y)){ numColor = 6;
         int r = Integer.valueOf(JOptionPane.showInputDialog("Enter first rgb value."));
         int g = Integer.valueOf(JOptionPane.showInputDialog("Enter second rgb value."));
         int b = Integer.valueOf(JOptionPane.showInputDialog("Enter third rgb value."));
         customColor = new Color(r, g,b);  
      }

      // Tool selection
      if (pencil.contains(x, y)) numTool = 1;
      else if (brush.contains(x, y)){ 
         numTool = 2;
         brushSize = Integer.valueOf(JOptionPane.showInputDialog("Enter size of brush (default is 10).")); 
      }
      else if (roller.contains(x, y)) numTool = 3;
      else if (spray.contains(x, y)) numTool = 4;
      else if (eraser.contains(x, y)) numTool = 5;
      else if (clear.contains(x, y)) numTool = 6;
      else if (line.contains(x, y)){ 
         numTool = 7;
         lineSize = Integer.valueOf(JOptionPane.showInputDialog("Enter size of line (default is 1)."));
         }
      else if (circle.contains(x, y)) numTool = 8;
      else if (oval.contains(x, y)) numTool = 9;
      else if (polygon.contains(x,y)){
         String[] options = {"Triangle", "Square", "Pentagon", "Hexagon", "Octagon"};
         numTool = 10; //Triangle, rectangle/square?, pentagon, hexagon, octagon; closed and open
         shapeChoice = JOptionPane.showOptionDialog(null, "Select a shape.", "Shape", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
         //new String[]{"Triangle", "Rectangle", "Pentagon", "Hexagon", "Octagon"});
         openOrClosed = JOptionPane.showOptionDialog(null, "Open or Closed?", "Fill", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Open", "Closed"}, null);
         //System.out.println(shapeChoice + " " + openOrClosed);
      }
      else if(star.contains(x, y)){
         numTool = 11;
         starPoints = Integer.valueOf(JOptionPane.showInputDialog("Enter Number of Points."));
         starOpenOrClosed = JOptionPane.showOptionDialog(null, "Open or Closed?", "Fill", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Open", "Closed"}, null);
      }

   }

   // Mouse press to start drawing
   public void mousePressed(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();

      if (x > 47+5+47) {
         isDrawing = true; 
         prevX = x; 
         prevY = y;
         xClick = x;
         yClick = y;
         draw(); // Draw the initial point
      }
   }

   // Mouse dragged to continue drawing
   public void mouseDragged(MouseEvent e) {
      if (isDrawing && numTool < 7) {
         prevX = xClick; 
         prevY = yClick;
         xClick = e.getX();
         yClick = e.getY();
         draw(); // Continuous drawing for pencil, brush, roller, etc.
      } else if (isDrawing) {
         isDraggingShape = true; // Indicate that a shape is being dragged
         currentX = e.getX();
         currentY = e.getY();
         component.repaint(); // Repaint to show the temporary shape
      }
   }

   // Mouse release to finalize drawing
   public void mouseReleased(MouseEvent e) {
      if (isDraggingShape) {
         xClick = e.getX();
         yClick = e.getY();
         draw(); // Finalize the shape on the canvas
         isDraggingShape = false; // Reset the dragging flag
         component.repaint(); // Repaint the canvas to show the final shape
      }
   }
   // Unused mouse listener methods
   public void mouseEntered(MouseEvent e) {}
   public void mouseExited(MouseEvent e) {}

   // Unused mouse motion listener method
   public void mouseMoved(MouseEvent e) {}

   // Draw the color palette and tool options
   public static void drawPalette(Graphics g) {
      g.setColor(Color.red);
      g.fillRect(5, 5, 42, 42);
      g.setColor(Color.blue);
      g.fillRect(5, 52, 42, 42);
      g.setColor(Color.yellow);
      g.fillRect(5, 99, 42, 42);
      g.setColor(Color.green);
      g.fillRect(5, 146, 42, 42);
      g.setColor(Color.black);
      g.fillRect(5, 193, 42, 42);
      g.setColor(Color.black);
      g.drawRect(5, 240, 42, 42);
      g.drawString("Pencil", 7, 255);
      g.drawRect(5, 287, 42, 42);
      g.drawString("Brush", 7, 302);
      g.drawRect(5, 334, 42, 42);
      g.drawString("Roller", 7, 349);
      g.drawRect(5, 381, 42, 42);
      g.drawString("Spray", 7, 396);
      g.drawRect(5, 428, 42, 42);
      g.drawString("Eraser", 7, 443);
      g.drawRect(5, 475, 42, 42);
      g.drawString("Clear", 7, 490);
      g.drawRect(5, 522, 42, 42);
      g.drawString("Line", 7, 537);
      g.drawRect(5, 569, 42, 42);
      g.drawString("Circle", 7, 584);
      g.drawRect(5, 616, 42, 42);
      g.drawString("Oval", 7, 631);
      g.drawRect(57, 5, 42, 42);
      g.drawString("Custom", 57, 20);
      g.drawRect(57, 52, 42, 42);
      g.drawString("Polygon", 57, 67);
      g.drawRect(57, 99, 42, 42);
      g.drawString("Star", 59, 116);
   }
}