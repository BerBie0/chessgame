package Model.Board;

import Model.Pieces.King;
import Model.Pieces.Pawn;
import Model.utils.Color2;
import Model.Pieces.Piece;
import Model.utils.PieceFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Board
{

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private int[] board;
    private ArrayList<Piece> pieces = new ArrayList<>();



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public Board()
    {
        this.board = new int[120];
        for(int i = 0; i < 120; i++)
        {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public ArrayList<Piece> getPieces()
    {
        return pieces;
    }



    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

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
            throw new IllegalArgumentException("Model.Board.Board.java : getPieceFromPosition(int position) : position < 21 or position > 98");
        }
        return pieces.stream()
                .filter( pos -> pos.getPosition() == position)
                .findFirst()
                .orElseThrow(
                        () -> {
                            throw new IllegalArgumentException("Model.Board.Board.java : getPieceFromPosition(int position) : can't find piece");
                        } );
    }


    public boolean isPositionOccupied(int position)
    {
        if(position < 21 && position > 98)
        {
            throw new IllegalArgumentException("Model.Board.Board.java : isPositionOccupied : position < 21 or position > 98");
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
            throw new IllegalArgumentException("Model.Board.Board.java : addPieceToBoard : Position is ever occupied by a piece");
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

    protected void validateMoveCommon(Piece piece, int newPos)
    {
        if( !piece.isValidMove(newPos) )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "the piece cannot move at the new position");

        if ( piece.getPosition() == newPos )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "can't move to the same position");

        if ( piece.getColor() == getPieceFromPosition(newPos).getColor() )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "can't capture ur piece");

        if ( !isPathFree(piece, newPos) )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "there a piece(s) in path");
    }

    public void validateSimpleMove(Piece piece, int newPos)
    {
        validateMoveCommon(piece, newPos);

        if ( !isPathFree(piece, newPos) )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "there a piece(s) in path");

        if ( isPositionOccupied(newPos) )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "position is occupied use AttackMove Move");

        if ( piece instanceof Pawn && ( piece.getPosition() + 11 == newPos
                                        || piece.getPosition() + 12 == newPos
                                        || piece.getPosition() - 11 == newPos
                                        || piece.getPosition() - 12 == newPos) )
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "seems to be a pawn attack move : use attackMove Move");


    }

    public void validateAttackMove(Piece piece, int newPos)
    {
        validateMoveCommon(piece, newPos);
    }

    //TODO isPathFree and axis and diagonal !
    public boolean isPathFree(Piece piece, int newPos)
    {
        return true;
    }
    //TODO isPathFree and axis and diagonal !
    public boolean isPathFreeAxis(Piece piece, int newPos){
        return true;
    }
    //TODO isPathFree and axis and diagonal !
    public boolean isPathFreeDiagonal(Piece piece, int newPos){
        return true;
    }

    public void move(Piece piece, int position)
    {
        board[piece.getPosition()] = 0;
        piece.setPosition(position);
        board[piece.getPosition()] = piece.getPieceCode();
    }

    public Piece getWhiteKing()
    {
        return pieces.stream()
                .filter( piece -> piece.getColor() == Color2.WHITE && piece instanceof King )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getWhiteKing() : king not found"));
    }

    public Piece getBlackKing()
    {
        return pieces.stream()
                .filter( piece -> piece.getColor() == Color2.BLACK && piece instanceof King )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getWhiteKing() : king not found"));
    }

    public King getKing(Color2 color)
    {
        return (King) pieces.stream()
                .filter( piece -> piece.getColor() == color && piece instanceof King )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getKing(Color2 color) : king not found"));
    }

    public Boolean isCheck(Color2 color)
    {
        Color2 attackColor = color == Color2.WHITE ? Color2.BLACK: Color2.WHITE;
        King king = getKing(color);
        Boolean check =  pieces.stream()
                .filter( piece -> piece.getColor() != attackColor )
                .anyMatch( piece -> piece.canCapturePiece(king) && isPathFree(piece, king.getPosition()) );
        if ( !king.getIsChecked() && check )
        {
            king.setIsChecked(true);
        }
        return check;
    }

    //TODO anyValidMove
    public boolean anyValidMove( Color2 color )
    {
        return true;
    }




    /*------------------------------------------------TEST----------------------------------------------------------*/

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

}
