package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Move.MoveLog;
import Model.Pieces.Piece;
import Model.Player.Player;
import View.GameFrame2;

import java.awt.event.MouseEvent;

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

    public void game(MouseEvent e, int tileId, GameFrame2 gameFrame2) {
        Player playerTurn = wPlayer.getUrTurn() ? wPlayer : bPlayer;

        if (isRightMouseButton(e)) {
            System.out.println("reset droit");
            selectedPiece = null;
            playerTurn.notifyObserverGame();
        } else if ( isLeftMouseButton(e) && selectedPiece == null ) {
            System.out.println("premier click");
            selectedPiece = board.getPieceFromPosition(tileId);
            if (selectedPiece.getColor() != playerTurn.getColor()) {
                selectedPiece = null;
                throw new IllegalArgumentException("not ur piece");
            }
            board.calculateLegalMoves(selectedPiece);

        } else if ( isLeftMouseButton(e) && selectedPiece != null ) {
            System.out.println("deuxieme click" + selectedPiece);
            IMove move = moveFactory.createMove(tileId, selectedPiece.getPosition(), selectedPiece, playerTurn, board);
            move.execute();
        }
    }


}
