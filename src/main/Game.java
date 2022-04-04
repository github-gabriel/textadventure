package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game implements ActionListener, KeyListener {

	private static final int WIDTH = 1280, HEIGHT = 720;

	private static final String WINDOW_TITLE = "Textadventure";

	// JFrame Components

	private JFrame window;
	private Container container;
	private JPanel main_menuPanel, buttonPanel, gamePanel, mainTextPanel;
	private JLabel titleLabel, playerInfoLabel;
	private JButton startButton, btn1, btn2, btn3, btn4;
	private JTextArea mainTextArea;
	private GridBagConstraints constraints, btnConstraints;
	private JButton[] buttons = new JButton[] { btn1, btn2, btn3, btn4 };
	private Font titlefont = new Font("Times New Roman", Font.PLAIN, 120);
	private Font normalFont = new Font("Times New Roman", Font.PLAIN, 34);

	// Variablen

	private int playerHp = 0, id = 0;
	private String playerWeapon = "", position = "";
	private boolean hasSword = false;
	private ChoiceHandler choiceHandler = new ChoiceHandler();

	// Button Text

	private String[] buttonNames = new String[] { "Button 1", "Button 2", "Button 3", "Button 4" };
	private String[] ja_nein = new String[] { "Ja", "Nein", "", "" };
	private String[] continueDialog = new String[] { ">", "", "", "" };
	private String[] baeume = new String[] { "Apfelbaum 1", "Apfelbaum 2", "Apfelbaum 3", "Apfelbaum 4" };

	// Action Command Text

	// GUI Setup

	public Game() {

		initGui();

		while (window != null) { // Game Loop

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (hasSword) {
				playerWeapon = "Schwert";
				playerInfoLabel.setText("HP: " + playerHp + " | Waffe: " + playerWeapon);
			}

			if (window.getHeight() < 759 && buttonPanel != null || window.getWidth() < 1296 && buttonPanel != null
					|| window.getHeight() < 759 && window.getWidth() < 1296 && buttonPanel != null) { // Buttons und TextArea im "MinimumSize" - Modus
				normalFont = new Font("Times New Roman", Font.PLAIN, 34);
				for (Component comp : buttonPanel.getComponents()) {
					if (comp != null) {
						comp.setFont(normalFont);
					}
				}
				if (mainTextArea != null) {
					mainTextArea.setFont(normalFont);
				}
				if (playerInfoLabel != null) {
					playerInfoLabel.setFont(normalFont);
				}
			}

			else if (window.getHeight() > 758 && buttonPanel != null || window.getWidth() > 1296 && buttonPanel != null
					|| window.getHeight() > 758 && window.getWidth() > 1296 && buttonPanel != null) { // Buttons und TextArea nicht im "MinimumSize" - Modus
				normalFont = new Font("Times New Roman", Font.PLAIN, 46);
				for (Component comp : buttonPanel.getComponents()) {
					if (comp != null) {
						comp.setFont(normalFont);
					}
				}
				if (mainTextArea != null) {
					mainTextArea.setFont(normalFont);
				}
				if (playerInfoLabel != null) {
					playerInfoLabel.setFont(normalFont);
				}
			}

		}

	}

	private void initGui() {

		window = new JFrame();
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.BLACK);
		window.setLayout(new GridBagLayout());
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(true);
		window.addKeyListener(this);
		container = window.getContentPane();

		constraints = new GridBagConstraints();
		constraints.gridy = 0;

		main_menuPanel = new JPanel(new GridBagLayout());
		main_menuPanel.setBackground(Color.BLACK);

		titleLabel = new JLabel(WINDOW_TITLE);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(titlefont);

		startButton = new JButton("Start");
		startButton.setFont(normalFont);
		startButton.setForeground(Color.WHITE);
		startButton.setPreferredSize(new Dimension(175, 55));
		startButton.setBackground(Color.BLACK);
		startButton.setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.WHITE));
		startButton.setFocusable(false);
		startButton.addActionListener(this);

		main_menuPanel.add(titleLabel, constraints);

		constraints.gridx = GridBagConstraints.RELATIVE;
		constraints.gridy = 1;

		main_menuPanel.add(startButton, constraints);

		main_menuPanel.repaint();
		main_menuPanel.revalidate();

		container.add(main_menuPanel);

		window.setContentPane(container);
		window.repaint();

	}

	private void createGameScreen() {

		constraints = new GridBagConstraints();
		constraints.gridx = GridBagConstraints.RELATIVE;
		constraints.gridy = 0;

		constraints.insets = new Insets(10, 0, 10, 0);

		btnConstraints = new GridBagConstraints();
		btnConstraints.gridx = GridBagConstraints.RELATIVE;

		main_menuPanel.setVisible(false);

		gamePanel = new JPanel(new GridBagLayout());
		gamePanel.setPreferredSize(new Dimension(1280, 720));
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
		gamePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setSize(new Dimension(1280, 260));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
		buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		mainTextPanel = new JPanel(new GridBagLayout());
		mainTextPanel.setSize(new Dimension(1280, 260));
		mainTextPanel.setBackground(Color.BLACK);
		mainTextPanel.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
		mainTextPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		playerInfoLabel = new JLabel("HP: " + playerHp + " | Waffe: " + playerWeapon);
		playerInfoLabel.setForeground(Color.WHITE);
		playerInfoLabel.setFont(normalFont);
		gamePanel.add(playerInfoLabel);

		mainTextArea = new JTextArea("");
		mainTextArea.setPreferredSize(new Dimension(700, 275));
		mainTextArea.setMinimumSize(new Dimension(500, 250));
		mainTextArea.setAlignmentY(JPanel.TOP_ALIGNMENT);
		mainTextArea.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		mainTextArea.setBackground(Color.BLACK);
		mainTextArea.setForeground(Color.WHITE);
		mainTextArea.setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.WHITE));
		mainTextArea.setFont(normalFont);
		mainTextArea.setLineWrap(true);
		mainTextArea.setEditable(false);
		mainTextPanel.add(mainTextArea);

		constraints.gridy = 1;

		gamePanel.add(mainTextPanel, constraints);

		btnConstraints.gridy = 1;

		for (int i = 0; i < 4; i++) {
			btnConstraints.gridy = i;
			buttons[i] = new JButton(buttonNames[i]);
			buttons[i].setFont(normalFont);
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setPreferredSize(new Dimension(700, 75));
			buttons[i].setMinimumSize(new Dimension(500, 50));
			buttons[i].setBackground(Color.BLACK);
			buttons[i].setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.WHITE));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(choiceHandler);
			buttons[i].setActionCommand("btn" + i);

			buttonPanel.add(buttons[i], btnConstraints);
		}
		constraints.gridy = 2;

		gamePanel.add(buttonPanel, constraints);

		container.add(gamePanel);

		playerSetup();

	}

	// Story

	private void playerSetup() {
		clear();
		playerHp = 100;
		playerWeapon = "Messer";
		playerInfoLabel.setText("HP: " + playerHp + " | Waffe: " + playerWeapon);

		wald();

	}

	private void wald() {
		position = "Wald";
		mainTextArea.setText(
				"Es ist ein sonniger Samstag und\ndu spazierst in einem Wald...\nDu siehst ein Haus...\nM�chtest du es betreten?");
		setButtons(ja_nein);
	}

	private void hausBetreten() {
		position = "Haus";
		mainTextArea.setText(
				"Frank: Willkommen Fremder!\nH�ttest du gerne ein Schwert?\nDu m�sstest auch nur eine\nKleinigkeit f�r mich erledigen");
		resetButtons();
		setButtons(ja_nein);
	}

	private void amHausVorbeigehen() {
		position = "EingangH�hle";
		mainTextArea.setText(
				"Du gehst am Haus vorbei...\nDabei st��t du auf eine H�hle.\nDu fragst dich was in der H�hle ist und betrittst sie");
		resetButtons();
		setButtons(continueDialog);
	}

	private void hausVerlassen() {
		position = "EingangH�hle";
		mainTextArea.setText("Du verl�sst das Haus.\nVor dir siehst du eine H�hle.\nDu betrittst sie...");
		resetButtons();
		setButtons(continueDialog);
	}

	private void quest() {
		position = "Apfelb�ume";
		mainTextArea.setText(
				"Frank: Also gut. Suche mir einen\ngoldenen Apfel.\nSie h�ngen wie ganz normale �pfel\nan den �pfelb�umen hier,\nnur seltener.");
		resetButtons();
		setButtons(baeume);
	}

	private void normalerApfel() {
		position = "Apfelb�ume";
		mainTextArea.setText("Leider nur ein normaler Apfel!");
		resetButtons();
		setButtons(baeume);
	}

	private void goldenerApfel() {
		position = "Apfelbaum";
		mainTextArea.setText("Ich habe einen\ngoldenen Apfel gefunden!\nIch bringe ihn besser\ndirekt zu Frank!");
		resetButtons();
		setButtons(continueDialog);
	}

	private void questAbgeschlossen() {
		position = "Schwert�bergabe";
		mainTextArea.setText("Frank: Perfekt!\nGenau was ich gebraucht habe!\nHier, dein Schwert.");
		hasSword = true;
		resetButtons();
		setButtons(continueDialog);
	}

	private void hoehle() {
		position = "H�hle";
		mainTextArea.setText("Du bist in der H�hle...\nDu erblickst ein...");
		resetButtons();
		setButtons(buttonNames);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == startButton) {

			createGameScreen();

		}

	}

	public class ChoiceHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			String choice = event.getActionCommand();

			switch (position) {
			case "Wald":
				switch (choice) {
				case "btn0": // Ja
					hausBetreten();
					break;
				case "btn1": // Nein
					amHausVorbeigehen();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
				break;
			case "Haus":
				switch (choice) {
				case "btn0": // Ja
					quest();
					break;
				case "btn1": // Nein
					hausVerlassen();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
				break;
			case "Apfelb�ume":
				switch (choice) {
				case "btn0": // Apfelbaum 1
					normalerApfel();
					break;
				case "btn1": // Apfelbaum 2
					normalerApfel();
					break;
				case "btn2": // Apfelbaum 3
					goldenerApfel();
					break;
				case "btn3": // Apfelbaum 4
					normalerApfel();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
				break;

			case "Apfelbaum":
				switch (choice) {
				case "btn0":  // >
					questAbgeschlossen();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
				break;

			case "Schwert�bergabe":
				switch (choice) {
				case "btn0": // >
					hoehle();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
				break;
			case "EingangH�hle":
				switch (choice) {
				case "btn0": // >
					hoehle();
					break;
				default:
					System.out.println("[Error]");
					break;
				}
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	// Hilfsmethoden

	/**
	 * Setzt die Textarea, das Label und die Buttons zur�ck
	 */

	private void clear() {
		mainTextArea.setText("");
		playerHp = 100;
		playerWeapon = "Messer";
		playerInfoLabel.setText("HP: " + playerHp + " | Waffe: " + playerWeapon);
		resetButtons();
	}

	/**
	 * Setzt den Text f�r x Buttons, ist der Text ein leerer String wird der Button an dieser Stelle entfernt
	 */

	private void setButtons(String[] array) {
		resetButtons();
		for (int i = 0; i < array.length; i++) {
			buttons[i].setText(array[i]);
			if (array[i].equals("")) {
				buttonPanel.remove(buttons[i]);
			}
		}
	}

	/**
	 * Entfernt alle Komponenten vom Button-Panel, f�gt dann alle Buttons neu zum Button-Panel hinzu
	 */

	private void resetButtons() {
		buttonPanel.removeAll();
		btnConstraints.gridy = 1;
		for (int i = 0; i < 4; i++) {
			btnConstraints.gridy = i;
			buttons[i] = new JButton(buttonNames[i]);
			buttons[i].setFont(normalFont);
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setPreferredSize(new Dimension(700, 75));
			buttons[i].setMinimumSize(new Dimension(500, 50));
			buttons[i].setBackground(Color.BLACK);
			buttons[i].setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.WHITE));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(choiceHandler);
			buttons[i].setActionCommand("btn" + i);

			buttonPanel.add(buttons[i], btnConstraints);
		}

		constraints.gridy = 2;
	}

	/**
	 * Gibt die Position von einem String in einem Array zur�ck
	 * 
	 * @param key
	 * @param array
	 * @return
	 */

	private int getPosInArray(String key, String[] array) {
		int pos = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(key)) {
				pos = i;
				break;
			}
		}
		return pos;
	}

}
