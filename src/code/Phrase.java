package code;

public class Phrase {
    private String chinese;
    private String english;
    private String audio;
    private String lastReviewTime;
    private int accuracy;
    private int testCount;
    public Phrase(String english, String chinese, String audio){
	this.chinese = chinese;
	this.english = english;
	this.audio = audio;
    }

    public Phrase(String english, String chinese, String audio, String time, int accuracy, int count){
	this.chinese = chinese;
	this.english = english;
	this.audio = audio;
	lastReviewTime = time;
	this.accuracy = accuracy;
	testCount = count;
    }
    
    public int getTestCount() {
	return testCount;
    }

    public Phrase(Phrase p){
	chinese = p.chinese;
	english = p.english;
	audio = p.audio;
    }

    /**
     * @return the chinese
     */
    public String getChinese() {
	return chinese;
    }

    /**
     * @param chinese the chinese to set
     */
    public void setChinese(String chinese) {
	this.chinese = chinese;
    }

    /**
     * @return the english
     */
    public String getEnglish() {
	return english;
    }

    /**
     * @param english the english to set
     */
    public void setEnglish(String english) {
	this.english = english;
    }

    /**
     * @return the audio
     */
    public String getAudio() {
	return audio;
    }

    /**
     * @param audio the audio to set
     */
    public void setAudio(String audio) {
	this.audio = audio;
    }

    /**
     * @return the lastReviewTime
     */
    public String getLastReviewTime() {
	return lastReviewTime;
    }

    /**
     * @param lastReviewTime the lastReviewTime to set
     */
    public void setLastReviewTime(String lastReviewTime) {
	this.lastReviewTime = lastReviewTime;
    }

    /**
     * @return the accuracy
     */
    public int getAccuracy() {
	return accuracy;
    }

    public void increaseAccuracy(){
	int i = (int) ((int) testCount * accuracy * 0.01);
	accuracy = (int) ( ++i * 100.0 / ++testCount);
    }

    public void decreaseAccuracy(){
	int i = (int) ((int) testCount * accuracy * 0.01);
	accuracy = (int) (i  * 100.0 / ++testCount);
    }
}
