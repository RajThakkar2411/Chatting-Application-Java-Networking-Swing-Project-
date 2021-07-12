
package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener{

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Boolean typing;

    Server(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground( new Color(7,94, 84));
        p1.setBounds(0,0,325, 50);
        f1.add(p1);

        // image icon class ka object banaya to fetch image from the disk.
        //Classloader ek predefined class hai usk andar static method hai getSystemResource
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        // koi b image ko direct frame pe nai daal sakte..so label pe dalke lable add karege frame pe
        JLabel l1 = new JLabel(i3);
        // khudka layout banane k liye ek method htaay setBounds which takes 4 arguments
        l1.setBounds(5,17,25,20);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });



        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(36,9,35,35);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(225,13,25,25);
        p1.add(l5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(265, 15,25,25);
        p1.add(l6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(9,23, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(300,15,9,23);
        p1.add(l7);

        JLabel l3 = new JLabel("Gaitonde");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD,15));
        l3.setForeground(Color.WHITE);
        l3.setBounds(83, 8, 100, 20);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN,11));
        l4.setForeground(Color.WHITE);
        l4.setBounds(83, 25, 80, 22);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Active Now");
                }
            }
        });

        t.setInitialDelay(2000);


        a1 = new JPanel();
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16 ));
        //a1.setBounds(2, 52, 320,412);

        // a1.setEditable(false);
        //a1.setLineWrap(true);
        //a1.setWrapStyleWord(true);
        //f1.add(a1);

        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(2,52,320,412);
        f1.add(sp);

        t1 = new JTextField();
        t1.setFont( new Font("SAN_SERIF", Font.PLAIN, 16));
        t1.setBounds(2, 468, 240, 30);
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }
            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }

        });



        b1 = new JButton("SEND");
        b1.setBounds(245, 468, 77, 30);
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(7, 94, 84));
        b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(Color.white);
//    by default borderLayout hotaay.. gridLayout b ek hotaay.. but muje mera apna layout banana hai so setLayout null maaro
        f1.setLayout(null);
        f1.setSize(325, 500);
        f1.setLocation(400, 165);
        f1.setUndecorated(true);
        f1.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){
        try{
            String out = t1.getText();
            sendTextToFile(out);

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void sendTextToFile(String message) throws  FileNotFoundException{
        try(FileWriter f = new FileWriter("chat.txt");
            PrintWriter p = new PrintWriter(new BufferedWriter(f));
        ){
            p.println("Gaitonde: "+message);

        }catch (Exception e){ e.printStackTrace();}
    }

    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args){
        new Server().f1.setVisible(true);

        String msginput = "";
        try{
            skt = new ServerSocket(6001);
            while(true){
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    msginput = din.readUTF();
                    JPanel p2 = formatLabel(msginput);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                }

            }

        }catch(Exception e){}
    }
}
/*package chatting.application;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener{

    // frame banana hai islie sabse pele constructr banayge.. same name as class name

    JPanel p1;// creates a <div> as that of in html
    JTextField t1;
    JButton b1;
    static JFrame f1 = new JFrame();
    static JPanel  a1;

   static Box vertical = Box.createVerticalBox();

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Boolean typing;


    Server(){

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground( new Color(7,94, 84));
        p1.setBounds(0,0,325, 50);
        f1.add(p1);

        // image icon class ka object banaya to fetch image from the disk.
        //Classloader ek predefined class hai usk andar static method hai getSystemResource
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        // koi b image ko direct frame pe nai daal sakte..so label pe dalke lable add karege frame pe
        JLabel l1 = new JLabel(i3);
        // khudka layout banane k liye ek method htaay setBounds which takes 4 arguments
        l1.setBounds(5,17,25,20);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
        }
        });



       ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/1.png"));
       Image i5 = i4.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
       ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(36,9,35,35);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(225,13,25,25);
        p1.add(l5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(265, 15,25,25);
        p1.add(l6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(9,23, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(300,15,9,23);
        p1.add(l7);

        JLabel l3 = new JLabel("Gaitonde");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD,15));
        l3.setForeground(Color.WHITE);
        l3.setBounds(83, 8, 100, 20);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN,11));
        l4.setForeground(Color.WHITE);
        l4.setBounds(83, 25, 80, 22);
        p1.add(l4);

        Timer t  = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                 if(!typing){
                     l4.setText("Active Now");

                 }

            }
        });
        t.setInitialDelay(2000);

        a1 = new JPanel();
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16 ));
        a1.setBounds(2, 52, 320,412);

       // a1.setEditable(false);
        //a1.setLineWrap(true);
        //a1.setWrapStyleWord(true);
        f1.add(a1);

        t1 = new JTextField();
        t1.setFont( new Font("SAN_SERIF", Font.PLAIN, 16));
        t1.setBounds(2, 468, 240, 30);
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }
            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }

        });



        b1 = new JButton("SEND");
        b1.setBounds(245, 468, 77, 30);
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(7, 94, 84));
        b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(Color.white);
//    by default borderLayout hotaay.. gridLayout b ek hotaay.. but muje mera apna layout banana hai so setLayout null maaro
        f1.setLayout(null);
        f1.setSize(325, 500);
        f1.setLocation(400, 165);
        f1.setUndecorated(true);
        f1.setVisible(true);

    }


    public void actionPerformed(ActionEvent ae) {

        try {

            String out = t1.getText();
            sendTextToFile(out);
            JPanel p2 = formatLabel(out);
            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(13));
            a1.add(vertical, BorderLayout.PAGE_START);


            //a1.add(p2);
           // a1.setText(a1.getText() + "\n\t\t" + out);

            dout.writeUTF(out);
            t1.setText("");

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void sendTextToFile(String message) {
        try(FileWriter f = new FileWriter("chat.txt");
            PrintWriter p = new PrintWriter(new BufferedWriter(f));
            ){
            p.println("Gaitonde: "+message);

        }catch (Exception e){ e.printStackTrace();}
    }

    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15, 15, 15, 50));
        p3.add(l1);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        p3.add(l2);

        return  p3;
    }

    public static void main(String[] args) {
        // frame ki saaari coding construtr k andar hai.. mai chaahta hu jaise h mai class ko run karu frame aana chayyye... run karne k
        // liye class ka object banaya and constructr call hoga.

        new Server().f1.setVisible(true);

        String msginput = "";
        try{
                skt = new ServerSocket(6001);  // ServerSocket Object banaya server end pe
            while(true) {
                s = skt.accept(); // accept method waits for the request from the client side and as it gets... creates Socket object.
                din = new DataInputStream(s.getInputStream()); // din me data ayga
                dout = new DataOutputStream(s.getOutputStream()); // dout me vo data hoga that we send

                while(true) {
                    msginput = din.readUTF();
                    JPanel p2 = formatLabel(msginput);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                    //   a1.setText(a1.getText() + "\n" + msginput);

                    skt.close();
                    s.close();
                }
            }


        }catch (Exception e){

        }
    }


}
*/