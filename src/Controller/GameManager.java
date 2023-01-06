package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Pieces.Knight;
import Model.utils.MoveLog;
import Model.Pieces.Piece;
import Model.utils.Color2;
import Model.Player.Player;
import View.EndMenu;
import View.GameFrame2;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GameManager {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final Board board;
    private final Player bPlayer;
    private final Player wPlayer;
    private final MoveLog moveLog;
    private final MoveFactory moveFactory;
    private IMove lastMove;
    private int oldPos;
    private int newPos;
    private Piece movedPiece;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameManager(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog) {
        this.board = board;
        this.bPlayer = bPlayer;
        this.wPlayer = wPlayer;
        this.moveLog = moveLog;
        this.moveFactory = new MoveFactory();
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
        return board.isCheck(currentPlayerColor) && !anyValidMove(currentPlayerColor);
    }

    //TODO fonction isPat
    public boolean isPat() {
        return false;
    }

    public IMove execute(int oldPos, int newPos, Piece piece, Player player, Board board) {
        IMove move = moveFactory.createMove(oldPos, newPos, piece, player, board);
        move.execute();
        lastMove = move;
        return move;
    }

    public void undo(MoveLog moveLog) {
        lastMove.undo(moveLog);
    }

    public void changeTurn() {
        this.setWhiteTurn(!this.isWhitePlayer());
        this.setBlackTurn(!this.isBlackPlayer());
    }

    //TODO anyValidMove ajouter dnas le if si une piece peut se mettre sur le chemin de ma piece qui met le roi echec
    public boolean anyValidMove(Color2 color) {
        /*
        List<Piece> teamPieces = pieces.stream().filter(piece -> piece.getColor() == color ).toList();
        List<Piece> ennemyPieces = pieces.stream().filter(piece -> piece.getColor() != color && isPathFree(piece, getKingPosition(color))).toList();
        for (Piece teamPiece : teamPieces) {
            for (Piece ennemyPiece : ennemyPieces) {
                if ( !willMoveResultInCheck(teamPiece, ennemyPiece.getPosition()) && teamPiece.canCapturePiece(ennemyPiece) ) {
                    System.out.println("heyy");
                    return true;
                }
            }
        }
        return false;
         */

        List<Piece> teamPieces = board.getPieces().stream().filter(piece -> piece.getColor() == color ).toList();
        for (Piece teamPiece : teamPieces) {
            for (int i = 21; i < 100; i += 10) {
                for (int j = 0; j < 8; j++) {
                    if ( !willMoveResultInCheck(teamPiece, i) ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean willMoveResultInCheck(Piece piece, int newPos2) {
        int oldPos2 = piece.getPosition();
        try {
            IMove move = this.execute(oldPos2, newPos2, piece, this.getCurrentPlayer(), board);
            if ( board.isCheck(this.getCurrentPlayer().getColor()) ) {
                this.undo(moveLog);
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
    }

    public void game(MouseEvent e, int tileId, GameFrame2 gameFrame2) {
        if ( isCheckMate() || isPat() ) {
            System.out.println("echec et mat");
            EndMenu endMenu = new EndMenu();
            gameFrame2.setVisible(false);
            endMenu.setVisible(true);
            return;
        }
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
                    System.out.println("GameFrame.java : "+ "Tile(final BoardPanel boardPanel, final int tileId)1 : " + exception);
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
                    //si le coup du joueur actuel le met en echec
                    if ( board.isCheck(this.getCurrentPlayer().getColor()) ) {
                        this.undo(moveLog);
                        JOptionPane.showMessageDialog(null, "coup invalide cause echec");
                        oldPos = 0;
                        newPos = 0;
                        movedPiece = null;
                        return;
                    }
                    moveLog.addMove(move);
                    oldPos = 0;
                    newPos = 0;
                    movedPiece = null;
                    this.changeTurn();
                    if ( isCheckMate() || isPat() ) {
                        System.out.println("echec et mat");
                        EndMenu endMenu = new EndMenu();
                        gameFrame2.setVisible(false);
                        endMenu.setVisible(true);
                    }
                } catch (Exception exception) {
                    System.out.println("GameFrame.java : Tile(final BoardPanel boardPanel, final int tileId)3 : " + exception);
                }
            }
        }
    }
}
