package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteOrder;

import javax.swing.*;
import javax.swing.border.Border;

import controller.Controller;
import model.BodyImpl;

import static java.awt.Font.PLAIN;

public class GUI extends JFrame implements KeyListener {
  private static final Color colors[] = {
          new Color(0, 0, 0),
          new Color(255, 255, 255),
          new Color(150, 150, 150),
          new Color(255, 50, 50),
          new Color(100, 255, 50)
  };
  private JLabel timeRemaining;
  private JLabel[] text;
  private Timer timer;
  private int secondsRemaining = 60;
  private JTextArea input;
  private JButton startButton;
  private JPanel topPanel;
  private boolean gameStarted = false;
  private JPanel textPanel_2;

  private BodyImpl body;
  private Controller controller;

  public GUI() {
    initializeBody();
    initializeController();
    build();
  }

  private void initializeController() {
    controller = new Controller(body, this);
  }

  private void initializeBody() {
    body = new BodyImpl();
  }

  private void initializeText() {
    setTitle("Typing Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setSize(1000, 600);

    String[] words = body.getParagraph().split(" ");
    text = new JLabel[words.length];

    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    for (int i = 0; i < words.length; i++) {
      text[i] = new JLabel(words[i]);
      text[i].setFont(new Font("Arial", PLAIN, 20));
      textPanel.add(text[i]);
    }

    add(textPanel, BorderLayout.CENTER);
  }

  private void initializeSecondText() {
    textPanel_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    textPanel_2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    add(textPanel_2, BorderLayout.CENTER);
  }

  private void initializeTextBox() {
    input = new JTextArea();
    input.setRows(1);
    input.setColumns(30);
    input.setLineWrap(true);
    input.setFont(new Font("Arial", PLAIN, 18));
    input.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    add(input, BorderLayout.SOUTH);
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
          body.endGame();
        }
      }
    });
    startButton = new JButton();
    startButton.setPreferredSize(new Dimension(100, 30));
    startButton.setText("START");
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        timerStart();
      }
    });

    topPanel.add(timeRemaining);
    topPanel.add(startButton);

    add(topPanel, BorderLayout.NORTH);
  }

  private void addCharacters(int pos, char c) {
    JLabel temp = new JLabel(String.valueOf(c));
    temp.setFont(new Font("Arial", PLAIN, 20));
  }

  public void timerStart() {
    gameStarted = true;
    timer.start();
  }

  private void build() {
    initializeText();
    initializeTextBox();
    initializeTimer();
    setVisible(true);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if(gameStarted == true) {
      char c = e.getKeyChar();
      controller.handleTypedChar(c);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(gameStarted == true) {
      if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        controller.handleBackspace();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    return;
  }
}
