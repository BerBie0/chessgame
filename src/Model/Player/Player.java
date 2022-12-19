package Model.Player;

import Model.utils.Color2;
import Model.Pieces.Piece;

import java.util.ArrayList;

public class Player {
    private String name;
    private boolean urTurn = false;
    private Color2 color;
    private ArrayList<Piece> capturedPiece;

    public Player(Color2 color, String name)
    {
        this.name = name;
        this.color = color;
        if(color == Color2.WHITE)
        {
            urTurn = true;
        }
    }

    public Color2 getColor()
    {
        return color;
    }
    public boolean isUrTurn()
    {
        return urTurn;
    }
    public void changeTurn()
    {
        urTurn = !urTurn;
    }
    public ArrayList<Piece> getCapturedPieces() { return capturedPiece; }
    public void addCapturedPiece(Piece piece)
    {
        capturedPiece.add(piece);
    }
    public void move(Piece piece, int position)
    {
        piece.setPosition(position);
    }
    public boolean isWhite()
    {
        return color == Color2.WHITE;
    }

}
