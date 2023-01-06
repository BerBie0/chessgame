package Model.Board;

import Model.Pieces.King;
import Model.Pieces.Pawn;
import Model.utils.Color2;
import Model.Pieces.Piece;
import Model.utils.PieceFactory;
import View.GameFrame2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

public class Board {

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private int[] board;
    private List<Piece> pieces;
    private List<IBoardObserver> observers;
    private LinkedList<Integer> highlightMove;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public Board() {
        this.board = new int[120];
        for (int i = 0; i < 120; i++) {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
        pieces = new LinkedList<>();
        observers = new LinkedList<>();
        highlightMove = new LinkedList<>();
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public List<Piece> getPieces() {
        return pieces;
    }

    public Piece getPieceFromPosition(int position) {
        Piece piece;

        piece = pieces.stream()
                .filter(pos -> pos.getPosition() == position)
                .findFirst()
                .orElseThrow(
                        () -> {
                            throw new IllegalArgumentException("Model.Board.Board.java : getPieceFromPosition(int position) : can't find piece");
                        });
        return piece;
    }

    public LinkedList<Integer> getHighLightMove() {
        return highlightMove;
    }

    public Board getBoard() {
        return this;
    }



    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public void calculateLegalMoves(Piece piece) {
        LinkedList<Integer> res = new LinkedList<>();

        Piece targetPiece;
        for (int i = 0; i < board.length; i++) {
            try {
                targetPiece = getPieceFromPosition(i);
                if ( piece.isValidMove(i) &&  isPathFree(piece, i) && targetPiece.getColor() != piece.getColor() ) {
                    res.add(i);
                }
            } catch (Exception e) {
                if ( piece.isValidMove(i) && isPathFree(piece, i) ) {
                    res.add(i);
                }
            }
        }
        highlightMove = res;
        notifyObservers();

    }


    public boolean isPositionOccupied(int position) {
        return pieces.stream().anyMatch(piece -> piece.getPosition() == position);
    }

    public void addPieceToBoard(Piece piece) {
        if (!isPositionOccupied(piece.getPosition())) {
            pieces.add(piece);
            board[piece.getPosition()] = piece.getPieceCode();
        } else {
            throw new IllegalArgumentException("Model.Board.Board.java : addPieceToBoard : Position is ever occupied by a piece");
        }
    }

    public void removePieceToBoard(Piece piece) {
        pieces.remove(piece);
        board[piece.getPosition()] = 0;
    }

    public void inializeBoard() {
        PieceFactory pf = new PieceFactory();
        for (int i = 81; i < 89; i++) {
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

        for (int i = 31; i < 39; i++) {
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

    protected void validateMoveCommon(Piece piece, int newPos) {
        if (!piece.isValidMove(newPos))
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "the piece cannot move at the new position");

        if (piece.getPosition() == newPos)
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "can't move to the same position");
        //FixMe les throws
        if (isPositionOccupied(newPos)) {
            if (piece.getColor() == getPieceFromPosition(newPos).getColor())
                throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                        "can't capture ur piece");
        }

        if (!isPathFree(piece, newPos)) {
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "there a piece(s) in path");
        }


    }

    public void validateSimpleMove(Piece piece, int newPos) {
        validateMoveCommon(piece, newPos);

        if (isPositionOccupied(newPos))
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "position is occupied use AttackMove Move");

        if (piece instanceof Pawn && (piece.getPosition() + 11 == newPos
                || piece.getPosition() + 12 == newPos
                || piece.getPosition() - 11 == newPos
                || piece.getPosition() - 12 == newPos))
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "seems to be a pawn attack move : use attackMove Move");

        notifyObservers();
    }

    public void validateAttackMove(Piece piece, int newPos) {
        validateMoveCommon(piece, newPos);
    }

    //TODO validateCastleMove
    public void validateCastleMove(Piece piece, int newPos) {
        validateMoveCommon(piece, newPos);
    }

    public boolean isPathFree(Piece piece, int newPos) {
        if ( piece instanceof Pawn && isPositionOccupied( newPos ) && (Math.abs(piece.getPosition() - newPos) == 9) || (Math.abs(piece.getPosition() - newPos)) == 11 && isPositionOccupied( newPos )) {
            return true;
        } else if ( piece instanceof Pawn && isPositionOccupied(newPos) ) {
            return false;
        } else {
            int oldPos = piece.getPosition();
            int[] offset = piece.executeStrategy();
            for (int j : offset) {
                int positionCalcul = oldPos;
                //cpt pour affichage des coups
                int cpt = 0;
                while ( board[positionCalcul + j] != -10 && cpt < 1 ) {
                    positionCalcul += j;
                    //pion en face d'un pion corrige l'affichage
                    if ( piece instanceof Pawn && board[positionCalcul] == Math.abs(1) ) break;
                    if( board[positionCalcul] != 0 ) cpt++;
                    if (positionCalcul == newPos) return true;
                }
            }
        }
        return false;
    }

    public void move(Piece piece, int position) {
        board[piece.getPosition()] = 0;
        piece.setPosition(position);
        board[piece.getPosition()] = piece.getPieceCode();
    }

    public Piece getWhiteKing() {
        return pieces.stream()
                .filter(piece -> piece.getColor() == Color2.WHITE && piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getWhiteKing() : king not found"));
    }

    public Piece getBlackKing() {
        return pieces.stream()
                .filter(piece -> piece.getColor() == Color2.BLACK && piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getWhiteKing() : king not found"));
    }

    public King getKing(Color2 color) {
        return (King) pieces.stream()
                .filter(piece -> piece.getColor() == color && piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Board.java : Piece getKing(Color2 color) : king not found"));
    }
    public int getKingPosition(Color2 color) {
        return getKing(color).getPosition();
    }

    public Boolean isCheck(Color2 color) {
        Color2 attackColor = color == Color2.WHITE ? Color2.BLACK : Color2.WHITE;
        King king = getKing(color);

        boolean check = pieces.stream()
                .filter(piece -> piece.getColor() == attackColor)
                .anyMatch(piece -> piece.canCapturePiece(king) && isPathFree(piece, king.getPosition()));
        if (!king.getIsChecked() && check) {
            king.setIsChecked(true);
        }
        return check;
    }



    /*-----------------------------------------------OBSERVER---------------------------------------------------------*/
    public void addObserver(IBoardObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers() {
        for (IBoardObserver obs : observers) {
            obs.updateBoardAndLegalMoves();
        }
    }

    /*------------------------------------------------TEST----------------------------------------------------------*/

    /*FONCTION TEST*/
    public void displayBoard() {
        for (int i = 0; i < 120; i++) {
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
            //System.out.print(i +", ");
            System.out.print(String.format("%4d", board[i]));
        }
    }

}
