package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDAO {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://127.0.0.1/mydb";
    private static Connection conn;

    public MySQLDAO() throws ClassNotFoundException {
        Class.forName(dbClassName);
        //Register JDBC driver
        Class.forName(dbClassName);
        //Database credentials
        final String USER = "root";
        final String PASS = "password";

        try {
            conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            System.out.println("Successfully connected to MySQL!");
        } catch (Exception e) {
            System.out.println("err1");
            e.printStackTrace();
        }
    }

    public void testing(String name, String address, String birthday, String occupation, int sin, String paymentInfo, int typeUser, String username, String pass) {
        try {

            System.out.println("Preparing a statement...");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Users (legalName, address, birthday, occupation, sin, paymentInfo, typeUser, username, pass) VALUES ('%s', '%s', '%s', '%s', %d, '%s', %d, '%s', '%s')";
            String query = String.format(sql, name, address, birthday, occupation, sin, paymentInfo, typeUser, username, pass);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("legalName"));
            }
            //STEP 5: Extract data from result set
           /* while (rs.next()) {
                //Retrieve by column name
                int sid = rs.getInt("sid");
                String sname = rs.getString("sname");
                int rating = rs.getInt("rating");
                int age = rs.getInt("age");

                //Display values
                System.out.print("ID: " + sid);
                System.out.print(", Name: " + sname);
                System.out.print(", Rating: " + rating);
                System.out.println(", Age: " + age);
            }*/


            System.out.println("Closing connection...");
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.err.println("Connection error occured!");
        }
    }

    public Boolean validUser(String username) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Users WHERE username = '%s'";
            String query = String.format(sql, username);
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.next()) {
                ret = true;
            }

            rs.close();
            stmt.close();
            return ret;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean addUser(String name, String address, String birthday, String occupation, int sin, String paymentInfo, int typeUser, String username, String pass) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Users (legalName, address, birthday, occupation, sin, paymentInfo, typeUser, username, pass) VALUES ('%s', '%s', '%s', '%s', %d, '%s', %d, '%s', '%s')";
            String query = String.format(sql, name, address, birthday, occupation, sin, paymentInfo, typeUser, username, pass);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Object> login(String username, String pass) {
        ArrayList<Object> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Users WHERE username = '%s' AND pass='%s'";
            String query = String.format(sql, username, pass);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                information.add(rs.getInt("userID"));
                information.add(rs.getInt("typeUser"));
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean addListing(int userID, int type, double longitude, double latitude, String postalCode, String city, String country, String amenities) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Listing (userID, placeType, longitude, latitude, postalCode, city, country, amenities) VALUES (%d, %d, %f, %f, '%s', '%s', '%s', '%s')";
            String query = String.format(sql, userID, type, longitude, latitude, postalCode, city, country, amenities);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean addCalendar(int LID, String start, String end, int price) {
        try {
            if (calendarOverlap(start, end, LID)) {
                return false;
            }
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Calendar (listingID, startDay, endDay, price) VALUES (%d, '%s', '%s', %d)";
            String query = String.format(sql, LID, start, end, price);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public int latestListID() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Listing ORDER BY listingID DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            int lid = rs.getInt("listingID");

            rs.close();
            stmt.close();
            return lid;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public Boolean calendarOverlap(String start, String end, int LID) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "SELECT count(*) AS count FROM Calendar where (startDay<='%s' AND endDay >= '%s') AND listingID = %d";
            String query = String.format(sql, end, start, LID);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            //System.out.println("count: "+ rs.getInt("count"));
            if(rs.getInt("count")>0){
                ret = true;
            }

            rs.close();
            stmt.close();
            return ret;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean bookingOverlap(String start, String end, int LID) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "SELECT count(*) AS count FROM Booking where (startDay<='%s' AND endDay >= '%s') AND listingID = %d AND currStatus=1";
            String query = String.format(sql, end, start, LID);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            //System.out.println("count: "+ rs.getInt("count"));
            if(rs.getInt("count")>0){
                ret = true;
            }

            rs.close();
            stmt.close();
            return ret;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<ArrayList<Object>> getUsersListings(int uid) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from Listing JOIN Users ON Listing.userID=Users.userID WHERE Users.userID=%d";
            String query = String.format(sql, uid);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getInt("listingID"));
                information.get(i).add(rs.getDouble("longitude"));
                information.get(i).add(rs.getDouble("latitude"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("amenities"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> getCalendar(int LID) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from Calendar WHERE listingID=%d";
            String query = String.format(sql, LID);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getInt("calendarID"));
                information.get(i).add(rs.getString("startDay"));
                information.get(i).add(rs.getString("endDay"));
                information.get(i).add(rs.getInt("price"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean updatePrice(int CID, int price ) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "UPDATE Calendar SET price = %d WHERE calendarID = %d";
            String query = String.format(sql, price, CID);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean updateAvailability(String start, String end, int CID ) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "UPDATE Calendar SET startDay = '%s', endDay = '%s' WHERE calendarID = %d";
            String query = String.format(sql, start, end, CID);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean deleteCalendar(int CID) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Calendar WHERE calendarID = %d";
            String query = String.format(sql, CID);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public ArrayList<ArrayList<Object>> getAllListings(String sql, int lattrue) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String query = sql;
            ResultSet rs = stmt.executeQuery(query);

            //System.out.println(query);
            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getInt("listingID"));
                information.get(i).add(rs.getDouble("longitude"));
                information.get(i).add(rs.getDouble("latitude"));
                information.get(i).add(rs.getString("Country"));

                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("postalCode"));
                information.get(i).add(rs.getInt("price"));
                information.get(i).add(rs.getString("amenities"));
                information.get(i).add(rs.getString("userID"));
                if(lattrue==1){
                    information.get(i).add(rs.getDouble("dist"));
                }
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Bookings for a specific listing
    public ArrayList<ArrayList<Object>> getBookings(int LID) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from Booking WHERE listingID=%d AND currStatus=1";
            String query = String.format(sql, LID);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());

                information.get(i).add(rs.getString("startDay"));
                information.get(i).add(rs.getString("endDay"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean addBooking(int uid, int lid, String start, String end, int price) {
        try {

            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Booking (userID, listingID, startDay, endDay, price, currStatus) VALUES (%d, %d, '%s', '%s', %d, 1)";
            String query = String.format(sql, uid, lid, start, end, price);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Boolean deleteListing(int LID){
        try {
            Boolean ret = true;
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Calendar WHERE listingID = %d";
            String query = String.format(sql, LID);
            int rs = stmt.executeUpdate(query);


            sql = "DELETE FROM Booking WHERE listingID = %d"; //
            query = String.format(sql, LID);
            rs = stmt.executeUpdate(query);


            sql = "DELETE FROM Listing WHERE listingID = %d";
            query = String.format(sql, LID);
            rs = stmt.executeUpdate(query);

            if (rs != 1) {
                ret = false;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    //Bookings for a specific listing
    public ArrayList<ArrayList<Object>> getUserBookings(int UID) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from booking join listing ON booking.listingID=listing.listingID WHERE booking.userID=%d";
            String query = String.format(sql, UID);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getInt("bookingID"));
                information.get(i).add(rs.getString("startDay"));
                information.get(i).add(rs.getString("endDay"));
                information.get(i).add(rs.getInt("price"));
                information.get(i).add(rs.getInt("currStatus"));
                information.get(i).add(rs.getInt("placeType"));
                information.get(i).add(rs.getDouble("longitude"));
                information.get(i).add(rs.getDouble("latitude"));
                information.get(i).add(rs.getString("postalCode"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("amenities"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    //Bookings for a specific listing
    public ArrayList<ArrayList<Object>> getHostBookings(int UID) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from booking join listing ON booking.listingID=listing.listingID WHERE listing.userID=%d";
            String query = String.format(sql, UID);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getInt("bookingID"));
                information.get(i).add(rs.getString("startDay"));
                information.get(i).add(rs.getString("endDay"));
                information.get(i).add(rs.getInt("price"));
                information.get(i).add(rs.getInt("currStatus"));
                information.get(i).add(rs.getInt("placeType"));
                information.get(i).add(rs.getDouble("longitude"));
                information.get(i).add(rs.getDouble("latitude"));
                information.get(i).add(rs.getString("postalCode"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("amenities"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean cancelBooking(int BID ) {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "UPDATE Booking SET currStatus = 0 WHERE bookingID = %d";
            String query = String.format(sql, BID);
            int rs = stmt.executeUpdate(query);

            if (rs == 1) {
                ret = true;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public ArrayList<ArrayList<Object>> totalBookingCity(String start, String end) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select city, count(*) as count from booking left join listing ON booking.listingID=listing.listingID WHERE startDay>='%s' AND endDay<='%s' group by city ";
            String query = String.format(sql,start, end);

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getInt("count"));

                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> totalBookingZIP(String start, String end) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = " select city, postalCode, count(*) as count from booking left join listing ON booking.listingID=listing.listingID WHERE startDay>='%s' AND endDay<='%s' group by city, postalCode";
            String query = String.format(sql, start, end);

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("postalCode"));
                information.get(i).add(rs.getInt("count"));

                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<ArrayList<Object>> totalListingCountry() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select country, count(*) as count from listing group by country;";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getInt("count"));

                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> totalListingCountryCity() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select country, city, count(*) as count from listing group by country, city";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getInt("count"));

                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> totalListingCountryCityZIP() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select country, city, postalCode, count(*) as count from listing group by country, city, postalCode";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("postalCode"));
                information.get(i).add(rs.getInt("count"));

                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankHostCountry() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, country, count(*) as count from listing left join users on listing.userID=users.userID group by country, legalName ORDER BY country, count desc;";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getString("count"));


                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankHostCountryCity() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, country, city, count(*) as count from listing left join users on listing.userID=users.userID group by city, legalName ORDER BY country, city, count desc";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getString("count"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> commercialHosts() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select * from (select legalName, country, a.city, count/total as percent from (select legalName, country, city, count(*) as count from listing left join users on listing.userID=users.userID group by city, legalName ORDER BY country, city, count desc) as a left join (select city, count(*) as total from listing left join users on listing.userID=users.userID group by city) as b on a.city=b.city) as a WHERE percent>0.1;";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("country"));
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getString("city"));
                information.get(i).add(rs.getDouble("percent"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankRenters(String start, String end) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, count(*) as count from booking left join users on booking.userID=users.userID where startDay >= '%s' AND endDay <='%s' group by legalName order by count desc ";
            String query = String.format(sql, start, end);
           // System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getString("count"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankRentersCity(String start, String end) {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, city, count(*) as count from booking left join users on booking.userID=users.userID left join listing on listing.listingid=booking.listingid where startDay >= '%s' AND endDay <='%s' group by legalName, city order by city, count desc";
            String query = String.format(sql, start, end);
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("City"));
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getString("count"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankCancellationRenter() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, count(*) as count from booking left join users on booking.userID=users.userID where currStatus = 0 group by legalName order by count desc";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getInt("count"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> rankCancellationHost() {
        ArrayList<ArrayList<Object>> information = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select legalName, count(*) as count from booking left join listing on booking.listingID=listing.listingID left join users on users.userID=listing.userID WHERE currStatus=0 group by legalName";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            int i=0;
            while (rs.next()) {
                information.add(i, new ArrayList<>());
                information.get(i).add(rs.getString("legalName"));
                information.get(i).add(rs.getInt("count"));
                i++;
            }

            rs.close();
            stmt.close();
            return information;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public double averagePrice() {
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select AVG(price) as average from Calendar";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            rs.next();

            double res = rs.getInt("average");

            rs.close();
            stmt.close();
            return res;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public ArrayList<Integer> commonAmenities() {
        ArrayList<Integer> features = new ArrayList<>();
        try {
            Boolean ret = false;
            Statement stmt = conn.createStatement();
            String sql = "select 1 as num, count(*) as count from listing where amenities LIKE '%1%' union select 2 as num, count(*) as count from listing where amenities LIKE '%2%' union select 3 as num, count(*) as count from listing where amenities LIKE '%3%' union select 4 as num, count(*) as count from listing where amenities LIKE '%4%' union select 5 as num, count(*) as count from listing where amenities LIKE '%5%' union select 6 as num, count(*) as count from listing where amenities LIKE '%6%' union select 7 as num, count(*) as count from listing where amenities LIKE '%7%' union select 8 as num, count(*) as count from listing where amenities LIKE '%8%' union select 9 as num, count(*) as count from listing where amenities LIKE '%9%' union select 10 as num, count(*) as count from listing where amenities LIKE '%10%' order by count desc limit 3";
            String query = sql;

            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            features.add(rs.getInt("num"));
            rs.next();
            features.add(rs.getInt("num"));
            rs.next();
            features.add(rs.getInt("num"));

            rs.close();
            stmt.close();
            return features;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean deleteUser(int uid){
        try {
            Boolean ret = true;
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Users WHERE userID = %d";
            String query = String.format(sql, uid);
            int rs = stmt.executeUpdate(query);


            sql = "DELETE FROM Listing WHERE userID = %d"; //
            query = String.format(sql, uid);
            rs = stmt.executeUpdate(query);


            sql = "DELETE FROM Booking WHERE userID = %d";
            query = String.format(sql, uid);
            rs = stmt.executeUpdate(query);

            if (rs != 1) {
                ret = false;
            }

            stmt.close();
            return ret;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean verifyAge(String date) {
        boolean valid=false;
        try {
            Statement stmt = conn.createStatement();
            String sql = "select '%s'<NOW()-INTERVAL 18 YEAR as valid;";
            String query = String.format(sql, date);

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            valid=rs.getBoolean("valid");

            rs.close();
            stmt.close();
            return valid;

        } catch (SQLException e) {
            System.err.println("Connection error occured!");
            System.err.println(e.getMessage());
            return false;
        }
    }

}
