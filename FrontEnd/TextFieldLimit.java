package frontEnd;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

public class TextFieldLimit extends PlainDocument {
  /**
   * the maximum number of characters to be endered into a given text field
   */
  private int limit;

  TextFieldLimit(int limit) {
   super();
   this.limit = limit;
   }
  	/**
  	 * stops the text field from allowing chars when more than limit chars are entered.
  	 */
    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
      if (str == null) return;
      else if ((getLength() + str.length()) <= limit) {
        super.insertString(offset, str, attr);
      }
    }
  }
