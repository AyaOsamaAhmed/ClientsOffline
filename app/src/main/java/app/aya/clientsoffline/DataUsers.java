package app.aya.clientsoffline;

/**
 * Created by egypt2 on 20-Dec-18.
 */

public class DataUsers {

    String user_id ;
    String user_Name;
    String name ;
    String password ;
    String user_phone;
    String user_date;
    String active;

    public DataUsers(){}

    public DataUsers(String user_id, String user_Name, String name, String password, String user_phone, String user_date, String active) {
        this.user_id = user_id;
        this.user_Name = user_Name;
        this.name = name;
        this.password = password;
        this.user_phone = user_phone;
        this.user_date = user_date;
        this.active = active;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_date() {
        return user_date;
    }

    public String getActive() {
        return active;
    }
}
