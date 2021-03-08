package controller.dbqueries;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionLog {

    public static void write(Exception e){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("error.log",true));
            //print date
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d = new Date(System.currentTimeMillis());
            pw.println(df.format(d));
            //print error
            pw.println(e.getMessage());
            e.printStackTrace(pw);
            pw.println();

            pw.flush();
            pw.close();

            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("---------------------------");
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }
    }
}
