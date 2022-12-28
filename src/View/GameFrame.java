package View;

import Controller.GameController;
import Controller.GameManager;
import Model.Board.Board;
import Model.Move.Move;
import Model.Pieces.Piece;
import Model.Player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;


public class GameFrame extends JFrame
{
    private final static Dimension GLOBAL_DIM = new Dimension(800,800);
    private final static Dimension BOARD_PANEL_DIM = new Dimension(600,600);
    private final static Dimension TILE_PANEL_DIM = new Dimension(20,20);
    private final static String pieceIconPath = "img/";

    private final BoardPanel boardPanel;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final MoveLog moveLog;

    private static GameFrame gameFrame;

    private BoardDirection boardDirection;
    private int oldPos;
    private int newPos;
    private Move move;
    private Player wPlayer;
    private Player bPlayer;
    private Piece humanMovedPiece;
    private GameController gameController;
    private Board chessBoard;

    private GameFrame(Player wPlayer, Player bPlayer, Board board, GameController gameController)
    {
        //Fixme ERREUR DANS TAKENPIECE
        //TODO est un singleton
        super(" Chess Game GameFrame");
        this.wPlayer = wPlayer;
        this.bPlayer = bPlayer;
        this.chessBoard = board;
        this.gameController = gameController;
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(GLOBAL_DIM);
        this.setLocationRelativeTo(null);
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.setJMenuBar(tableMenuBar);
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog(chessBoard);
        this.boardDirection = BoardDirection.NORMAL;
        this.add(this.boardPanel, BorderLayout.CENTER);
        this.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.add(this.takenPiecesPanel, BorderLayout.WEST);


        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int clickedButton = JOptionPane.showConfirmDialog(GameFrame.this, "etes vous surs ?", "title", JOptionPane.YES_NO_OPTION);
                if(clickedButton == JOptionPane.YES_OPTION)
                {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    public static GameFrame createInstance(Player wPlayer, Player bPlayer, Board board, GameController gameController)
    {
        if( gameFrame == null )
        {
            gameFrame = new GameFrame(wPlayer, bPlayer, board, gameController);
        }
        return gameFrame;
    }

    private JMenuBar createTableMenuBar()
    {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferenceMenu());
        return  tableMenuBar;
    }

    private JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN file");
        fileMenu.add(openPGN);
        return fileMenu;
    }
    private JMenu createPreferenceMenu()
    {
        final JMenu preferenceMenu = new JMenu("Preference");
        final JMenuItem flipBoardMenuItem = new JMenuItem("flipBoard");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });
        preferenceMenu.add(flipBoardMenuItem);
        return preferenceMenu;
    }

    private class BoardPanel extends JPanel
    {
        final List<TilePanel> boardTiles;

        BoardPanel()
        {
            super( new GridLayout(8, 8));
            this.boardTiles = new LinkedList<>();
            for(int i = 21; i < 100; i+=10)
            {
                for (int j = 0; j < 8; j++)
                {
                    final TilePanel tilePanel = new TilePanel(this, i + j);
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIM);
            validate();
        }

        public void drawBoard(final Board board)
        {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles))
            {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    } // end BoardPanel class

    public static class MoveLog
    {
        private final List<Integer> positions;
        private final Board board;

        MoveLog(Board chessBoard)
        {
            this.positions = new ArrayList<>();
            this.board = chessBoard;
        }
        public List<Integer> getMoves()
        {
            return this.positions;
        }
        public void addMove(final Integer position)
        {
            this.positions.add(position);
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

    
    private class TilePanel extends JPanel
    {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId)
        {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIM);
            assignTileColor();
            assignTilePieceImg(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if( isRightMouseButton(e) )
                    {
                        oldPos = 0;
                        newPos = 0;
                        humanMovedPiece = null;
                    }
                    else if ( isLeftMouseButton(e) )
                    {
                        if(oldPos == 0)
                        {
                            try
                            {
                                oldPos = chessBoard.getPieceFromPosition(tileId).getPosition();
                            }
                            catch (Exception exception)
                            {
                                System.out.println("GameFrame.java : TilePanel(final BoardPanel boardPanel, final int tileId) : " + exception);
                            }
                            try
                            {
                                humanMovedPiece = chessBoard.getPieceFromPosition(oldPos);
                            }
                            catch (Exception exception)
                            {
                                System.out.println("GameFrame.java : TilePanel(final BoardPanel boardPanel, final int tileId) : " + exception);
                            }

                            if(humanMovedPiece == null)
                            {
                                oldPos = 0;
                            }
                        }
                        else
                        {
                            newPos = tileId;
                            try
                            {
                                gameController.execute(oldPos, newPos, humanMovedPiece, wPlayer, chessBoard);
                                //humanMovedPiece.setPosition(newPos);
                            }
                            catch (Exception exception)
                            {
                                System.out.println("GameFrame.java : TilePanel(final BoardPanel boardPanel, final int tileId) : " + exception);
                            }
                            moveLog.addMove(newPos);
                            oldPos = 0;
                            newPos = 0;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                //takenPiecesPanel.redo(moveLog, chessBoard);
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            validate();
        }

        private void assignTileColor()
        {
            int ligne = this.tileId / 10;
            if( ligne == 2
                || ligne == 4
                || ligne == 6
                || ligne == 8 )
                setBackground(this.tileId % 2 != 0 ? Color.WHITE : Color.GREEN);
            else
                setBackground(this.tileId % 2 == 0 ? Color.WHITE : Color.GREEN);
        }

        public void drawTile(final Board board)
        {
            assignTileColor();
            assignTilePieceImg(board);
            highLightLegalsMove(board);
            validate();
            repaint();
        }
        private void assignTilePieceImg(final Board board)
        {
            this.removeAll();
            if(board.isPositionOccupied(this.tileId))
            {
                try
                {
                    BufferedImage image = ImageIO.read(new File( pieceIconPath + board.getPieceFromPosition(this.tileId).toString() + ".gif" ) );
                    Image newImage = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                    ImageIcon imageIcon = new ImageIcon(newImage);
                    add(new JLabel( new ImageIcon(imageIcon.getImage())));

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        private void highLightLegalsMove(final Board board)
        {
            if(true)
            {
                for (final int position : pieceLegalMoves(board))
                {
                    if(position == this.tileId)
                    {
                        try
                        {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File(pieceIconPath + "greenDot.png")))));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private LinkedList<Integer> pieceLegalMoves(final Board board)
        {
            if(humanMovedPiece != null)
                return board.calculateLegalMoves(humanMovedPiece);
            return new LinkedList<>();
        }

    } // end TilePanel class

    public enum BoardDirection
    {
        NORMAL
                {
                    @Override
                    List<TilePanel> traverse(final List<TilePanel> boardTiles)
                    {
                        return boardTiles;
                    }
                    @Override
                    BoardDirection opposite()
                    {
                        return FLIPPED;
                    }
                },
        FLIPPED
                {
                    @Override
                    List<TilePanel> traverse(final List<TilePanel> boardTiles)
                    {
                        Collections.reverse(boardTiles);
                        return boardTiles;
                    }
                    @Override
                    BoardDirection opposite()
                    {
                        return NORMAL;
                    }
                };
        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    } // end enum Board direction


} // end GameFrame class


