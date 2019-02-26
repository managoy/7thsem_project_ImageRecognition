




import java.io.File;
import javax.swing.filechooser.FileFilter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SUYOG
 */
public class ImageFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        
    if(file.isDirectory()){
        return true;
    }
    String ext= Utils.getExtension(file);
         if (ext != null) {
            if (ext.equals(Utils.tiff) ||
                ext.equals(Utils.tif) ||
                ext.equals(Utils.gif) ||
                ext.equals(Utils.jpeg) ||
                ext.equals(Utils.jpg) ||
                ext.equals(Utils.png)) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
   
    
    
    
    
    }

    @Override
    public String getDescription() {
    return "Only Images";
    }
    
    
    
}
