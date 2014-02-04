package eu.pryds.misermeter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class BitcoinAddress {
    private String address;
    private double balance; // in BTC
    private String comment;
    
    public BitcoinAddress(String address, String comment) {
        this.address = address;
        this.balance = 0.0;
        this.comment = comment;
    }
    
    public void updateFromFeed() {
        String feedUrl = "http://blockchain.info/da/q/addressbalance/";
        long satoshi = Long.parseLong(readStringFromHTTP(feedUrl + address));
        Log.d("FeedUpdate", "Address result: " + satoshi + " - Address: " + address);
        balance = satoshi * 0.00000001;
    }
    
    
    public String getAddress() {
        return address;
    }
    
    public String getBalanceAsString() {
        return String.format("BTC %.8f", balance);
    }
    
    public String getComment() {
        return comment;
    }
    
    public String getConvertedBalanceAsString() {
        return String.format("DKK %.2f", balance * 4827.0);
    }
    
    private String readStringFromHTTP(String urlString) {
        BufferedReader in;
        try {
            // Create a URL for the desired page
            URL url = new URL(urlString);

            // Read all the text returned by the server
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            if ((str = in.readLine()) != null) {
                return str;
            }
            in.close();
        } catch (Exception e) {
            Log.d("FeedUpdate", "Error: " + e.toString());
        }
        return "0";
    }
}