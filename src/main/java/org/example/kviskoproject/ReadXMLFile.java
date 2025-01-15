package org.example.kviskoproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReadXMLFile {
    private static List<Question> questions = new ArrayList<>();
    public static List<Question> getQuestions() {
        if (questions.isEmpty())
            readXMLFile();
        return questions;
    }

    public List<Question> getQuestionsAsList() {
        return questions;
    }

    public static void readXMLFile() {
        try {
            File xmlFile = new File(
                    "src" + File.separator +
                            "main" + File.separator +
                            "resources" + File.separator +
                            "org" + File.separator +
                            "example" + File.separator +
                            "kviskoproject" + File.separator +
                            "questions.xml"); // kreiramo objekat klase File sa putanjom do naseg XML file-a

            // Ovde sada kreiramo objekat koji ce nam sluziti za parsiranje datog File-a
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            // Kreiramo objekat koji nam sluzi za ucitavanje XML file-a
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            // Ovde kreiramo Document objekat, ucitavamo XML file. Predstavlja cjelokupan XML file i omogucava pristup njegovim elementima
            Document document = documentBuilder.parse(xmlFile);

            // Normalizuje strukturu dokumenta, uskladjuje formate, uklanja prazne cvorove itd.
            document.getDocumentElement().normalize();

            // Kreiramo listu cvorova po kljucnoj rijeci "question"
            NodeList questionsNodes = document.getElementsByTagName("question");

            for (int i = 0; i < questionsNodes.getLength(); i++) {

                Question question = new Question();
                Node questionNode = questionsNodes.item(i);

                if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element questionElement = (Element) questionNode;

                    // Ovde kupimo tekst pitanja iz Node-a i setujemo na objekat pitanja
                    String questionText = questionElement.getElementsByTagName("text").item(0).getTextContent();
                    question.setQuestionText(questionText);

                    // Ovde kupimo odgovore na pitanja
                    ArrayList<String> answers = new ArrayList<>();
                    NodeList answerElement = questionElement.getElementsByTagName("answer");
                    for (int j = 0; j < answerElement.getLength(); j++) {
                        String answer = answerElement.item(j).getTextContent();
                        if (answer.contains("*")) {
                            question.setCorrectAnswer(answer.replace("*", ""));
                        } else {
                            answers.add(answer);
                        }
                    }
                    question.setAnswers(answers);
                    questions.add(question);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void questionsSort() {
        Collections.sort(questions, new Comparator<Question>() {
            @Override
            public int compare(Question questionOne, Question questionTwo) {
                if(questionOne.getQuestionCounter() != questionTwo.getQuestionCounter()) {
                    return Integer.compare(questionOne.getQuestionCounter(), questionTwo.getQuestionCounter());
                }
                return questionOne.getQuestionText().compareTo(questionTwo.getQuestionText());
            }
        });
    }
}
