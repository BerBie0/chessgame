package View;

import Controller.GameController;
import Model.Board.Board;
import Model.utils.Color2;
import Model.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static View.GameFrame2.*;

public class TakenPiecesPanel extends JPanel {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.GRAY;
    private static final Dimension TAKEN_PIECES_DIM = new Dimension(100, 800);
    private static final Dimension TAKEN_PIECES_DIM_ICON = new Dimension(40, 40);
    private final JPanel northPanel;
    private final JPanel southPanel;
    private List<Piece> whiteTakenPieces;
    private List<Piece> blackTakenPieces;
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public TakenPiecesPanel(List<Piece> whiteTakenPieces, List<Piece> blackTakenPieces) {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel();
        this.southPanel = new JPanel();
        northPanel.setLayout(new GridLayout(8, 2));
        southPanel.setLayout(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        this.southPanel.setBackground(PANEL_COLOR);
        this.whiteTakenPieces = whiteTakenPieces;
        this.blackTakenPieces = blackTakenPieces;
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIM);
    }

    public void redo() {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        whiteTakenPieces.sort(Comparator.comparingInt(Piece::getPieceCode));

        blackTakenPieces.sort(Comparator.comparingInt(Piece::getPieceCode));

        for (final Piece takenPiece : whiteTakenPieces) {
            try {
                //System.out.println(takenPiece.toString());
                final BufferedImage image = ImageIO.read(new File("img/" + takenPiece.toString() + ".gif"));
                Image dimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                final ImageIcon icon = new ImageIcon(dimg);
                final JLabel imageLabel = new JLabel(icon);
                imageLabel.setPreferredSize(TAKEN_PIECES_DIM_ICON);
                imageLabel.setSize(TAKEN_PIECES_DIM_ICON);
                southPanel.add(imageLabel);
                System.out.println(takenPiece.toString());
            } catch (final IOException e) {
                e.printStackTrace();
                System.out.println("TakenPiecePanel.java : redo(final MoveLog moveLog, final Board board) : pb image");
            }
        }

        for (final Piece takenPiece : blackTakenPieces) {
            try {
                final BufferedImage image = ImageIO.read(new File("img/" + takenPiece.toString() + ".gif"));
                Image dimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                final ImageIcon icon = new ImageIcon(dimg);
                final JLabel imageLabel = new JLabel(icon);
                imageLabel.setPreferredSize(TAKEN_PIECES_DIM_ICON);
                imageLabel.setSize(TAKEN_PIECES_DIM_ICON);
                northPanel.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
                System.out.println("TakenPiecePanel.java : redo(final MoveLog moveLog, final Board board) : pb image");
            }
        }

        validate();
        repaint();
    }


} // end TakenPiecesPanel

