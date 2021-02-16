package river;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 * 
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    private Rectangle boatRec;
    private final Rectangle restartButtonRect = new Rectangle(350, 120, 100, 30);

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private boolean restart = false;
    private Map<Item, Rectangle> itemRecs;
    private Item passenger1, passenger2;
    int[] dx = {0, 60, 0, 60};
    int[] dy = {0, 0, -60, -60};

    private int leftBaseX = 20;
    private int leftBaseY = 275;
    private int leftBoatX = 140;
    private int leftBoatY = 275;

    private int rightBaseX = 670;
    private int rightBaseY = 275;
    private int rightBoatX = 550;
    private int rightBoatY = 275;

    private int rectHeight = 50;
    private int rectWidth = 50;
    private int boatHeight = 50;
    private int boatWidth = 110;
    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new FarmerGameEngine();
        addMouseListener(this);
        itemRecs = new HashMap<>();
        itemRecs.put(Item.ITEM_0, new Rectangle());
        itemRecs.put(Item.ITEM_1, new Rectangle());
        itemRecs.put(Item.ITEM_2, new Rectangle());
        itemRecs.put(Item.ITEM_3, new Rectangle());
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        refreshItemRectangles();
        refreshBoatRectangles();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        paintItems(g);
        paintBoat(g);
        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintRestartButton(g);
        }

    }

    private void refreshItemRectangles(){
        for(Item item: itemRecs.keySet()){
            itemRecs.put(item, getItemRec(item));
        }
    }

    private void refreshBoatRectangles(){
        boatRec = getBoatRec();
    }

    public void paintItems(Graphics g){
        for(Item item : itemRecs.keySet()){
            paintStringInRectangle(engine.getItemLabel(item), g, engine.getItemColor(item), getItemRec(item));
        }
    }

    private void paintBoat(Graphics g){
        Rectangle rec = getBoatRec();
        paintStringInRectangle("", g, Color.ORANGE, rec);
    }

    public Rectangle getItemRec(Item item){
        Rectangle rec;
        int index = item.ordinal();
        Location loc = engine.getItemLocation(item);
        Location boatLoc = engine.getBoatLocation();

        if(loc == Location.START){
            rec = new Rectangle(leftBaseX + dx[index], leftBaseY + dy[index], rectWidth, rectHeight);
        }else if(loc == Location.FINISH){
            rec = new Rectangle(rightBaseX + dx[index], rightBaseY + dy[index], rectWidth, rectHeight);
        }else{
            if(boatLoc == Location.START){
                if(item == passenger1){
                    return new Rectangle(leftBoatX, leftBoatY - 60, rectWidth, rectHeight);
                }else{
                    return new Rectangle(leftBoatX + 60, leftBoatY - 60, rectWidth, rectHeight);
                }
            }else{
                if(item == passenger1){
                    return new Rectangle(rightBoatX, rightBoatY - 60, rectWidth, rectHeight);
                }else{
                    return new Rectangle(rightBoatX + 60, rightBoatY - 60, rectWidth, rectHeight);
                }
            }
        }

        return rec;
    }

    private Rectangle getBoatRec(){
        if(engine.getBoatLocation() == Location.START){
            return new Rectangle(leftBoatX, leftBoatY, boatWidth, boatHeight);
        }else{
            return new Rectangle(rightBoatX, rightBoatY, boatWidth, boatHeight);
        }
    }

    public void paintStringInRectangle(String str, Graphics g, Color color, Rectangle rect) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        int fontSize = (rect.height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rect.x + rect.width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = rect.y + rect.height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(restartButtonRect, 3, g);
        g.setColor(Color.PINK);
        paintStringInRectangle("RESTART", g, Color.PINK, restartButtonRect);
    }

    public void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }


    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.restartButtonRect.contains(e.getPoint())) {
                engine.resetGame();
                restart = false;
                passenger1 = null;
                passenger2 = null;
                repaint();
            }
            return;
        }

        for(Map.Entry<Item, Rectangle> entry : itemRecs.entrySet()){
            Item item = entry.getKey();
            Rectangle rec = entry.getValue();
            Location loc = engine.getItemLocation(item);
            if(rec.contains(e.getPoint())){
                if(loc == Location.BOAT){
                    engine.unloadBoat(item);
                    removePassenger(item);
                }else if(loc == engine.getBoatLocation()){
                    engine.loadBoat(item);
                    setPassenger(item);
                }
                repaint();
                return;
            }
        }

        if(boatRec.contains(e.getPoint()) && engine.getItemLocation(Item.ITEM_3) == Location.BOAT){
            engine.rowBoat();
        }
        repaint();
    }

    public void setPassenger(Item item){
        if(this.passenger1 == null){
            this.passenger1 = item;
        }else{
            this.passenger2 = item;
        }
    }

    private void removePassenger(Item passenger){
        if(passenger == passenger1){
            this.passenger1 = null;
        }else{
            this.passenger2 = null;
        }
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
