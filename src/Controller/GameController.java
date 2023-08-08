package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Move.MoveLog;
import Model.Pieces.Piece;
import Model.Player.Player;
import Model.Utils.Color2;
import View.EndMenu;
import View.GameFrame2;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;


import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GameController {

    private final Board board;
    private final Player bPlayer;
    private final Player wPlayer;
    private final MoveLog moveLog;
    private final MoveFactory moveFactory;
    private Piece selectedPiece = null;

    public GameController(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog) {
        this.board = board;
        this.bPlayer = bPlayer;
        this.wPlayer = wPlayer;
        this.moveLog = moveLog;
        this.moveFactory = new MoveFactory(board);
    }

    public void setPlayerName(Player player, String name) {
        player.setName(name);
    }
    public boolean willMoveResultInCheck(Piece piece, int newPos) {
        Player playerTurn = wPlayer.getUrTurn() ? wPlayer : bPlayer;
        int oldPos = piece.getPosition();
        try {
            IMove move = moveFactory.createMove(newPos, oldPos, piece, playerTurn, board);
            move.execute();
            if ( board.isCheck(playerTurn.getColor()) ) {
                move.undo(moveLog);
                return true;
            } else {
                move.undo(moveLog);
                return false;
            }
        } catch (Exception exception) {
            return true;
        }
    }
    public boolean isAnyValidMove(Color2 color) {
        List<Piece> teamPieces = board.getPieces().stream().filter(piece -> piece.getColor() == color ).toList();
        for (Piece teamPiece : teamPieces) {
            for (int i = 21; i < 100; i += 10) {
                for (int j = 0; j < 8; j++) {
                    if ( !willMoveResultInCheck(teamPiece, i+j) ) {
                        return true;
                    }
                }
            }
        }
        return false;

    }
    public boolean isCheckMate(Color2 color) {
        return board.isCheck(color) && !isAnyValidMove(color);
    }

    public void game(MouseEvent e, int tileId, GameFrame2 gameFrame2) {
        Player playerTurn = wPlayer.getUrTurn() ? wPlayer : bPlayer;
        Player otherPlayer = wPlayer.getUrTurn() ? bPlayer : wPlayer;

        if (isRightMouseButton(e)) {
            selectedPiece = null;
            playerTurn.notifyObserverGame();
        } else if ( isLeftMouseButton(e) && selectedPiece == null ) {
            selectedPiece = board.getPieceFromPosition(tileId);
            if (selectedPiece.getColor() != playerTurn.getColor()) {
                selectedPiece = null;
                throw new IllegalArgumentException("not ur piece");
            }
            board.calculateLegalMoves(selectedPiece);

        } else if ( isLeftMouseButton(e) && selectedPiece != null ) {
            IMove move = moveFactory.createMove(tileId, selectedPiece.getPosition(), selectedPiece, playerTurn, board);
            move.execute();
            if ( board.isCheck(playerTurn.getColor()) ) {
                move.undo(moveLog);
                JOptionPane.showMessageDialog(null, "coup invalide cause echec");
                selectedPiece = null;
                return;
            }
            moveLog.addMove(move);
            selectedPiece = null;
            playerTurn.setUrTurn(false);
            otherPlayer.setUrTurn(true);
        }

        if (isCheckMate(otherPlayer.getColor())) {
            EndMenu endMenu = new EndMenu(playerTurn.getColor());
            gameFrame2.setVisible(false);
            endMenu.setVisible(true);
        }
    }


}
