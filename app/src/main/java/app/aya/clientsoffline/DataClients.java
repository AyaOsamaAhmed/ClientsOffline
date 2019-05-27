package app.aya.clientsoffline;

/**
 * Created by egypt2 on 20-Dec-18.
 */

public class DataClients {

    String user_id ;
    String client_name;
    String user_card;
    String user_phone;
    String user_cash;
    String user_buy;
    String user_date;
    String remainder ;

    public DataClients(){}


    public DataClients(String user_id, String client_name, String user_card, String user_phone, String user_cash, String user_buy, String user_date, String remainder) {
        this.user_id = user_id;
        this.client_name = client_name;
        this.user_card = user_card;
        this.user_phone = user_phone;
        this.user_cash = user_cash;
        this.user_buy = user_buy;
        this.user_date = user_date;
        this.remainder = remainder;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getUser_card() {
        return user_card;
    }

    public String getUser_phone() {
        return user_phone;
    }
    public String getUser_cash() {
        return user_cash;
    }

    public String getUser_buy() {
        return user_buy;
    }

    public String getUser_date() {
        return user_date;
    }

    public String getRemainder() {
        return remainder;
    }
}
