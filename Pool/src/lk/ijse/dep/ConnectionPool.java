package lk.ijse.dep;

import java.sql.Connection;
import java.util.ArrayList;

public class ConnectionPool {
    private static Connection[] connectionpool= new Connection[5];
    private static Connection[] consumingpool= new Connection[5];
    private ArrayList<Thread> clients = new ArrayList<>();
    private static ConnectionPool pool =null;

    private ConnectionPool(){
        DBConnection dbConnection = new DBConnection();
        for (int i=0; i<connectionpool.length;i++){
            connectionpool[i] = dbConnection.getConnection();
        }
    }

    public Connection getConnection(){
        //Thread thread=Thread.currentThread();
        for (int i=0;i<connectionpool.length;i++){
            if (connectionpool[i]!=null){
                consumingpool[i]=connectionpool[i];
                return consumingpool[i];
            }
            else if (i==4){
                try {
                    synchronized (ConnectionPool.class){
                        ConnectionPool.class.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i=0;i<connectionpool.length;i++){
            if (connectionpool[i]!=null){
                consumingpool[i]=connectionpool[i];
                return consumingpool[i];
            }
        }
        return null;
    }

    public void releaseConnection(Connection connection){
        for (int i=0;i<connectionpool.length;i++){
            if (connectionpool[i]==null){
                connectionpool[i]=connection;
                consumingpool[i]=null;

                ConnectionPool.class.notify();
            }
        }
    }

    public static ConnectionPool getInstance(){
        if (pool == null){
            return  new ConnectionPool();
        }
        else
            return pool;
    }

}
