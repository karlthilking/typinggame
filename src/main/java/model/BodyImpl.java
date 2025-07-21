package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BodyImpl implements Body {
  private final String text = "the of and to a in is it you that he was for on are as with his they be at one have this from or had by word but what some we can out other were all there when up use your how said an each she which do their time if will way about many then them these so her would make like into him has two more go no could people my than first been call who its now find long down day did get come made may part over new sound take only little work know place year live me back give most very after thing our just name good sentence man think say great where help through much before line right too mean old any same tell boy follow came want show also around form three small set put end why again turn here ask went men read need land different home us move try kind hand picture change off play spell air away animal house point page letter mother answer found study still learn should america world high every near add food between own below country plant last school father keep tree never start city earth eyes light thought head under story saw left few while along might close something seem next hard open example begin life always those both paper together got group often run important until children side feet car miles night walked white sea began grow took river four carry state once book hear stop without second late miss idea enough eat face watch far family several red brought water big does another well large must such because turned means asked going put course social general however lead better feel interest local later sure today music usually toward develop power since national love human case system against program political control order during fact money number person service building question community including special public remember young business area school information problem nothing needed god minutes table upon hours black although products type years weeks rather days keep through heard within united change words thinking morning shown mind really quite within found early used small quite office available sure whole however white without light house right level however become tell";
  private String[] words;
  private String paragraph;
  private StringBuilder paragraphBuilder;
  private CharList<EnhancedChar> chars;
  private int correctChars = 0;
  private int numTyped;

  public BodyImpl() {
    words = text.split(" ");
    paragraphBuilder = new StringBuilder();
    generateParagraph();
  }

  public void generateParagraph() {
    paragraphBuilder = new StringBuilder();
    for(int i = 0; i < 200; i++) {
      int x = (int)(Math.random() * words.length);
      String temp = words[x];
      paragraphBuilder.append(temp );

      if(i < 199) {
        paragraphBuilder.append(" ");
      }

    }
    paragraph = paragraphBuilder.toString();
    chars = new CharList<>();

    for(int i = 0; i < paragraph.length(); i++) {
      char c = paragraph.charAt(i);
      EnhancedChar enhancedChar = new EnhancedChar(c, Color.BLACK, i);
      chars.add(enhancedChar);
    }
  }

  public String getParagraph() {
    if(paragraph == null) {
      return "Body of text has not been initialized";
    }
    else {
      return paragraph;
    }
  }

  public CharList<EnhancedChar> getChars() {
    if(chars == null) {
      return new CharList<>();
    }
    else {
      return chars;
    }
  }

  public void correctTyped() {
    correctChars++;
    numTyped++;
  }

  public void incorrectTyped() {
    numTyped++;
  }

  public void deletedCorrect() {
    correctChars--;
  }

  public String getWPM(double mins) {
    double wpm = (correctChars / 5.0) / mins;
    String roundedWPM = String.format("%.2f", wpm);
    return roundedWPM;
  }

  public String getAccuracy() {
    double acc = ((double)correctChars / numTyped) * 100.0;
    String roundedAcc = String.format("%.2f", acc);
    return roundedAcc;
  }

  public List<String> sortResults(List<String> fifteen, List<String> thirty, List<String> one, List<String> two) {
    List<String> topResults = new ArrayList<>();
    double fifteenBest = fifteen.stream()
        .mapToDouble(Double::parseDouble)
        .max()
        .orElse(0.0);
    double thirtyBest = thirty.stream()
        .mapToDouble(Double::parseDouble)
        .max()
        .orElse(0.0);
    double oneBest = one.stream()
        .mapToDouble(Double::parseDouble)
        .max()
        .orElse(0.0);
    double twoBest = two.stream()
        .mapToDouble(Double::parseDouble)
        .max()
        .orElse(0.0);

    topResults = List.of(fifteenBest, thirtyBest, oneBest, twoBest).stream()
        .map(result -> String.format("%.2f", result))
        .collect(Collectors.toList());

    return topResults;
  }
}
