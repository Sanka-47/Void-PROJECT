package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.io.Serializable;

public class CustomColor extends javax.swing.JPanel implements Serializable {

    private static final int ROUND_CORNER_SIZE = 33;
    private int arcWidth = 500;
    private int arcHeight = 500;

    public CustomColor() {
        setOpaque(false);
//        setForeground(Color.WHITE);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define the colors and their positions
//        float[] fractions = {0.0f, 0.5f, 1.0f}; // Position of colors
//        Color[] colors = {
//            Color.decode("#4c65bc"), // Start color
//            Color.decode("#edf0f8"), // Middle color
//            Color.decode("#4c65bc")  // End color
//        };
//        Color[] colors = {
//            Color.decode("#edf0f8"), // Start color
//            Color.decode("#4c65bc"), // Middle color
//            Color.decode("#edf0f8")  // End color
//        }; //Relay Reversed
//        Color[] colors = {
//            Color.decode("#3A1C71"), // Start color
//            Color.decode("#D76D77"), // Middle color
//            Color.decode("#FFAF7B")  // End color
//        }; //Relay
        // Define gradient fractions and colors
        float[] fractions = {0.2f, 0.8f};
        Color[] colors = {
            Color.decode("#E0EAF3"), // Light color (top-left)
            Color.decode("#CFCEF3") // Dark color (bottom-right)
        };

        LinearGradientPaint gradient = new LinearGradientPaint(0, 0, getWidth(), getHeight(), fractions, colors);

//        LinearGradientPaint gradient = new LinearGradientPaint(0, 0, getWidth(), 0, fractions, colors);
//        LinearGradientPaint gradient = new LinearGradientPaint(0, 0, 0, getHeight(), fractions, colors);
//        LinearGradientPaint gradient = new LinearGradientPaint(0, getHeight() / 2, getWidth(), getHeight() / 2, fractions, colors);
//        LinearGradientPaint gradient = new LinearGradientPaint(getWidth() / 2, 0, getWidth() / 2, getHeight() , fractions, colors);

//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#4c65bc"), getWidth(), 0, Color.decode("#edf0f8"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#edf0f8"), getWidth(), 0, Color.decode("#4c65bc"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#4c65bc"), 0, getHeight(), Color.decode("#edf0f8"));

        //Orange Fun
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FC4A1A"), getWidth(), 0, Color.decode("#F7B733"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FC4A1A"), 0, getHeight(), Color.decode("#F7B733"));

        //Expresso
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#AD5389"), getWidth(), 0, Color.decode("#3C1053"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#AD5389"), 0, getHeight(), Color.decode("#3C1053"));

        //Custom(Dark Purple and Crimson Red)
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#420039"), getWidth(), 0, Color.decode("#D72638"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#420039"), 0, getHeight(), Color.decode("#D72638"));

        //Cosmic Fusion 2
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#89253E"), getWidth(), 0, Color.decode("#3A6186"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#89253E"), 0, getHeight(), Color.decode("#3A6186"));

        //Cosmic Fusion 1
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#333399"), getWidth(), 0, Color.decode("#FF00CC"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#333399"), 0, getHeight(), Color.decode("#FF00CC"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FF00CC"), 0, getHeight(), Color.decode("#333399"));

        //Roseanna02
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FFAFBD"), getWidth(), 0, Color.decode("#FFC3A0"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FFAFBD"), 0, getHeight(), Color.decode("#FFC3A0"));

        //Roseanna01
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#F3904F"), getWidth(), 0, Color.decode("#3B4371"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#F3904F"), 0, getHeight(), Color.decode("#3B4371"));

        //Tropical Paradise or Honey Drew
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#43C6AC"), getWidth(), 0, Color.decode("#F8FFAE"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#43C6AC"), 0, getHeight(), Color.decode("#F8FFAE"));

        //Glacial Stream
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#83A4D4"), getWidth(), 0, Color.decode("#B6FBFF"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#83A4D4"), 0, getHeight(), Color.decode("#B6FBFF"));

        //Spring Meadow
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FDFC47"), getWidth(), 0, Color.decode("#24FE41"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FF7E5F"), 0, getHeight(), Color.decode("#FEB47B"));

        //Sunset Glow
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FF7E5F"), getWidth(), 0, Color.decode("#FEB47B"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#FF7E5F"), 0, getHeight(), Color.decode("#FEB47B"));

        //Ocean Breeze
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#00B4DB"), 0, getHeight(), Color.decode("#0083B0"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#00B4DB"), getWidth(), 0, Color.decode("#0083B0"));

        //Desert Twilight
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#DAE2F8"), 0, getHeight(), Color.decode("#D6A4A4"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#DAE2F8"), getWidth(), 0, Color.decode("#D6A4A4"));

        //Mountain Mist
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#E0EAF3"), 0, getHeight(), Color.decode("#CFCEF3"));
//        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#E0EAF3"), getWidth(), 0, Color.decode("#CFCEF3"));

        g2.setPaint(gradient);
//        int arcWidth = getHeight();
//        int arcHeight = getHeight();
        g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, ROUND_CORNER_SIZE, ROUND_CORNER_SIZE);
//        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        super.paintComponent(graphics);

        // **STEP 2: Apply Glassmorphism Overlay**
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f)); // 20% transparency
//        Color glassColor = new Color(255, 255, 255, 226); // Adjust opacity for better blending
//        g2.setColor(glassColor);
//        g2.fillRoundRect(5, 42, getWidth() - 10, getHeight() - 50, ROUND_CORNER_SIZE, ROUND_CORNER_SIZE);
//
//        g2.dispose();
    }
}
