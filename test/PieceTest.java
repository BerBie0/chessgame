
import Model.Pieces.*;
import Model.utils.Color2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//TODO mettre Model.Pieces.King en public pour les tests
class PieceTest {

    @DisplayName("Test Model.Pieces.Knight move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Knight(pos=21, newpos={0}).")
    @ValueSource(ints = { 33, 42 })
    void testValidMoveKnight(int position)
    {
        Knight knight = new Knight(Color2.WHITE, 21);
        Assertions.assertTrue(knight.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Knight move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Knight(pos=21, newpos={0}).")
    @ValueSource(ints = { 0, 2, 9, 13, 29, 31, 32, 22, 41, 23 })
    void testNoValidMoveKnight(int position)
    {
        Knight knight = new Knight(Color2.WHITE, 21);
        Assertions.assertFalse(knight.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Knight move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.Knight(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMoveKnight2(int position)
    {
        Knight knight = new Knight(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> knight.isValidMove(position));
    }


    @DisplayName("Test bishop move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Bishop(pos=82, newpos={0}).")
    @ValueSource(ints = { 71, 73, 64, 55, 46, 37, 28, 91, 93 })
    void testValidMoveBishop(int position)
    {
        Bishop bishop = new Bishop(Color2.WHITE, 82);
        Assertions.assertTrue(bishop.isValidMove(position));
    }

    @DisplayName("Test bishop move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Bishop(pos=82, newpos={0}).")
    @ValueSource(ints = { 72, 83, 92, 81, 60, 19, 115, 100 })
    void testNoValidMoveBishop(int position)
    {
        Bishop bishop = new Bishop(Color2.WHITE, 82);
        Assertions.assertFalse(bishop.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Bishop move not allowed 2")
    @ParameterizedTest(name = "for example, bishop(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMoveBishop2(int position)
    {
        Bishop bishop = new Bishop(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> bishop.isValidMove(position));
    }


    @DisplayName("Test Model.Pieces.Rook move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Rook(pos=44, newpos={0}).")
    @ValueSource(ints = { 34, 24, 43, 42, 41, 45, 46, 47, 48, 54, 64, 74, 84, 94 })
    void testValidMoveRook(int position)
    {
        Rook Rook = new Rook(Color2.WHITE, 44);
        Assertions.assertTrue(Rook.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Rook move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Rook(pos=44, newpos={0}).")
    @ValueSource(ints = { 33, 35, 53, 55, 40, 49, 14, 104 })
    void testNoValidMoveRook(int position)
    {
        Rook Rook = new Rook(Color2.WHITE, 44);
        Assertions.assertFalse(Rook.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Rook move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.Rook(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMoveRook2(int position)
    {
        Rook Rook = new Rook(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> Rook.isValidMove(position));
    }


    @DisplayName("Test Model.Pieces.Queen move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Queen(pos=44, newpos={0}).")
    @ValueSource(ints = { 33, 22, 34, 24, 35, 26, 43, 42, 41, 45, 46, 47, 48, 53, 62, 71, 54, 64, 74, 84, 94, 55, 66, 77, 88 })
    void testValidMoveQueen(int position)
    {
        Queen Queen = new Queen(Color2.WHITE, 44);
        Assertions.assertTrue(Queen.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Queen move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Queen(pos=44, newpos={0}).")
    @ValueSource(ints = { 40, 49, 14, 104, 11, 99, 80, 17, 32, 23, 25, 36, 56, 65, 63, 52 })
    void testNoValidMoveQueen(int position)
    {
        Queen Queen = new Queen(Color2.WHITE, 44);
        Assertions.assertFalse(Queen.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Queen move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.Queen(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMoveQueen2(int position)
    {
        Queen Queen = new Queen(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> Queen.isValidMove(position));
    }



    @DisplayName("Test Model.Pieces.King move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.King(pos=44, newpos={0}).")
    @ValueSource(ints = { 33, 34, 35, 43, 45, 53, 54, 55 })
    void testValidMoveKing(int position)
    {
        King King = new King(Color2.WHITE, 44);
        Assertions.assertTrue(King.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.King move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.King(pos=44, newpos={0}).")
    @ValueSource(ints = { 22, 23, 24, 25, 26, 36, 46, 56, 66, 65, 64, 63, 62, 52, 42, 32 })
    void testNoValidMoveKing(int position)
    {
        King King = new King(Color2.WHITE, 44);
        Assertions.assertFalse(King.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.King move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.King(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMoveKing2(int position)
    {
        King King = new King(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> King.isValidMove(position));
    }



    @DisplayName("Test Model.Pieces.Pawn move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=93, newpos={0}).")
    @ValueSource(ints = { 83, 73 })
    void testValidMovePawn(int position)
    {
        Pawn Pawn = new Pawn(Color2.WHITE, 93);
        Assertions.assertTrue(Pawn.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Pawn move not allowed 3")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=93, newpos={0}).")
    @ValueSource(ints = { 73 })
    void testValidMovePawn2(int position)
    {
        Pawn Pawn = new Pawn(Color2.WHITE, 93);
        Pawn.setHasMovedOnce(true);
        Assertions.assertFalse(Pawn.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Pawn move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=93, newpos={0}).")
    @ValueSource(ints = { 103, 91, 92, 82, 84, 94, 104, 102 })
    void testNoValidMovePawn(int position)
    {
        Pawn Pawn = new Pawn(Color2.WHITE, 93);
        Assertions.assertFalse(Pawn.isValidMove(position));
    }

    @DisplayName("Test Model.Pieces.Pawn move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMovePawn2(int position)
    {
        Pawn Pawn = new Pawn(Color2.WHITE, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> Pawn.isValidMove(position));
    }


    @DisplayName("Test black Model.Pieces.Pawn move allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=23, newpos={0}).")
    @ValueSource(ints = { 33, 43 })
    void testValidMovePawnB(int position)
    {
        Pawn Pawn = new Pawn(Color2.BLACK, 23);
        Assertions.assertTrue(Pawn.isValidMove(position));
    }

    @DisplayName("Test black Model.Pieces.Pawn move not allowed 3")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=23, newpos={0}).")
    @ValueSource(ints = { 43 })
    void testValidMovePawn2B(int position)
    {
        Pawn Pawn = new Pawn(Color2.BLACK, 23);
        Pawn.setHasMovedOnce(true);
        Assertions.assertFalse(Pawn.isValidMove(position));
    }

    @DisplayName("Test black Model.Pieces.Pawn move not allowed")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=23, newpos={0}).")
    @ValueSource(ints = { 12, 13, 14, 24, 34, 32, 22 })
    void testNoValidMovePawnB(int position)
    {
        Pawn Pawn = new Pawn(Color2.BLACK, 23);
        Assertions.assertFalse(Pawn.isValidMove(position));
    }

    @DisplayName("Test black Model.Pieces.Pawn move not allowed 2")
    @ParameterizedTest(name = "for example, Model.Pieces.Pawn(pos=21, newpos={0}).")
    @ValueSource(ints = { -1 })
    void testNoValidMovePawn2B(int position)
    {
        Pawn Pawn = new Pawn(Color2.BLACK, 21);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> Pawn.isValidMove(position));
    }


}