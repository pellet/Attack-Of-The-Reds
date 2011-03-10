/**
 * 
 */
package highscore;

/**
 * @author pettit
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Highscore {
	
	protected final String ADDRESS = "localhost/";

	private final String SAVE_URL = "http://"+ADDRESS+"save.php";
	private final String READ_URL = "http://"+ADDRESS+"read.php";
	
	private final String PASS = "*********";
	private final Object SUCCESS = "success";
	
	
	public boolean recordScore(String name, int score) throws WebException {
		String address = SAVE_URL + "?name=" + name + "&score=" + score + "&pass=" + PASS;
		try
		{
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = r.readLine();
			if (line.equals(SUCCESS)) {
				return true;
			}
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			throw new WebException("Failed to write high score to server.");
		}
	}
	
	public String[] retrieveScores() throws WebException {
		String address = READ_URL;
		try {
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			List<String> scores = new ArrayList<String>();
			String score;
			while ((score = r.readLine()) != null)
			{
				scores.add(score);
			}
			return scores.toArray(new String[0]);
		} catch (Exception e) {
			throw new WebException("Failed to read high score from server.");
		}
	}
	
	public void main(String[] args) 
	{
//		try {
//			String[] ss = retrieveScores();
//			for (String s : ss) {
//				System.out.println(s);
//			}
//		} catch (WebException e) {
//			e.printStackTrace();
//		}
	}
}

