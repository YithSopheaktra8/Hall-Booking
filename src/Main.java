import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String[] bookingHistory = new String[0];

        do {
            mainMenu();
            ch = validateInputChar("> Choose option : ","+".repeat(60)+"\n# Option must be alphabet from A-F\n"+"+".repeat(60),"[a-fA-F]+",input);
            switch (ch){
                case 'a' -> {
                    bookingHistory = booking(morningHall,afternoonHall,nightHall,bookingHistory);
                }
                case 'b' -> showAllHall(morningHall,afternoonHall,nightHall);
                case 'c' -> showTimeMenu();
                case 'd' -> {
                    rebootAllHall(morningHall,afternoonHall,nightHall);
                    bookingHistory = null;
                }
                case 'e' -> displayBookingHistory(bookingHistory);
                case 'f' -> System.exit(0);
                default -> System.out.println("+".repeat(60)+"\n# Please input option from A-F\n"+"+".repeat(60));
            }

        }while (!(ch.equals('f')));

    }




//    Booking seat
    public static String[] booking(String[][] morningHall, String[][] afternoonHall, String[][] nightHall , String[] currentHistory){
        Scanner scanner = new Scanner(System.in);
        showTimeMenu();
        Character choice = validateInputChar("> Please select show time (A | B | C): ","Please input A-B-C","[a-cA-C]+",scanner);
        String[][] hall = getHall(morningHall,afternoonHall,nightHall, choice);
        displayOneHall(hall);
        String[] userInput = singleAndMultipleSelect();
        String seat = Arrays.toString(userInput);
        String userID = "";
        String history = "";
        for (String input : userInput) {
            String getUserInput = input.replaceAll("-", "");
            int number = Integer.parseInt(getUserInput.replaceAll("[^0-9]", ""));
            char letter = getUserInput.replaceAll("[^a-zA-Z]", "").charAt(0);
            for (int i = 0; i < hall.length; i++) {
                for (int j = 0; j < hall[i].length; j++) {
                    char alphabet = (char) ('A' + i);
                    if (alphabet == letter && (number -1 ) == j) {
                        if (hall[i][j].equals("BO")) {
                            System.out.println("+".repeat(60));
                            System.out.println("!! ["+alphabet+"-"+(1+j)+"] already booked!");
                            System.out.println("!! ["+alphabet+"-"+(1+j)+"] cannot be booked because of unavailability!");
                            System.out.println("+".repeat(60));
                            break;
                        } else {
                            userID = validateInputString("> Please enter userID : ", "!! ID can not be special character!","[a-zA-Z0-9]+", scanner);
                            char isSure = validateInputChar("Are you sure to book? (Y/N) : ", "please input Y or N ","[yYNn]+", scanner);
                            if (isSure == 'y') {
                                hall[i][j] = "BO";
                                history = addToHistory(seat, userID , choice);
                                // Update the booking history array
                                String[] newHistory = Arrays.copyOf(currentHistory, currentHistory.length + 1);
                                newHistory[newHistory.length - 1] = history;
                                System.out.println("+".repeat(60));
                                System.out.println("# " + seat + " booked successfully.");
                                System.out.println("+".repeat(60));
                                return newHistory;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // Display booking history
    public static void displayBookingHistory(String[] bookingHistory) {
        System.out.println("+".repeat(60));
        System.out.println("# Booking History:");
        if(bookingHistory.length == 0){
            System.out.println("-".repeat(60));
            System.out.println("                There is no history");
            System.out.println("-".repeat(60));
            System.out.println("+".repeat(60));


        }
        for (String history : bookingHistory) {
            System.out.println("-".repeat(60));
            System.out.println(history);
            System.out.println("-".repeat(60));
            System.out.println("+".repeat(60));

        }
    }

    // add to history
    public static String addToHistory(String seat, String userID , Character choice){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y hh:mm");
        String formattedDateTime = localDateTime.format(formatter);

        char hall = Character.toUpperCase(choice);
        String name =  String.format(
                "#SEATS: [" + seat + "]" +
                        "\n#HALL         #USER.ID               #CREATED AT" +
                        "\nHALL " + hall + "        " + userID + "                " + formattedDateTime
        );
        return name;
    }


//  getOneHall
    public static String[][] getHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall , Character choice){
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
        Scanner scanner = new Scanner(System.in);
        char isSure = validateInputChar("Are you sure to reboot hall ? (Y/N) : ", "please input Y or N ","[yYnN]+", scanner);
        if (isSure == 'y') {
            initHall(morningHall);
            initHall(afternoonHall);
            initHall(nightHall);
            System.out.println("+".repeat(60));
            System.out.println("# start rebooting the hall.........");
            System.out.println("# Rebooted successfully.");
            System.out.println("+".repeat(60));
        }

    }

    //  display Hall
    public static void displayOneHall(String[][] hall){
        System.out.println("+".repeat(60));
        loopHallEvent(hall);
    }
//   chooseSeat
    public static String[] singleAndMultipleSelect(){
        Scanner input = new Scanner(System.in);
        String[] stringArray = new String[0];
        // Input strings dynamically
        while (true) {
            System.out.print("""
                    # INSTRUCTION
                    # SINGLE : C-1
                    # Multiple (Separate by comma (,)) : C-1,C-2
                    """);
            String userInput = validateInputString("> Please select available seat : ","# !! Please input base on Instruction !","([A-C]-[1-9],)*[A-C]-[1-9]",input);
            // Split the input string by commas
            System.out.println(userInput);
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



    public static String[] getHistory(String history, String[] currentHistories) {
        // Create a new array with increased length to accommodate the new history
        String[] newHistories = Arrays.copyOf(currentHistories, currentHistories.length + 1);
        // Add the new history to the end of the array
        newHistories[newHistories.length - 1] = history;

        return newHistories;
    }

    //    show all hall
    public static void showAllHall(String[][] morningHall, String[][] afternoonHall, String[][] nightHall){
        // morning
        System.out.println("# Hall information");
        System.out.println("+".repeat(60));
        System.out.println("# Hall - Morning");
        loopHallEvent(morningHall);
//        afternoon
        System.out.println("# Hall - Afternoon");
        loopHallEvent(afternoonHall);
//       night
        System.out.println("# Hall - Night");
        loopHallEvent(nightHall);
    }
    public static void loopHallEvent(String[][] hall) {
        for (int i = 0; i<hall.length; i++){
            for (int j= 0; j<hall[i].length; j++){
                char alphabet = (char) ('A' + i ) ;
                System.out.print("  |"+alphabet+"-"+(1+j)+"::"+hall[i][j]+"|\t");
            }
            System.out.println();
        }
        System.out.println("+".repeat(60));
    }
    //  validate char
    public static Character validateInputChar(String message, String error, String stringPattern ,Scanner input){
        while (true){
            System.out.print(message);
            String choice = input.nextLine();
            Pattern pattern = Pattern.compile(stringPattern);
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
    // validate String
    public static String validateInputString(String message, String error, String patternString, Scanner input ){
        while (true){
            System.out.print(message);
            String choice = input.nextLine();
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(choice);
            if(matcher.matches()){
                return choice;
            }
            else
                System.out.println(error);
        }
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

