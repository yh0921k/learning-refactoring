package book.chapter11;

public class Application11_9 {
  public static void main(String[] args) {
    MedicalExam medicalExam = new MedicalExam(true);
    Candidate candidate = new Candidate();
    ScoringGuide scoringGuide = new ScoringGuide();

    int result = score(candidate, medicalExam, scoringGuide);
    System.out.println("result = " + result);
  }

  public static int score(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {
    return new Scorer(candidate, medicalExam, scoringGuide).execute();
  }
}

class Scorer {
  private final Candidate candidate;
  private final MedicalExam medicalExam;
  private final ScoringGuide scoringGuide;

  private int result;
  private int healthLevel;
  private boolean highMedicalRiskFlag;
  private String certificationGrade = "regular";


  public Scorer(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {
    this.candidate = candidate;
    this.medicalExam = medicalExam;
    this.scoringGuide = scoringGuide;
  }

  public int execute() {
    scoreSmoking();
    scoreSomething();

    // 비슷한 코드가 한 참 이어짐
    result -= Math.max(healthLevel - 5, 0);
    return result;
  }

  private void scoreSomething() {
    if (scoringGuide.stateWithLowCertification(candidate.originalState)) {
      certificationGrade = "low";
      result -= 5;
    }
  }

  private void scoreSmoking() {
    if (medicalExam.isSmoker) {
      healthLevel += 10;
      highMedicalRiskFlag = true;
    }
  }
}

class ScoringGuide {
  public boolean stateWithLowCertification(String originalState) {
    return false;
  }
}

class Candidate {
  public String originalState = "Test";
}

class MedicalExam {
  public boolean isSmoker;

  public MedicalExam(boolean isSmoker) {
    this.isSmoker = isSmoker;
  }
}
