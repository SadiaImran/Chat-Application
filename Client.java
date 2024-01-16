
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client  implements ActionListener {
    JButton send ;
    JTextField textField ;

    static JPanel messagePanel ;

    static Box vertical = Box.createVerticalBox();

    static PrintWriter printWriter;
    static JFrame f = new JFrame();
    Client() throws IOException {
        f.setSize(450,700);
        f.setLayout(null);
        f.setLocation(0,50);
        f.getContentPane().setBackground(Color.WHITE);
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(120, 90, 170));
        panel1.setBounds(0,0,f.getWidth() , 85);
        panel1.setLayout(null);
        f.add(panel1);


        ImageIcon iconImage = new ImageIcon(ClassLoader.getSystemResource("Icons/left-arrow.png"));
//        Image imageScale = iconImage.getImage().getScaledInstance(25 , 25 ,Image.SCALE_DEFAULT);
//        ImageIcon iconImageNew = new ImageIcon(imageScale);
        JLabel iconLabel = new JLabel(iconImage);
        iconLabel.setBounds(5 , 25  , 30 ,30);
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        panel1.add(iconLabel);

        ImageIcon iconPerson = new ImageIcon(ClassLoader.getSystemResource("Icons/avatar-ali.png"));
        Image imageScale = iconPerson.getImage().getScaledInstance(50 , 50 ,Image.SCALE_DEFAULT);
        ImageIcon iconPersonScaled = new ImageIcon(imageScale);
        JLabel iconPersonLabel = new JLabel(iconPersonScaled);
        iconPersonLabel.setBounds(iconLabel.getX() + 35 , 20 , 50 ,50);
        iconPersonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        panel1.add(iconPersonLabel);
        panel1.add(iconLabel);

        ImageIcon iconVideo = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
//        Image imageScale = iconImage.getImage().getScaledInstance(25 , 25 ,Image.SCALE_DEFAULT);
//        ImageIcon iconImageNew = new ImageIcon(imageScale);
        JLabel iconVideoLabel = new JLabel(iconVideo);
        iconVideoLabel.setBounds(300 , 30   , 25 ,25);
        iconVideoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        panel1.add(iconVideoLabel);

        ImageIcon iconCall= new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
//        Image imageScale = iconImage.getImage().getScaledInstance(25 , 25 ,Image.SCALE_DEFAULT);
//        ImageIcon iconImageNew = new ImageIcon(imageScale);
        JLabel iconCallLabel = new JLabel(iconCall);
        iconCallLabel.setBounds(iconVideoLabel.getX() + 50 , 30   , 25 ,25);
        iconCallLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        panel1.add(iconCallLabel);

        ImageIcon iconDots= new ImageIcon(ClassLoader.getSystemResource("Icons/dots.png"));
//        Image imageScale = iconImage.getImage().getScaledInstance(25 , 25 ,Image.SCALE_DEFAULT);
//        ImageIcon iconImageNew = new ImageIcon(imageScale);
        JLabel iconDotsLabel = new JLabel(iconDots);
        iconDotsLabel.setBounds(iconCallLabel.getX() + 50 , 30   , 25 ,25);
        iconDotsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.dispose();
            }
        });
        panel1.add(iconDotsLabel);


        JLabel name = new JLabel("Ali");
        name.setBounds(iconPersonLabel.getX()+60 , iconPersonLabel.getY() +5 , 80, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF",Font.BOLD,18));
        panel1.add(name);

        JLabel status = new JLabel("online");
        status.setBounds(iconPersonLabel.getX()+60 , name.getY() + 20 , 80, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN SERIF",Font.BOLD,12));
        panel1.add(status);



        // Overlay messagePanel on top of the JLabel to display messages
        messagePanel = new JPanel();

        messagePanel.setBounds(0, panel1.getY() + 85, 450, 560);
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        f.add(messagePanel);

        textField = new JTextField();
        textField.setBounds(8,655,310,40);
        textField.setFont(new Font("SAN SERIF",Font.PLAIN , 16));
        f.add(textField);

        send = new JButton("Send") ;
        send.setBounds(320 , 655 , 123 , 40 );
        send.setBackground(new Color(120, 90, 170));
        send.setForeground(Color.WHITE);
        send.setFont(textField.getFont());
        send.addActionListener(this);
        f.add(send);


        f.setUndecorated(true);
        f.setVisible(true);


    }

    public static void main (String[] args) throws IOException {
        new Client();

        try{
            Socket s = new Socket(InetAddress.getLocalHost(), 1234);
            InputStreamReader inputStreamReader = new InputStreamReader(s.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(s.getOutputStream());

            while(true){
                messagePanel.setLayout(new BorderLayout());

                String msg = bufferedReader.readLine();
                if(msg == null || msg.equals("stop")) break;
                JPanel panel = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                messagePanel.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }
            s.close();
            f.dispose();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == send){
            try {

                String text = textField.getText();
                JLabel output = new JLabel(text);

                JPanel outputStore = formatLabel(text);
                //            outputStore.add(output);

                messagePanel.setLayout(new BorderLayout());
                JPanel right = new JPanel(new BorderLayout());

                right.add(outputStore, BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));
                messagePanel.add(vertical, BorderLayout.PAGE_START);

                printWriter.println(text);
                printWriter.flush();
                textField.setText("");
                f.repaint();
                f.revalidate();
            }catch(Exception exception){
                exception.printStackTrace();
            }

        }


    }
    public static  JPanel formatLabel(String output){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
        JLabel outputLabel = new JLabel("<html><p  style=\"width:150px\" >"+output+"</p></html>");
        outputLabel.setFont(new Font("Tahoma" , Font.PLAIN , 16));
        outputLabel.setBackground(new Color(120, 90, 170));
        outputLabel.setForeground(Color.WHITE);
        outputLabel.setOpaque(true);
        outputLabel.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(outputLabel);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm") ;
        JLabel time = new JLabel("12:00");
        time.setText(simpleDateFormat.format(calendar.getTime()));
        panel.add(time);


        return  panel ;
    }
}