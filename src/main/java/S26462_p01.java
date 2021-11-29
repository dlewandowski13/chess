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
    static String WhiteKing = "\u001B[34m\u2654  ";
    static String WhiteQueen = "\u001B[34m\u2655  ";
    static String WhiteRook1 = "\u001B[34m\u2656  ";
    static String WhiteRook2 = "\u001B[34m\u2656  ";
    static String WhiteBishop1 = "\u001B[34m\u2657  ";
    static String WhiteBishop2 = "\u001B[34m\u2657  ";
    static String WhiteKnight1 = "\u001B[34m\u2658  ";
    static String WhiteKnight2 = "\u001B[34m\u2658  ";
    static String WhitePawn1 = "\u001B[34m\u2659  ";
    static String WhitePawn2 = "\u001B[34m\u2659  ";
    static String WhitePawn3 = "\u001B[34m\u2659  ";
    static String WhitePawn4 = "\u001B[34m\u2659  ";
    static String WhitePawn5 = "\u001B[34m\u2659  ";
    static String WhitePawn6 = "\u001B[34m\u2659  ";
    static String WhitePawn7 = "\u001B[34m\u2659  ";
    static String WhitePawn8 = "\u001B[34m\u2659  ";

    static String BlackKing = "\u001B[31m\u265A  ";
    static String BlackQueen = "\u001B[31m\u265B  ";
    static String BlackRook1 = "\u001B[31m\u265C  ";
    static String BlackRook2 = "\u001B[31m\u265C  ";
    static String BlackBishop1 = "\u001B[31m\u265D  ";
    static String BlackBishop2 = "\u001B[31m\u265D  ";
    static String BlackKnight1 = "\u001B[31m\u265E  ";
    static String BlackKnight2 = "\u001B[31m\u265E  ";
    static String BlackPawn1 = "\u001B[31m\u265F  ";
    static String BlackPawn2 = "\u001B[31m\u265F  ";
    static String BlackPawn3 = "\u001B[31m\u265F  ";
    static String BlackPawn4 = "\u001B[31m\u265F  ";
    static String BlackPawn5 = "\u001B[31m\u265F  ";
    static String BlackPawn6 = "\u001B[31m\u265F  ";
    static String BlackPawn7 = "\u001B[31m\u265F  ";
    static String BlackPawn8 = "\u001B[31m\u265F  ";

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

        System.out.println("Podaj współrzędne początkowe zgodnie ze wzorem np. A1");
        fromMove = scan.nextLine();
        if (fromMove.length() > 2) {
            System.out.println("Współrzędne za długie");
        } else{
            if (!checkLocation(ChessBoard,fromMove,"start")){
                System.out.println("Ta pozycja jest pusta");
            } else {
                System.out.println("Podaj współrzędne końcowe zgodnie ze wzorem np. A3");
                toMove = scan.nextLine();
                if (toMove.length() > 2) {
                    System.out.println("Współrzędne za długie");
                } else{
                    if (!checkLocation(ChessBoard,fromMove,"end")) {
                        System.out.println("Ta pozycja jest zajęta");
                    } else {
                        move(fromMove,toMove);
                    }
                }
            }
        }
    }

    // czyszczenie konsoli
    public static void clearConsole() {
        // Wyczyszczenie konsoli na początku gry
        System.out.print("\033[H\033[2J");
        // ustawienie tła i napisów
        System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
    }

    // metoda to rysowania planszy, jako argument podaje się tablicę z aktualnym
    // stanem figur
    public static void drawBoard(String[][] ChessBoard) {
        // Wyświetlenie planszy
        // Pokolorowanie planszy
        for (int i = 0; i < ChessBoard.length; i++) {
            for (int j = 0; j < ChessBoard[i].length; j++) {
                if (i == 0 || i == ChessBoard.length - 1 || j == 0 || j == ChessBoard[j].length - 1) {
                    // System.out.print("\033[107m");
                    System.out.print("\u001B[46m \u001B[30m");
                } else {

                    switch (i) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                            switch (j) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                    System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
                                    break;
                                default:
                                    System.out.print("\u001B[47m \u001B[30m"); // białe tło, czarne napisy
                                    break;
                            }
                            break;
                        case 2:
                        case 4:
                        case 6:
                        case 8:
                            switch (j) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
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
    public static void showChessPieces() {
        // definicje figur
        String[] WhiteFigure;
        WhiteFigure = new String[] { "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659" };

        String[] BlackFigure;
        BlackFigure = new String[] { "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F" };
        // zmieniam kolory figur białych i czarnych na odpowiednio niebieskie i
        // czerwone, żeby były czytelne na planszy
        System.out.println("Lista figur \"białych\"");

        for (int i = 0; i < WhiteFigure.length; i++) {
            System.out.print("\u001B[40m\u001B[34m"); // białe tło, czarne napisy
            System.out.print(WhiteFigure[i] + "   ");
        }
        System.out.print("\u001B[40m\u001B[37m"); // czarne tło, białe napisy
        System.out.println();

        System.out.println("Lista figur \"czarnych\":");

        for (int i = 0; i < BlackFigure.length; i++) {
            System.out.print("\u001B[40m\u001B[31m"); // białe tło, czarne napisy
            System.out.print(BlackFigure[i] + "   ");
        }

        System.out.println();
    }

    // początkowe rozstawienie figur na planszy
    public static String[][] firstSeeding() {

        ChessBoard = new String[][] { { "    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    " },
                { "1   ", BlackRook1, BlackKnight1, BlackBishop1, BlackQueen, BlackKing, BlackBishop2, BlackKnight2,
                        BlackRook2, "   1" },
                { "2   ", BlackPawn1, BlackPawn2, BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7,
                        BlackPawn8, "   2" },
                { "3   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   3" },
                { "4   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   4" },
                { "5   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   5" },
                { "6   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   6" },
                { "7   ", WhitePawn1, WhitePawn2, WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7,
                        WhitePawn8, "   7" },
                { "8   ", WhiteRook1, WhiteKnight1, WhiteBishop1, WhiteQueen, WhiteKing, WhiteBishop2, WhiteKnight2,
                        WhiteRook2, "   8" },
                { "    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    " } };

        return ChessBoard;
    }

    // sparwdzenie, czy podana współrzędna przechowuje jakąś figurę
    public static Boolean checkLocation(String[][] chessBoard, String location, String destination) {

        int row = checkRow(location);
        int column = Integer.parseInt(location.substring(1, location.length()));
        Boolean ok = false;


        switch (destination) {
            case "start": {
                if (chessBoard[column][row] != "   ") {
                    ok = true;
                } else {
                    ok = false;
                }
                break;
            }
            case "end": {
                if (chessBoard[column][row] == "   ") {
                    ok = true;
                } else {
                    ok = false;
                }
                break;
            }
        }

        if (chessBoard[column][row] != "   ") {
            ok = true;
        }

        return ok;
    }

    // zamiana litery na liczbę
    private static int checkRow(String location) {

        int column = 0;
        location = location.substring(0, location.length() - 1);
        switch (location.toUpperCase()) {
            case "A": {
                column = 1;
                break;
            }
            case "B": {
                column = 2;
                break;
            }
            case "C": {
                column = 3;
                break;
            }
            case "D": {
                column = 4;
                break;
            }
            case "E": {
                column = 5;
                break;
            }
            case "F": {
                column = 6;
                break;
            }
            case "G": {
                column = 7;
                break;
            }
            case "H": {
                column = 8;
                break;
            }
        }
        return column;
    }


    //wykonanie ruchu
    public static void move(String startLocation, String endLocation){
        int colStart = Integer.parseInt(startLocation.substring(1, startLocation.length()));
        int colEnd = Integer.parseInt(endLocation.substring(1, endLocation.length()));

        //wartość z pozycji początkowej przypisuję do wartości końcowej i czyszczę wartość początkową
        ChessBoard[colEnd][checkRow(endLocation)] = ChessBoard[colStart][checkRow(startLocation)];
        ChessBoard[colStart][checkRow(startLocation)] = "   ";
        clearConsole();
        drawBoard(ChessBoard);
    }

}
