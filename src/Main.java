import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
//        System.out.print("> Config total rows in hall : ");
//        String row = input.nextLine();
//        System.out.print("> Config total rows in hall : ");
//        String col = input.nextLine();
//
//        String initializeRowCol = validateInputNumber(row) && validateInputNumber(col) ? "Successfully created Hall" : "row and column can be number only";
//
//        Integer[][] hallA = new Integer[stringToInt(row)][stringToInt(col)];
//        Integer[][] hallB = new Integer[stringToInt(row)][stringToInt(col)];
//        Integer[][] hallC = new Integer[stringToInt(row)][stringToInt(col)];
//        System.out.println(Arrays.deepToString(hallA));

        String ch = "";
        do {
            System.out.println("Choose option : ");
            ch = input.nextLine();
            if(!validateInputChar(ch)){
                System.out.println("Wrong command");
            }
        }while (true);


        
    }

    // convert from String to Integer
    public static int stringToInt(String value){
        return Integer.parseInt(value);
    }

    //  validate input character
    public static boolean validateInputChar(String input){
        Pattern pattern = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static String validateInputNumber(String input , String successMsg, String errorMsg){
        Pattern pattern = Pattern.compile("[1-9]+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() ? successMsg : errorMsg;
    }

    public static void mainMenu(){
        System.out.println("""
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
        System.out.println("""
                +++++++++++++++++++++++++++++++++++++++++++++++
                # Daily Showtime of CSTAD HALL:
                # A) Morning (10:00AM - 12:30PM)
                # B) Afternoon (03:00PM - 50:30PM)
                # C) Night (07:00PM - 09:30PM)
                ++++++++++++++++++++++++++++++++++++++++++++++
                """);
    }
}
