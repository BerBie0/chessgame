package View;

import Model.Pieces.Piece;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class TakenPiecesPanel extends JPanel {
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.GRAY;
    private static final Dimension TAKEN_PIECES_DIM = new Dimension(100, 800);
    private static final Dimension TAKEN_PIECES_DIM_ICON = new Dimension(40, 40);
    private final static String pieceIconPath = "src/img/";
    private final JPanel northPanel;
    private final JPanel southPanel;
    private List<Piece> whiteTakenPieces;
    private List<Piece> blackTakenPieces;

    public TakenPiecesPanel(List<Piece> whiteTakenPieces, List<Piece> blackTakenPieces) {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel();
        this.southPanel = new JPanel();
        northPanel.setLayout(new GridLayout(8, 2));
        southPanel.setLayout(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
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
            java.net.URL imageURL = getClass().getClassLoader().getResource(takenPiece.toString() + ".gif");
            ImageIcon icon = new ImageIcon(imageURL);
            Image icon2 = icon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
            Image dimg = icon2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon icon3 = new ImageIcon(dimg);
            JLabel imageLabel = new JLabel(icon3);
            imageLabel.setPreferredSize(TAKEN_PIECES_DIM_ICON);
            imageLabel.setSize(TAKEN_PIECES_DIM_ICON);
            southPanel.add(imageLabel);
        }

        for (final Piece takenPiece : blackTakenPieces) {
            java.net.URL imageURL = getClass().getClassLoader().getResource(takenPiece.toString() + ".gif");
            ImageIcon icon = new ImageIcon(imageURL);
            Image icon2 = icon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
            Image dimg = icon2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon icon3 = new ImageIcon(dimg);
            JLabel imageLabel = new JLabel(icon3);
            imageLabel.setPreferredSize(TAKEN_PIECES_DIM_ICON);
            imageLabel.setSize(TAKEN_PIECES_DIM_ICON);
            northPanel.add(imageLabel);
        }
        validate();
        repaint();
    }


}
