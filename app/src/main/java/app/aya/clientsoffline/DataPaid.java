package app.aya.clientsoffline;

/**
 * Created by egypt2 on 27-Dec-18.
 */

public class DataPaid  {

    String paid_id ;
    String client_Name;
    String user_cash;
    String user_buy;
    String paid_details ;
    String user_date;
    String remainder  ;

    public DataPaid(){}

    public DataPaid(String paid_id, String client_Name, String user_cash, String user_buy, String paid_details, String user_date, String remainder) {
        this.paid_id = paid_id;
        this.client_Name = client_Name;
        this.user_cash = user_cash;
        this.user_buy = user_buy;
        this.paid_details = paid_details;
        this.user_date = user_date;
        this.remainder = remainder;
    }

    public String getPaid_id() {
        return paid_id;
    }

    public String getClient_Name() {
        return client_Name;
    }

    public String getUser_cash() {
        return user_cash;
    }

    public String getUser_buy() {
        return user_buy;
    }

    public String getPaid_details() {
        return paid_details;
    }

    public String getUser_date() {
        return user_date;
    }

    public String getRemainder() {
        return remainder;
    }
}
