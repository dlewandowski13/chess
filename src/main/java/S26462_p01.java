public class S26462_p01 {
    public static void main(String[] args) {
        onInit();

    }

    private static void onInit(){
// Wyczyszczenie konsoli na początku gry
        System.out.print("\033[H\033[2J");
        System.out.println();
        System.out.println("Gra w szachy!");
        System.out.println();
        System.out.println();
        System.out.print("\u001B[40m \u001B[37m"); //czarne tło, białe napisy
    }
}
