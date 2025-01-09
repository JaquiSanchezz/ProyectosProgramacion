/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ImageLoginView extends JPanel {
     //[400, 600]
    private URL url = getClass().getResource("imagen.jpg");
    Image image = new ImageIcon(url).getImage();

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0,0, 460, 600, this);
        setOpaque(false);
        super.paint(g);
    }
}
