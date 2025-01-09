/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;


public class HintPasswordField extends JPasswordField implements FocusListener {
    
  private final String hint;
  private boolean showingHint;

  public HintPasswordField(final String hint) {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    this.setEchoChar((char) 0);
    super.addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    if(this.getText().isEmpty()) {
      super.setText("");
      showingHint = false;
     this.setEchoChar('*');
    }
  }
  @Override
  public void focusLost(FocusEvent e) {
    if(this.getText().isEmpty()) {
      super.setText(hint);
      showingHint = true;
      this.setEchoChar((char) 0);
      
    }
  }

  @Override
  public String getText() {
      
    return showingHint ? "" : super.getText();
  }

    
}
