package View;

import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardPanel extends BasePanel {

    private BufferedImage boardImg;
    private Map<String, BufferedImage> playerPinsImg = new HashMap<String, BufferedImage>();
    private List<String> activePlayerList = new ArrayList<String>();

    public BoardPanel(int width, int height) 
    {
        super(width, height);
        
        LoadImages();
    }
    
    public void LoadImages()
    {
    	try 
    	{
            boardImg = ImageIO.read(getClass().getResource("/resources/tabuleiro.png"));
            
            BufferedImage currentPlayerPin;
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin0.png"));
            playerPinsImg.put("Red", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin1.png"));
            playerPinsImg.put("Blue", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin2.png"));
            playerPinsImg.put("Orange", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin3.png"));
            playerPinsImg.put("Yellow", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin4.png"));
            playerPinsImg.put("Purple", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin5.png"));
            playerPinsImg.put("Grey", currentPlayerPin);
        } 
    	catch (IOException e) 
    	{
    		System.err.println("Image file not found");
            e.printStackTrace();
        }
    }
    
    public void AddPlayer(String color) 
    {
    	activePlayerList.add(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (boardImg != null) 
        {
            g2d.drawImage(boardImg, 0, 0, getWidth(), getHeight(), this);
        }
        int i = 0;
        for (String playerColor : activePlayerList) 
        {
        	g2d.drawImage(playerPinsImg.get(playerColor), i * 50, 0, 25, 40, this);
        	i++;
        }
    }
    
    private Map<String, BufferedImage> propertyCards() {
    	Map<String, BufferedImage> propertyCards = new HashMap<String, BufferedImage>();
    	
    	try {
    		BufferedImage currentPropertyCard;
    		
    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_9_de_julho.png"));
    		propertyCards.put("av_9_de_julho", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_atlantica.png"));
    		propertyCards.put("av_atlantica", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_brasil.png"));
    		propertyCards.put("av_brasil", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_brigadero_faria_lima.png"));
    		propertyCards.put("av_brigadero_faria_lima", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_europa.png"));
    		propertyCards.put("av_europa", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_nossa_s..de_copacabana.png"));
    		propertyCards.put("av_nossa_s..de_copacabana", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_pacaembu.png"));
    		propertyCards.put("av_pacaembu", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_paulista.png"));
    		propertyCards.put("av_paulista", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_presidente_vargas.png"));
    		propertyCards.put("av_presidente_vargas", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_reboucas.png"));
    		propertyCards.put("av_reboucas", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/av_vieira_souto.png"));
    		propertyCards.put("av_vieira_souto", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Botafogo.png"));
    		propertyCards.put("Botafogo", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Brooklin.png"));
    		propertyCards.put("Brooklin", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Copacabana.png"));
    		propertyCards.put("Copacabana", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Flamengo.png"));
    		propertyCards.put("Flamengo", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Interlagos.png"));
    		propertyCards.put("Interlagos", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Ipanema.png"));
    		propertyCards.put("Ipanema", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/jardim.europa.png"));
    		propertyCards.put("jardim.europa", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/jardim_paulista.png"));
    		propertyCards.put("jardim_paulista", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Leblon.png"));
    		propertyCards.put("Leblon", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/Morumbi.png"));
    		propertyCards.put("Morumbi", currentPropertyCard);

    		currentPropertyCard = ImageIO.read(getClass().getResource("/resources/cards/properties/rua_augusta.png"));
    		propertyCards.put("rua_augusta", currentPropertyCard);
    		
    	}
    	
    	catch(IOException e) {
    		System.err.println("Image file not found");
            e.printStackTrace();
    	}
    	
    	return propertyCards;
    }
    
    
    
}
