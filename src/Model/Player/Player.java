package Model.Player;

import Model.Pieces.Piece;
import Model.Utils.Color2;
import java.util.ArrayList;

public class Player {
    private String name;
    private boolean urTurn = false;
    private Color2 color;
    private ArrayList<Piece> capturedPiece;
    private ArrayList<IPlayerObserver> observers;
    private ArrayList<IPlayerObserverGame> observersGame;

    public Player(Color2 color, String name) {
        this.name = name;
        this.color = color;
        if (color == Color2.WHITE)
            urTurn = true;
        observers = new ArrayList<IPlayerObserver>();
        observersGame = new ArrayList<IPlayerObserverGame>();
        capturedPiece = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
        notifyObserver();
    }
    public String getName() {
        return name;
    }
    public Color2 getColor() {
        return color;
    }
    public void setUrTurn(boolean urTurn) {
        this.urTurn = urTurn;
    }
    public boolean getUrTurn() {
        return urTurn;
    }
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPiece;
    }

    public void changeTurn() {
        urTurn = !urTurn;
    }
    public void addCapturedPiece(Piece piece) {
        capturedPiece.add(piece);
    }
    public void move(Piece piece, int newPos) {
        piece.setPosition(newPos);
        notifyObserverGame();
    }
    public boolean isWhite() {
        return color == Color2.WHITE;
    }

    public void addObserver(IPlayerObserver obs) {
        observers.add(obs);
    }
    public void notifyObserver() {
        for (IPlayerObserver obs : observers)
            obs.updatePlayerName();
    }
    public void addObserverGame(IPlayerObserverGame obs) {
        observersGame.add(obs);
    }
    public void notifyObserverGame() {
        for (IPlayerObserverGame obs : observersGame)
            obs.updateBoard();
    }
    public void notifyObserverCapturePiece() {
        for (IPlayerObserverGame obs : observersGame)
            obs.updateTakenPiecePanel();
    }

    @Override
    public String toString() {
        return "player : " + getColor();
    }



























































}
