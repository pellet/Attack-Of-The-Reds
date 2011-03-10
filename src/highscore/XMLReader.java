package highscore;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader
{
	public static short NUM_OF_SCORES = 10;
	protected Score[] highScores;
	
	public Score[] getScores()
	{
		return highScores;
	}
	
	public void saveXML()
	{
	  try 
	  {
		  File file = new File("myxml.xml");
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document doc = db.parse(file);
		  doc.getDocumentElement().normalize();
		  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		  NodeList nodeLst = doc.getElementsByTagName("player");
		  System.out.println("Information of all players");
		  
		  highScores = new Score[NUM_OF_SCORES];
		
		  int numofScores = nodeLst.getLength();
		  for (int s = 0; s < numofScores ; s++) 
		  {
			    Node fstNode = nodeLst.item(s);
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE)
			    {
				      Score currentScore = highScores[s];
				      
				      Element fstElmnt = (Element) fstNode;
				      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("name");
				      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				      NodeList nameData = fstNmElmnt.getChildNodes();
				      nameData.item(0).setNodeValue(currentScore.name);
//				      currentScore.name = ((Node) nameData.item(0)).getNodeValue();
				      System.out.println("Name : "  + currentScore.name);
				      
				      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("score");
				      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				      NodeList scoreData = lstNmElmnt.getChildNodes();
				      scoreData.item(0).setNodeValue(Integer.toString(currentScore.score));
//				      currentScore.score = Integer.valueOf(((Node) scoreData.item(0)).getNodeValue());
				      System.out.println("Score : " + currentScore.score);
				      
				      NodeList fbEList = fstElmnt.getElementsByTagName("facebookId");
				      Element fbE = (Element) fbEList.item(0);
				      NodeList fbId = fbE.getChildNodes();
				      fbId.item(0).setNodeValue(currentScore.facebookId);
//				      currentScore.facebookId = ((Node) fbId.item(0)).getNodeValue();
				      System.out.println("Facebook ID : " + currentScore.facebookId);
			    }
		
		  }
	  }catch (Exception e) 
	  {
	    e.printStackTrace();
	  }
	}
	
	public XMLReader()
//	public static void main(String argv[])
	{

	  try 
	  {
		  File file = new File("myxml.xml");
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document doc = db.parse(file);
		  doc.getDocumentElement().normalize();
		  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		  NodeList nodeLst = doc.getElementsByTagName("player");
		  System.out.println("Information of all players");
		  
		  highScores = new Score[NUM_OF_SCORES];
		
		  int numofScores = nodeLst.getLength();
		  for (int s = 0; s < numofScores ; s++) 
		  {
			    Node fstNode = nodeLst.item(s);
			    if (fstNode.getNodeType() == Node.ELEMENT_NODE)
			    {
				      Score currentScore = new Score();
				      
				      Element fstElmnt = (Element) fstNode;
				      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("name");
				      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				      NodeList nameData = fstNmElmnt.getChildNodes();
				      currentScore.name = ((Node) nameData.item(0)).getNodeValue();
				      System.out.println("Name : "  + currentScore.name);
				      
				      NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("score");
				      Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				      NodeList scoreData = lstNmElmnt.getChildNodes();
				      currentScore.score = Integer.valueOf(((Node) scoreData.item(0)).getNodeValue());
				      System.out.println("Score : " + currentScore.score);
				      
				      NodeList fbEList = fstElmnt.getElementsByTagName("facebookId");
				      Element fbE = (Element) fbEList.item(0);
				      NodeList fbId = fbE.getChildNodes();
				      currentScore.facebookId = ((Node) fbId.item(0)).getNodeValue();
				      System.out.println("Facebook ID : " + currentScore.facebookId);
				      
				      highScores[s] = currentScore;
			    }
		
		  }
	  }catch (Exception e) 
	  {
	    e.printStackTrace();
	  }
	}
}