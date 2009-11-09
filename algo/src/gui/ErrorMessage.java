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
		StringWriter stackTrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stackTrace));
		JOptionPane.showMessageDialog(this,
			    "Error detail :\n"+stackTrace,
			    "Details",
			    JOptionPane.WARNING_MESSAGE);
	}

}
