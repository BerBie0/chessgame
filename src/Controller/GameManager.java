package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Pieces.King;
import Model.Pieces.Piece;
import Model.utils.Color2;
import Model.Player.Player;

import javax.swing.*;
import java.awt.event.MouseEvent;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GameManager {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final Board board;
    private final Player bPlayer;
    private final Player wPlayer;
    private int oldPos;
    private int newPos;
    private Piece movedPiece;
    private IMove lastMove;
    private final MoveFactory moveFactory;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameManager(Player wPlayer, Player bPlayer, Board board) {
        this.board = board;
        this.bPlayer = bPlayer;
        this.wPlayer = wPlayer;
        this.moveFactory = new MoveFactory(board);
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public Player getCurrentPlayer() {
        return wPlayer.isUrTurn() ? wPlayer : bPlayer;
    }

    public void setBlackTurn(boolean turn) {
        bPlayer.setUrTurn(turn);
    }

    public void setWhiteTurn(boolean turn) {
        wPlayer.setUrTurn(turn);
    }

    public void setPlayerName(String name, Player player) {
        if (player.isWhite()) {
            wPlayer.setName(name);
        } else {
            bPlayer.setName(name);
        }
    }

    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public boolean isWhitePlayer() {
        return wPlayer.isUrTurn();
    }

    public boolean isBlackPlayer() {
        return bPlayer.isUrTurn();
    }

    public boolean isCheckMate() {
        Color2 currentPlayerColor = getCurrentPlayer().getColor();
        King currentPlayerKing = (King) board.getKing(currentPlayerColor);
        return board.isCheck(currentPlayerColor) && !board.anyValidMove(currentPlayerColor);
    }

    public boolean isCurrentPlayerCheck() {
        return board.isCheck(board.getKing(getCurrentPlayer().getColor()).getColor());
    }

    //TODO
    public boolean isPat() {
        return false;
    }

    public IMove execute(int oldPos, int newPos, Piece piece, Player player, Board board) {
        IMove move = moveFactory.createMove(oldPos, newPos, piece, player, board);
        move.execute();
        lastMove = move;
        return move;
    }

    public void changeTurn() {
        this.setWhiteTurn(!this.isWhitePlayer());
        this.setBlackTurn(!this.isBlackPlayer());
    }

    public void undo() {
        lastMove.undo();
    }

    public void game(MouseEvent e, int tileId) {
        if (isRightMouseButton(e)) {
            oldPos = 0;
            newPos = 0;
            movedPiece = null;
            wPlayer.notifyObserversGame();
        } else if (isLeftMouseButton(e)) {
            //click game
            if (oldPos == 0) {
                //first click
                try {
                    oldPos = board.
                            getPieceFromPosition(tileId).getPosition();
                } catch (Exception exception) {
                    System.out.println("GameFrame.java : " + "Tile(final BoardPanel boardPanel, final int tileId)1 : " + exception);
                }
                try {
                    movedPiece = board.getPieceFromPosition(oldPos);
                } catch (Exception exception) {
                    System.out.println("GameFrame.java : " + "Tile(final BoardPanel boardPanel, final int tileId)2 : " + exception);
                }
                //update mvc
                if (movedPiece == null) {
                    oldPos = 0;
                } else {
                    if (movedPiece.getColor() != this.getCurrentPlayer().getColor()) {
                        oldPos = 0;
                        JOptionPane.showMessageDialog(null, "pas votre tour");
                    } else {
                        board.calculateLegalMoves(movedPiece);
                    }
                }
            } else {
                //second click
                newPos = tileId;
                try {
                    //update mvc
                    IMove move = this.execute(oldPos, newPos, movedPiece, this.getCurrentPlayer(), board);
                    if ( board.isCheck(this.getCurrentPlayer().getColor()) ) {
                        this.undo();
                        JOptionPane.showMessageDialog(null, "coup invalide cause echec");
                        oldPos = 0;
                        newPos = 0;
                        movedPiece = null;
                        return;
                    }
                    //moveLog.addMove(move);
                    //gameHistoryPanel.redo(gameManager.getBoard(), moveLog);
                    //takenPiecesPanel.redo();
                    oldPos = 0;
                    newPos = 0;
                    movedPiece = null;
                    this.changeTurn();
                } catch (Exception exception) {
                    System.out.println("GameFrame.java : Tile(final BoardPanel boardPanel, final int tileId)3 : " + exception);
                }

            }
        }
    }
}
