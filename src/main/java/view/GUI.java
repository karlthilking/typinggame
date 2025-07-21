package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.*;

import controller.AudioPlayer;
import controller.Controller;
import model.BodyImpl;
import model.EnhancedChar;

import static java.awt.Font.PLAIN;

public class GUI extends JFrame implements KeyListener, ComponentListener {
  private JLabel timeRemaining;
  private JLabel[] text;
  private JPanel textPanel;
  private Timer timer;
  private TimeMode timeMode = TimeMode.ONE_MINUTE;
  private int secondsRemaining = 60;
  private JPanel topPanel;
  private GameState state = GameState.NOT_STARTED;
  private JPanel statsPanel;

  private Controller controller;
  private BodyImpl body;
  private AudioPlayer audioPlayer;

  public GUI() {
    initialize();
    build();

    this.addKeyListener(this);
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  private void initialize() {
    body = new BodyImpl();
    controller = new Controller(body, this);
    audioPlayer = new AudioPlayer();
  }

  private void initializeText() {
    setTitle("Typing Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setSize(1000, 600);
    setBackground(Color.LIGHT_GRAY);

    text = new JLabel[body.getChars().size()];

    textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    textPanel.setBackground(Color.LIGHT_GRAY);

    for (int i = 0; i < text.length; i++) {
      text[i] = new JLabel(body.getChars().get(i).getCharacter() + "");
      text[i].setFont(new Font("Arial", PLAIN, 20));
      text[i].setBackground(null);
      text[i].setOpaque(true);
      textPanel.add(text[i]);
    }

    add(textPanel, BorderLayout.CENTER);
  }

  private void initializeTimer() {
    topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    timeRemaining = new JLabel("60");
    timeRemaining.setFont(new Font("Arial", Font.BOLD, 20));
    timeRemaining.setHorizontalAlignment(SwingConstants.LEFT);
    timeRemaining.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        secondsRemaining--;
        timeRemaining.setText(String.valueOf(secondsRemaining));

        if (secondsRemaining <= 0) {
          timer.stop();
          controller.endGame(timeMode.toString());
          state = GameState.ENDED;
        }
      }
    });

    topPanel.add(timeRemaining);
    add(topPanel, BorderLayout.NORTH);
  }

  private void initializeTopPanelComponents() {
    JButton selectFifteenSecond = new JButton("15");
    selectFifteenSecond.setSize(50, 25);
    selectFifteenSecond.setFocusable(false);
    selectFifteenSecond.addActionListener(e -> {
      if(state == GameState.NOT_STARTED) {
        timeMode = TimeMode.FIFTEEN_SECOND;
        secondsRemaining = 15;
        timeRemaining.setText(String.valueOf(secondsRemaining));
      }
    });

    JButton selectThirtySecond = new JButton("30");
    selectThirtySecond.setSize(50, 25);
    selectThirtySecond.setFocusable(false);
    selectThirtySecond.addActionListener(e -> {
      if(state == GameState.NOT_STARTED) {
        timeMode = TimeMode.THIRTY_SECOND;
        secondsRemaining = 30;
        timeRemaining.setText(String.valueOf(secondsRemaining));
      }
    });

    JButton selectOneMinute = new JButton("60");
    selectOneMinute.setSize(50, 25);
    selectOneMinute.setFocusable(false);
    selectOneMinute.addActionListener(e -> {
      if(state == GameState.NOT_STARTED) {
        timeMode = TimeMode.ONE_MINUTE;
        secondsRemaining = 60;
        timeRemaining.setText(String.valueOf(secondsRemaining));
      }
    });

    JButton selectTwoMinute = new JButton("120");
    selectTwoMinute.setSize(50, 25);
    selectTwoMinute.setFocusable(false);
    selectTwoMinute.addActionListener(e -> {
      if(state == GameState.NOT_STARTED) {
        timeMode = TimeMode.TWO_MINUTE;
        secondsRemaining = 120;
        timeRemaining.setText(String.valueOf(secondsRemaining));
      }
    });

    ImageIcon audio = new ImageIcon(Paths.get("sound.png").toFile().getAbsolutePath());
    ImageIcon resizedAudioIcon = new ImageIcon(
        audio.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    JButton audioSelection = new JButton(resizedAudioIcon);
    audioSelection.setSize(25, 25);
    audioSelection.setFocusable(false);
    audioSelection.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    audioSelection.addActionListener( e -> {
      String[] options = {"osu", "minecraft"};
      String selectedSound = (String) JOptionPane.showInputDialog(
          this, "Select a sound effect:", "Sound Selection",
          JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      if (selectedSound != null) {
        audioPlayer.selectSound(selectedSound);
      }
    });

    topPanel.add(selectFifteenSecond);
    topPanel.add(selectThirtySecond);
    topPanel.add(selectOneMinute);
    topPanel.add(selectTwoMinute);
    topPanel.add(audioSelection);

  }

  public void updateDisplay(int pos) {
    System.out.println("updateDisplay called with pos: " + pos);

    for (int i = 0; i < body.getChars().size(); i++) {
      JLabel label = text[i];
      EnhancedChar temp = body.getChars().get(i);

      if (i == pos) {
        label.setBackground(Color.YELLOW);
      } else if (i < pos) {
        if (temp.getCharacter() == ' ' && temp.getColor() == Color.RED) {
          label.setText("_");
          label.setForeground(Color.RED);
          label.setBackground(null);
        } else if (text[i].getText() == "_" && temp.getColor() != Color.RED) {
          label.setText(" ");
          label.setBackground(null);
        } else {
          label.setForeground(body.getChars().get(i).getColor());
          label.setBackground(null);
        }
      } else {
        label.setForeground(Color.BLACK);
        label.setBackground(null);
      }
    }

    textPanel.revalidate();
    textPanel.repaint();
  }

  public void endOfGameDisplay(String WPM, String accuracy) {
    getContentPane().removeAll();

    statsPanel = new JPanel();
    statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
    statsPanel.setBackground(Color.LIGHT_GRAY);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    List<String> results = controller.getTopResults();

    JLabel message = new JLabel("Game Completed");
    message.setFont(new Font("Arial", Font.BOLD, 75));
    message.setAlignmentX(Component.CENTER_ALIGNMENT);
    message.setAlignmentY(Component.CENTER_ALIGNMENT);

    JLabel wpmLabel = new JLabel("WPM: " + WPM);
    wpmLabel.setFont(new Font("Arial", Font.BOLD, 60));
    wpmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    wpmLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

    JLabel accuracyLabel = new JLabel("Accuracy: " + accuracy + "%");
    accuracyLabel.setFont(new Font("Arial", Font.BOLD, 60));
    accuracyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    accuracyLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

    JPanel resultsPanel = new JPanel();
    resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
    resultsPanel.setBackground(Color.LIGHT_GRAY);
    resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel title = new JLabel("Top Results");
    title.setFont(new Font("Arial", Font.BOLD, 40));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    resultsPanel.add(title);

    for(int i = 0; i < results.size(); i++) {
      int seconds = 0;
      switch(i) {
        case 0 -> seconds = 15;
        case 1 -> seconds = 30;
        case 2 -> seconds = 60;
        case 3 -> seconds = 120;
      }
      JLabel resultLabel = new JLabel( seconds + " seconds: " + results.get(i));
      resultLabel.setFont(new Font("Arial", Font.PLAIN, 30));
      resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      resultsPanel.add(resultLabel);
    }

    statsPanel.add(message);
    statsPanel.add(wpmLabel);
    statsPanel.add(accuracyLabel);
    statsPanel.add(resultsPanel);
    add(statsPanel, BorderLayout.CENTER);

    controller.addResults(WPM, timeMode.toString());
  }

  public void timerStart() {
    state = GameState.STARTED;
    timer.start();
  }

  private void build() {
    initializeText();
    initializeTimer();
    initializeTopPanelComponents();
    setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (state == GameState.STARTED) {
      char c = e.getKeyChar();
      try {
        audioPlayer.playSound();
      }
      catch(NullPointerException ex) {
        System.out.println(ex.getMessage());
      }

      if (c == KeyEvent.VK_BACK_SPACE) {
        return;
      }

      System.out.println("Key typed: " + c);
      controller.handleTypedChar(c);
    }
    if (state == GameState.NOT_STARTED) {
      char c = e.getKeyChar();
      try {
        audioPlayer.playSound();
      }
      catch(NullPointerException ex) {
        System.out.println(ex.getMessage());
      }

      timerStart();
      controller.handleTypedChar(c);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (state == GameState.STARTED) {
      if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        controller.handleBackspace();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void componentResized(java.awt.event.ComponentEvent e) {

  }
  @Override
  public void componentMoved(java.awt.event.ComponentEvent e) {}
  @Override
  public void componentShown(java.awt.event.ComponentEvent e) {}
  @Override
  public void componentHidden(java.awt.event.ComponentEvent e) {}
}
