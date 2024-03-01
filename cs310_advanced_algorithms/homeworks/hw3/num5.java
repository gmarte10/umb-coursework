public class ResumeScorer {
	private Set<String> J;
	private Set<String> R;
	private String jFile;
	private String rFile;
	private int score;

	public ResumeScorer(String rFile, String jFile) {
		J = new HashSet<>();
		R = new HashSet<>();
		readFiles(rFile, jFile);
		calculateScore();
	}

	private void readFiles(String rFile, String jFile) {
		File rf = new File(rFile);
		File jf = new File(jFile);
		Scanner scanR = new Scanner(rf);
		Scanner scanJ = new Scanner(jf);
		while (scanR.hasNext()) {
			String word = scanR.next();
			R.add(word);
		}

		while (scanJ.hasNext()) {
			String word = scanJ.next();
			J.add(word);
		}
	}

	private void calculateScore() {
		Set<String> intersect = new HashSet<String>(J);
		intersect.retainAll(R);
		Set<String> diff = new HashSet<String>(J);
		diff.removeAll(R);
		score = intersect.size() - diff.size();
		printScore();
	}

	private void printScore() {
		System.out.println("The score is: " + socre);
	}

}