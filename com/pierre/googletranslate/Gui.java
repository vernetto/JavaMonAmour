package com.pierre.googletranslate;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Gui extends JPanel implements TranslationListener {
	public static final String PROGRESS_DISPLAY = "label2";
	private static final int PREF_W = 800;
	private static final int PREF_H = 400;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Gui() {
		//PlainHTTPConnection.listener = this;

		JLabel label1 = new JLabel("File to prepare and translate:");
		add(label1);

		JTextField txtField = new JTextField("", 60);
		add(txtField);

		JButton prepareButton = new JButton("Prepare!");
		prepareButton.setBorderPainted(true);
		prepareButton.setContentAreaFilled(true);
		add(prepareButton);
		prepareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtField.getText());
				PlainHTTPConnection.setInputFile(txtField.getText());
				try {
					PrepareText.prepare();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton translateButton = new JButton("Translate!");
		translateButton.setBorderPainted(true);
		translateButton.setContentAreaFilled(true);
		add(translateButton);
		translateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtField.getText());
				PlainHTTPConnection.setInputFile(txtField.getText());
				try {
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								PlainHTTPConnection.translate();
							} catch (Throwable t) {
								t.printStackTrace();
							}

						}
					});
					thread.start();

				} catch (Throwable t) {
					t.printStackTrace();
				}

			}
		});

		JLabel label2 = new JLabel("progress");
		label2.setName(PROGRESS_DISPLAY);
		add(label2);
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setName("progressbar");
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		add(progressBar);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	private static void createAndShowGui() {
		Gui mainPanel = new Gui();

		JFrame frame = new JFrame("Google Translate");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	@Override
	public void handleEvent(String message, int progress) {

		Component[] components = getComponents();

		for (Component component : components) {
			String name = component.getName();
			if (name != null && name.equals(PROGRESS_DISPLAY)) {
				JLabel component2 = (JLabel) component;
				component2.setText(message);
				component2.repaint();
				component2.revalidate();
				validate();
				repaint();

			}
			if (name != null && name.equals("progressbar")) {
				((JProgressBar) component).setValue(progress);

			}
		}
	}
}
