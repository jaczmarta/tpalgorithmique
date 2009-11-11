package gui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

public class ErrorMessage extends Window {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5703762122313832867L;

	public ErrorMessage(Exception e) {
		Object[] options = {"OK", "Details"};
		int n = JOptionPane.showOptionDialog(this,
		    "Erreur inattendue:\n"
		    + e.getMessage(),
		    "Erreur",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);

		if (n == JOptionPane.NO_OPTION) {
			StringWriter stackTrace = new StringWriter();
			e.printStackTrace(new PrintWriter(stackTrace));
			JOptionPane.showMessageDialog(this,
				    "Error detail :\n"+stackTrace,
				    "Details",
				    JOptionPane.WARNING_MESSAGE);
		}
		return;
	}

}
