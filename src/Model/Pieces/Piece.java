package Model.Pieces;

import Model.utils.Color2;

/**
 * Parent class Model.Pieces.Piece which describe common attributes with children class
 */
public abstract class Piece
{

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    // TODO ecrire isvalid dans les enfants ou dans la classe parent ? parent : override pion cavalier et roi
    // TODO context dans le constructeur enfant ?
    // TODO retirer fonction inutile

    /**Model.Pieces.Piece's color*/
    private final Color2 color;
    /**Model.Pieces.Piece's position in the board*/
    private int position;
    /**Model.Pieces.Piece's offset movement */
    private StrategyMovement context;
    /**Model.Pieces.Piece's value*/
    private float value;
    /**Model.Pieces.Piece's code : 1 : Model.Pieces.Pawn, 2 : Model.Pieces.Knight, 3 : Model.Pieces.Bishop, 4 : Model.Pieces.Rook, 5 : Model.Pieces.Queen, 6 : Model.Pieces.King, Negative for black piece*/
    private int pieceCode;
    /**
     * static 10x12 array, describe the board which is initialised one time at the creation of the first piece,
     * 0 : empty position, -10 : non reachable position.
     */
    public static int[] board;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * Model.Pieces.Piece's constructor's which initialise a board.
     * @param color piece's color.
     * @param position piece's position in the board.
     * @param value piece's value.
     * @param pieceCode piece's value.
     */
    public Piece(Color2 color, int position, float value, int pieceCode)
    {
        this.color = color;
        this.position = position;
        this.value = value;
        this.pieceCode = pieceCode;
        //initialise array of 10x12 elems, need to validate move.
        board = new int[120];
        for(int i = 0; i < 120; i++)
        {
            board[i] = (i < 20 || i > 100 || i % 10 == 9 || i % 10 == 0) ? -10 : 0;
        }
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/

    /**
     * position's getter.
     * @return position of the piece.
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * position's setter, set the position of a piece if the new position is validated,
     * throw exception if move isn't valid.
     * @param position position in the board.
     */
    public void setPosition( int position )
    {
        //TODO deja verifier ?
        /*
        if(position < 0)
            throw new IllegalArgumentException("Model.Pieces.Piece.java : setPosition(int position) : position < 0");
        if ( board[position] == -10 )
            throw new IllegalArgumentException("Model.Pieces.Piece.java : setPosition(int position) : position = -10");
        if ( !isValidMove(position) )
            throw new IllegalArgumentException("Model.Pieces.Piece.java : setPosition(int position) : move not valid");
        */
        this.position = position;

    }

    /**
     * strategies setter.
     * @param context the new strategy to apply.
     */
    public void setStrategy(StrategyMovement context)
    {
        this.context = context;
    }

    /**
     * color's getter.
     * @return piece's color.
     */
    public Color2 getColor()
    {
        return color;
    }

    /**
     * value's setter.
     * @param value value of the piece.
     */
    public void setValue(float value)
    {
        this.value = value;
    }
    public void setPieceCode(int value)
    {
        this.value = value;
    }

    /**
     * pieceCode's getter.
     * @return pieceCode of the piece.
     */
    public int getPieceCode()
    {
        return pieceCode;
    }

    /**
     * value's getter.
     * @return value of the piece.
     */
    public float getValue()
    {
        return value;
    }



    /*-----------------------------------------------METHODES---------------------------------------------------------*/

    /**
     * execute a piece's strategy movement according to the context.
     * @return an array of offset that describe possible direction move.
     */
    public int[] executeStrategy()
    {
        return context.offset();
    }

    /**
     * valid a move, calculate all possible move.
     * @param position piece's position in the board.
     * @return true if the move is valid, false else.
     */

    public boolean isWhite()
    {
        return color == Color2.WHITE;
    }

    public boolean canCapturePiece(Piece piece)
    {
        if(piece.getColor() == this.color)
            throw new IllegalArgumentException("Model.Pieces.Piece.java : canCapturePiece : trying to capture ur own piece ");
        if(piece.getPosition() == this.getPosition())
            return false;
        if ( !this.isValidMove(piece.getPosition()) )
            return false;
        return true;
    }



    /*-----------------------------------------------ABSTRACT---------------------------------------------------------*/

    public abstract boolean isValidMove(int position);

    public static void main(String[] args) {
        King b = new King(Color2.WHITE, 44);
        System.out.println(b.isValidMove(52));
    }

}
