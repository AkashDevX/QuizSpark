package com.gamev.quizsparkstd.QuestionModels;

public class CelebrityQuestion {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;
    private String imageFileName; // null or empty means no image, otherwise filename in assets folder
    private int imageResourceId; // 0 means no image, otherwise drawable resource ID

    // Constructor for assets folder images
    public CelebrityQuestion(String question, String option1, String option2, String option3, String option4, int correctAnswer, String imageFileName) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.imageFileName = imageFileName;
        this.imageResourceId = 0;
    }

    // Constructor for drawable resource images
    public CelebrityQuestion(String question, String option1, String option2, String option3, String option4, int correctAnswer, int imageResourceId, boolean useResource) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.imageResourceId = imageResourceId;
        this.imageFileName = null;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage() {
        return (imageFileName != null && !imageFileName.isEmpty()) || imageResourceId != 0;
    }

    public boolean isResourceImage() {
        return imageResourceId != 0;
    }
}

