package jogo.ui.gui.resources;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageLoader {
    static HashMap<String, Image> loadedImages;

    static {
        loadedImages = new HashMap<>();
    }

    private ImageLoader(){}

    public static Image getImage(String fileName) {
        Image img = loadedImages.get(fileName);

        if (img != null)
            return img;

        try {
            img = new Image(Resources.getResourceFileAsStream("images/"+fileName));
            loadedImages.put(fileName,img);
            return img;
        } catch (Exception e) {
            return null;
        }
    }

    public static Image getImageForce(String fileName) {
        loadedImages.remove(fileName);
        return getImage(fileName);
    }
}
