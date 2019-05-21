package populate;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Parser {

 public static void main(String[] args) {
  // CSV file
  File file = new File("assets/datafiles/tags.dat");
  Scanner sc = null;
  try {
   sc = new Scanner(file);
   // Check if there is another line of input
   while(sc.hasNextLine()){
    String str = sc.nextLine();
    parseLine(str);
   }
   
  } catch (IOException  exp) {
   // TODO Auto-generated catch block
   exp.printStackTrace();
  }
  
  sc.close();
 }
 
 private static void parseLine(String str){
  String id, value;
  Scanner sc = new Scanner(str);
  sc.useDelimiter("\t");
  int i = 0;
  // Check if there is another line of input
  while(i < 5){
   id = sc.next();
   value = sc.next();
   i++;
   System.out.println("ID - " + id + " Author - " + value);  
  }
  sc.close();
 }
}