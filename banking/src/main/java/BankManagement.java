import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BankManagement {
    static Connection con = ConnectionDB.getConnection();

    public static boolean createAccount(String name, int passcode){
        if(name.isEmpty() || passcode <= 0){
            System.out.println("Invalid name or passcode");
            return false;
        }
        try{
            String query = "insert into customer(name, passpin) values(?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, passcode);
            int res = ps.executeUpdate();
            if(res == 1){
                System.out.println("Account created successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Error creating account: " + e.getMessage());
        }
        return false;
    }

    public static boolean loginAccount(String name, int passcode){
        if(name.isEmpty() || passcode <= 0){
            System.out.println("Invalid name or passcode");
            return false;
        }
        try{
            String query = "select * from customer where name=? and passpin=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, passcode);
            ResultSet rs = ps.executeQuery();
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            if(rs.next()){
                int senderac = rs.getInt("id");
                int ch;
                while(true){
                    System.out.println("\n Hello, " + rs.getString("name") + "! What would you like to do?");
                    System.out.println("1) Transfer Money");
                    System.out.println("2) View Balance");
                    System.out.println("3) Logout");
                    System.out.print("Enter Choice: ");
                    ch = Integer.parseInt(sc.readLine());
                    if (ch == 1) {
                        System.out.print("Enter Receiver A/c No: ");
                        int receiverAc = Integer.parseInt(sc.readLine());
                        System.out.print("Enter Amount: ");
                        int amt = Integer.parseInt(sc.readLine());

                        if (transferMoney(senderac, receiverAc, amt)) {
                            System.out.println("Transaction successful!");
                        } else {
                            System.out.println("Transaction failed! Please try again.");
                        }
                    } else if (ch == 2) {
                        getBalance(senderac);
                    } else if (ch == 3) {
                        System.out.println("Logged out successfully. Returning to main menu.");
                        break;
                    } else {
                        System.out.println("Invalid choice! Try again.");
                    }
                }
                return true;
            }
            else {
                System.out.println("Invalid username or password!");
                return false;
            }
        }
        catch(Exception e){
            System.out.println("Error logging in: " + e.getMessage());
        } 
        return false;  
    }

    public static void getBalance(int acno){
        try{
            String query = "select balance from customer where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, acno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("\n-------------------------------------------------");
                System.out.println("Your current balance is: " + rs.getInt("balance"));
                System.out.println("\n-------------------------------------------------");
            }
        }
        catch(Exception e){
            System.out.println("Error fetching balance: " + e.getMessage());
        }
    }

    public static boolean transferMoney(int sender, int reciver, int amount){
        if(reciver == 0 && sender == 0){
            System.out.println("Invalid account numbers");
            return false;
        }
        if(amount <= 0){
            System.out.println("Invalid amount");
            return false;
        }
        try{
            con.setAutoCommit(false);
            String checkBalance = "SELECT balance FROM customer WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(checkBalance);
            ps.setInt(1, sender);
            ResultSet rs = ps.executeQuery();
            if(rs.next() && rs.getInt("balance")<amount){
                System.out.println("Insufficient balance");
                return  false;
            }
            String debit = "update customer set balance = balance - ? where id = ?";
            PreparedStatement psd = con.prepareStatement(debit);
            psd.setInt(1,amount);
            psd.setInt(2, sender);
            psd.executeUpdate();

            String credit = "UPDATE customer SET balance = balance + ? WHERE id = ?";
            PreparedStatement psCredit = con.prepareStatement(credit);
            psCredit.setInt(1, amount);
            psCredit.setInt(2, reciver);
            psCredit.executeUpdate();
            con.commit();
            return true;
        }
        catch(Exception e){
            try{
                con.rollback();
            }
            catch(Exception ex){
                System.out.println("Error rolling back: " + ex.getMessage());
            }
            System.out.println("Error transferring money: " + e.getMessage());
        }
        return false;
    }
}
