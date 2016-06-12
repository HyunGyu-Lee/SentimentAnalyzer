package tweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.JTextArea;

public class cmdTest {

	String fName;
	public cmdTest(JTextArea area, String fName)
	{
		long start, finish;
		this.fName = fName;
		area.append("Create batch file\n");
		area.append("Batchfile name : "+"c:\\resource\\tweet.bat\n");
		startup();
		try
		{		
			start = System.currentTimeMillis();
			Process process = Runtime.getRuntime().exec("c:\\resource\\tweet.bat");
			
			BufferedReader consoleIn = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String a;
			
			while(true)
			{
				a = consoleIn.readLine();
				if(a==null)break;
				area.append(a+"\n");
			}
		
			process.destroy();
			
			finish = System.currentTimeMillis();
			area.append("Runtime : "+(finish-start)/1000+"sec\n");
		}catch(IOException e){System.err.println(e);}
	}

	
	
	public void startup()
	{		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\resource\\tweet.bat")));
			bw.write("cd c:\\\\resource\\\\cnuma\\\\cnuma\\\\py_ver");
			bw.newLine();
			bw.write("ma.bat "+"c:\\\\resource\\\\"+fName.split("\\\\")[2]+".txt");
			bw.newLine();
			bw.write("pause");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
