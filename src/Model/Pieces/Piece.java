package Model.Pieces;

import Exceptions.InvalidMoveException;
import Model.Utils.Color2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Piece {
    private Color2 color;
    private int position;
    private float value;
    private int pieceCode;
    private IStrategyMovement strategyMovement;
    public static int[] board;


    public Piece(Color2 color, int position, float value, int pieceCode) {
        this.color = color;
        this.position = position;
        this.value = value;
        this.pieceCode = pieceCode;
        board = new int[120];
        for (int i = 0; i < 120; i++) {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setStrategy(IStrategyMovement strategyMovement) {
        this.strategyMovement = strategyMovement;
    }
    public Color2 getColor(){
        return color;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public float getValue() {
        return value;
    }
    public int getPieceCode() {
        return pieceCode;
    }
    public int[] executeStrategy() {
        return strategyMovement.offset();
    }
    public boolean isWhite() {
        return color == Color2.WHITE;
    }
    public boolean canCapturePiece(Piece piece) throws Exception{
        if (piece == null)
            throw new IllegalArgumentException("piece cannot be null");
        if (this.color == piece.getColor())
            throw new InvalidMoveException("cannot capture same color piece");
        if (this.position == piece.getPosition())
            throw new InvalidMoveException("cannot capture your piece with the same piece");
        if ( !isValideMove(piece.getPosition()) )
            throw new InvalidMoveException("move not valid");
        return true;
    }

    public abstract boolean isValideMove(int position);
    public abstract ArrayList<Integer> allLegalMove(int position);
}
