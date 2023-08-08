package Model.Board;

import Exceptions.InvalidMoveException;
import Model.Pieces.King;
import Model.Pieces.Pawn;
import Model.Pieces.Piece;
import Model.Pieces.PieceFactory;
import Model.Utils.Color2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Board {
    private int[] board;
    private ArrayList<Piece> pieces;
    private ArrayList<IBoardObserver> observers;
    private ArrayList<Integer> highLightMove;

    public Board() {
        board = new int[120];
        for (int i = 0; i < 120; i++) {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
        pieces = new ArrayList<>();
        observers = new ArrayList<>();
        highLightMove = new ArrayList<>();
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public ArrayList<Integer> getHighLightMove() {
        return highLightMove;
    }
    public Board getBoard() {
        return this;
    }

    public Piece getPieceFromPosition(int position) {
        Piece piece;
        piece = pieces.stream()
                .filter(pos -> pos.getPosition() == position)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("can't find piece")
                );
        return piece;
    }
    public boolean isPositionOccupied(int position) {
        return ( board[position] != -10 && board[position] != 0 );
    }
    public void calculateLegalMoves(Piece piece) {
        ArrayList<Integer> res = new ArrayList<>();
        Piece targetPiece;
        for (int i = 0; i < board.length; i++) {
            try {
                targetPiece = getPieceFromPosition(i);
                if ( piece.isValidMove(i) && targetPiece.getColor() != piece.getColor() && isPathFree(piece, i) )
                    res.add(i);
            } catch (Exception e){
                if ( piece.isValidMove(i) && isPathFree(piece, i)  ) {
                    res.add(i);
                }

            }
        }
        highLightMove = res;
        notfifyObserver();
    }
    public void addPieceToBoard(Piece piece) {
        int piecePos = piece.getPosition();
        if (!isPositionOccupied(piecePos)) {
            pieces.add(piece);
            board[piecePos] = piece.getPieceCode();
        }
    }
    public void removePieceFromBoard(Piece piece) {
        pieces.remove(piece);
        board[piece.getPosition()] = 0;
    }

    public void initializeBoard() {
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
    public boolean isPathFree(Piece piece, int newPos) {
        int[] offset = piece.executeStrategy();
        int piecePos = piece.getPosition();
        if ( piece instanceof Pawn && isPositionOccupied( newPos ) && (Math.abs(piecePos - newPos) == 9) || (Math.abs(piecePos - newPos)) == 11 && isPositionOccupied( newPos ))
            return true;
        if ( piece instanceof Pawn && isPositionOccupied(newPos) )
            return false;
        for (int i : offset) {
            int nextPos = piecePos + i;
            int nbMeetingPiece = 0;
            while ( ( nbMeetingPiece < 1  ) && board[nextPos] != -10 ) {
                if ( piece instanceof Pawn && board[nextPos] == Math.abs(1) ) break;
                if (board[nextPos] != 0 && board[nextPos] != -10)
                    nbMeetingPiece++;
                if (nextPos == newPos) return true;
                nextPos += i;
            }
        }
        return false;
    }

    public void move(Piece piece, int position) {
        board[piece.getPosition()] = 0;
        piece.setPosition(position);
        board[piece.getPosition()] = piece.getPieceCode();
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

    protected void validateMoveCommon(Piece piece, int newPos){
        if (!piece.isValidMove(newPos))
            throw new InvalidMoveException("move not valid");
        if (piece.getPosition() == newPos)
            throw new InvalidMoveException("cannot move to the same position");
        if (isPositionOccupied(newPos) && piece.getColor() == getPieceFromPosition(newPos).getColor())
            throw new InvalidMoveException("cannot capture ur piece");
        if (!isPathFree(piece, newPos))
            throw new InvalidMoveException("cannot move piece block in path");
    }
    public void validateSimpleMove(Piece piece, int newPos) {
        validateMoveCommon(piece, newPos);
        if (isPositionOccupied(newPos))
            throw new IllegalArgumentException("Board.java : validateMove(Piece piece, int newPos) : " +
                    "position is occupied use AttackMove Move");
    }
    public void validateAttackMove(Piece piece, int newPos) {
        validateMoveCommon(piece, newPos);
        if (!isPositionOccupied(newPos) || !piece.canCapturePiece(getPieceFromPosition(newPos)))
            throw new InvalidMoveException("cannot capture this piece");
    }
    public void validateCastleMove(Piece piece, int newPos) throws Exception {
        validateMoveCommon(piece, newPos);
    }
    public Boolean isCheck(Color2 color) {
        Color2 attackColor = color == Color2.WHITE ? Color2.BLACK : Color2.WHITE;
        King defKing = getKing(color);

        boolean check = pieces.stream()
                .filter(piece -> piece.getColor() == attackColor)
                .anyMatch(piece -> piece.canCapturePiece(defKing) && isPathFree(piece, defKing.getPosition()));
        defKing.setIsChecked(check);
        return check;
    }
    public void addObserver(IBoardObserver obs) {
        observers.add(obs);
    }
    public void notfifyObserver() {
        for (IBoardObserver obs : observers) {
            obs.updateBoardAndLegalMoves();
        }

    }

    public void displayBoard() {
        for (int i = 0; i < 120; i++) {
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
            //System.out.print(i +", ");
            System.out.print(String.format("%4d", i));
        }
    }




















































































}
