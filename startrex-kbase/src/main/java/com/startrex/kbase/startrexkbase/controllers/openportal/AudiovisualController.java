package com.startrex.kbase.startrexkbase.controllers.openportal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.startrex.kbase.startrexkbase.controllers.AudiovisualRestController;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseCode;
import com.startrex.kbase.startrexkbase.helpers.response.STResponseComponent;

@Controller
@RequestMapping("openportal/video")
public class AudiovisualController {
	
	@Autowired
	private AudiovisualRestController avRestController;
	
	@RequestMapping(method=RequestMethod.GET, value= "{topicId}/primary")
	public String getCourseContent(HttpServletRequest request, 
							Model model,
							Pageable pageable,
							@PathVariable(value="topicId") String topicId) {
		
		try {
			STResponseComponent sTResponseComponent = avRestController.getFirstByTopic(request, topicId);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("audiovisual", sTResponseComponent.context.get("audiovisual"));
				model.addAttribute("audiovisuals", sTResponseComponent.context.get("audiovisuals"));				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "video";
	}
	
	@RequestMapping(method=RequestMethod.GET, value= "{id}/{title}")
	public String get(HttpServletRequest request, 
							Model model,
							Pageable pageable,
							@PathVariable(value="id") String id) {
		
		try {
			STResponseComponent sTResponseComponent = avRestController.get(request, id);
			if(sTResponseComponent.responseCode == STResponseCode.OK) {
				model.addAttribute("audiovisual", sTResponseComponent.context.get("audiovisual"));
				model.addAttribute("audiovisuals", sTResponseComponent.context.get("audiovisuals"));				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "video";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="thumbnail/{url}")
	public BufferedImage loadVideoThumbnail(@PathVariable(value="url") String url) {
		File file;
		BufferedImage image = null;
        
		try {
			file = new File(url);
			image = ImageIO.read(file);
	        
		}catch(IOException ex) {
			
		}
		return image;
	}
	

}
