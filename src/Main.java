import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
//
        Integer row = validateInputNumber("> Config total rows in hall : ","only number from 1-9",input);
        Integer col = validateInputNumber("> Config total cols in hall : ","only number from 1-9",input);
//
        String[][] morningHall = new String[row][col];
        String[][] afternoonHall = new String[row][col];
        String[][] nightHall = new String[row][col];
        initHall(morningHall);
        initHall(afternoonHall);
        initHall(nightHall);

        displayOneHall(morningHall);
        chooseSeat(morningHall);
        displayOneHall(morningHall);


//        Character ch;
//        do {
//            mainMenu();
//            ch = validateInputChar("Choose option : ","wrong command",input);
//            switch (ch){
//                case 'a' -> System.out.println("booking");
////                case 'b' -> booking();
//                case 'c' -> showTimeMenu();
//                case 'f' -> System.exit(0);
//            }
//
//
//        }while (!(ch.equals('f')));


        
    }

    //  validate char
    public static Character validateInputChar(String message, String error, Scanner input){
        while (true){
            System.out.print(message);
            String choice = input.nextLine();
            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            Matcher matcher = pattern.matcher(choice);
            if(matcher.matches())
                return Character.toLowerCase(choice.charAt(0));
            else
                System.out.println(error);
        }
    }

//    validate number
    public static Integer validateInputNumber(String message, String error, Scanner input){
        while (true){
            System.out.print(message);
            String choice = input.nextLine();
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(choice);
            if(matcher.matches()){
                if(!(Integer.parseInt(choice) == 0))
                    return Integer.parseInt(choice);
                else
                    System.out.println("Can not be ZERO");
            }
            else
                System.out.println(error);
        }
    }

//    Booking seat
    public static void booking(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        Scanner input = new Scanner(System.in);

//        do {
//            showTimeMenu();
//            String[][] hall = getHall(morningHall,afternoonHall,nightHall);
//            displayHall(hall,'A');
//
//        }while ()


    }

//  getOneHall
    public static String[][] getHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        Scanner input = new Scanner(System.in);
        Character choice = validateInputChar("> Please select show time (A | B | C): ","Please input A-B-C",input);
        if(choice.equals('a')){
            return morningHall;
        } else if (choice.equals('b')) {
            return afternoonHall;
        }else
            return nightHall;
    }

    //   Initialize hall
    public static void initHall(String[][] hall){
        for (int i = 0; i<hall.length; i++){
            for (int j = 0; j<hall[i].length; j++){
                hall[i][j] = "AV";
            }
        }
    }

//  display Hall
    public static void displayOneHall(String[][] hall){
        System.out.println("+".repeat(60));
        for (int i = 0; i<hall.length; i++){
            for (int j= 0; j<hall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                System.out.print("|"+aphabet+"-"+(1+j)+"::"+hall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));
    }

//   chooseSeat
    public static void chooseSeat(String[][] hall){
        Scanner input = new Scanner(System.in);
        char c = 67;
        int number = 2;
        String[] stringArray = new String[0];
        // Input strings dynamically
        while (true) {
            System.out.print("Enter a string (press Enter to finish): ");
            String userInput = input.nextLine();

            // Split the input string by commas
            String[] substrings = userInput.split(",");

            // Add the substrings to the array
            String[] newArray = new String[stringArray.length + substrings.length];
            System.arraycopy(stringArray, 0, newArray, 0, stringArray.length);
            System.arraycopy(substrings, 0, newArray, stringArray.length, substrings.length);

            // Update the reference to the new array
            stringArray = newArray;

            // Check if the user wants to stop (pressing Enter key)
            if (!(userInput.isEmpty())) {
                break;
            }
        }



        for (int i = 0; i<hall.length; i++){
            for (int j= 0; j<hall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                if(aphabet == c && number == j){
                    System.out.println("founded"+" row : "+i+ " cols : "+j);
                    hall[i][j] = "BO";
                }
            }
        }
    }


    public static void mainMenu(){
        System.out.print("""
                [[ Application Menu ]]
                <A> Booking
                <B> Hall
                <C> Showtime
                <D> Reboot Showtime
                <E> History
                <F> Exit
                """);
    }

     public static void showTimeMenu(){
        System.out.print("""
                +++++++++++++++++++++++++++++++++++++++++++++++
                # Daily Showtime of CSTAD HALL:
                # A) Morning (10:00AM - 12:30PM)
                # B) Afternoon (03:00PM - 50:30PM)
                # C) Night (07:00PM - 09:30PM)
                ++++++++++++++++++++++++++++++++++++++++++++++
                """);
    }


}
