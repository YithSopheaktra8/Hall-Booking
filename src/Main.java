import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Integer row = validateInputNumber("> Config total rows in hall : ","only number from 1-9",input);
        Integer col = validateInputNumber("> Config total cols in hall : ","only number from 1-9",input);
        String[][] morningHall = new String[row][col];
        String[][] afternoonHall = new String[row][col];
        String[][] nightHall = new String[row][col];
        initializeAllHall(morningHall,afternoonHall,nightHall);
        Character ch;
        do {
            mainMenu();
            ch = validateInputChar("> Choose option : ","+".repeat(60)+"\n# Option must be alphabet from A-F\n"+"+".repeat(60),input);
            switch (ch){
                case 'a' -> booking(morningHall,afternoonHall,nightHall);
                case 'b' -> showAllHall(morningHall,afternoonHall,nightHall);
                case 'c' -> showTimeMenu();
                case 'd' -> rebootAllHall(morningHall,afternoonHall,nightHall);
                case 'f' -> System.exit(0);
                default -> System.out.println("+".repeat(60)+"\n# Please input option from A-F\n"+"+".repeat(60));
            }

        }while (!(ch.equals('f')));

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
        showTimeMenu();
        String[][] hall = getHall(morningHall,afternoonHall,nightHall);
        displayOneHall(hall);
        validateBooking(hall);
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


    //   initAll
    public static void initializeAllHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        initHall(morningHall);
        initHall(afternoonHall);
        initHall(nightHall);
    }


    //   Init hall
    public static void initHall(String[][] hall){
        for (int i = 0; i<hall.length; i++){
            for (int j = 0; j<hall[i].length; j++){
                hall[i][j] = "AV";
            }
        }
    }
    // rebootHall
    public static void rebootAllHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        initHall(morningHall);
        initHall(afternoonHall);
        initHall(nightHall);
        System.out.println("+".repeat(60));
        System.out.println("# start rebooting the hall.........");
        System.out.println("# Rebooted successfully.");
        System.out.println("+".repeat(60));
    }

    //  display Hall
    public static void displayOneHall(String[][] hall){
        System.out.println("+".repeat(60));
        for (int i = 0; i<hall.length; i++){
            for (int j= 0; j<hall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                System.out.print("  |"+aphabet+"-"+(1+j)+"::"+hall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));
    }

//   chooseSeat
    public static String[] singleAndMultipleSelect(String[][] hall){
        Scanner input = new Scanner(System.in);
        String[] stringArray = new String[0];
        // Input strings dynamically
        while (true) {
            System.out.print("""
                    # INSTRUCTION
                    # SINGLE : C-1
                    # Multiple (Separate by comma (,)) : C-1,C-2
                    """);
            System.out.print("> Please select available seat : ");
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
        return stringArray;
    }

    // validate booking
    public static void validateBooking(String[][] hall) {
        Scanner scanner = new Scanner(System.in);
        String[] userInput = singleAndMultipleSelect(hall);
        boolean isBook = false;
        for (String input : userInput) {
            String getUserInput = input.replaceAll("-", "");
            Integer number = Integer.parseInt(getUserInput.replaceAll("[^0-9]", ""));
            char letter = getUserInput.replaceAll("[^a-zA-Z]", "").charAt(0);
            for (int i = 0; i < hall.length; i++) {
                for (int j = 0; j < hall[i].length; j++) {
                    char aphabet = (char) ('A' + i);
                    if (aphabet == letter && (number -1 ) == j) {
                        if (hall[i][j].equals("BO")) {
                            isBook = false;
                            break;
                        } else {
                            hall[i][j] = "BO";
                            isBook = true;
                        }
                    }
                }
            }
        }
        if (isBook) {
            char isSure = validateInputChar("Are you sure to book? (Y/N)", "please input Y or N ", scanner);
            if (isSure == 'y') {
                System.out.println("+".repeat(60));
                System.out.println("# " + Arrays.toString(userInput) + " booked successfully.");
                System.out.println("+".repeat(60));
            }
        }else {
            System.out.println("+".repeat(60));
            System.out.println("!! "+ Arrays.toString(userInput) +" already booked!");
            System.out.println("!! "+ Arrays.toString(userInput) +" cannot be booked because of unavailability!");
            System.out.println("+".repeat(60));
        }
    }

//    show all hall
    public static void showAllHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        // morning
        System.out.println("# Hall information");
        System.out.println("+".repeat(60));
        System.out.println("# Hall - Morning");
        for (int i = 0; i<morningHall.length; i++){
            for (int j= 0; j<morningHall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                System.out.print("  |"+aphabet+"-"+(1+j)+"::"+morningHall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));

//        afternoon
        System.out.println("# Hall - Afternoon");
        for (int i = 0; i<afternoonHall.length; i++){
            for (int j= 0; j<afternoonHall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                System.out.print("  |"+aphabet+"-"+(1+j)+"::"+afternoonHall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));

//       night
        System.out.println("# Hall - Night");
        for (int i = 0; i<nightHall.length; i++){
            for (int j= 0; j<nightHall[i].length; j++){
                char aphabet = (char) ('A' + i ) ;
                System.out.print("  |"+aphabet+"-"+(1+j)+"::"+nightHall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));
    }


    public static void mainMenu () {
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

    public static void showTimeMenu () {
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

