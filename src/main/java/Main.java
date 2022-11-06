import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger lenghtWordThree = new AtomicInteger(0);
    public static AtomicInteger lenghtWordFour = new AtomicInteger(0);
    public static AtomicInteger lenghtWordFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        //одинаковые буквы
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                boolean results = false;
                for (int i = 0; i < text.length() - 1; i++) {
                    if (text.charAt(i) == text.charAt(i + 1)) {
                        results = true;
                        continue;
                    }
                    results = false;
                    break;
                }
                if (results && text.length() == 3) {
                    lenghtWordThree.addAndGet(1);
                } else if (results && text.length() == 4) {
                    lenghtWordFour.addAndGet(1);
                } else if (results && text.length() == 5) {
                    lenghtWordFive.addAndGet(1);
                }
            }
        });
        //полиндром
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (text.length() > 1){
                    boolean result = true;
                    for (int i = 0; i < text.length() / 2; i++){
                        if (text.charAt(i) == text.charAt(text.length() - 1 - i)){
                        } else {
                            result = false;
                            break;
                        }
                    }
                    if (result && text.length() == 3) {
                        lenghtWordThree.addAndGet(1);
                    } else if (result && text.length() == 4) {
                        lenghtWordFour.addAndGet(1);
                    } else if (result && text.length() == 5) {
                        lenghtWordFive.addAndGet(1);
                    }
                }
             }
        });
        // если буквы идут по возрастанию
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                boolean results = false;
                for (int i = 0; i < text.length() - 1; i++) {
                    if (text.charAt(i) <= text.charAt(i + 1)) {
                        results = true;
                        continue;
                    }
                    results = false;
                    break;
                }
                if (results && text.length() == 3) {
                    lenghtWordThree.addAndGet(1);
                } else if (results && text.length() == 4) {
                    lenghtWordFour.addAndGet(1);
                } else if (results && text.length() == 5) {
                    lenghtWordFive.addAndGet(1);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + lenghtWordThree.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + lenghtWordFour.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + lenghtWordFive.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

