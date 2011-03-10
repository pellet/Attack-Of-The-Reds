package game.Display;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

import util.H_DC;

//import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;


public class SpriteCache 
{
	//Max Total stipple Textures
	protected static final short MAX_TEXTURES = 100;
	
	//Textures path
	private static final String RESOURCEPATH = "res/";
	
	//Textures
	private HashMap<String, BufferedImage> sprites;
	
	//Loaded Texture IDs
	private HashMap<String, Integer> textures;
	
	//Polygon Stipples(Masks) for loaded textures.
	protected HashMap<Integer, ByteBuffer> textureMasks;
	
	
    /** The colour model including alpha for the GL image */
    private ColorModel glAlphaColorModel;
    
    /** The colour model for the GL image */
    private ColorModel glColorModel;
    
    /** The texture dump **/
	ByteBuffer byteBuffer = null;

	
	SpriteCache()
	{
		sprites = new HashMap<String, BufferedImage>();
		textures = new HashMap<String, Integer>();
		textureMasks = new HashMap<Integer, ByteBuffer>();
		
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);
                
		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
		                new int[] {8,8,8,0},
		                false,
		                false,
		                ComponentColorModel.OPAQUE,
		                DataBuffer.TYPE_BYTE);

	}
	
	private BufferedImage loadImage(String name)
	{
		URL url = null;
		try 
		{
			url = getClass().getClassLoader().getResource(RESOURCEPATH + name);
			return ImageIO.read(url);
		}catch(Exception e) 
		{
			File f = new File(RESOURCEPATH + name);
			try
			{
				BufferedImage bi = ImageIO.read(f);
				return bi;
			} catch (IOException e1)
			{
				System.out.println("Resource file not found : " + RESOURCEPATH + name);
				System.exit(1);
				return null;
			}
		}
	}
	
	 
	public void bindTexture(String imageName, H_DC dc)
	{
		GL gl = dc.getGL();
		
		Integer textureId = (Integer) textures.get(imageName);
		if(textureId==null)
		{	
			loadTexture(dc, imageName);
			
		}else//Bind texture and mask from memory
		{
			gl.glBindTexture(GL.GL_TEXTURE_2D, textureId.intValue());
			//ByteBuffer stipple = textureMasks.get(textureId.intValue());
		}
	}
	
	
	public BufferedImage getSprite(String name)
	{
		
		BufferedImage image = (BufferedImage)sprites.get(name);
		if (image == null)
		{
			image = loadImage(name);
			sprites.put(name, image);
		}
		return image;
	}
	
	
	/**
	 * Set texture
	 * 
	 * @param gl
	 */
	protected void loadTexture(H_DC dc, String name)
	{
		GL gl = dc.getGL();
		
		//Load texture.
		int id[] = new int[1];
		
		//generate a number to associate texture with and place it into 'textureID'
		gl.glGenTextures(1, id,0);
        
		//Get Image data
		BufferedImage image = getSprite(name);

		//Specify texturing style
		boolean isAlpha = getByteBuffer(image);

		//select the texture to work with
		gl.glBindTexture(GL.GL_TEXTURE_2D, id[0]);
	    
	    //Add texture to video memory
		int width = image.getWidth();
		int height = image.getHeight();
		try
		{
			if(isAlpha)
			{
				gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, width,
					        height, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, byteBuffer);
			}else
			{
				gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, width,
				        height, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, byteBuffer);
			}
		    
			//Place id into hashtable.
			Integer textureId = Integer.valueOf(id[0]);
			textures.put(name, textureId);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	/**
	 * Extract raster data from buffered image to use in video memory.
	 *  
	 * @param bufferedImage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean getByteBuffer(BufferedImage bufferedImage)
	{
		boolean isAlpha;
        WritableRaster raster;
        BufferedImage texImage;
        
        int texWidth = bufferedImage.getWidth();
        int texHeight = bufferedImage.getHeight();
		
        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
            isAlpha = true;
        }else
        {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
            texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
            isAlpha = false;
        }
            
        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f,0f,0f,0f));
        g.fillRect(0,0,texWidth,texHeight);
        g.drawImage(bufferedImage,0,0,null);

		
        // build a byte buffer from the temporary image 
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        byteBuffer = ByteBuffer.allocateDirect(data.length);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(data, 0, data.length);
        byteBuffer.flip();
        
        return isAlpha;
	}
	
//	/**
//	 * Extract alpha data to use as stipple
//	 *  
//	 * @param bufferedImage
//	 * @return
//	 */
//	protected ByteBuffer getMask(BufferedImage bufferedImage)
//	{
//        ByteBuffer byteBuffer = null; 
//        WritableRaster raster;
//        BufferedImage texImage;
//        
//        int texWidth = bufferedImage.getWidth();
//        int texHeight = bufferedImage.getHeight();
//		
//        // create a raster that can be used by OpenGL as a source
//        // for a texture
//        assert (bufferedImage.getColorModel().hasAlpha());
//        {
//            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
//            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
//        }
//            
//        // copy the source image into the produced image
//        Graphics g = texImage.getGraphics();
//        g.setColor(new Color(0f,0f,0f,0f));
//        g.fillRect(0,0,texWidth,texHeight);
//        g.drawImage(bufferedImage,0,0,null);
//
//		
//        // build a byte buffer from the temporary image 
//        // that be used by OpenGL to produce a texture.
//        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
//        
//        byte[] alphaData = new byte[data.length/4];
//        for(int i=0;i<alphaData.length;i++)
//        {
//        	alphaData[i] = (byte) 0xcf;
//        }
//        //Read alpha values
//        final short byteBits = 8;
//        
////        for(int alpha=0, bit=0, i=0;alpha<data.length; alpha+=4, bit++)
////        {
////            if(bit==byteBits)
////            {
////            	bit=0;
////            	i++;
////            }
////            
////        	if(data[alpha]!=0)
////        		alphaData[i] |= (byte) (1 << bit);
////        }
//
//        byteBuffer = ByteBuffer.allocateDirect(alphaData.length);
//        byteBuffer.order(ByteOrder.nativeOrder()); 
//        byteBuffer.put(alphaData, 0, alphaData.length);
//        byteBuffer.flip();
//        
//        return byteBuffer;
//	}
}