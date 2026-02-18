import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import javax.imageio.ImageIO;
import java.io.File;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image img) {
        this.backgroundImage = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class EridianChronometer extends JFrame {

    private static final char[] ERIDIAN_SYMBOLS = {'ℓ', 'I', 'V', 'λ', '+', '∀'};
    private static final int MAX_IMAGE_SIZE = 240;
    private JLabel earthTimeLabel;
    private JLabel eridianTimeLabel;

    public EridianChronometer() {
        setTitle("Eridian Chronometer 🪐");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // NOTE: The file paths below for 'nebulaa.jpg', 'earthh.png', and 'erid.png'
        // need to be valid on the system where this code is run.
        Image nebulaBg = loadNebulaBackground("C:\\Users\\tejas\\OneDrive\\Desktop\\JAVA PBL\\nebulaa.jpg");
        BackgroundPanel contentPane = new BackgroundPanel(nebulaBg);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Main content area, excluding the top (header) and using the center space
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        // -------------------------------------------------------------
        // --- FIX: HEADING ALIGNMENT ---
        // Create a header container that uses the same BorderLayout structure
        // as the main content to ensure alignment.
        JPanel headerAlignmentPanel = new JPanel(new BorderLayout());
        headerAlignmentPanel.setOpaque(false);
        headerAlignmentPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        // LEFT SIDE OF HEADER: Matches the clocksPanel width (850px)
        JPanel clocksHeaderContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clocksHeaderContainer.setOpaque(false);
        clocksHeaderContainer.setPreferredSize(new Dimension(850, 50));

        JLabel heading = new JLabel("Eridian Chronometer", SwingConstants.CENTER);
        heading.setFont(new Font("Roboto", Font.BOLD, 40));
        heading.setForeground(new Color(255, 255, 255, 220));

        clocksHeaderContainer.add(heading);

        headerAlignmentPanel.add(clocksHeaderContainer, BorderLayout.WEST);

        // RIGHT SIDE OF HEADER: Matches the descriptionPanel width + padding
        headerAlignmentPanel.add(Box.createRigidArea(new Dimension(350 + 60, 0)), BorderLayout.EAST);

        contentPane.add(headerAlignmentPanel, BorderLayout.NORTH);
        // -------------------------------------------------------------

        // LEFT SIDE: CLOCKS (850px width, matching the header container)
        JPanel clocksPanel = new JPanel(new BorderLayout());
        clocksPanel.setOpaque(false);
        clocksPanel.setPreferredSize(new Dimension(850, 500));
        clocksPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));

        JPanel clockMainPanel = new JPanel(new GridLayout(1, 2, 60, 0));
        clockMainPanel.setOpaque(false);
        clockMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        // LEFT: EARTH
        JPanel leftPanel = createPlanetPanel(Color.CYAN, new Color(0, 200, 255, 80));
        leftPanel.setOpaque(false);
        ImageIcon earthIcon = loadImageAbsolute("C:\\Users\\tejas\\OneDrive\\Desktop\\JAVA PBL\\Earth_Western_Hemisphere_transparent_background.png");
        JLabel earthImg = new JLabel(earthIcon);
        earthImg.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(earthImg, BorderLayout.CENTER);

        JPanel earthTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        earthTimePanel.setOpaque(false);
        earthTimeLabel = createRobotoLabel("00:00:00", 40, Font.BOLD);
        JLabel earthText = createRobotoLabel("Earth 🌍:", 28, Font.BOLD);
        earthTimePanel.add(earthText);
        earthTimePanel.add(earthTimeLabel);
        leftPanel.add(earthTimePanel, BorderLayout.SOUTH);

        // RIGHT: ERIDIAN
        JPanel rightPanel = createPlanetPanel(new Color(200, 50, 255), new Color(150, 20, 200, 90));
        rightPanel.setOpaque(false);
        ImageIcon eridianIcon = loadImageAbsolute("C:\\Users\\tejas\\OneDrive\\Desktop\\JAVA PBL\\eridd.png\\");
        JLabel eridianImg = new JLabel(eridianIcon);
        eridianImg.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(eridianImg, BorderLayout.CENTER);

        JPanel eridianTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        eridianTimePanel.setOpaque(false);
        eridianTimeLabel = createRobotoLabel("ℓℓ:ℓℓ:ℓℓ", 40, Font.BOLD);
        JLabel eridianText = createRobotoLabel("Eridian 🌌:", 28, Font.BOLD);
        eridianTimePanel.add(eridianText);
        eridianTimePanel.add(eridianTimeLabel);
        rightPanel.add(eridianTimePanel, BorderLayout.SOUTH);

        clockMainPanel.add(leftPanel);
        clockMainPanel.add(rightPanel);
        clocksPanel.add(clockMainPanel, BorderLayout.CENTER);
        mainContent.add(clocksPanel, BorderLayout.WEST);

        // RIGHT SIDE: DESCRIPTION
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setPreferredSize(new Dimension(350, 500));
        // MODIFIED PADDING to create a buffer from the right edge
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 60));
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));

        JLabel descTitle = createRobotoLabel("Eridian Time System", 24, Font.BOLD);
        descTitle.setForeground(new Color(220, 220, 255, 220));
        descTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea descArea = new JTextArea();
        descArea.setText(
                "An alien world, Erid, where life evolved with 6 fingers.\n" +
                        "Hence, Eridians use base-6 math for time and numbers.\n\n" +
                        "This clock converts Earth (base-10) time live into\n" +
                        "Eridian base-6 time using symbols:\n\n" +
                        "ℓ=0, I=1, V=2, λ=3, +=4, ∀=5.\n\n" +
                        "Left: Earth time | Right: Eridian time"
        );
        descArea.setFont(new Font("Roboto", Font.PLAIN, 20));
        descArea.setForeground(Color.WHITE);
        descArea.setBackground(new Color(0, 0, 0, 0));
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setMaximumSize(new Dimension(300, 300));
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        descriptionPanel.add(descTitle);
        descriptionPanel.add(Box.createVerticalStrut(20));
        descriptionPanel.add(descArea);
        descriptionPanel.add(Box.createVerticalGlue());

        mainContent.add(descriptionPanel, BorderLayout.EAST);

        contentPane.add(mainContent, BorderLayout.CENTER);

        Timer timer = new Timer(1000, e -> updateClocks());
        timer.start();
        updateClocks();
        setVisible(true);
    }

    private JLabel createRobotoLabel(String text, int size, int style) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", style, size));
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        return label;
    }

    private JPanel createPlanetPanel(Color outerGlow, Color innerGlow) {
        return new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int size = MAX_IMAGE_SIZE + 60;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size - 60) / 2;

                // Outer Glow
                RadialGradientPaint outer = new RadialGradientPaint(
                        new Point(x + size/2, y + size/2), size/2,
                        new float[]{0.0f, 0.7f}, new Color[]{outerGlow, new Color(0,0,0,0)}
                );
                g2d.setPaint(outer);
                g2d.fillOval(x-20, y-20, size+40, size+40);

                // Inner Glow
                RadialGradientPaint inner = new RadialGradientPaint(
                        new Point(x + size/2, y + size/2), size/3,
                        new float[]{0.0f, 0.8f}, new Color[]{innerGlow, new Color(0,0,0,0)}
                );
                g2d.setPaint(inner);
                g2d.fillOval(x, y, size, size);

                g2d.dispose();
            }
        };
    }

    private Image loadNebulaBackground(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                Image original = ImageIO.read(file);
                return original.getScaledInstance(1200, 600, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            System.err.println("Could not load background image from: " + filepath);
        }
        return null;
    }

    private ImageIcon loadImageAbsolute(String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                Image original = ImageIO.read(file);
                BufferedImage circleImage = makePerfectPlanetNoSquish(original, MAX_IMAGE_SIZE);
                return new ImageIcon(circleImage);
            } else {
                System.err.println("Planet image file not found: " + filepath);
            }
        } catch (Exception e) {
            System.err.println("Error loading planet image: " + e.getMessage());
        }
        return createPlaceholder(Color.GRAY);
    }

    private BufferedImage makePerfectPlanetNoSquish(Image original, int size) {
        BufferedImage circleImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circleImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Ellipse2D circle = new Ellipse2D.Double(0, 0, size, size);
        g2d.setClip(circle);

        int origW = original.getWidth(null);
        int origH = original.getHeight(null);
        // Scale to fit within the circle and maintain aspect ratio
        double scale = Math.min((size * 0.9) / origW, (size * 0.9) / origH);
        int newW = (int)(origW * scale);
        int newH = (int)(origH * scale);
        int x = (size - newW) / 2;
        int y = (size - newH) / 2;

        g2d.drawImage(original, x, y, newW, newH, null);
        g2d.dispose();
        return circleImg;
    }

    private ImageIcon createPlaceholder(Color color) {
        BufferedImage img = new BufferedImage(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(8, 8, MAX_IMAGE_SIZE-16, MAX_IMAGE_SIZE-16);
        g.dispose();
        return new ImageIcon(img);
    }

    private void updateClocks() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int min = now.getMinute();
        int sec = now.getSecond();

        earthTimeLabel.setText(String.format("%02d:%02d:%02d", hour, min, sec));

        String erHour = toEridian(hour);
        String erMin = toEridian(min);
        String erSec = toEridian(sec);

        // Ensure each Eridian time unit has two symbols (e.g., 0 becomes 'ℓℓ', 5 becomes 'ℓ∀')
        eridianTimeLabel.setText(
                (erHour.length() == 1 ? "ℓ" : "") + erHour + ":" +
                        (erMin.length() == 1 ? "ℓ" : "") + erMin + ":" +
                        (erSec.length() == 1 ? "ℓ" : "") + erSec
        );
    }

    private String toEridian(int num) {
        // Convert the base-10 number to a base-6 String
        String base6 = Integer.toString(num, 6);
        StringBuilder result = new StringBuilder();
        // Map the base-6 digits (0-5) to Eridian symbols
        for (char c : base6.toCharArray()) {
            result.append(ERIDIAN_SYMBOLS[c - '0']);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Use the event dispatch thread for Swing components
        SwingUtilities.invokeLater(() -> new EridianChronometer());
    }
}