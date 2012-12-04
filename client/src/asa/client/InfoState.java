package asa.client;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import asa.client.resources.Resource;
import java.util.ArrayList;
import service.Device;

public class InfoState extends ArduinoGameState {

	ServerAdapter server;
	Image tandwiel1;
	Image tandwiel2;
	Image background;
	Image spinner;
	Image background_spinner;
	Image spinneroverlay;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	List<WheelOption> wheelOptions = new ArrayList<WheelOption>();
			
	
	int targetrotation = 0;
	int tandwielOffset = 30;
	
	float rotation = 0;
	float spinnerrotation = 0;
	double rotationEase = 5.0;
	
	Dimension screenSize;
	Dimension center;
	
	int selectionDegrees = 45;
	int selectedOption = 0;
	int oldSelectedOption = 0;
	
	public InfoState(int stateID, ServerAdapter server) {
		super(stateID);
		this.server = server;
		loadWheelOptions();
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		arduino.addListener(new ArduinoAdapter() {
			@Override
			public void wheelEvent(int direction, int speed) {
				if(direction == 1){
					targetrotation += 3*speed;
				} else {
					targetrotation -= 3*speed;
				}
			}
		});
		
		center = new Dimension(AsaGame.SOURCE_RESOLUTION.width / 2 - 100, AsaGame.SOURCE_RESOLUTION.height / 2);
		selectionDegrees = 360/wheelOptions.size();
		tandwiel1 = new Image(Resource.getPath(Resource.TANDWIEL5));
		tandwiel2 = new Image(Resource.getPath(Resource.TANDWIEL6));
		spinner = new Image(Resource.getPath(Resource.SPINNER));
		spinneroverlay = new Image(Resource.getPath(Resource.SPINNER_OVERLAY));
		background_spinner = new Image(Resource.getPath(Resource.BACKGROUND_SPINNER));
		background = new Image(Resource.getPath(Resource.BACKGROUND_KOFFIE));
		
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		background.draw(0,0);
		tandwiel1.draw(-tandwiel1.getWidth()/2, AsaGame.SOURCE_RESOLUTION.height/2-tandwiel1.getHeight()/2);
		tandwiel2.draw(tandwiel1.getWidth()/2-tandwielOffset-40, AsaGame.SOURCE_RESOLUTION.height / 2 - tandwiel2.getHeight());
		spinner.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		spinneroverlay.draw(center.getWidth() - spinner.getWidth() / 2, center.getHeight() - spinner.getHeight() / 2);
		background_spinner.draw(center.getWidth() - background_spinner.getWidth() / 2, center.getHeight() - background_spinner.getHeight() / 2);
		
		for(int i = 0; i < wheelOptions.size(); i++){
			float offsetDegree = 360/wheelOptions.size();
			float degrees = 270 + ((rotation + offsetDegree*i) % 360);
			float rad = (float) (degrees * (Math.PI / 180));
			float radius = 310;
			
			float x = (float) (center.getWidth() + radius * Math.cos(rad));
			float y = (float) (center.getHeight() + radius * Math.sin(rad));
			
			WheelOption option = wheelOptions.get(i);
			Image optionIcon = option.getIcon();
			x = x - optionIcon.getWidth()/2;
			y = y - optionIcon.getHeight()/2;
			option.getIcon().draw(x, y);
			
			//TODO: find correct selection
			//TODO: animate background switch
			if(degrees > 270-(selectionDegrees/2) && degrees < 270+(selectionDegrees/2)){
				oldSelectedOption = selectedOption;
				background = option.background();
				selectedOption = i;
			}
			logger.debug(option.getDescription() + " : " + degrees);
		}
		
		WheelOption option = wheelOptions.get(selectedOption);
		logger.debug(option.getDescription());
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		super.update(gameContainer, stateBasedGame, delta);
		rotation += (targetrotation - rotation) / rotationEase;
		tandwiel1.setRotation(rotation);
		tandwiel2.setRotation((float) ((float) -(rotation*1.818181818181818)+16.36363636363636));
		spinner.setRotation(rotation);
	}

	private void loadWheelOptions() {
		//wheelOptions.clear();
		List<Device> deviceList = server.getAllDevices();
		for(Device device : deviceList){
			wheelOptions.add(new WheelOption(device.getName(), device.getLogoUrl(), device.getPhotoUrl()));
		}
	}

}