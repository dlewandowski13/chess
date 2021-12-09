/*
    Author: Dawid Lewandowski s26462
    Title: Gra w Szachy
    Requirements: UTF-8 - do prawidłowego wyświetlania symboli figur szachowych, kolorów planszy itp
*/

import java.util.Scanner;

import static java.lang.Math.*;

public class S26462_p01 {
    //    szachownica
    private static String[][] ChessBoard;
    //    puste pole na planszy
    private static String empty = "   ";

    private static Boolean finishGame = false;

    //    definicje figur
    private static String[] WhiteFigure = new String[]{"\u001B[34m\u2654  "/*król*/, "\u001B[34m\u2655  "/*hetman*/,
            "\u001B[34m\u2656  "/*wieża*/, "\u001B[34m\u2657  "/*laufer*/, "\u001B[34m\u2658  "/*goniec*/,
            "\u001B[34m\u2659  "/*pion*/};

    private static String[] BlackFigure = new String[]{"\u001B[31m\u265A  "/*król*/, "\u001B[31m\u265B  "/*hetman*/,
            "\u001B[31m\u265C  "/*wieża*/, "\u001B[31m\u265D  "/*laufer*/, "\u001B[31m\u265E  "/*goniec*/,
            "\u001B[31m\u265F  " /*pion*/};


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String fromMove = "0", toMove = "0";

        Boolean startLocation;
        Boolean endLocation;
//      gracze 1 - białe, 2 - czarne
        Integer player = 1;

// Wyczyszczenie konsoli na początku gry
        clearConsole();
//plansza do gry
        System.out.println("Gra w szachy!");
//wyświetlenie listy figur
        showChessPieces();
//narysowanie pierwotnego rozstawienia
        ChessBoard = firstSeeding();
        drawBoard(ChessBoard);
        while (!finishGame) {
            startLocation = false;
            endLocation = false;
            while (!startLocation) {
                if (player == 1) {
                    System.out.println("Ruch \"białych\"");
                } else{
                    System.out.println("Ruch \"czarnych\"");
                }
                System.out.println("Podaj współrzędne początkowe zgodnie ze wzorem np. A1");
                fromMove = scan.nextLine();
//                TODO Bonus, krótkie menu
//                if (fromMove == "help") {
//                    help();
//                    continue;
//                }
//                if (fromMove == "restart") {
//                    ChessBoard = firstSeeding();
//                    drawBoard(ChessBoard);
//                    continue;
//                }

//              sprawdzam czy ktoś nie podał za dużej liczby znaków
                if (fromMove.length() != 2) {
                    System.out.println("Współrzędne nieprawidłowe");
                    clearConsole();
                    drawBoard(ChessBoard);
                    continue;
                }
//              sprawdzam, czy pozycja początkowa nie jest pusta
                if (!checkLocation(ChessBoard, fromMove, "start", player)) {
                    System.out.println("Ta pozycja jest pusta, albo jest na niej nie Twoja figura!");
                } else {
                    startLocation = true;
                }
            }

            while (!endLocation) {
                System.out.println("Podaj współrzędne końcowe zgodnie ze wzorem np. A3");
                toMove = scan.nextLine();
//                TODO Bonus, krótkie menu
//                if (toMove == "help") {
//                    help();
//                    continue;
//                }
//                if (toMove == "restart") {
//                    ChessBoard = firstSeeding();
//                    drawBoard(ChessBoard);
//                    continue;
//                }
                if (toMove.equals("0")) {
                    endLocation = true;
                    continue;
                }
                if (toMove.length() != 2) {
                    System.out.println("Współrzędne nieprawidłowe");
                    continue;
                } else {
//                    TODO uporządkować ruch, przenieść wszystko do metody move
//                    if (!checkLocation(ChessBoard, toMove, "end", player)) {
//                        System.out.println("Ta pozycja jest zajęta");
//                    } else {
                        if (checkMove(fromMove, toMove, player)) {
                            move(fromMove, toMove);
//                          ustawiam odpowiedniego gracza
                            if (player == 1) {
                                player = 2;
                            } else {
                                player = 1;
                            }
                            endLocation = true;
                        } else {
                            System.out.println("Niedozwolony ruch!");
                        }
//                    }
                }
            }
        }
    }


    // czyszczenie konsoli
    private static void clearConsole() {
        // Wyczyszczenie konsoli na początku gry
        System.out.print("\033[H\033[2J");
        // ustawienie tła i napisów
        System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
    }

    // metoda do rysowania planszy, jako argument podaje się tablicę z aktualnym stanem figur
    private static void drawBoard(String[][] ChessBoard) {
        // Wyświetlenie i pokolorowanie planszy
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


        ChessBoard = new String[][]{{"    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    "},
                {"1   ", BlackFigure[2], BlackFigure[4], BlackFigure[3], BlackFigure[1], BlackFigure[0], BlackFigure[3], BlackFigure[4],
                        BlackFigure[2], "   1"},
                {"2   ", BlackFigure[5], BlackFigure[5], BlackFigure[5], BlackFigure[5], BlackFigure[5], BlackFigure[5], BlackFigure[5],
                        BlackFigure[5], "   2"},
                {"3   ", empty, empty, empty, empty, empty, empty, empty, empty, "   3"},
                {"4   ", empty, empty, empty, empty, empty, empty, empty, empty, "   4"},
                {"5   ", empty, empty, empty, empty, empty, empty, empty, empty, "   5"},
                {"6   ", empty, empty, empty, empty, empty, empty, empty, empty, "   6"},
                {"7   ", WhiteFigure[5], WhiteFigure[5], WhiteFigure[5], WhiteFigure[5], WhiteFigure[5], WhiteFigure[5], WhiteFigure[5],
                        WhiteFigure[5], "   7"},
                {"8   ", WhiteFigure[2], WhiteFigure[4], WhiteFigure[3], WhiteFigure[1], WhiteFigure[0], WhiteFigure[3], WhiteFigure[4],
                        WhiteFigure[2], "   8"},
                {"    ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", "    "}};

        return ChessBoard;
    }

    // sparwdzenie, czy podana współrzędna przechowuje jakąś figurę
    private static Boolean checkLocation(String[][] chessBoard, String location, String destination, Integer player) {

        int row = checkRow(location);
        int column = Integer.parseInt(location.substring(1, location.length()));
        Boolean ok = false;
        if (row != 0) {
            switch (destination) {
                case "start": {
//                    ok = chessBoard[column][row] != empty;
                    if (chessBoard[column][row] != empty) {
//                      sprawdzam którego gracza kolej
                        if (player == 1) {
                            for (int i = 0; i < WhiteFigure.length; i++) {
//                                System.out.println("chessBoard[column][row] = " + chessBoard[column][row] + "WhiteFigure[" + i + "] = " + WhiteFigure[i] );
                                if (chessBoard[column][row] == WhiteFigure[i]) {
                                    ok = true;
                                } else {
                                    ok = false;
                                }
                            }
                        } else {
                            for (int i = 0; i < BlackFigure.length; i++) {
                                if (chessBoard[column][row] == BlackFigure[i]) {
                                    ok = true;
                                } else {
                                    ok = false;
                                }
                            }
                        }
                    }
                    break;
                }
                case "end": {
                    ok = chessBoard[column][row] == empty;
                    break;
                }
            }

//            if (chessBoard[column][row] != empty) {
//                ok = true;
//            }
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
            default:
                row = 0;
        }
        return row;
    }

    //sprawdzenie możliwości wykonania ruchu
    private static Boolean checkMove(String startLocation, String endLocation, Integer player) {
        int colStart = Integer.parseInt(startLocation.substring(1, startLocation.length()));
        int colEnd = Integer.parseInt(endLocation.substring(1, endLocation.length()));
//      zmienna do sprawdzenia stanu początkowego pionów w zależności od gracza
        int pawnStartPosition;
        String pawn;
//      ustawiam zmienn w zależności od gracza
        if (player == 1) {
            pawnStartPosition = 7;
            pawn = WhiteFigure[5];
        } else {
            pawnStartPosition = 2;
            pawn = BlackFigure[5];;
        }
        System.out.println("colStart = " + colStart + ", colend = " + colEnd);
        System.out.println(ChessBoard[colStart][checkRow(startLocation)]);

//białe
//sprawdzanie ruchu dla piona
        if (ChessBoard[colStart][checkRow(startLocation)] == pawn) {
//              sprawdzam czy ruch jest pionowy
            if (verticalMove(startLocation, endLocation)) {
                System.out.println("Ruch pionowy");
//              sprawdzam, czy jest to ruch z początkowej lokalizacji - wówczas może być o 2 pola
                System.out.println("colStart = " + colStart);
                System.out.println("checkRow(startLocation)" + checkRow(startLocation));
                System.out.println("pawnStartPosition = " + pawnStartPosition);
                if (colStart == pawnStartPosition) {
//                  pionem nie można poruszać się wstecz
//                    if (abs(colStart - colEnd) <= 2) {
                    if ((player == 1 && (colStart - colEnd) <= 2 && (colStart - colEnd) > 0) || (player == 2 && (colStart - colEnd) >= -2 && (colStart - colEnd) < 0)) {
                        move(startLocation, endLocation);
                        return true;
                    } else {
                        return false;
                    }
//                  jeżeli lokalizacja nie jest początkowa, wówczas ruch o 1 pole
                } else {
//                    if (abs(colStart - colEnd) == 1) {
                    if ((player == 1 && (colStart - colEnd) == 1) || (player == 2 && (colStart - colEnd) == -1)) {
                        move(startLocation, endLocation);
                        return true;
                    } else {
                        return false;
                    }
                }
//              sprawdzam bicie piona w zależności od gracza
            } else {
                if (player == 1) {
                    if (abs(checkRow(startLocation) - checkRow(endLocation)) == 1) {
                        for (int i = 0; i < BlackFigure.length; i++) {
                            if (ChessBoard[colEnd][checkRow(endLocation)] == BlackFigure[i]) {
                                tookPiece(endLocation);
                                return true;
                            }
                        }
                    } else {
                        return false;
                    }
                } else {

                    System.out.println("ChessBoard[colEnd][checkRow(endLocation)] = " + ChessBoard[colEnd][checkRow(endLocation)]);
                    if (abs(checkRow(startLocation) - checkRow(endLocation)) == 1) {
                        for (int i = 0; i < WhiteFigure.length; i++) {
                            System.out.println("WhiteFigure[i] = " + WhiteFigure[i]);
                            if (ChessBoard[colEnd][checkRow(endLocation)] == WhiteFigure[i]) {
                                tookPiece(endLocation);
                                return true;
                            }
                        }
                    } else {
                        return false;
                    }

                }
                return false;
            }
        } else {
            return false;
        }
    }

    private static void tookPiece(String location) {
        int col = Integer.parseInt(location.substring(1, location.length()));
        ChessBoard[col][checkRow(location)] = empty;
        clearConsole();
        drawBoard(ChessBoard);
    }

    //wykonanie ruchu
    //wykonanie ruchu
    private static String move(String startLocation, String endLocation) {
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

    //sprawdzanie ruchu w poziomie
    private static Boolean horizontalMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));

        if (rowFrom != rowTo && columnFrom == columnTo) {
            return true;
        } else {
            return false;
        }
    }

    //sprawdzanie ruchu w pionie
    private static Boolean verticalMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));
        System.out.println("rowFrom = " + rowFrom + ",columnFrom,  rowTo" + rowTo + ",columnTo " + columnTo);
        if (rowFrom == rowTo && columnFrom != columnTo) {
            return true;
        } else {
            return false;
        }
    }

    //sprawdzanie ruchu po skosie
    private static Boolean crossMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));

        if (abs(rowFrom - rowTo) == abs(columnFrom - columnTo)) {
            return true;
        } else {
            return false;
        }

    }

    //sprawdzanie ruchu "l"
    private static Boolean lMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));

        if ((abs(rowFrom - rowTo) == 2 && abs(columnFrom - columnTo) == 1) ||
                (abs(rowFrom - rowTo) == 1 && abs(columnFrom - columnTo) == 2)) {
            return true;
        } else {
            return false;
        }

    }


//    TODO help
//    private static void help() {
//        System.out.println("Witaj w pomocy!");
//        System.out.println("Jeżeli chcesz poddać grę wpisz \"koniec\"");
//        System.out.println("Jeżeli rozpocząć grę od nowa wpisz \"restart\" - " +
//                "uwaga drugi gracz będzie musiał zaakceptować tę operację, poprzez wpisanie \"tak\"");
//        System.out.println("Jeżeli chcesz anulować wybór początkowej pozycji, w końcowej wpisz 0.");
//
//    }

}