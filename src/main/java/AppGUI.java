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
    private Color color1;
    private JPanel URLPanel;
    private JLabel tLabel;
    private Spider spider;
    private String[] start_url;
    private JTextArea textBox;


    public AppGUI() throws IOException {
        frame = new JFrame();
        panel = new JPanel();
        URLPanel = new JPanel();
        textBox = new JTextArea();
        b1 = new JButton("Start Crawler");
        tLabel = new JLabel("Crawled URL:");
        tLabel.setBounds(10, 60, 200, 40);
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

        // Text Box ...
        textBox.setBounds(10, 10 , 500, 30);
        textBox.setText("Enter URL here...");

        // URL Panel ....
        URLPanel.setBounds(10, 100, 500, 800);
        URLPanel.setAutoscrolls(true);
        URLPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        URLPanel.setBackground(Color.darkGray);
        //URLPanel.add(tLabel, BorderLayout.CENTER);

        // Main Panel ...
        panel.setBounds(0,0, 1000, 1000);
        panel.setLayout(null);
        panel.setBackground(Color.CYAN);
        panel.add(textBox);
        panel.add(b1);
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
