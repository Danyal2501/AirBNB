package sql;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Program {

    public static ArrayList<String> featuresTranslated = new ArrayList<>();
    public static ArrayList<String> typeTranslated = new ArrayList<>();
    public static ArrayList<String> statusTranslated = new ArrayList<>();
    public static String error;
    public static int typeUser;
    public static int uid;

    public static void main(String[] args) throws ClassNotFoundException, ParseException {
        MySQLDAO sql = new MySQLDAO();
        String choice = "";
        error = "Invalid input, please try again";

        statusTranslated.add("Cancelled");
        statusTranslated.add("Good");


        typeTranslated.add("Full House");
        typeTranslated.add("Apartment");
        typeTranslated.add("Room");

        featuresTranslated.add("Pool");
        featuresTranslated.add("Hot Tub");
        featuresTranslated.add("Free parking on premises");
        featuresTranslated.add("EV Charger");
        featuresTranslated.add("Crib");
        featuresTranslated.add("Gym");
        featuresTranslated.add("BBQ Grill");
        featuresTranslated.add("Breakfast");
        featuresTranslated.add("Indoor Fireplace");
        featuresTranslated.add("Smoking allowed");

        Scanner input = new Scanner(System.in);
        boolean cont = false;
        String username = "";
        typeUser = 0;
        uid = 0;
        ArrayList<Object> info = new ArrayList<>();
        List<List<String>> listing = new ArrayList<>();

        //Login and Registration
        while (!cont) {
            System.out.println("1. Login\n2. Sign up");
            choice = input.nextLine();
            if (choice.equals("1")) {
                info = login(input, sql);
                if (info.isEmpty()) {
                    System.out.println("Invalid login");
                } else {
                    uid = (int) info.get(0);
                    typeUser = (int) info.get(1);
                    cont = true;
                }
            } else if (choice.equals("2")) {
                while (!cont) {
                    System.out.println("1. Renter\n2. Host");
                    choice = input.nextLine();
                    if (choice.equals("1")) {
                        cont = createUser(input, sql, 1);
                    } else if (choice.equals("2")) {
                        cont = createUser(input, sql, 2);
                    } else {
                        break;
                    }
                    if (!cont) {
                        System.out.println("Database Error");
                    } else {
                        cont = false;
                        break;
                    }
                }
            } else {
                System.out.println(error);
            }
        }

        cont = false;
        //User menu
        if (typeUser == 1) {
            while (!cont) {
                System.out.println("1. View Listings\n2. My Bookings\n3. Reports\n4. Delete my account");
                choice = input.nextLine();

                if (choice.equals("1")) {
                    allListings(input, sql);
                } else if (choice.equals("2")) {
                    getBookings(input,sql,1);
                } else if (choice.equals("3")) {
                    System.out.println("-----------------------");
                    while(true) {
                        System.out.println("1: Total booking within...");
                        System.out.println("2: Total number of listings");
                        System.out.println("3: Host ranking");
                        System.out.println("4: Commerical hosts");
                        System.out.println("5: Rank renters");
                        System.out.println("6: Cancellations");
                        System.out.println("7: Exit");
                        choice = input.nextLine();
                        if (choice.equals("1")) {
                            totalBooking(input, sql);
                        }
                        else if (choice.equals("2")) {
                            totalListing(input, sql);
                        }
                        else if (choice.equals("3")) {
                            rankHostCountry(input, sql);
                        }
                        else if (choice.equals("4")) {
                            commercialHost(input, sql);
                        }
                        else if (choice.equals("5")) {
                            rankRenter(input, sql);
                        }
                        else if (choice.equals("6")) {
                            rankCancellation(input, sql);
                        } else {
                            break;
                        }
                    }
                }
                else if (choice.equals("4")){
                    if(deleteUser(input,sql,uid)){
                        cont=true;
                        break;
                    }
                }
                else {
                    System.out.println(error);
                }
            }
        }

        //Host menu
        else {
            while (!cont) {
                System.out.println("1. Listings\n2. My Bookings\n3. Customer Bookings\n4. Reports\n5. Toolkit\n6. Delete my account");
                choice = input.nextLine();

                if (choice.equals("1")) {
                    System.out.println("1. Create listing\n2. Manage my listings\n3. View listings");
                    choice = input.nextLine();
                    if (choice.equals("1")) {
                        createListing(input, sql);
                    } else if (choice.equals("2")) {
                        usersListings(input, sql, uid);
                    } else if (choice.equals("3")){
                        allListings(input, sql);
                    }
                } else if(choice.equals("2")){
                    getBookings(input,sql,1);
                }
                else if (choice.equals("3")){
                    getBookings(input,sql,2);
                }
                else if (choice.equals("4")){
                    System.out.println("-----------------------");
                    while(true) {
                        System.out.println("1: Total booking within...");
                        System.out.println("2: Total number of listings");
                        System.out.println("3: Host ranking");
                        System.out.println("4: Commerical hosts");
                        System.out.println("5: Rank renters");
                        System.out.println("6: Cancellations");
                        System.out.println("7: Exit");
                        choice = input.nextLine();
                        if (choice.equals("1")) {
                            totalBooking(input, sql);
                        }
                        else if (choice.equals("2")) {
                            totalListing(input, sql);
                        }
                        else if (choice.equals("3")) {
                            rankHostCountry(input, sql);
                        }
                        else if (choice.equals("4")) {
                            commercialHost(input, sql);
                        }
                        else if (choice.equals("5")) {
                            rankRenter(input, sql);
                        }
                        else if (choice.equals("6")) {
                            rankCancellation(input, sql);
                        } else {
                            break;
                        }
                    }
                }
                else if(choice.equals("5")){
                    toolKit(input, sql);
                }
                else if(choice.equals("6")){
                    if(deleteUser(input,sql,uid)){
                        cont=true;
                        break;
                    }
                }
                else {
                    System.out.println(error);
                }
            }
        }


    }

    public static Boolean createUser(Scanner input, MySQLDAO sql, int type) {
        String username = "";
        while (true) {
            System.out.println("Username:");
            username = input.nextLine();
            if (sql.validUser(username)) {
                break;
            }
            System.out.println("This user already exists!");
        }

        System.out.println("Password:");
        String password = input.nextLine();
        System.out.println("Name:");
        String name = input.nextLine();
        System.out.println("Address:");
        String address = input.nextLine();
        String birth="";
        while(true) {
            System.out.println("Date of Birth (YYYY-MM-DD):");
            birth = input.nextLine();
            if(!sql.verifyAge(birth)){
                System.out.println("You must be 18 years or older to register");
                continue;
            }
            break;
        }
        System.out.println("Occupation:");
        String occupation = input.nextLine();
        System.out.println("SIN (Number):");
        int sin = input.nextInt();
        input.nextLine();
        String creditcard = "";
        if (type == 1) {
            System.out.println("Credit Card Number:");
            creditcard = input.nextLine();
        }
        return sql.addUser(name, address, birth, occupation, sin, creditcard, type, username, password);
    }

    public static ArrayList<Object> login(Scanner input, MySQLDAO sql) {
        System.out.println("Username:");
        String name = input.nextLine();
        System.out.println("Password:");
        String password = input.nextLine();
        return sql.login(name, password);
    }

    public static List<List<Object>> listings(Scanner input, MySQLDAO sql) {
        List<List<Object>> res = new ArrayList<>();
        return res;
    }

    public static Boolean createListing(Scanner input, MySQLDAO sql) {
        Boolean cont = false;
        Boolean ret = false;
        int type = -1;
        while (!cont) {
            System.out.println("Type of listing:\n1: Full House\n2: Apartment\n3: Room");
            String typeStr = input.nextLine();
            cont = true;
            if (typeStr.equals("1")) {
                type = 1;
            } else if (typeStr.equals("2")) {
                type = 2;
            } else if (typeStr.equals("3")) {
                type = 3;
            } else {
                System.out.println("Please select a value from 1 to 3");
                cont = false;
            }
        }
        double longitude;
        while (true) {
            System.out.println("Longitude: ");
            longitude = input.nextDouble();
            if (longitude>-180 && longitude<180){
                break;
            }
            else{
                System.out.println("Longitude must be between -180 and 180");
            }
        }
        double latitude;
        while (true) {
            System.out.println("Latitude: ");
            latitude = input.nextDouble();
            if (latitude>-90 && latitude<90){
                break;
            }
            else{
                System.out.println("Latitude must be between -90 and 90");
            }
        }


        input.nextLine();
        System.out.println("Country: ");
        String country = input.nextLine();
        System.out.println("City: ");
        String city = input.nextLine();
        String postalCode;
        while(true) {
            System.out.println("Postal Code (XXXXXX): ");
            postalCode = input.nextLine();

            if (postalCode.length()!=6){
                System.out.println("Postal code must be in formation XXXXXX, ex M1B3H6");
            }
            else{
                break;
            }
        }
        cont = false;
        List<Integer> features = new ArrayList<>();
        while (!cont) {
            System.out.println("Amenities (Enter to add/remove): ");
            for (int i = 0; i < featuresTranslated.size(); i++) {
                System.out.println((i + 1) + ": " + featuresTranslated.get(i));
            }
            System.out.println("11: Exit");
            System.out.println("------------------\nCurrently Selected:");
            for (int i = 0; i < features.size(); i++) {
                System.out.println(featuresTranslated.get(features.get(i) - 1));
            }
            int feature = 0;
            try {
                feature = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(error);
                continue;
            }

            if (feature == 11) {
                input.nextLine();
                cont = true;
            } else if (features.contains(feature)) {
                features.remove(features.indexOf(feature));
            } else if (0 < feature && feature < 11) {
                features.add(feature);
            }
        }
        ret = sql.addListing(uid, type, longitude, latitude, postalCode, city, country, features.toString());

        cont = false;
        List<String> calendar = new ArrayList();

        while (!cont) {
            System.out.println("Availability");
            System.out.println("Start Date (YYYY-MM-DD): ");
            String start = input.nextLine();
            System.out.println("End Date (YYYY-MM-DD): ");
            String end = input.nextLine();
            System.out.println("Price: ");
            int price = input.nextInt();
            input.nextLine();

            //System.out.println(sql.calendarOverlap(start,end,sql.latestListID()));
            if (!sql.addCalendar(sql.latestListID(), start, end, price)) {
                System.out.println("Error: This range overlaps with an existing availability");
            }
            ;
            System.out.println("1: Add more availabilities\n2: Done");
            String inp = input.nextLine();
            if (inp.equals("1")) {
                continue;
            } else {
                cont = true;
            }

        }
        return ret;
    }

    public static Boolean usersListings(Scanner input, MySQLDAO sql, int uid) {
        ArrayList<ArrayList<Object>> listings = new ArrayList<>();
        ArrayList<ArrayList<Object>> calendar = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> Calendarids = new ArrayList<>();
        Boolean cont=false;
        while (!cont) {
            listings.clear();
            calendar.clear();
            ids.clear();
            Calendarids.clear();
            listings = sql.getUsersListings(uid);
            System.out.println("--------------Listings--------------");
            for(int i=0; i< listings.size(); i++){
                System.out.println("ID: "+ listings.get(i).get(0)+", Longitude: "+listings.get(i).get(1)+", Latitude: "+listings.get(i).get(2)+", Country: "+listings.get(i).get(4)+", City: "+listings.get(i).get(3));
                String amenities = listings.get(i).get(5).toString();
                for(int j=featuresTranslated.size()-1; j>= 0; j--){
                    amenities=amenities.replace(Integer.toString(j+1), featuresTranslated.get(j));
                }
                System.out.println("Amenities: "+amenities);
                System.out.println("--------------------------------");
                ids.add((int)listings.get(i).get(0));
            }

            System.out.println("\nSelect an ID to manage, or any other number to go back");
            int select = Integer.parseInt(input.nextLine());
            int LID=select;
           // System.out.println(select);
            if(!ids.contains(select)){
                cont=false;
                break;
            }

            System.out.println("1: Availability\n2: Delete listing");
            String choice1 = input.nextLine();

            if (choice1.equals("2")){
                sql.deleteListing(select);
                continue;
            }
            else if (!choice1.equals("1")){
                continue;
            }

            calendar=sql.getCalendar(select);
            System.out.println("--------------Availability--------------");

            for(int i=0; i<calendar.size(); i++){
                System.out.println("ID: "+calendar.get(i).get(0)+", Price: $"+calendar.get(i).get(3)+", Start Date: "+calendar.get(i).get(1) + ", End Date: "+calendar.get(i).get(2));
                Calendarids.add((int)calendar.get(i).get(0));
            }

            System.out.println("\nSelect an ID to manage, or any other number to go back");
            select = Integer.parseInt(input.nextLine());

            if (!Calendarids.contains(select)){
                continue;
            }

            System.out.println("1: Edit price\n2: Edit availability\n3: Delete availability");
            String choice = input.nextLine();

            //System.out.println(Calendarids);
            int price = (int) calendar.get(Calendarids.indexOf(select)).get(3);

            if(choice.equals("1")){
                System.out.println("Current price: "+price);
                System.out.println("Enter new price:");
                price = input.nextInt();
                input.nextLine();
                sql.updatePrice(select, price);

            }
            else if (choice.equals("2")){
                if (sql.bookingOverlap(calendar.get(Calendarids.indexOf(select)).get(1).toString(), calendar.get(Calendarids.indexOf(select)).get(2).toString(), select)){
                    System.out.println("A booking already exists during this period. Cannot change.");
                    continue;
                }
                System.out.println("Current Start: "+calendar.get(Calendarids.indexOf(select)).get(1));
                System.out.println("Current End: "+calendar.get(Calendarids.indexOf(select)).get(2));
                System.out.println("Enter new start (YYYY-MM-DD):");
                String start = input.nextLine();
                System.out.println("Enter new end (YYYY-MM-DD):");
                String end = input.nextLine();
                sql.updateAvailability(start,end,select);
            }
            else if (choice.equals("3")){
                if (sql.bookingOverlap(calendar.get(Calendarids.indexOf(select)).get(1).toString(), calendar.get(Calendarids.indexOf(select)).get(2).toString(), select)){
                    System.out.println("A booking already exists during this period. Cannot delete.");
                    continue;
                }
                else{
                    sql.deleteCalendar(select);
                }
            }
            else{
                continue;
            }

        }
        return true;
    }

    public static Boolean allListings(Scanner input, MySQLDAO sql) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<ArrayList<Object>> listings = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<ArrayList<Object>> availability = new ArrayList<>();
        ArrayList<Object> Calendarids = new ArrayList<>();
        ArrayList<ArrayList<Object>> booked = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();
        Boolean cont=false;
        int lattrue=0;
        int posttrue=0;
        int addresstrue=0;
        String latitude;
        String longitude;
        int distance=0;
        String sortDistance = "";

        String sqlstatement="select *  from (select listingID, MIN(price) as price from Calendar GROUP BY listingID) as c natural join ";
        boolean searched=false;

        System.out.println("Filter by distance? (0=NO 1=YES)");
        lattrue=Integer.parseInt(input.nextLine());

        if(lattrue==1) {

            while (true) {
                System.out.println("Enter Latitude: ");
                latitude = input.nextLine();
                if (Double.parseDouble(latitude)>-90 && Double.parseDouble(latitude)<90){
                    break;
                }
                else{
                    System.out.println("Latitude must be between -90 and 90");
                }
            }
            while (true) {
                System.out.println("Enter Longitude: ");
                longitude = input.nextLine();
                if (Double.parseDouble(longitude)>-180 && Double.parseDouble(latitude)<180){
                    break;
                }
                else{
                    System.out.println("Longitude must be between -180 and 180");
                }
            }
            System.out.println("Within Distance (m) (MAX: 2147483647)");
            distance = input.nextInt();
            input.nextLine();
            //sqlstatement+=String.format("ST_Distance_Sphere(point(listing.longitude,listing.latitude), point('%s','%s')) < %d",longitude,latitude,distance);
            sqlstatement+=String.format("(select *, ST_Distance_Sphere(point(longitude, latitude), point('%s','%s')) AS dist from listing) as l ",longitude,latitude);
            sortDistance=" ORDER BY dist ASC";
            searched=true;
        }


        if(lattrue==1){
            sqlstatement+=String.format("WHERE dist < %d", distance);
        }
        else{
            sqlstatement+="listing ";
        }

        System.out.println("Filter by postal code? (0=NO 1=YES)");
        posttrue=Integer.parseInt(input.nextLine());

        String searchPost;
        if (posttrue==1){

            if (!searched){
                sqlstatement+=" WHERE ";
            }

            System.out.println("Enter first three characters of postal code (XXX)");
            searchPost=input.nextLine();

            if(searched){
                sqlstatement+=" AND ";
            }

            sqlstatement+=String.format("postalCode LIKE '%s___'", searchPost);
            searched=true;
        }

        System.out.println("Filter by address? (0=NO 1=YES)");
        addresstrue=Integer.parseInt(input.nextLine());

        String country;
        String city;
        if(addresstrue==1){

            if (!searched){
                sqlstatement+=" WHERE ";
            }

            System.out.println("Country: ");
            country = input.nextLine();

            System.out.println("City: ");
            city = input.nextLine();

            if(searched){
                sqlstatement+=" AND ";
            }

            sqlstatement+=String.format("country='%s' AND city='%s'", country, city);
            searched=true;
        }

        int featurestrue=-1;
        System.out.println("Filter by features (0=NO 1=YES)");
        featurestrue=Integer.parseInt(input.nextLine());

        cont = false;
        List<Integer> features = new ArrayList<>();
        if(featurestrue==1){

            if (!searched){
                sqlstatement+=" WHERE ";
            }

            while (!cont) {
                System.out.println("Amenities (Enter to add/remove): ");
                for (int i = 0; i < featuresTranslated.size(); i++) {
                    System.out.println((i + 1) + ": " + featuresTranslated.get(i));
                }
                System.out.println("11: Exit");
                System.out.println("------------------\nCurrently Selected:");
                for (int i = 0; i < features.size(); i++) {
                    System.out.println(featuresTranslated.get(features.get(i) - 1));
                }
                int feature = 0;
                try {
                    feature = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(error);
                    continue;
                }

                if (feature == 11) {
                    input.nextLine();
                    cont = true;
                } else if (features.contains(feature)) {
                    features.remove(features.indexOf(feature));
                } else if (0 < feature && feature < 11) {
                    features.add(feature);
                }
            }
            cont=false;
            if(!features.isEmpty()){

                if(searched){
                    sqlstatement+=" AND ";
                }
                sqlstatement+="(";
                for(int i=0; i< features.size(); i++){
                    if(i>0){
                        sqlstatement+=" AND ";
                    }
                    sqlstatement+=("amenities LIKE '%" + features.get(i) + "%'");
                }
                sqlstatement+=")";
            searched=true;
            }
        }

        int sortPrice;
        int order=-1;
        System.out.println("Sort by price? (0=NO 1=YES)");
        sortPrice=Integer.parseInt(input.nextLine());

        if (sortPrice==1){
            System.out.println("1: Ascending, 2: Descending");
            order=Integer.parseInt(input.nextLine());

            if(order==1){
                sqlstatement+=" ORDER BY price ASC";
            }
            else{
                sqlstatement+=" ORDER BY price DESC";
            }
        }
        else if(lattrue==1){
            sqlstatement+=sortDistance;
        }

       //System.out.println("statement: "+ sqlstatement);
        //System.out.println(cont);

        while(!cont) {
            curr.clear();
            ids.clear();
            availability.clear();
            Calendarids.clear();
            booked.clear();
            //System.out.println(sqlstatement+"::" +lattrue);
            listings = sql.getAllListings(sqlstatement, lattrue);
            System.out.println("--------------Listings--------------");
            //System.out.println(listings);
            for (int i = 0; i < listings.size(); i++) {
                curr = listings.get(i);
                System.out.print("ID: "+curr.get(0)+", Host ID: " + curr.get(8).toString() + ", Longitude: " + curr.get(1) + ", Latitude: " + curr.get(2) + ", Country: " + curr.get(3) + ", City: " + curr.get(4) + ", Postal Code: " + curr.get(5) + ", Price(Starting from): $"+ curr.get(6));
                if (lattrue==1){
                    System.out.println(", Distance(meters): "+Math.round(Double.parseDouble(curr.get(9).toString())));
                }
                else{
                    System.out.println();
                }
                String amenities = curr.get(7).toString();
                for(int j=featuresTranslated.size()-1; j>= 0; j--){
                    amenities=amenities.replace(Integer.toString(j+1), featuresTranslated.get(j));
                }
                System.out.println("Amenities: "+amenities);
                System.out.println("----------------------------------------------------");


                ids.add((int) curr.get(0));
            }
            System.out.println("\nSelect an ID to view availability, or any other number to go back");
            int select = Integer.parseInt(input.nextLine());

            if (!ids.contains(select)) {
                cont = false;
                break;
            }

            else{
                booked=sql.getBookings(select);
                while(true) {
                    System.out.println("----------Availability-------------");
                    availability = sql.getCalendar(select);
                    for (int i = 0; i < availability.size(); i++) {
                        curr = availability.get(i);
                        Calendarids.add(curr.get(0));
                        System.out.println("ID: " + curr.get(0) + ", Start Day:" + curr.get(1) + ", End Day: " + curr.get(2) + ", Price: $" + curr.get(3));
                    }
                    System.out.println("--------Booked timeslots (unavailable)----------");
                    for (int i = 0; i < booked.size(); i++) {
                        curr= booked.get(i);
                        System.out.println("Start Day:" + curr.get(0) + ", End Day: " + curr.get(1));
                    }

                    System.out.println("\nSelect an ID to book in that time slot, or any other number to go back");
                    int select1 = Integer.parseInt(input.nextLine());
                    if (!Calendarids.contains(select1)) {
                        break;
                    }
                    System.out.println("Start Day (YYYY-MM-DD):");
                    String startDay=input.nextLine();
                    Date start = sdf.parse(startDay);
                    System.out.println("End Day (YYYY-MM-DD):");
                    String endDay=input.nextLine();
                    Date end = sdf.parse(endDay);

                    curr = availability.get(Calendarids.indexOf(select1));


                    if (start.compareTo(sdf.parse(curr.get(1).toString())) < 0 || end.compareTo(sdf.parse(curr.get(2).toString())) > 0) {
                        System.out.println("Error: Start and end day must be between the available dates");
                        continue;
                    }
                    else if(sql.bookingOverlap(startDay, endDay, select)){
                        System.out.println("Error: The dates overlap with a currently booked time, see 'booked timeslots'");
                        continue;
                    }
                    sql.addBooking(uid,select,startDay,endDay,(int)curr.get(3));
                    System.out.println("--Booked successfully--");
                    break;

                }


            }

        }

        return true;
    }

    public static Boolean getBookings(Scanner input, MySQLDAO sql, int host) {


        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();
        ArrayList<Integer> bookingids = new ArrayList<>();


        // System.out.println(bookings);
        Boolean cont = false;
        while (!cont) {
            bookings.clear();
            curr.clear();
            if(host==1) {
                bookings = sql.getUserBookings(uid);
            }
            else if(host==2){
                bookings = sql.getHostBookings(uid);
            }
            //System.out.println(bookings);
            System.out.println("-------------------Bookings-------------------------");
            for (int i = 0; i < bookings.size(); i++) {
                curr = bookings.get(i);
                System.out.print("ID: " + curr.get(0));
                System.out.print(", Start: " + curr.get(1));
                System.out.print(", End: " + curr.get(2));
                System.out.print(", Price: $" + curr.get(3));
                System.out.print(", Type: " + typeTranslated.get((int) curr.get(5) -1));
                System.out.print(", Longitude: " + curr.get(6));
                System.out.print(", Latitude: " + curr.get(7));
                System.out.print(", Postal Code: " + curr.get(8));
                System.out.print(", City: " + curr.get(9));
                System.out.print(", Country: " + curr.get(10));
                System.out.print(", Status: " + statusTranslated.get((int) curr.get(4)) + "\n");
                String features = curr.get(11).toString();
                for(int j=featuresTranslated.size()-1; j>= 0; j--){
                    features=features.replace(Integer.toString(j+1), featuresTranslated.get(j));
                }
                System.out.println("Amenities: "+features);
                System.out.println("----------------------------------------------------");
                bookingids.add((int)curr.get(0));
            }

            System.out.println("Select ID to cancel booking, or any other number to exit");
            int bookingID = Integer.parseInt(input.nextLine());

            if(!bookingids.contains(bookingID)){
                cont=false;
                break;
            }
            int status=(int)bookings.get(bookingids.indexOf(bookingID)).get(4);

            if(status==0){
                System.out.println("This booking is already cancelled!");
                continue;
            }
            sql.cancelBooking(bookingID);


        }
        return true;
    }

    public static Boolean totalBooking(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();
        System.out.println("1: By zip/city\n2: By city");
        String choice=input.nextLine();

        System.out.println("Start date (YYYY-MM-DD): ");
        String start = input.nextLine();
        System.out.println("End date (YYYY-MM-DD): ");
        String end = input.nextLine();

        System.out.println("-------------------------------");
        if(choice.equals("2")) {
            bookings = sql.totalBookingCity(start, end);
            for (int i=0; i< bookings.size(); i++){
                curr= bookings.get(i);
                System.out.println("City: "+curr.get(0)+", Bookings: "+curr.get(1));
            }
        }
        else if(choice.equals("1")){
            bookings = sql.totalBookingZIP(start, end);
            for (int i=0; i< bookings.size(); i++){
                curr= bookings.get(i);
                System.out.println("City: "+curr.get(0)+", ZIP: "+curr.get(1)+", Bookings: "+curr.get(2));
            }
        }
        System.out.println("-------------------------------");
        return true;

    }

    public static Boolean totalListing(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();



        System.out.println("-------------Country------------------");

        bookings = sql.totalListingCountry();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", Listings: "+curr.get(1));
        }

        System.out.println("--------------Country/City-----------------");

        bookings = sql.totalListingCountryCity();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", City: "+curr.get(1)+", Listings: "+curr.get(2));
        }
        System.out.println("-------------Country/City/Zip Code------------------");
        bookings = sql.totalListingCountryCityZIP();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", City: "+curr.get(1)+", ZIP Code "+curr.get(2)+", Listings: "+curr.get(3));
        }

        System.out.println("-------------------------------");
        return true;

    }

    public static Boolean rankHostCountry(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();



        System.out.println("------------By Country-------------------");

        bookings = sql.rankHostCountry();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", Name: "+curr.get(1)+", Listings: "+curr.get(2));
        }
        if(typeUser==1){
            return true;
        }

        System.out.println("------------By City/Country-------------------");

        bookings = sql.rankHostCountryCity();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", City: "+curr.get(2)+", Name: "+curr.get(1)+", Listings: "+curr.get(3));
        }

        System.out.println("-------------------------------");
        return true;

    }

    public static Boolean commercialHost(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();



        System.out.println("---------------------------------");

        bookings = sql.commercialHosts();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Country: "+curr.get(0)+", Name: "+curr.get(1)+", City: "+curr.get(2)+", Percent: "+Double.parseDouble(curr.get(3).toString())*100+"%");
        }

        System.out.println("-------------------------------");
        return true;

    }

    public static Boolean rankRenter(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();

        System.out.println("Start Day (YYYY-MM-DD): ");
        String start = input.nextLine();
        System.out.println("End Day (YYYY-MM-DD): ");
        String end = input.nextLine();

        System.out.println("---------------Rank renters------------------");

        bookings = sql.rankRenters(start, end);
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Name: "+curr.get(0)+", Bookings: "+curr.get(1));
        }

        System.out.println("-------------Rank renters by city------------------");

        bookings = sql.rankRentersCity(start, end);
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("City: "+curr.get(0)+", Name: "+curr.get(1)+", Bookings: "+curr.get(2));
        }

        System.out.println("-------------------------------");
        return true;

    }

    public static Boolean rankCancellation(Scanner input, MySQLDAO sql) {
        ArrayList<ArrayList<Object>> bookings = new ArrayList<>();
        ArrayList<Object> curr = new ArrayList<>();

        System.out.println("---------------Renter Ranking------------------");

        bookings = sql.rankCancellationRenter();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Name: "+curr.get(0)+", Cancellations: "+curr.get(1));
        }

        System.out.println("---------------Host Ranking------------------");

        bookings = sql.rankCancellationHost();
        for (int i=0; i< bookings.size(); i++){
            curr= bookings.get(i);
            System.out.println("Name: "+curr.get(0)+", Cancellations: "+curr.get(1));
        }

        System.out.println("------------------------------------------------");
        return true;

    }

    public static Boolean toolKit(Scanner input, MySQLDAO sql) {

        System.out.println("---------------Recommended Price------------------");

        double price = sql.averagePrice();
        System.out.println("$"+price);

        System.out.println("---------------Recommended Amenities------------------");

        ArrayList<Integer> features = sql.commonAmenities();
        for(int i=0; i< features.size(); i++){
            System.out.println(i+1+": "+featuresTranslated.get(features.get(i)-1));
        }
        System.out.println("-----------------------------------------------------");
        return true;

    }

    public static Boolean deleteUser(Scanner input, MySQLDAO sql, int uid) {

        System.out.println("ARE YOU SURE YOU WOULD LIKE TO DELETE YOUR ACCOUNT? (0=NO, 1=YES)");

        String choice = input.nextLine();

        if (!choice.equals("1")){
            return false;
        }
        sql.deleteUser(uid);
        return true;

    }
}

