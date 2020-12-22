import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class AppGUI {

    private JFrame frame;
    private JPanel panel;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private Color color1;
    private JTextArea URLPanel;
    private JLabel tLabel;
    private Spider spider;
    private String[] start_url;
    private JTextArea textBox;


    public AppGUI() throws IOException {
        frame = new JFrame();
        panel = new JPanel();
        URLPanel = new JTextArea(20,2);
        textBox = new JTextArea();

        b1 = new JButton("Start Crawler");
        b2 = new JButton("Scrape page content");
        b3 = new JButton("Scrape page code");

        tLabel = new JLabel("Output panel(The output panel cannot scroll at the moment):");
        tLabel.setBounds(10, 110, 500, 40);
        tLabel.setForeground(Color.darkGray);


        // Buttons ...
        b1.setBounds(550,10, 200, 30);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.CENTER);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Start crawler  ...
                SwingWorker sw = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        try {
                            String str = textBox.getText();
                            start_url = new String[]{str};
                            spider.crawl(start_url, spider.extractDomains(start_url), true);
                            // panel.updateUI();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        return null;
                    }
                };
                sw.execute();
            };
        });

        b2.setBounds(550,50, 200, 30);
        b2.setVerticalTextPosition(AbstractButton.CENTER);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Start crawler  ...
                SwingWorker sw2 = new SwingWorker() {

                    protected Object doInBackground()  throws Exception{
                        try {
                            String str = textBox.getText();
                            spider.debugMode(str, false);
                        } catch (Exception e){}
                        return null;
                    }
                };
                sw2.execute();
            };
        });

        b3.setBounds(550,100, 200, 30);
        b3.setVerticalTextPosition(AbstractButton.CENTER);
        b3.setHorizontalTextPosition(AbstractButton.CENTER);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Start crawler  ...
                SwingWorker sw2 = new SwingWorker() {

                    protected Object doInBackground()  throws Exception{
                        try {
                            String str = textBox.getText();
                            spider.debugMode(str, true);
                        } catch (Exception e){}
                        return null;
                    }
                };
                sw2.execute();
            };
        });


        // Text Box ...
        textBox.setBounds(10, 10 , 500, 30);
        textBox.setText("http://aniweb.epizy.com/");

        // URL Panel ....
        URLPanel.setBounds(10, 150, 800, 800);
        URLPanel.setAutoscrolls(true);
        URLPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        URLPanel.setForeground(Color.white);
        URLPanel.setLineWrap(true);
        URLPanel.setBorder(BorderFactory.createCompoundBorder(URLPanel.getBorder(),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        URLPanel.setBackground(Color.darkGray);
        //URLPanel.add(tLabel, BorderLayout.CENTER);

        // Main Panel ...
        panel.setBounds(0,0, 1000, 1000);
        panel.setLayout(null);
        panel.setBackground(Color.CYAN);
        panel.add(textBox);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(tLabel);
        panel.add(URLPanel);



        // Setting up Spider ...
        spider = new Spider(URLPanel);
//        start_url = new String[]{"https://asoftmurmur.com/"};

        // Setting up  frame ...
        frame.add(panel);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Crawler GUI");
        frame.setSize(1000, 800);
        //frame.pack();
        frame.setVisible(true);


    }

}
