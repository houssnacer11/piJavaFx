/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.overpowered.pijavafx.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openimaj.image.ImageUtilities;
import org.openimaj.math.geometry.shape.Rectangle;
import tn.esprit.overpowered.byusforus.entities.candidat.CandidateApplication;
import tn.esprit.overpowered.byusforus.entities.quiz.Answer;
import tn.esprit.overpowered.byusforus.entities.quiz.Choice;
import tn.esprit.overpowered.byusforus.entities.quiz.Question;
import tn.esprit.overpowered.byusforus.entities.quiz.QuestionType;
import tn.esprit.overpowered.byusforus.entities.quiz.Quiz;
import tn.esprit.overpowered.byusforus.entities.quiz.QuizTry;
import tn.esprit.overpowered.byusforus.entities.users.Candidate;
import tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote;
import tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.AnswerFacadeRemote;
import tn.esprit.overpowered.byusforus.services.quiz.QuizTryFacadeRemote;
import tn.esprit.overpowered.byusforus.util.JobApplicationState;
import util.authentication.Authenticator;
import util.factories.CreateAlert;
import util.routers.FXRouter;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class TryQuizController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private Boolean stopCamera = false;
    private BufferedImage grabbedImage;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private List<DetectedFace> faces = null;
    private Webcam webcam = null;
    private final String mediaPath = "../media/";
    BufferedImage capture = null;
    List<BufferedImage> facesList = new ArrayList<>();
    private IMediaWriter writer;
    @FXML
    private ImageView videoArea;
    private HBox topHB;
    @FXML
    private Label question;
    @FXML
    private GridPane choicesGridPane;
    private Quiz quiz;
    private List<Question> questionsList;
    @FXML
    private Button nextQuestion;
    private int currentQuestionIndex = 0;
    @FXML
    private Button previousQuestion;
    @FXML
    private Label timeLabel;
    private Map<Question, Choice> candidateAnswers;
    private List<Answer> answers;
    private QuizTry quizTry;
    @FXML
    private Label quizTitle;
    @FXML
    private Label quizDescription;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorScrollPane;
    private List<Integer> lastFiveFaces = new ArrayList<>();
    private String cAppMotivationLetter;
    private CandidateApplication cApp;
    private CandidateApplicationFacadeRemote candidateApplicationFacade;
    private Boolean cheatAlertAlreadyShown = false;
    private Boolean noFacesAlertAlreadyShown = false;
    private Context context = null;
    private Context secondContext = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("alert accepted - TryQuizController");
        answers = new ArrayList<>();
        quiz = (Quiz) FXRouter.getData();
        quizTry = new QuizTry((Candidate) Authenticator.currentUser);
        System.out.println("quiz try serial " + QuizTry.getSerialVersionUID());
        long start = System.currentTimeMillis();
        quizTry.setStartDate(new Date());
        quizTry.setQuiz(quiz);
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jndiName2 = "piJEE-ejb-1.0/CandidateApplicationFacade!tn.esprit.overpowered.byusforus.services.candidat.CandidateApplicationFacadeRemote";
        try {
            candidateApplicationFacade = (CandidateApplicationFacadeRemote) context.lookup(jndiName2);
        } catch (NamingException ex) {
            Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
        }
//                    CandidateApplication cApp = candidateApplicationFacade.getCAppByMotivLetter(Authenticator.currentUser.getId(), quiz.getJobOffer().getId());
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("candidate_apps.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray quizJList = (JSONArray) obj;
            System.out.println(quizJList);
            for (Object quiz : quizJList) {
                JSONObject quizJson = (JSONObject) quiz;
                Long jobOfferId = (Long) quizJson.get("jobOfferId");
                Long candidateId = (Long) quizJson.get("candidateId");
                if (jobOfferId == quizTry.getQuiz().getJobOffer().getId() && candidateId == Authenticator.currentUser.getId()) {
                    cAppMotivationLetter = (String) quizJson.get("cAppMotivationLetter");
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(ListJobOfferCandidatesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cApp = candidateApplicationFacade.getCAppByMotivLetter(cAppMotivationLetter);

        FXRouter.when("QuizResults", "QuizResults.fxml");
        FXRouter.setRouteContainer("QuizResults", anchorPane);
        scrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue() - 5);
        anchorScrollPane.setPrefHeight((double) FXRouter.scene.heightProperty().doubleValue());
        scrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue() - 5);
        anchorScrollPane.setPrefWidth((double) FXRouter.scene.widthProperty().doubleValue());
        double titleLabelLayoutX1 = (FXRouter.scene.widthProperty().doubleValue() / 2) - quizTitle.getWidth() / 2;
        quizTitle.setLayoutX(titleLabelLayoutX1);
        double quizDescriptionLayoutX1 = (FXRouter.scene.widthProperty().doubleValue() / 2) - quizDescription.getWidth() / 2;
        quizDescription.setLayoutX(quizDescriptionLayoutX1);
        FXRouter.scene.widthProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("scene width changed: " + newValue);
            scrollPane.setPrefWidth((double) newValue - 5);
            anchorScrollPane.setPrefWidth((double) newValue);
            double titleLabelLayoutX = (newValue.doubleValue() / 2) - quizTitle.getWidth() / 2;
            quizTitle.setLayoutX(titleLabelLayoutX);
            double quizDescriptionLayoutX = (newValue.doubleValue() / 2) - quizDescription.getWidth() / 2;
            quizDescription.setLayoutX(quizDescriptionLayoutX);
        });
        FXRouter.scene.heightProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("scene height changed: " + newValue);
            scrollPane.setPrefHeight((double) newValue - 5);
            anchorScrollPane.setPrefHeight((double) newValue);
        });

        initVideo(quizTry.getRecording());
        initCam();

//        Button stopCapture = new Button("Stop capture");
//        anchorPane.getChildren().add(stopCapture);
        Thread cameraThread = new Thread(() -> {
            while (!stopCamera) {
                capture = webcam.getImage();
                try {
                    detectFaces(capture);
                } catch (IOException ex) {
                    Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Image img = SwingFXUtils.toFXImage(capture, null);
                imageProperty.set(img);
                saveCaptureToVideo(capture, start);
                System.out.println("Detected " + faces.size() + " faces");

            }

        });
        cameraThread.setDaemon(true);
        cameraThread.start();

        videoArea.imageProperty().bind(imageProperty);
//        stopCapture.setOnAction((e) -> {
//            stopCamera = true;
//            writer.close();
//            webcam.close();
//        });

        quizTitle.setText(quiz.getName());
        quizDescription.setText(quiz.getDetails());
        questionsList = quiz.getQuestions();
        previousQuestion.setDisable(true);
        fillGridPane(questionsList.get(0));
        nextQuestion.setOnAction((event) -> {
            if (previousQuestion.isDisable()) {
                previousQuestion.setDisable(false);
            }
            if (nextQuestion.getText().equals("Submit")) {
                try {
                    quizTry.setFinishDate(new Date());
                    try {
                        context = new InitialContext();
                        secondContext = new InitialContext();
                    } catch (NamingException ex) {
                        System.out.println(ex.getExplanation());
                    }
                    String answerFacadejndiName = "piJEE-ejb-1.0/AnswerFacade!tn.esprit.overpowered.byusforus.services.quiz.AnswerFacadeRemote";
                    AnswerFacadeRemote answerFacadeProxy = (AnswerFacadeRemote) context.lookup(answerFacadejndiName);
                    for (Map.Entry<Question, Choice> entry : candidateAnswers.entrySet()) {
//                        Answer candidateAnswer = new Answer(entry.getKey(), entry.getValue());
                        Answer candidateAnswer = new Answer(entry.getValue());
                        answers.add(candidateAnswer);
//                        answerFacadeProxy.create(candidateAnswer);
                    }
                    quizTry.setAnswers(answers);
                    quizTry.setPercentage(calculateQuizScore());
                    String jndiName = "piJEE-ejb-1.0/QuizTryFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizTryFacadeRemote";
                    String jndiNamejo = "piJEE-ejb-1.0/JobOfferFacade!tn.esprit.overpowered.byusforus.services.entrepriseprofile.JobOfferFacadeRemote";
                    String quizFacadejndiName = "piJEE-ejb-1.0/QuizFacade!tn.esprit.overpowered.byusforus.services.quiz.QuizFacadeRemote";
                    QuizTryFacadeRemote quizTryFacadeProxy = (QuizTryFacadeRemote) secondContext.lookup(jndiName);
                    JobOfferFacadeRemote jobOfferFacade = (JobOfferFacadeRemote) secondContext.lookup(jndiNamejo);
//                    QuizFacadeRemote quizFacadeProxy = (QuizFacadeRemote) context.lookup(quizFacadejndiName);
                    System.out.println("quiz try serial" + QuizTry.getSerialVersionUID());
//                    quizTryFacadeProxy.create(quizTry);
                    ObjectMapper mapper = new ObjectMapper();
                    quizTry.setIdQuizTry(250L);
//                    quiz.setJobOffer(jobOfferFacade.find(1L));
                    String fileName = "quiz_tries";
//                    mapper.writeValue(new File(fileName + ".json"), quizTry);

                    JSONArray quizJList = null;
//                    File yourFile = new File("quiz_tries_.json");
//                    try {
//                        yourFile.createNewFile(); // if file already exists will do nothing 
//                    } catch (IOException ex) {
//                        Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    try (FileReader reader = new FileReader("quiz_tries_.json")) {
                        //Read JSON file
                        Object obj = jsonParser.parse(reader);

                        quizJList = (JSONArray) obj;
                        System.out.println(quizJList);
                        JSONObject quizTryJSON = new JSONObject();
                        quizTryJSON.put("quizId", quiz.getId());
                        quizTryJSON.put("candidateId", Authenticator.currentUser.getId());
                        quizTryJSON.put("score", quizTry.getPercentage());
                        quizTryJSON.put("answers", candidateAnswers);
                        quizJList.add(quizTryJSON);

                    } catch (FileNotFoundException e) {
                    } catch (IOException | ParseException e) {
                    }
                    try (FileWriter file = new FileWriter("quiz_tries_.json")) {
                        file.write(quizJList.toJSONString());
                        file.flush();
                    } catch (IOException e) {
                    }
                    if (quizTry.getPercentage() >= 80f) {
                        cApp.setJobApplicationState(JobApplicationState.ACCEPTED_FOR_INTERVIEW);
                        candidateApplicationFacade.updateCandidateApplication(cApp.getId(), cApp.getAdditionalInfo(), cApp.getJobApplicationState());
                    } else {
                        cApp.setJobApplicationState(JobApplicationState.REFUSED);
                        candidateApplicationFacade.updateCandidateApplication(cApp.getId(), cApp.getAdditionalInfo(), cApp.getJobApplicationState());

                    }
//                    quiz.getQuizTries().add(quizTry);
//                    quizFacadeProxy.edit(quiz);
                    try {
//                        QuizTry quizTry2 = mapper.readValue(new File(fileName + ".json"), QuizTry.class);
//                        System.out.println("this is quiz try from json : \n " + quizTry2);
                        FXRouter.goTo("QuizResults", quizTry);
                        stopCamera = true;
//                        writer.close();
//                        webcam.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                choicesGridPane.getChildren().clear();
                currentQuestionIndex++;
                fillGridPane(questionsList.get(currentQuestionIndex));
                if (currentQuestionIndex == questionsList.size() - 1) {
                    nextQuestion.setText("Submit");
                }
            }
        });
        previousQuestion.setOnAction((previous) -> {
            choicesGridPane.getChildren().clear();
            currentQuestionIndex--;
            fillGridPane(questionsList.get(currentQuestionIndex));
            if (currentQuestionIndex == 0) {
                previousQuestion.setDisable(true);
            }

        });

        //TIMER
        setTimer();

        candidateAnswers = new HashMap<>();
    }

    public void setTimer() {
        IntegerProperty duration = new SimpleIntegerProperty((int) Duration.minutes(quiz.getDuration()).toSeconds());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (duration.get() > 0) {
                    String timeLeft = DurationFormatUtils.formatDuration((long) Duration.seconds(duration.get()).toMillis(), "HH:mm:ss", true);
                    Platform.runLater(() -> timeLabel.setText("Time left: " + timeLeft));
                    duration.setValue(duration.get() - 1);
                } else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    public void fillGridPane(Question questionToDisplay) {
        question.setText("Question : " + questionToDisplay.getQuestionText());
        choicesGridPane.setPadding(new Insets(5));
        int index = 0;
        System.out.println("Choices for question: " + questionToDisplay.getQuestionText() + " : ");
        ToggleGroup tg = new ToggleGroup();
        for (Choice c : questionToDisplay.getChoices()) {
            System.out.println("choice: " + c.getChoiceText());
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(tg);
            choicesGridPane.add(rb, 0, index);
            choicesGridPane.add(new Label(c.getChoiceText()), 1, index);
            index++;
            rb.setOnAction((rbClickedEvent) -> {
                QuestionType qType = questionToDisplay.getQuestionType();
                switch (qType) {
                    case SINGLE_ANSWER:
                        candidateAnswers.put(questionToDisplay, c);
                        break;
                    case MULTI_ANSWER:
                        candidateAnswers.put(questionToDisplay, c);
                        break;
                    case FILL_IN_BLANKS:
                        break;
                    case DRAG_AND_DROP:
                        break;
                    default:
                        throw new AssertionError(qType.name());
                }
            });
        }
    }

    public Boolean checkLastFiveFaces() {
        Boolean moreThan = true;
        for (int a : lastFiveFaces) {
            if (a < 2) {
                moreThan = false;
            }
        }
        return moreThan;
    }

    public Boolean checkLastFiveEmpty() {
        Boolean empty = true;
        for (int a : lastFiveFaces) {
            if (a > 0) {
                empty = false;
            }
        }
        return empty;
    }

    public void detectFaces(BufferedImage imageToAnalyse) throws IOException {
        HaarCascadeDetector detector = new HaarCascadeDetector();
        faces = detector.detectFaces(ImageUtilities.createFImage(imageToAnalyse));
        lastFiveFaces.add(faces.size());
        if (lastFiveFaces.size() >= 5) {
            if (checkLastFiveFaces()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Detected more than two faces!!!");
                        if (!cheatAlertAlreadyShown) {
                            cheatAlertAlreadyShown = true;
                            CreateAlert.CreateAlert(Alert.AlertType.ERROR, "ERROR!", "Cheating detected.",
                                    "We have detected a cheating attempt from your part."
                                    + " An employee will review the incident and make a decision.");
                            candidateApplicationFacade.updateCandidateApplication(cApp.getId(), "Cheating detected", JobApplicationState.REFUSED);
                            stopCamera = true;
                            writer.close();
                            webcam.close();
                        }
                        try {
                            FXRouter.goTo("baseView");
                        } catch (IOException ex) {
                            Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            } else {
                if (checkLastFiveEmpty()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!noFacesAlertAlreadyShown) {
                                noFacesAlertAlreadyShown = true;
                                System.out.println("No faces detected!!!");
                                CreateAlert.CreateAlert(Alert.AlertType.ERROR, "ERROR!", "No faces detected.",
                                        "We couldn't detect any faces from your webcam."
                                        + " An employee will review the incident and make a decision.");
                                stopCamera = true;
                                System.out.println("cApp ID from No Faces" + cApp.getId());
                                candidateApplicationFacade.updateCandidateApplication(cApp.getId(), "No faces detected", JobApplicationState.REFUSED);
                                stopCamera = true;
                                writer.close();
                                webcam.close();
                            }

                            try {
                                FXRouter.goTo("baseView");
                            } catch (IOException ex) {
                                Logger.getLogger(TryQuizController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                } else {
                    lastFiveFaces.clear();
                }
            }
            facesList.clear();
            for (DetectedFace face : faces) {
                BufferedImage facePatch = ImageUtilities.createBufferedImage(face.getFacePatch());
                facesList.add(facePatch);
                Rectangle bounds = face.getBounds();
                int dx = (int) (0.1 * bounds.width);
                int dy = (int) (0.2 * bounds.height);
                int x = (int) bounds.x - dx;
                int y = (int) bounds.y - dy;
                int w = (int) bounds.width + 2 * dx;
                int h = (int) bounds.height + dy;
                Graphics g = imageToAnalyse.getGraphics();
                g.drawRect(x, y, w, h);
            }
        }
    }

    public void initCam() {
        webcam = Webcam.getWebcams().get(1);
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
    }

    public void initVideo(String fileName) {
        File file = new File(fileName);
        writer = ToolFactory.makeWriter(mediaPath + file.getName());
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, 640, 480);
    }

    private void saveCaptureToVideo(BufferedImage imageToSave, long start) {
        BufferedImage videoImage = ConverterFactory.convertToType(imageToSave, BufferedImage.TYPE_3BYTE_BGR);
        IConverter converter = ConverterFactory.createConverter(videoImage, IPixelFormat.Type.YUV420P);
        IVideoPicture frame = converter.toPicture(videoImage, (System.currentTimeMillis() - start) * 1000);
        writer.encodeVideo(0, frame);
    }

    private float calculateQuizScore() {
        float score = 0f;
        float pointsSum = 0f;
        float correctAnswersScore = 0f;
        for (Map.Entry<Question, Choice> entry : candidateAnswers.entrySet()) {
            Question workingQuestion = entry.getKey();
            Choice workingChoice = entry.getValue();
            float questionPoints = workingQuestion.getQuestionPoints();
            pointsSum += questionPoints;
            if (workingChoice.getIsCorrectChoice()) {
                correctAnswersScore += questionPoints;
            }
        }
        System.out.println("Points Sum : " + pointsSum);
        System.out.println("Correct Answers Score : " + correctAnswersScore);
        return (float) (correctAnswersScore / pointsSum) * 100;
    }
}
