/*
    Author: Dawid Lewandowski
    Title: Gra w Szachy
    Wymagania: UTF-8 - do prawidłowego wyświetlania symboli figur szachowych, kolorów planszy itp
*/

import java.util.Scanner;

public class S26462_p01 {
    // szachownica
    private static String[][] ChessBoard;

    // definicje figur szachowych
    private static String WhiteKing = "\u001B[34m\u2654  ";
    private static String WhiteQueen = "\u001B[34m\u2655  ";
    private static String WhiteRook1 = "\u001B[34m\u2656  ";
    private static String WhiteRook2 = "\u001B[34m\u2656  ";
    private static String WhiteBishop1 = "\u001B[34m\u2657  ";
    private static String WhiteBishop2 = "\u001B[34m\u2657  ";
    private static String WhiteKnight1 = "\u001B[34m\u2658  ";
    private static String WhiteKnight2 = "\u001B[34m\u2658  ";
    private static String WhitePawn1 = "\u001B[34m\u2659  ";
    private static String WhitePawn2 = "\u001B[34m\u2659  ";
    private static String WhitePawn3 = "\u001B[34m\u2659  ";
    private static String WhitePawn4 = "\u001B[34m\u2659  ";
    private static String WhitePawn5 = "\u001B[34m\u2659  ";
    private static String WhitePawn6 = "\u001B[34m\u2659  ";
    private static String WhitePawn7 = "\u001B[34m\u2659  ";
    private static String WhitePawn8 = "\u001B[34m\u2659  ";

    private static String BlackKing = "\u001B[31m\u265A  ";
    private static String BlackQueen = "\u001B[31m\u265B  ";
    private static String BlackRook1 = "\u001B[31m\u265C  ";
    private static String BlackRook2 = "\u001B[31m\u265C  ";
    private static String BlackBishop1 = "\u001B[31m\u265D  ";
    private static String BlackBishop2 = "\u001B[31m\u265D  ";
    private static String BlackKnight1 = "\u001B[31m\u265E  ";
    private static String BlackKnight2 = "\u001B[31m\u265E  ";
    private static String BlackPawn1 = "\u001B[31m\u265F  ";
    private static String BlackPawn2 = "\u001B[31m\u265F  ";
    private static String BlackPawn3 = "\u001B[31m\u265F  ";
    private static String BlackPawn4 = "\u001B[31m\u265F  ";
    private static String BlackPawn5 = "\u001B[31m\u265F  ";
    private static String BlackPawn6 = "\u001B[31m\u265F  ";
    private static String BlackPawn7 = "\u001B[31m\u265F  ";
    private static String BlackPawn8 = "\u001B[31m\u265F  ";

    //puste pole na planszy
    private static String empty = "   ";

    private static Boolean finishGame = false;

    // definicje figur
    private static String[] WhiteFigure = new String[] { "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659" };

    private static String[] BlackFigure = new String[] { "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F" };

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String fromMove, toMove;

// Wyczyszczenie konsoli na początku gry
        clearConsole();
//plansza do gry
        System.out.println("Gra w szachy!");
//wyświetlenie listy figur
        showChessPieces();
//narysowanie pierwotnego rozstawienia
        ChessBoard = firstSeeding();
        drawBoard(ChessBoard);

        while(finishGame){
            System.out.println("Podaj współrzędne początkowe zgodnie ze wzorem np. A1");
            fromMove = scan.nextLine();
            //sprawdzam czy ktoś nie podał za dużej liczby znaków
            if (fromMove.length() > 2) {
                System.out.println("Współrzędne za długie");
                clearConsole();
                drawBoard(ChessBoard);
                continue;
            }
            //sprawdzam, czy pozycja nie początkowa nie jest pusta
            if (!checkLocation(ChessBoard,fromMove,"start")){
                System.out.println("Ta pozycja jest pusta");
            }



        }

//           if (!checkLocation(ChessBoard,fromMove,"start")){
//                System.out.println("Ta pozycja jest pusta");
//            } else {
//                System.out.println("Podaj współrzędne końcowe zgodnie ze wzorem np. A3");
//                toMove = scan.nextLine();
//                if (toMove.length() > 2) {
//                    System.out.println("Współrzędne za długie");
//                } else{
//                    if (!checkLocation(ChessBoard,fromMove,"end")) {
//                        System.out.println("Ta pozycja jest zajęta");
//                    } else {
//                        move(fromMove,toMove);
//                        System.out.println(move(fromMove,toMove));
//                    }
//                }
//            }
//        }
    }

    // czyszczenie konsoli
    private static void clearConsole() {
        // Wyczyszczenie konsoli na początku gry
        System.out.print("\033[H\033[2J");
        // ustawienie tła i napisów
        System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
    }

    // metoda to rysowania planszy, jako argument podaje się tablicę z aktualnym
    // stanem figur
    private static void drawBoard(String[][] ChessBoard) {
        // Wyświetlenie planszy
        // Pokolorowanie planszy
        for (int i = 0; i < ChessBoard.length; i++) {
            for (int j = 0; j < ChessBoard[i].length; j++) {
                if (i == 0 || i == ChessBoard.length - 1 || j == 0 || j == ChessBoard[j].length - 1) {
                    // System.out.print("\033[107m");
                    System.out.print("\u001B[46m \u001B[30m");
                } else {

                    switch (i) {
                        case 1: case 3: case 5: case 7:
                            switch (j) {
                                case 1: case 3: case 5: case 7:
                                    System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
                                    break;
                                default:
                                    System.out.print("\u001B[47m \u001B[30m"); // białe tło, czarne napisy
                                    break;
                            }
                            break;
                        case 2: case 4: case 6: case 8:
                            switch (j) {
                                case 1: case 3: case 5: case 7:
                                    System.out.print("\u001B[47m \u001B[30m"); // białe tło, czarne napisy
                                    break;
                                default:
                                    System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
                                    break;
                            }
                    }
                }

                System.out.print(ChessBoard[i][j]);
            }
            System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
            System.out.println();
        }
    }

    // metoda to wyświetlenia listy figur na początku gry
    private static void showChessPieces() {

        // zmieniam kolory figur białych i czarnych na odpowiednio niebieskie i
        // czerwone, żeby były czytelne na planszy
        System.out.println("Lista figur \"białych\"");

        for (int i = 0; i < WhiteFigure.length; i++) {
            System.out.print("\u001B[40m\u001B[34m"); // białe tło, niebieskie napisy
            System.out.print(WhiteFigure[i] + "   ");
        }
        System.out.print("\u001B[40m\u001B[37m"); // czarne tło, białe napisy
        System.out.println();

        System.out.println("Lista figur \"czarnych\":");

        for (int i = 0; i < BlackFigure.length; i++) {
            System.out.print("\u001B[40m\u001B[31m"); // białe tło, czerwone napisy
            System.out.print(BlackFigure[i] + "   ");
        }

        System.out.println();
    }

    // początkowe rozstawienie figur na planszy
    private static String[][] firstSeeding() {


        ChessBoard = new String[][] { { "    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    " },
                { "1   ", BlackRook1, BlackKnight1, BlackBishop1, BlackQueen, BlackKing, BlackBishop2, BlackKnight2,
                        BlackRook2, "   1" },
                { "2   ", BlackPawn1, BlackPawn2, BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7,
                        BlackPawn8, "   2" },
                { "3   ", empty, empty, empty, empty, empty, empty, empty, empty, "   3" },
                { "4   ", empty, empty, empty, empty, empty, empty, empty, empty, "   4" },
                { "5   ", empty, empty, empty, empty, empty, empty, empty, empty, "   5" },
                { "6   ", empty, empty, empty, empty, empty, empty, empty, empty, "   6" },
                { "7   ", WhitePawn1, WhitePawn2, WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7,
                        WhitePawn8, "   7" },
                { "8   ", WhiteRook1, WhiteKnight1, WhiteBishop1, WhiteQueen, WhiteKing, WhiteBishop2, WhiteKnight2,
                        WhiteRook2, "   8" },
                { "    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    " } };

        return ChessBoard;
    }

    // sparwdzenie, czy podana współrzędna przechowuje jakąś figurę
    private static Boolean checkLocation(String[][] chessBoard, String location, String destination) {

        int row = checkRow(location);
        int column = Integer.parseInt(location.substring(1, location.length()));
        Boolean ok = false;
        if (row != 0) {
            switch (destination) {
                case "start": {
                    ok = chessBoard[column][row] != "   ";
                    break;
                }
                case "end": {
                    ok = chessBoard[column][row] == "   ";
                    break;
                }
            }

            if (chessBoard[column][row] != "   ") {
                ok = true;
            }
            return ok;
        } else {
            return ok;
        }


    }

    // zamiana litery na liczbę
    private static int checkRow(String location) {

        int row = 0;
        location = location.substring(0, location.length() - 1);
        switch (location.toUpperCase()) {
            case "A": {
                row = 1;
                break;
            }
            case "B": {
                row = 2;
                break;
            }
            case "C": {
                row = 3;
                break;
            }
            case "D": {
                row = 4;
                break;
            }
            case "E": {
                row = 5;
                break;
            }
            case "F": {
                row = 6;
                break;
            }
            case "G": {
                row = 7;
                break;
            }
            case "H": {
                row = 8;
                break;
            }
            default: row = 0;
        }
        return row;
    }


    //wykonanie ruchu
    private static String move(String startLocation, String endLocation){
        int colStart = Integer.parseInt(startLocation.substring(1, startLocation.length()));
        int colEnd = Integer.parseInt(endLocation.substring(1, endLocation.length()));

        if (ChessBoard[colEnd][checkRow(endLocation)] == empty) {
            //wartość z pozycji początkowej przypisuję do wartości końcowej i czyszczę wartość początkową
            ChessBoard[colEnd][checkRow(endLocation)] = ChessBoard[colStart][checkRow(startLocation)];
            ChessBoard[colStart][checkRow(startLocation)] = "   ";
        } else {
            return "Nie możesz wykonać ruchu na to pole!";
        }
        clearConsole();
        drawBoard(ChessBoard);
        return "Wykonano ruch";
    }

}