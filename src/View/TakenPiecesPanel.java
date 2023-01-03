package View;

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

public class TakenPiecesPanel extends JPanel
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.GRAY;
    private static final Dimension TAKEN_PIECES_DIM = new Dimension(80, 80);
    private final JPanel northPanel;
    private final JPanel southPanel;
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public TakenPiecesPanel()
    {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel  = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIM);
    }

    public void redo(final MoveLog moveLog, final Board board)
    {
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for (final Integer position : moveLog.getMoves())
        {
            final Piece takenPiece = board.getPieceFromPosition(position);
            if(takenPiece.getColor() == Color2.WHITE)
                whiteTakenPieces.add(takenPiece);
            else if (takenPiece.getColor() == Color2.BLACK)
                blackTakenPieces.add(takenPiece);
            else
                throw new RuntimeException("TakenPiecesPanel.java : redo(final MoveLog moveLog, final Board board) : no whithe no black piece");
        }

        Collections.sort(whiteTakenPieces, new Comparator<Piece>()
        {
            @Override
            public int compare(Piece o1, Piece o2)
            {
                return Integer.compare(o1.getPieceCode(), o2.getPieceCode());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(final Piece o1, final Piece o2) {
                return Integer.compare(o1.getPieceCode(), o2.getPieceCode());
            }
        });

        for (final Piece takenPiece : whiteTakenPieces)
        {
            try
            {
                //System.out.println(takenPiece.toString());
                final BufferedImage image = ImageIO.read(new File("img/" + takenPiece.toString() + ".gif"));
                //final BufferedImage image = ImageIO.read(new File("img/WHITEpawn"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            } catch (final IOException e)
            {
                e.printStackTrace();
                System.out.println("TakenPiecePanel.java : redo(final MoveLog moveLog, final Board board) : pb image");
            }
        }

        for (final Piece takenPiece : blackTakenPieces)
        {
            try
            {
                final BufferedImage image = ImageIO.read(new File("img/" + takenPiece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            } catch (final IOException e)
            {
                e.printStackTrace();
                System.out.println("TakenPiecePanel.java : redo(final MoveLog moveLog, final Board board) : pb image");
            }
        }
        validate();
    }


} // end TakenPiecesPanel

