package View;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;

public class GameFrame2 extends JFrame{
    JButton pushme = new JButton("push me");
    JButton pushme2 = new JButton("click me");
    JButton pushme3 = new JButton("click me");
    JButton pushme4 = new JButton("click me");
    public GameFrame2()
    {
        super(" Chess Game GUI");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) this.getContentPane();
        //contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        //contentPane.setLayout(new GridLayout(2,2,20, 20));
        contentPane.setLayout(new FlowLayout());
        //pushme.setPreferredSize(new Dimension(200,200));

        //contentPane.add(createToolBar(), BorderLayout.NORTH);

        pushme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPush(e);
            }
        });
        contentPane.add(pushme);
        pushme2.addActionListener((event)-> btnPush(event));
        contentPane.add(pushme2);
        contentPane.add(pushme3);
        pushme3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                pushme3.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pushme3.setForeground(Color.BLACK);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pushme3.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pushme3.setForeground(Color.BLACK);
            }
        });
        contentPane.add(pushme4);

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int clickedButton = JOptionPane.showConfirmDialog(GameFrame2.this, "etes vous surs ?", "title", JOptionPane.YES_NO_OPTION);
                if(clickedButton == JOptionPane.YES_OPTION)
                {
                    dispose();
                }
            }
        });

    }

    /*private JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        toolBar.add(pushme);
        toolBar.add(pushme2);
        toolBar.add(pushme3);
        toolBar.add(pushme4);

        return toolBar;
    }*/

    private void btnPush( ActionEvent event)
    {

        System.out.println("heyy");
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        GameFrame2 w = new GameFrame2();
        w.setVisible(true);

    }
    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == pushme )
            System.out.println("bouton cliqué");
        else
            System.out.println("autre bouton");
    }
    */
}
