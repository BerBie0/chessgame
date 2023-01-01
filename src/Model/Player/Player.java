package Model.Player;

import Model.Pieces.Pawn;
import Model.utils.Color2;
import Model.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private boolean urTurn = false;
    private Color2 color;
    private ArrayList<Piece> capturedPiece;
    private List<IPlayerObserver> observers;
    private List<IPlayerObserverGame> observersGame;

    public Player(Color2 color, String name) {
        this.name = name;
        this.color = color;
        if (color == Color2.WHITE) {
            urTurn = true;
        }
        observers = new ArrayList<>();
        observersGame = new ArrayList<>();
        capturedPiece = new ArrayList<>();

    }

    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public Color2 getColor() {
        return color;
    }

    public void setUrTurn(boolean turn) {
        urTurn = turn;
    }

    public boolean isUrTurn() {
        return urTurn;
    }

    public void changeTurn() {
        urTurn = !urTurn;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPiece;
    }

    public void addCapturedPiece(Piece piece) {
        capturedPiece.add(piece);
    }

    public void move(Piece piece, int position) {
        if(piece instanceof Pawn && !((Pawn) piece).getHasMovedOnce()) {
            ((Pawn) piece).setHasMovedOnce(true);
        }
        piece.setPosition(position);
        notifyObserversGame();
    }

    public boolean isWhite() {
        return color == Color2.WHITE;
    }

    public void addObserver(IPlayerObserver obs) {
        observers.add(obs);
    }

    public void notifyObservers() {
        for (IPlayerObserver obs : observers) {
            obs.updatePlayerName();
        }
    }

    public void addObserverGame(IPlayerObserverGame obs) {
        observersGame.add(obs);
    }

    public void notifyObserversGame() {
        for (IPlayerObserverGame obs : observersGame) {
            obs.updateBoard();
        }
    }

    @Override
    public String toString() {
        return "player : " + getColor();
    }

}
