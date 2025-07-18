package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;
import model.BodyImpl;
import model.EnhancedChar;

import static java.awt.Font.PLAIN;

public class GUI extends JFrame implements KeyListener {
  private JLabel timeRemaining;
  private JLabel[] text;
  private JPanel textPanel;
  private Timer timer;
  private int secondsRemaining = 60;
  private JButton startButton;
  private JPanel topPanel;
  private GameState state = GameState.NOT_STARTED;
  private JPanel statsPanel;

  private Controller controller;
  private BodyImpl body;

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
  }

  private void initializeText() {
    setTitle("Typing Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
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

    timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        secondsRemaining--;
        timeRemaining.setText(String.valueOf(secondsRemaining));

        if (secondsRemaining <= 0) {
          timer.stop();
          controller.endGame();
          state = GameState.ENDED;
        }
      }
    });
    startButton = new JButton();
    startButton.setPreferredSize(new Dimension(100, 30));
    startButton.setText("START");
    startButton.setFocusable(false);
    startButton.addActionListener(e -> timerStart());

    topPanel.add(timeRemaining);
    topPanel.add(startButton);

    add(topPanel, BorderLayout.NORTH);
  }

  public void updateDisplay(int pos) {
    System.out.println("updateDisplay called with pos: " + pos);

    for (int i = 0; i < body.getChars().size(); i++) {
      JLabel label = text[i];

      if (i == pos) {
        label.setBackground(Color.YELLOW);
      } else if (i < pos) {
          label.setForeground(body.getChars().get(i).getColor());
          label.setBackground(null);
      } else {
        label.setForeground(Color.BLACK);
        label.setBackground(null);
      }
    }

    textPanel.revalidate();
    textPanel.repaint();
  }

  public void endOfGameDisplay(double WPM, double accuracy) {
    getContentPane().removeAll();

    statsPanel = new JPanel(new FlowLayout());
    statsPanel.setBackground(Color.LIGHT_GRAY);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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


    statsPanel.add(message);
    statsPanel.add(wpmLabel);
    statsPanel.add(accuracyLabel);
    add(statsPanel, BorderLayout.CENTER);
  }

  public void timerStart() {
    state = GameState.STARTED;
    timer.start();
  }

  private void build() {
    initializeText();
    initializeTimer();
    setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (state == GameState.STARTED) {
      char c = e.getKeyChar();

      if(c == KeyEvent.VK_BACK_SPACE) {
        return;
      }

      System.out.println("Key typed: " + c);
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
  public void keyReleased(KeyEvent e) {
  }
}
