import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec("echo $MYPROJECT");
			InputStream is = p.getInputStream();  
			InputStreamReader isr = new InputStreamReader(is);  
			BufferedReader br = new BufferedReader(isr);  
			String line = null;  
			while ((line = br.readLine()) != null) {  
                System.out.println(line);  
            }  
            is.close();  
            isr.close();  
            br.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
