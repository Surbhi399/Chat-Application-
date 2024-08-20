import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;


public class Server  implements ActionListener
{
    JTextField msg;
    JPanel p2;
  static  Box vertical = Box.createVerticalBox();
   static JFrame f = new JFrame();
    static DataOutputStream dsend;
    Server()
    {

        f.setLayout(null);
        f.setSize(450,665);
        f.setUndecorated(true);
        f.setLocation(100,40);
        f.getContentPane().setBackground(Color.white);


        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0,0,450,48);
        p1.setBackground(new Color(156,64,191)); // teal green
        f.add(p1);

        ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i1 =i.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon arrow = new ImageIcon(i1);
        JLabel image1 = new JLabel(arrow);
        image1.setBounds(5,13,25,25);
        p1.add(image1);
        image1.addMouseListener( new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });


        ImageIcon m = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image m1 =m.getImage().getScaledInstance(28,30,Image.SCALE_DEFAULT);
        ImageIcon profile = new ImageIcon(m1);
        JLabel profile1 = new JLabel(profile);
        profile1.setBounds(31,13,28,30);
        p1.add(profile1);

        ImageIcon v = new ImageIcon(ClassLoader.getSystemResource("Group Chatting Icons\\video (1).png"));
        Image v1 =v.getImage().getScaledInstance(24,28,Image.SCALE_DEFAULT);
        ImageIcon video = new ImageIcon(v1);
        JLabel video1 = new JLabel(video);
        video1.setBounds(300,12,28,30);
        p1.add(video1);

        ImageIcon c = new ImageIcon(ClassLoader.getSystemResource("Group Chatting Icons\\phone (1).png"));
        Image c1 =c.getImage().getScaledInstance(24,28,Image.SCALE_DEFAULT);
        ImageIcon call = new ImageIcon(c1);
        JLabel call1 = new JLabel(call);
        call1.setBounds(360,13,28,30);
        p1.add(call1);

        ImageIcon t = new ImageIcon(ClassLoader.getSystemResource("Group Chatting Icons/3icon (1).png"));
        Image t1 =t.getImage().getScaledInstance(8,22,Image.SCALE_DEFAULT);
        ImageIcon dots = new ImageIcon(t1);
        JLabel dots1 = new JLabel(dots);
        dots1.setBounds(408,13,25,28);
        p1.add(dots1);

        JLabel name = new JLabel("Devil");
        name.setBounds(70,6,100,26);
        name.setForeground(Color.white);
        name.setFont(new Font("Harrington", Font.BOLD, 20));
        p1.add(name);

        JLabel active = new JLabel("online");
        active.setBounds(80,30,100,16);
        active.setForeground(Color.white);
        active.setFont(new Font("Harrington", Font.PLAIN, 12));
        p1.add(active);

        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(5,50,440,550);
        f.add(p2);

        msg = new JTextField();
        msg.setBounds(5,620,300,35);
        msg.setFont(new Font("Harrington", Font.BOLD, 18));
        f.add(msg);

        JButton send = new JButton("send");
        send.setBounds(300,620,140,35);
        send.setFont(new Font("Tobana", Font.BOLD, 16));
        send.setBackground(new Color(156,64,191));
        send.setForeground(Color.BLACK);
        send.addActionListener(this);
        f.add(send);


        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        try
        {
        String    output = msg.getText();// IT WILL TAKE TEXT WRITTEN IN MSG
        JPanel p3 = formatLabel(output);
        p2.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p3, BorderLayout.LINE_END); // IT IS NOT ACCEPT STRING DIRECTLY SO WE HAVE TO CHANGE IT IN COMPONENT
        vertical.add(right); // MULTIPLE MESSAGES BACK TO BACK IN SEQUENCE
        vertical.add(Box.createVerticalStrut(15));// IT WILL ALIGN THE MSG ONE BY ONE
        p2.add(vertical, BorderLayout.PAGE_START);// IT WILL PUT MSG FROM START LINE OF P2 PANEL

        dsend.writeUTF(output);

        msg.setText("");// text field will get empty on clicking send button
        f.repaint();// TO SHOW THE MSG
        f.validate();//TO SHOW THE MSG
        f.invalidate();// TO SHOW THE MSG
       }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }

    }

    public  static JPanel formatLabel(String output) {
        JPanel panel = new JPanel();
        panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel out = new JLabel("<html><p style = \"width: 150px\">" + output + "</p></html>");
        out.setFont(new Font("Harrington", Font.BOLD, 16));
        out.setBackground(new Color(203,195,227));
        out.setOpaque(true);
        out.setBorder(new EmptyBorder(10,15,10,50));
        panel.add(out);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(hm.format(calendar.getTime()));// dynamically text insert karane set text use hota hain
         panel.add(time);
         return panel;
    }


    public  static  void  main(String [] args)
     {
         new Server();

        try{
             ServerSocket s = new ServerSocket(6001);

             while (true)// if we want to run server infinite times so, we use while loop giving as true
             {
                 Socket socket = s.accept();
                 DataInputStream dataread = new DataInputStream(socket.getInputStream()); // receiving
                 dsend = new DataOutputStream(socket.getOutputStream()); //  sending msg

                 while(true) {
                     String text = dataread.readUTF();
                     JPanel panel = formatLabel(text);

                     JPanel left = new JPanel(new BorderLayout());
                     left.add(panel,BorderLayout.LINE_START); // IT IS NOT ACCEPT STRING DIRECTLY SO WE HAVE TO CHANGE IT IN COMPONENT
                     vertical.add(left); // MULTIPLE MESSAGES BACK TO BACK IN SEQUENCE
                     f.validate();//TO SHOW THE MSG

                 }
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }
}
