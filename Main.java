import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {

    private JFrame frame;
    private JLabel imageLabel;
    private BufferedImage image;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().createGUI();
        });
    }

    // interface
    public void createGUI() {
        frame = new JFrame("Image Filter App");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton grayButton = new JButton("Gray Filter");
        grayButton.addActionListener(e -> applyGrayFilter());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openImage());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveImage());

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        frame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        frame.add(grayButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // image
    public void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                image = ImageIO.read(file);
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Filtre gris
    public void applyGrayFilter() {
        if (image == null) return;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int gray = (r + g + b) / 3;

                int newRGB = (gray << 16) | (gray << 8) | gray;

                image.setRGB(x, y, newRGB);
            }
        }

        imageLabel.setIcon(new ImageIcon(image));
    }

    // Sauvegarde image
    public void saveImage() {
        if (image == null) return;

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(image, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}