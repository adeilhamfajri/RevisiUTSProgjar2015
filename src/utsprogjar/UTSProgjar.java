/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utsprogjar;
import java.io.*;
import java.net.Socket;
import java.util.*;


/**
 *
 * @author Ade Ilham Fajri
 */
public class UTSProgjar {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
         
            Socket socket = new Socket("10.151.34.155", 6666);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            while (true) {
                byte[] buf = new byte[1];
                is.read(buf);
                String cmd = new String(buf);
                
                while (!cmd.endsWith("\n")) {
                    is.read(buf);
                    cmd += new String(buf);
                }
                
                System.out.print("Server: " + cmd);
                 if(cmd.equals("Hash:\n")) {
                    byte[] dum = new byte[1];
                    is.read(dum);
                    String arguments = new String(dum);
                    while (!arguments.endsWith("\n")) {
                        is.read(dum);
                        arguments += new String(dum);
                    }
                    System.out.print(arguments);
                    arguments = arguments.trim();
                    
                    String[] arg = arguments.split(":");
                    int len = Integer.parseInt(arg[1]);
                    
                    String respond = "Hash:";
                    for(int i=0;i<len;i++) {
                        is.read(dum);
                        respond += new String(dum);
                    }
                    
                    respond += "\n";
                    System.out.print("Respond: " + respond);
                    os.write(respond.getBytes());
                    os.flush();
                    
                    is.read(dum);
                    is.read(dum); //somehow servernya return line kosong
                    arguments = new String(dum);
                    while (!arguments.endsWith("\n")) {
                        is.read(dum);
                        arguments += new String(dum);
                    }
                    
                    String[] status = arguments.trim().split(" ");
                    
                    if(status[0].equals("666")){
                        System.out.print(arguments);
                        break;
                    } else {
                        System.out.println("Error " + status[0] + "\n");
                    }
                }
                 
                 else if(cmd.endsWith("?\n")) {
                    cmd = cmd.trim();
                    String[] inputs = cmd.split(" ");
                    int A = Integer.parseInt(inputs[0]);
                    int B = Integer.parseInt(inputs[2]);
                    int temp;
                    
                    switch (inputs[1]) {
                        case "+":
                            temp = A+B;
                            break;
                        case "-":
                            temp = A-B;
                            break;
                        case "x":
                            temp = A*B;
                            break;
                        default:
                            temp = A%B;
                            break;
                    }
                    
                    String respond = "result:"+ Integer.toString(temp) + "\n";
                    System.out.print("Respond: " + respond);
                    os.write(respond.getBytes());
                    os.flush();
                }
                 
                 else if(cmd.endsWith("NRP\\n\n")) {
                    Scanner scanner = new Scanner(System.in);
                    String username = scanner.nextLine();
                    username = "Username:" + username + "\n";
                    os.write(username.getBytes());
                    os.flush();
                }
            }
            os.close();
            is.close();
            socket.close();
        }
    }