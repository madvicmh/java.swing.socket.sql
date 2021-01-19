package usantatecla.tictactoe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import usantatecla.utils.Console;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class BoardTest {

    BoardBuilder boardBuilder;

    @BeforeEach
    public void beforeEach() {
        this.boardBuilder = new BoardBuilder();
    }

    @Test
    public void testGivenEmptyBoardWhenStartThenIsEmpty() {
        Board board = this.boardBuilder.build();
        for (int i = 0; i < Coordinate.DIMENSION; i++) {
            for (int j = 0; j < Coordinate.DIMENSION; j++) {
                assertThat(board.isEmpty(new Coordinate(i, j)), is(true));
            }
        }
    }

    @Test
    public void testGivenNewBoardWhenPutNewTokenThenIsOccupiedIsTrue() {
        Board board = this.boardBuilder.build();
        Color color = Color.O;
        Coordinate coordinate = new Coordinate(0, 0);
        board.put(coordinate, color);
        assertThat(board.isOccupied(coordinate, color), is(true));
    }

    @Test
    public void testGivenBoardWhenMoveXTokenOriginIsEmptyAndTargetIsOccupiedThenIsTrue() {
        Board board = this.boardBuilder.rows(
                "X  ",
                "   ",
                "   ").build();
        Color color = Color.X;
        Coordinate origin = new Coordinate(0, 0);
        Coordinate target = new Coordinate(0, 1);
        board.move(origin, target);
        assertThat(board.isEmpty(origin), is(true));
        assertThat(board.isOccupied(target, color), is(true));
    }

    @Test
    public void testGivenBoardWhenMoveXTokenAndTargetIsOccupiedThenIsAssertion() {
        Board board = this.boardBuilder.rows(
                "XO ",
                "   ",
                "   ").build();
        Coordinate origin = new Coordinate(0, 0);
        Coordinate target = new Coordinate(0, 1);
        Assertions.assertThrows(AssertionError.class, () -> board.move(origin, target));
    }

    @Test
    public void testGivenBoardWhenMoveTokenAndOriginIsEmptyThenIsAssertion() {
        Board board = this.boardBuilder.rows(
                "   ",
                "   ",
                "   ").build();
        Coordinate origin = new Coordinate(1, 0);
        Coordinate target = new Coordinate(2, 2);
        Assertions.assertThrows(AssertionError.class, () -> board.move(origin, target));
    }

    @Test
    public void testGivenBoardWhenMoveTokenAndOriginIsEqualsTargetThenIsAssertion() {
        Board board = this.boardBuilder.rows(
                "X  ",
                "   ",
                "   ").build();
        Coordinate origin = new Coordinate(0, 0);
        Coordinate target = new Coordinate(0, 0);
        Assertions.assertThrows(AssertionError.class, () -> board.move(origin, target));
    }

    @Test
    public void testGivenEmptyBoardWhenCheckIsOccupiedThenIsFalse() {
        Board board = this.boardBuilder.build();
        assertThat(board.isOccupied(new Coordinate(0, 0), Color.X), is(false));
    }

    @Test
    public void testGivenBoardWhenCheckIsOccupiedThenIsTrue() {
        Board board = this.boardBuilder.rows(
                "X  ",
                "   ",
                "   ").build();
        assertThat(board.isOccupied(new Coordinate(0, 0), Color.X), is(true));
    }

    @Test
    public void testGivenBoardWhenCheckNullCoordinateIsOccupiedThenAssertionError() {
        Board board = this.boardBuilder.build();
        Assertions.assertThrows(AssertionError.class, () -> board.isOccupied(new Coordinate(), Color.O));
    }

    @Test
    public void testGivenBoardWhenIsTicTacToeThenIsFalse() {
        Board board = this.boardBuilder.build();
        assertThat(board.isTicTacToe(Color.O), is(false));
    }

    @Test
    public void testGivenBoardWhenIsTicTacToeNullColorThenAssertionError() {
        Board board = this.boardBuilder.build();
        Assertions.assertThrows(AssertionError.class, () -> board.isTicTacToe(Color.NULL));
    }

    //TODO vertical diagonal inversa
    @Test
    public void testGivenBoardWhenIsTicTacToeThenIsTrue() {
        Board board = this.boardBuilder.rows(
                " X ",
                "OXO",
                " X ").build();
        Color color = Color.X;
        assertThat(board.isTicTacToe(color), is(true));
    }

    @Test
    public void testGivenCompleteBoardAndIsTicTacToeThenIsFalse() {
        Board board = this.boardBuilder.rows(
                "XO ",
                "XO ",
                "OX ").build();
        Color token = Color.O;
        assertThat(board.isTicTacToe(token), is(false));
    }

    @Test
    public void testGivenEmptyBoardWhenWriteThenPrint() {
        Console console = mock(Console.class);
        try (MockedStatic<Console> staticConsole = mockStatic(Console.class)) {
            staticConsole.when(Console::getInstance).thenReturn(console);
            Board board = this.boardBuilder.build();
            board.write();
            verify(console, times(2)).writeln("---------------");
            verify(console, times(Coordinate.DIMENSION * Coordinate.DIMENSION + Coordinate.DIMENSION)).write(" | ");
            verify(console, times(Coordinate.DIMENSION)).writeln();
            verify(console, times(Coordinate.DIMENSION * Coordinate.DIMENSION)).write(" ");
        }
    }

    //TODO ArgumentCaptor
    @Test
    public void testGivenCompleteBoardWhenWriteThenPrintInCorrectOrder() {
        Console console = mock(Console.class);
        try (MockedStatic<Console> staticConsole = mockStatic(Console.class)) {
            staticConsole.when(Console::getInstance).thenReturn(console);
            InOrder colorPrinted = inOrder(console);
            Board board = this.boardBuilder.rows(
                    "X X",
                    "XO ",
                    "O O").build();
            board.write();
            verify(console, times(2)).writeln("---------------");
            verify(console, times(Coordinate.DIMENSION * Coordinate.DIMENSION + Coordinate.DIMENSION)).write(" | ");
            verify(console, times(Coordinate.DIMENSION)).writeln();
            colorPrinted.verify(console).write("X");
            colorPrinted.verify(console).write(" ");
            colorPrinted.verify(console).write("X");
            colorPrinted.verify(console).write("X");
            colorPrinted.verify(console).write("O");
            colorPrinted.verify(console).write(" ");
            colorPrinted.verify(console).write("O");
            colorPrinted.verify(console).write(" ");
            colorPrinted.verify(console).write("O");
        }
    }
}
