package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int[] board;
    private ArrayList<Piece> pieces = new ArrayList<>();

    public Board()
    {
        this.board = new int[120];
        for(int i = 0; i < 120; i++)
        {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
    }

    public LinkedList<Integer> calculateLegalMoves(Piece piece)
    {
        LinkedList<Integer> res = new LinkedList<>();
        for(int i = 0; i < board.length; i++)
        {
            if(piece.isValidMove(i) && !this.isPositionOccupied(i))
            {
                res.add(i);
            }
        }
        return res;
    }
    public Piece getPieceFromPosition(int position)
    {
        if(position < 21 && position > 98)
        {
            throw new IllegalArgumentException("Model.Board.java : getPieceFromPosition(int position) : position < 21 or position > 98");
        }
        return pieces.stream()
                .filter( pos -> pos.getPosition() == position)
                .findFirst()
                .orElseThrow(
                        () -> {
                            throw new IllegalArgumentException("Model.Board.java : getPieceFromPosition(int position) : can't find piece");
                        } );
    }

    public ArrayList<Piece> getPieces()
    {
        return pieces;
    }
    public boolean isPositionOccupied(int position)
    {
        if(position < 21 && position > 98)
        {
            throw new IllegalArgumentException("Model.Board.java : isPositionOccupied : position < 21 or position > 98");
        }
        return pieces.stream().anyMatch(piece -> piece.getPosition() == position);
    }
    public void addPieceToBoard(Piece piece)
    {
        if( !isPositionOccupied(piece.getPosition()) )
        {
            pieces.add(piece);
            board[piece.getPosition()] = piece.getPieceCode();
        }
        else
        {
            throw new IllegalArgumentException("Model.Board.java : addPieceToBoard : Position is ever occupied by a piece");
        }
    }
    public void removePieceToBoard(Piece piece)
    {
        pieces.remove(piece);
        board[piece.getPosition()] = piece.getPosition();
    }

    public void inializeBoard()
    {
        PieceFactory pf = new PieceFactory();
        for(int i = 81; i < 89; i++)
        {
            addPieceToBoard(pf.createPawn(Color2.WHITE, i));
        }
        addPieceToBoard(pf.createRook(Color2.WHITE, 91));
        addPieceToBoard(pf.createRook(Color2.WHITE, 98));
        addPieceToBoard(pf.createKnight(Color2.WHITE, 92));
        addPieceToBoard(pf.createKnight(Color2.WHITE, 97));
        addPieceToBoard(pf.createBishop(Color2.WHITE, 93));
        addPieceToBoard(pf.createBishop(Color2.WHITE, 96));
        addPieceToBoard(pf.createQueen(Color2.WHITE, 94));
        addPieceToBoard(pf.createKing(Color2.WHITE, 95));

        for(int i = 31; i < 39; i++)
        {
            addPieceToBoard(pf.createPawn(Color2.BLACK, i));
        }
        addPieceToBoard(pf.createRook(Color2.BLACK, 21));
        addPieceToBoard(pf.createRook(Color2.BLACK, 28));
        addPieceToBoard(pf.createKnight(Color2.BLACK, 22));
        addPieceToBoard(pf.createKnight(Color2.BLACK, 27));
        addPieceToBoard(pf.createBishop(Color2.BLACK, 23));
        addPieceToBoard(pf.createBishop(Color2.BLACK, 26));
        addPieceToBoard(pf.createQueen(Color2.BLACK, 24));
        addPieceToBoard(pf.createKing(Color2.BLACK, 25));
    }


    /*FONCTION TEST*/
    public void displayBoard()
    {
        for(int i = 0; i < 120; i++)
        {
            if(i % 10 == 0 && i !=0)
            {
                System.out.println();
            }
            //System.out.print(i +", ");
            System.out.print(String.format("%4d",board[i]));
        }
    }
    public void move(Piece piece, int position)
    {
        board[piece.getPosition()] = 0;
        piece.setPosition(position);
        board[piece.getPosition()] = piece.getPieceCode();
    }
}
