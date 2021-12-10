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
//    private static String[] WhiteFigure = new String[]{"\u001B[34m\u2654  "/*król*/, "\u001B[34m\u2655  "/*hetman*/,
//            "\u001B[34m\u2656  "/*wieża*/, "\u001B[34m\u2657  "/*goniec*/, "\u001B[34m\u2658  "/*skoczek*/,
//            "\u001B[34m\u2659  "/*pion*/};
//
//    private static String[] BlackFigure = new String[]{"\u001B[31m\u265A  "/*król*/, "\u001B[31m\u265B  "/*hetman*/,
//            "\u001B[31m\u265C  "/*wieża*/, "\u001B[31m\u265D  "/*goniec*/, "\u001B[31m\u265E  "/*skoczek*/,
//            "\u001B[31m\u265F  " /*pion*/};
    private static String[] WhiteFigure = new String[]{" \u2654 "/*król*/, " \u2655 "/*hetman*/,
            " \u2656 "/*wieża*/, " \u2657 "/*goniec*/, " \u2658 "/*skoczek*/,
            " \u2659 "/*pion*/};

    private static String[] BlackFigure = new String[]{" \u265A "/*król*/, " \u265B "/*hetman*/,
            " \u265C "/*wieża*/, " \u265D "/*goniec*/, " \u265E "/*skoczek*/,
            " \u265F " /*pion*/};


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
        System.out.println("length od blackfigure[1] = " + BlackFigure[1].length());
        System.out.println("length od empty = " + empty.length());
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
                } else {
                    System.out.println("Ruch \"czarnych\"");
                }
                System.out.println("Podaj współrzędne początkowe zgodnie ze wzorem np. A1");
                fromMove = scan.nextLine();
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
                if (toMove.equals("0")) {
                    endLocation = true;
                    continue;
                }
                if (toMove.length() != 2) {
                    System.out.println("Współrzędne nieprawidłowe");
                    continue;
                } else {
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
        System.out.println("Jeżeli chcesz rozpocząć ruch od nowa, to wpisz w konsoli 0.");
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
//                                    System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy 3;43;30m
                                    System.out.print("\u001B[3;100;30m"); // czarne tło, białe napisy 3;104;30m
                                    break;
                                default:
//                                    System.out.print("\u001B[47m \u001B[30m"); // białe tło, czarne napisy
                                    System.out.print("\u001B[47m"); // białe tło, czarne napisy
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
//                                    System.out.print("\u001B[47m \u001B[30m"); // białe tło, czarne napisy
                                    System.out.print("\u001B[47m"); // białe tło, czarne napisy
                                    break;
                                default:
//                                    System.out.print("\u001B[40m \u001B[37m"); // czarne tło, białe napisy
                                    System.out.print("\u001B[3;100;30m"); // czarne tło, białe napisy 3;104;30m
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
//            System.out.print("\u001B[40m\u001B[34m"); // białe tło, niebieskie napisy
            System.out.print(WhiteFigure[i] + "   ");
        }
        System.out.print("\u001B[40m\u001B[37m"); // czarne tło, białe napisy
        System.out.println();

        System.out.println("Lista figur \"czarnych\":");

        for (int i = 0; i < BlackFigure.length; i++) {
//            System.out.print("\u001B[40m\u001B[31m"); // białe tło, czerwone napisy
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
//                                System.out.println("chessBoard[column][row] = " + chessBoard[column][row] + "WhiteFigure[" + i + "] = " + WhiteFigure[i]);
                                if (chessBoard[column][row] == WhiteFigure[i]) {
                                    ok = true;
                                    break;
                                } else {
                                    ok = false;
                                }
                            }
                        } else {
                            for (int i = 0; i < BlackFigure.length; i++) {
                                if (chessBoard[column][row] == BlackFigure[i]) {
                                    ok = true;
                                    break;
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
        }
        return ok;


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
        int rowFrom = checkRow(startLocation);
        int columnFrom = Integer.parseInt(startLocation.substring(1, startLocation.length()));
        int rowTo = checkRow(endLocation);
        int columnTo = Integer.parseInt(endLocation.substring(1, endLocation.length()));
        Boolean ok = true;
//      zmienna do sprawdzenia stanu początkowego pionów w zależności od gracza
        int pawnStartPosition;
        String king, queen, rook, bishop, knight, pawn;
//      ustawiam zmienn w zależności od gracza
        if (player == 1) {
            pawnStartPosition = 7;
            king = WhiteFigure[0];
            queen = WhiteFigure[1];
            rook = WhiteFigure[2];
            bishop = WhiteFigure[3];
            knight = WhiteFigure[4];
            pawn = WhiteFigure[5];

        } else {
            pawnStartPosition = 2;
            king = BlackFigure[0];
            queen = BlackFigure[1];
            rook = BlackFigure[2];
            bishop = BlackFigure[3];
            knight = BlackFigure[4];
            pawn = BlackFigure[5];
        }
//sprawdzanie ruchu dla piona
        if (ChessBoard[columnFrom][checkRow(startLocation)] == pawn) {
//              sprawdzam czy ruch jest pionowy
            if (verticalMove(startLocation, endLocation)) {
//              sprawdzam, czy jest to ruch z początkowej lokalizacji - wówczas może być o 2 pola
                if (columnFrom == pawnStartPosition) {
//                  pionem nie można poruszać się wstecz
                    if ((player == 1 && (columnFrom - columnTo) <= 2 && (columnFrom - columnTo) > 0) || (player == 2 && (columnFrom - columnTo) >= -2 && (columnFrom - columnTo) < 0)) {
//                        move(startLocation, endLocation);
                        return true;
                    } else {
                        return false;
                    }
//                  jeżeli lokalizacja nie jest początkowa, wówczas ruch o 1 pole
                } else {
                    if ((player == 1 && (columnFrom - columnTo) == 1) || (player == 2 && (columnFrom - columnTo) == -1)) {
//                        move(startLocation, endLocation);
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
                            if (ChessBoard[columnTo][checkRow(endLocation)] == BlackFigure[i]) {
                                tookPiece(endLocation);
                                return true;
                            }
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (abs(checkRow(startLocation) - checkRow(endLocation)) == 1) {
                        for (int i = 0; i < WhiteFigure.length; i++) {
                            if (ChessBoard[columnTo][checkRow(endLocation)] == WhiteFigure[i]) {
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
//      sprawdzenie ruchu dla wieży
        } else if (ChessBoard[columnFrom][checkRow(startLocation)] == rook) {
//          sprawdzam czy ruch jest pionowy lub poziomy
            if (checkHorVerMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                return true;
            } else {
                return false;
            }

//sprawdzanie ruchu dla gońca
        } else if (ChessBoard[columnFrom][rowFrom] == bishop) {
            if (checkCrossMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                return true;
            } else {
                return false;
            }
//      sprawdzenie ruchu dla skoczka
        } else if (ChessBoard[columnFrom][rowFrom] == knight) {
//          sprawdzenie ruchu l
            if (lMove(startLocation, endLocation)) {
//              sprawdzam, czy miejsce docelowe jest puste
                if (ChessBoard[columnTo][rowTo] == empty) {
                    return true;
//              jeżeli miejsce docelowe nie jest puste, to sprawdzam bicie
                } else if (checkTookPiece(columnTo, rowTo, player)) {
                    tookPiece(endLocation);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
//      sprawdzenie ruchu dla hetmana
        } else if (ChessBoard[columnFrom][rowFrom] == queen) {
//          sprawdzam czy ruch jest pionowy lub poziomy
            if (checkHorVerMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                return true;
            } else if (checkCrossMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                return true;
            } else {
                return false;
            }
        } else if (ChessBoard[columnFrom][rowFrom] == king){
            if (abs(columnFrom - columnTo) > 1 || abs(rowFrom - rowTo) > 1) {
                return false;
            } else {
                if (checkHorVerMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                    return true;
                } else if (checkCrossMove(startLocation, endLocation, rowFrom, rowTo, columnFrom, columnTo, player)) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        return false;
    }

    private static void tookPiece(String location) {
        int col = Integer.parseInt(location.substring(1, location.length()));
        ChessBoard[col][checkRow(location)] = empty;
        clearConsole();
        drawBoard(ChessBoard);
    }

    //wykonanie ruchu
    //wykonanie ruchu
    private static void move(String startLocation, String endLocation) {
        int colStart = Integer.parseInt(startLocation.substring(1, startLocation.length()));
        int colEnd = Integer.parseInt(endLocation.substring(1, endLocation.length()));

        if (ChessBoard[colEnd][checkRow(endLocation)] == empty) {
            //wartość z pozycji początkowej przypisuję do wartości końcowej i czyszczę wartość początkową
            ChessBoard[colEnd][checkRow(endLocation)] = ChessBoard[colStart][checkRow(startLocation)];
            ChessBoard[colStart][checkRow(startLocation)] = "   ";
        }
        clearConsole();
        drawBoard(ChessBoard);
    }

    //sprawdzanie ruchu w poziomie
    private static Boolean horizontalMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));

        if (rowFrom != rowTo && columnFrom == columnTo) {
//            System.out.println("horizontal rowFrom = " + rowFrom + ",columnFrom = " + columnFrom + ",  rowTo = " + rowTo + ",columnTo = " + columnTo);
            return true;
        } else {
//            System.out.println("nie poziomy");
            return false;
        }
    }

    //sprawdzanie ruchu w pionie
    private static Boolean verticalMove(String fromMove, String toMove) {
        int rowFrom = checkRow(fromMove);
        int columnFrom = Integer.parseInt(fromMove.substring(1, fromMove.length()));

        int rowTo = checkRow(toMove);
        int columnTo = Integer.parseInt(toMove.substring(1, toMove.length()));
        if (rowFrom == rowTo && columnFrom != columnTo) {
//            System.out.println("vertical rowFrom = " + rowFrom + ",columnFrom = " + columnFrom + ",  rowTo = " + rowTo + ",columnTo = " + columnTo);
            return true;
        } else {
//            System.out.println("nie pionowy");
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

    private static Boolean checkTookPiece(Integer column, Integer row, Integer player) {
        if (player == 1) {
            for (int i = 0; i < BlackFigure.length; i++) {
                if (ChessBoard[column][row] == BlackFigure[i]) {
//                    tookPiece(endLocation);
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < WhiteFigure.length; i++) {
                if (ChessBoard[column][row] == WhiteFigure[i]) {
//                    tookPiece(endLocation);
                    return true;
                }
            }
            return false;
        }
    }

    private static Boolean checkHorVerMove(String startLocation, String endLocation, Integer rowFrom, Integer rowTo, Integer columnFrom, Integer columnTo, Integer player) {
        //          sprawdzam czy ruch jest pionowy lub poziomy
        Boolean ok = true;
        if ((verticalMove(startLocation, endLocation)) || (horizontalMove(startLocation, endLocation))) {
            System.out.println("vertical lub horizontal");
            System.out.println("columnFrom = " + columnFrom + ", columnTo = " + columnTo);
//              sprawdzam, czy nic nie stoi pomiędzy początkiem ruchu, a jego końcem w pionie
            if (columnFrom > columnTo) {
                for (int i = columnFrom - 1; i > columnTo; i--) {
                    if (ChessBoard[i][rowFrom] != empty) {
                        System.out.println("coś tu stoi");
                        ok = false;
                        break;
                    }
                }
            } else if (columnFrom < columnTo) {
                for (int i = columnFrom + 1; i < columnTo; i++) {
                    if (ChessBoard[i][rowFrom] != empty) {
                        ok = false;
                        break;
                    }
                }
            }
            System.out.println("rowFrom = " + rowFrom + ", rowTo = " + rowTo);
//              sprawdzam, czy nic nie stoi pomiędzy początkiem ruchu, a jego końcem w poziomie
            if (rowFrom > rowTo) {
                for (int i = rowFrom - 1; i > rowTo; i--) {
                    if (ChessBoard[columnFrom][i] != empty) {
                        ok = false;
                        break;
                    }
                }
            } else if (rowFrom < rowTo) {
                for (int i = rowFrom + 1; i < rowTo; i++) {
                    if (ChessBoard[columnFrom][i] != empty) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok == false) {
                return false;
            }
//          sprawdzam, czy miejsce końcowe jest puste, jeżeli nie, to sparwdzam możliwość bicia
            if (ChessBoard[columnTo][rowTo] == empty) {
                return true;
            } else {
                if (checkTookPiece(columnTo, rowTo, player)) {
                    tookPiece(endLocation);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
//              jeżeli nie jest to ruch ani pionowy, ani poziomy, to kończymy
            return false;
        }
    }

    private static Boolean checkCrossMove(String startLocation, String endLocation, Integer rowFrom, Integer rowTo, Integer columnFrom, Integer columnTo, Integer player) {
        if (crossMove(startLocation, endLocation)) {
//              sprawdzam, czy nic nie stoi na linii ruchu skośnego
            for (int i = 1; i < abs(rowFrom - rowTo); i++) {
//                  ruch na pónocny zachód
                if (columnFrom > columnTo && rowFrom > rowTo) {
                    if (ChessBoard[columnFrom - i][rowFrom - i] != empty) {
                        return false;
                    }
//                  ruch na pónocny wschód
                } else if (columnFrom > columnTo && rowFrom < rowTo) {
                    if (ChessBoard[columnFrom - i][rowFrom + i] != empty) {
                        return false;
                    }
//                  ruch na południowy zachód
                } else if (columnFrom < columnTo && rowFrom > rowTo) {
                    if (ChessBoard[columnFrom + i][rowFrom - i] != empty) {
                        return false;
                    }
//                  ruch na południowy wschód
                } else if (columnFrom < columnTo && rowFrom < rowTo) {
                    if (ChessBoard[columnFrom + i][rowFrom + i] != empty) {
                        return false;
                    }
                }
            }
//          sprawdzam, czy miejsce docelowe jest zajęte
            if (ChessBoard[columnTo][rowTo].equals(empty)) {
                return true;
//          jeżeli jest zajęte, to sprawdzam bicie
            } else if (checkTookPiece(columnTo, rowTo, player)) {
                tookPiece(endLocation);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}