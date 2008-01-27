/**
 *
 */

import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;

import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This example shows rendering a page where the font size can be adjusted dynamically once the page is loaded using
 * a font scaling factor. Note that scaling up or down once the page is rendered requires it to be re-rendered
 * and re-layed out, which takes a small (possibly insignificant) amount of time, but which may be noticeable for
 * larger pages..
 *
 *
 * @author Patrick Wright
 */
public class FontScaleJPanelRender {
    private String fileName;

    public static void main(String[] args) throws Exception {
        new FontScaleJPanelRender().run(args);
    }

    private void run(String[] args) {
        loadAndCheckArgs(args);

        // Create a JPanel subclass to render the page
        final XHTMLPanel panel = new XHTMLPanel();

        // Set the XHTML document to render. We use the simplest form
        // of the API call, which uses a File reference. There
        // are a variety of overloads for setDocument().
        try {
            panel.setDocument(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // Put our panel in a scrolling pane. You can use
        // a regular JScrollPane here, or our FSScrollPane.
        // FSScrollPane is already set up to move the correct
        // amount when scrolling 1 line or 1 page
        FSScrollPane scroll = new FSScrollPane(panel);

        JFrame frame = new JFrame("Flying Saucer: " + panel.getDocumentTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);

        panel.setFontScalingFactor(1.15F);
        panel.setMinFontScale(0.01F);
        panel.setMaxFontScale(12F);
        JButton smaller = new JButton("F-");
        smaller.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panel.decrementFontSize();
                System.out.println("decremented");
            }
        });
        JButton def = new JButton("F0");
        def.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panel.resetFontSize();

                System.out.println("reset");
            }
        });
        JButton larger = new JButton("F+");
        larger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panel.incrementFontSize();
                System.out.println("incremented");
            }
        });
        JPanel top = new JPanel(new FlowLayout());
        top.add(smaller);
        top.add(def);
        top.add(larger);

        frame.getContentPane().add(BorderLayout.NORTH, top);

        frame.pack();
        frame.setSize(1024, 768);
        frame.setVisible(true);
    }

    private void loadAndCheckArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter a file or URI.");
            System.exit(-1);
        }
        String name = args[0];
        if (! new File(name).exists()) {
            System.out.println("File " + name + " does not exist.");
            System.exit(-1);
        }
        this.fileName = name;
    }
}