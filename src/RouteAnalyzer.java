import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RouteAnalyzer {
	public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(new RouteAnalyzerTask());
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Выводим результаты
		int mostFrequentSize = 0;
		int maxFreq = 0;
		System.out.println("Самое частое количество повторений:");
		for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
			int size = entry.getKey();
			int freq = entry.getValue();
			if (freq > maxFreq) {
				maxFreq = freq;
				mostFrequentSize = size;
			}
		}
		System.out.println(mostFrequentSize + " (встретилось " + maxFreq + " раз)");
		System.out.println("Другие размеры:");
		for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
			int size = entry.getKey();
			int freq = entry.getValue();
			if (size != mostFrequentSize) {
				System.out.println("- " + size + " (" + freq + " раз)");
			}
		}
	}

	public static String generateRoute(String letters, int length) {
		Random random = new Random();
		StringBuilder route = new StringBuilder();
		for (int i = 0; i < length; i++) {
			route.append(letters.charAt(random.nextInt(letters.length())));
		}
		return route.toString();
	}

	static class RouteAnalyzerTask implements Runnable {
		@Override
		public void run() {
			String route = generateRoute("RLRFR", 100);
			int countR = countTurnsRight(route);
			synchronized (sizeToFreq) {
				sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
			}
		}

		private int countTurnsRight(String route) {
			int count = 0;
			for (int i = 0; i < route.length(); i++) {
				if (route.charAt(i) == 'R') {
					count++;
				}
			}
			return count;
		}
	}
}
