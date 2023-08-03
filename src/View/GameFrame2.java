package View;

import Controller.GameController;
import Model.Board.Board;
import Model.Board.IBoardObserver;
import Model.Move.IObserverMoveLog;
import Model.Move.MoveLog;
import Model.Player.IPlayerObserverGame;
import Model.Player.Player;
import Model.Utils.Color2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class GameFrame2 extends JFrame implements IBoardObserver, IPlayerObserverGame, IObserverMoveLog {
    private final static Dimension GLOBAL_DIM = new Dimension(800, 800);
    private final static Dimension BOARD_PANEL_DIM = new Dimension(600, 600);
    private final static Dimension TILE_PANEL_DIM = new Dimension(20, 20);
    private final static String pieceIconPath = "src/img/";
    private final static Color whiteTile = new Color(240,217,181);
    private final static Color blackTile = new Color(180,136,99);
    private final BoardPanel boardPanel;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final MoveLog moveLog;
    private static GameFrame2 gameFrame2;
    private GameController gm;
    private Player wPlayer;
    private Player bPlayer;
    private Board board;
    private BoardDirection2 boardDirection;

    private GameFrame2(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog, GameController gm) {
        super("chess game 2 players");
        this.gm = gm;
        this.wPlayer = wPlayer;
        this.bPlayer = bPlayer;
        this.board = board;
        this.moveLog = moveLog;
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(GLOBAL_DIM);
        this.setLocationRelativeTo(null);
        JMenuBar jMenuBar = new Menu();
        this.setJMenuBar(jMenuBar);
        this.gameHistoryPanel = new GameHistoryPanel(wPlayer.getName(), bPlayer.getName());
        this.takenPiecesPanel = new TakenPiecesPanel(wPlayer.getCapturedPieces(), bPlayer.getCapturedPieces());
        boardPanel = new BoardPanel();
        this.add(this.boardPanel, BorderLayout.CENTER);
        this.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.add(this.takenPiecesPanel, BorderLayout.WEST);
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

    public static GameFrame2 createInstance(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog, GameController gm) {
        if (gameFrame2 == null)
            gameFrame2 = new GameFrame2(wPlayer, bPlayer, board, moveLog, gm);
        return gameFrame2;
    }


    @Override
    public void updateBoardAndLegalMoves() {
        boardPanel.drawBoardAndLegalMoves(board.getBoard());

    }

    @Override
    public void updateGameHistoryPanel() {
        gameHistoryPanel.redo(moveLog);
    }

    @Override
    public void updateBoard() {
        boardPanel.drawBoard(board.getBoard());
    }

    @Override
    public void updateTakenPiecePanel() {
        takenPiecesPanel.redo();
    }

    private class Menu extends JMenuBar {
        public Menu() {
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.add(tableMenuBar);
        }
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
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(board);
                validate();
                repaint();
            });
            preferenceMenu.add(flipBoardMenuItem);
            return preferenceMenu;
        }
    } //end class Menu

    private class BoardPanel extends JPanel {

        final List<Tile> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            boardTiles = new LinkedList<>();
            for (int i = 21; i < 100; i += 10) {
                for (int j = 0; j < 8; j++) {
                    final Tile tile = new Tile(i + j);
                    this.boardTiles.add(tile);
                    this.add(tile);
                }
            }
            setPreferredSize(BOARD_PANEL_DIM);
            validate();
        }
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
    } // end BoardPanel

    private class Tile extends JPanel {
        private final int tileId;
        Tile(final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIM);
            assignTileColor();
            assignTilePieceImg(board.getBoard());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    clickTile(e, tileId);
                }
            });
        }
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
                java.net.URL imageURL = getClass().getClassLoader().getResource(board.getPieceFromPosition(this.tileId).toString() + ".gif");
                if (imageURL != null) {
                    ImageIcon icon = new ImageIcon(imageURL);
                    add(new JLabel(icon));
                }
            }
        }
        public void assignCheckToTile(final Board board) {
            if (board.getBoard().isCheck(Color2.WHITE)) {
                if (board.getKingPosition(Color2.WHITE) == this.tileId) {
                    java.net.URL imageURL = getClass().getClassLoader().getResource("redDot.png");
                    if (imageURL != null) {
                        ImageIcon icon = new ImageIcon(imageURL);
                        add(new JLabel(new ImageIcon(icon.getImage())));
                    }
                }
            } else if (board.getBoard().isCheck(Color2.BLACK)) {
                if (board.getKingPosition(Color2.BLACK) == this.tileId) {
                    java.net.URL imageURL = getClass().getClassLoader().getResource("redDot.png");
                    if (imageURL != null) {
                        ImageIcon icon = new ImageIcon(imageURL);
                        add(new JLabel(new ImageIcon(icon.getImage())));
                    }
                }
            }
        }

        private void highLightLegalsMove(final Board board) {
            if (!board.getBoard().getHighLightMove().isEmpty()) {
                for (final int position : board.getBoard().getHighLightMove()) {
                    if (position == this.tileId) {
                        java.net.URL imageURL = getClass().getClassLoader().getResource("greenDot.png");
                        if (imageURL != null) {
                            ImageIcon icon = new ImageIcon(imageURL);
                            add(new JLabel(new ImageIcon(icon.getImage())));
                        }
                    }
                }
            }
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceImg(board);
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

        public void clickTile(MouseEvent e, int tileId) {
            gm.game(e, tileId, gameFrame2);
        }
    }// end Tile

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
