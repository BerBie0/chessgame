import Controller.GameController;
import Controller.GameManager;
import Model.Board.Board;
import Model.Player.Player;
import Model.utils.Color2;

public class ChessMain
{
    public static void main(String[] args)
    {
        //model
        Player wPlayer = new Player(Color2.WHITE, "nom1");
        Player wBlayer = new Player(Color2.BLACK, "nom2");
        Board board = new Board();
        board.inializeBoard();

        //controller
        GameController gameController = new GameController();

        //view

    }
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/
}
