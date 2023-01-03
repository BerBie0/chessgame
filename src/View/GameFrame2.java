package View;

import Controller.GameController;
import Model.Board.Board;
import Model.Board.IBoardObserver;
import Model.Pieces.Piece;
import Model.Player.IPlayerObserver;
import Model.Player.IPlayerObserverGame;
import Model.utils.Color2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GameFrame2 extends JFrame implements IBoardObserver, IPlayerObserverGame {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final static Dimension GLOBAL_DIM = new Dimension(800, 800);
    private final static Dimension BOARD_PANEL_DIM = new Dimension(600, 600);
    private final static Dimension TILE_PANEL_DIM = new Dimension(20, 20);
    private final static String pieceIconPath = "img/";
    private final static Color whiteTile = new Color(240,217,181);
    private final static Color blackTile = new Color(180,136,99);

    private final BoardPanel boardPanel;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final MoveLog moveLog;

    private static GameFrame2 gameFrame2;

    private GameController gameController;

    private BoardDirection2 boardDirection;
    private int oldPos;
    private int newPos;
    private Piece movedPiece;

    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    private GameFrame2(GameController gameController) {
        super("chess game 2 players");
        this.gameController = gameController;
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(GLOBAL_DIM);
        this.setLocationRelativeTo(null);
        JMenuBar jMenuBar = new Menu();
        this.setJMenuBar(jMenuBar);
        this.gameHistoryPanel = new GameHistoryPanel(gameController);
        this.takenPiecesPanel = new TakenPiecesPanel();
        boardPanel = new BoardPanel();
        this.moveLog = new MoveLog(gameController.getBoard());
        this.add(this.boardPanel, BorderLayout.CENTER);
        this.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.add(this.takenPiecesPanel, BorderLayout.SOUTH);
        this.boardDirection = BoardDirection2.NORMAL;
        this.add(boardPanel, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int clickedButton = JOptionPane.showConfirmDialog(GameFrame2.this, "etes vous surs ?", "title", JOptionPane.YES_NO_OPTION);
                if (clickedButton == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    public static GameFrame2 createInstance(GameController gameController) {
        if (gameFrame2 == null)
            gameFrame2 = new GameFrame2(gameController);
        return gameFrame2;
    }


    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/

    @Override
    public void updateBoard() {
        boardPanel.drawBoard(gameController.getBoard());
    }

    @Override
    public void updateBoardAndLegalMoves() {
        boardPanel.drawBoardAndLegalMoves(gameController.getBoard());
    }
    /*------------------------------------------------METHOD----------------------------------------------------------*/
    /*-----------------------------------------------CLASSE INTERNE---------------------------------------------------*/

    private class Menu extends JMenuBar {
        /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
        /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

        public Menu() {
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.add(tableMenuBar);
        }

        /*---------------------------------------------GET SET------------------------------------------------------------*/
        /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
        /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
        /*------------------------------------------------METHOD----------------------------------------------------------*/

        private JMenuBar createTableMenuBar() {
            final JMenuBar tableMenuBar = new JMenuBar();
            tableMenuBar.add(createFileMenu());
            tableMenuBar.add(createPreferenceMenu());
            return tableMenuBar;
        }

        private JMenu createFileMenu() {
            final JMenu fileMenu = new JMenu("File");
            final JMenuItem openPGN = new JMenuItem("Load PGN file");
            fileMenu.add(openPGN);
            return fileMenu;
        }

        private JMenu createPreferenceMenu() {
            final JMenu preferenceMenu = new JMenu("Preference");
            final JMenuItem flipBoardMenuItem = new JMenuItem("flipBoard");
            flipBoardMenuItem.addActionListener(e -> {
                //boardDirection = boardDirection.opposite();
                //boardPanel.drawBoard(chessBoard);
            });
            preferenceMenu.add(flipBoardMenuItem);
            return preferenceMenu;
        }
    } //end class Menu


    private class BoardPanel extends JPanel {
        /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

        private final static Dimension BOARD_PANEL_DIM = new Dimension(600, 600);

        final List<Tile> boardTiles;

        /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

        BoardPanel() {
            super(new GridLayout(8, 8));
            boardTiles = new LinkedList<>();
            for (int i = 21; i < 100; i += 10) {
                for (int j = 0; j < 8; j++) {
                    final Tile tile = new Tile(this, i + j);
                    this.boardTiles.add(tile);
                    this.add(tile);
                }
            }
            setPreferredSize(BOARD_PANEL_DIM);
            validate();
        }

        /*---------------------------------------------GET SET------------------------------------------------------------*/
        /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
        /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/

        public void drawBoardAndLegalMoves(Board board) {
            removeAll();
            for (final Tile tile : boardDirection.traverse(boardTiles)) {
                tile.drawTileAndLegalMoves(board);
                add(tile);
            }
            validate();
            repaint();
        }

        public void drawBoard(Board board) {
            removeAll();
            for (final Tile tile : boardDirection.traverse(boardTiles)) {
                tile.drawTile(board);
                add(tile);
            }
            validate();
            repaint();
        }


        /*------------------------------------------------METHOD----------------------------------------------------------*/


    } // end BoardPanel

    private class Tile extends JPanel {
        /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

        private final static Dimension TILE_PANEL_DIM = new Dimension(20, 20);
        private final static String pieceIconPath = "img/";

        private final int tileId;

        /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

        Tile(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIM);
            assignTileColor();
            assignTilePieceImg(gameController.getBoard());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    clickTile(e);
                }
            });
        }

        /*---------------------------------------------GET SET------------------------------------------------------------*/
        /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
        /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
        /*------------------------------------------------METHOD----------------------------------------------------------*/

        public void assignTileColor() {
            int ligne = this.tileId / 10;
            if (ligne == 2
                    || ligne == 4
                    || ligne == 6
                    || ligne == 8)
                setBackground(this.tileId % 2 != 0 ? whiteTile : blackTile);
            else
                setBackground(this.tileId % 2 == 0 ? whiteTile : blackTile);
        }

        public void assignTilePieceImg(final Board board) {
            this.removeAll();
            if (board.isPositionOccupied(this.tileId)) {
                try {
                    BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getPieceFromPosition(this.tileId).toString() + ".gif"));
                    Image newImage = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                    ImageIcon imageIcon = new ImageIcon(newImage);
                    add(new JLabel(new ImageIcon(imageIcon.getImage())));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void assignCheckToTile(final Board board) {
            if (gameController.getBoard().isCheck(Color2.WHITE)) {
                if (board.getKingPosition(Color2.WHITE) == this.tileId) {
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File(pieceIconPath + "redDot.png")))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (gameController.getBoard().isCheck(Color2.BLACK)) {
                if (board.getKingPosition(Color2.BLACK) == this.tileId) {
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File(pieceIconPath + "redDot.png")))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceImg(board);
            //highLightLegalsMove(board);
            assignCheckToTile(board);
            validate();
            repaint();
        }

        public void drawTileAndLegalMoves(final Board board) {
            assignTileColor();
            assignTilePieceImg(board);
            highLightLegalsMove(board);
            assignCheckToTile(board);
            validate();
            repaint();
        }

        public void clickTile(MouseEvent e) {
            //TODO ajouter les observers
            //TODO apres les catch reset des coups valide ?
            if (isRightMouseButton(e)) {
                //reset selected piece
                oldPos = 0;
                newPos = 0;
                movedPiece = null;
                //update mvc
                boardPanel.drawBoard(gameController.getBoard());

            } else if (isLeftMouseButton(e)) {
                //click game
                if (oldPos == 0) {
                    //first click
                    //TODO 1 seul try catch
                    try {
                        oldPos = gameController.getBoard().
                                getPieceFromPosition(tileId).getPosition();
                    } catch (Exception exception) {
                        System.out.println("GameFrame.java : " + "Tile(final BoardPanel boardPanel, final int tileId)1 : " + exception);
                    }
                    try {
                        movedPiece = gameController.getBoard().getPieceFromPosition(oldPos);
                    } catch (Exception exception) {
                        System.out.println("GameFrame.java : " + "Tile(final BoardPanel boardPanel, final int tileId)2 : " + exception);
                    }
                    //update mvc
                    if (movedPiece == null) {
                        oldPos = 0;
                    } else {
                        if (movedPiece.getColor() != gameController.getCurrentPlayer().getColor()) {
                            oldPos = 0;
                            JOptionPane.showMessageDialog(null, "pas votre tour");
                        } else {
                            gameController.getBoard().calculateLegalMoves(movedPiece);
                        }
                    }
                } else {
                    //second click
                    newPos = tileId;
                    try {
                        //update mvc
                        gameController.execute(oldPos, newPos, movedPiece, gameController.getCurrentPlayer(), gameController.getBoard());
                        if ( gameController.getBoard().isCheck(gameController.getCurrentPlayer().getColor()) ) {
                            gameController.undo();
                            JOptionPane.showMessageDialog(null, "coup invalide cause echec");
                            oldPos = 0;
                            newPos = 0;
                            movedPiece = null;
                            return;
                        }
                        moveLog.addMove(newPos);
                        gameHistoryPanel.redo(gameController.getBoard(), moveLog);
                        oldPos = 0;
                        newPos = 0;
                        movedPiece = null;
                        gameController.changeTurn();
                        //movedPiece.setPosition(tileId);
                    } catch (Exception exception) {
                        System.out.println("GameFrame.java : Tile(final BoardPanel boardPanel, final int tileId)3 : " + exception);
                    }

                }
            }
        }

        private void highLightLegalsMove(final Board board) {
            if (!gameController.getBoard().getHighLightMove().isEmpty()) {
                for (final int position : gameController.getBoard().getHighLightMove()) {
                    if (position == this.tileId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File(pieceIconPath + "greenDot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }// end Tile

    public static class MoveLog
    {
        private final List<Integer> positions;
        private final Board board;

        MoveLog(Board board)
        {
            this.positions = new ArrayList<>();
            this.board = board;
        }
        public List<Integer> getMoves()
        {
            return this.positions;
        }
        public void addMove(final Integer position)
        {
            this.positions.add(position);
            System.out.println(positions);
        }

        public int size()
        {
            return this.positions.size();
        }
        public void clear()
        {
            this.positions.clear();
        }
        public boolean removeMove(final Integer position)
        {
            return this.positions.remove(position);
        }

    } // end MoveLog

    public enum BoardDirection2 {
        NORMAL {
            @Override
            List<Tile> traverse(final List<Tile> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection2 opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<Tile> traverse(final List<Tile> boardTiles) {
                Collections.reverse(boardTiles);
                return boardTiles;
            }

            @Override
            BoardDirection2 opposite() {
                return NORMAL;
            }
        };

        abstract List<Tile> traverse(final List<Tile> boardTiles);

        abstract BoardDirection2 opposite();
    } // end BoardDirection2

}
