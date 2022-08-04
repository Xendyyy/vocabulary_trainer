package com.example.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Vector;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

public class Data {

    public class Lang {

        private String name;
        private Vector<Lesson> lessons;
        public int getLessonCount() {
            return lessons.size();
        }

        public Lesson getLesson(int index) {
            return lessons.get(index);
        }

        public String getName() {
            return name;
        }

        public int correctLessons() {
            int count = 0;
            for (Lesson lesson : lessons) {
                if (lesson.isCorrect()) {
                    count++;
                }
            }
            return count;
        }

        public int correctAnswers() {
            int count = 0;
            for (Lesson lesson : lessons) {
                count += lesson.getCorrect();
            }
            return count;
        }

        public int totalAnswers() {
            int count = 0;
            for (Lesson lesson : lessons) {
                count += lesson.total();
            }
            return count;
        }

        public int correctPercent() {
            int total = totalAnswers();
            if (total == 0) {
                return 0;
            }
            return correctAnswers() * 100 / total;
        }

        public int totalLessons() {
            return lessons.size();
        }

    }
    public class Lesson {
        private Vector<SPair> words;
        private int correct = 0;
        private int difficulty; // 0 to 5
        public int total() {
            return words.size();
        }

        public int getCorrect() {
            return correct;
        }
        public void setCorrect(int newCorrect) {
            if (newCorrect > correct) {
                correct = newCorrect;
            }
        }
        public boolean isCorrect(){
            return correct == words.size();
        }
        public int getDifficulty() {
            if (difficulty < 0) {
                difficulty = 0;
            } else if (difficulty > 5) {
                difficulty = 5;
            }
            return difficulty;
        }
        public int correctPercent() {
            int total = total();
            if (total == 0) {
                return 0;
            }
            return correct * 100 / total;
        }

        public Vector<SPair> getWordsRandom() {
            Vector<SPair> result = words;
            Collections.shuffle(result);
            return result;
        }
        public Vector<Character> randomLettersOriginal() {
            Vector<LPair> combination = new Vector<LPair>();
            for (SPair word : words) {
                Vector<LPair> letts = word.getLettersOriginal();
                outerLoop:
                for (int i = 0; i < letts.size(); i++) {
                    for (LPair letter : combination) {
                        LPair pair = letts.get(i);
                        if (letter.letter == pair.letter && letter.count < pair.count) {
                            letter.count = pair.count;
                            continue outerLoop;
                        }
                    }
                    combination.add(letts.get(i));
                }
            }
            Vector<Character> result = new Vector<Character>();
            for (LPair pair : combination) {
                for (int i = 0; i < pair.count; i++) {
                    result.add(pair.letter);
                }
            }
            Collections.shuffle(result);
            return result;
        }
        public Vector<Character> randomLettersTranslated(){
            Vector<LPair> combination = new Vector<LPair>();
            for (SPair word : words) {
                Vector<LPair> letts = word.getLettersTranslated();
                outerLoop:
                for (int i = 0; i < letts.size(); i++) {
                    for (LPair letter : combination) {
                        LPair pair = letts.get(i);
                        if (letter.letter == pair.letter && letter.count < pair.count) {
                            letter.count = pair.count;
                            continue outerLoop;
                        }
                    }
                    combination.add(letts.get(i));
                }
            }
            Vector<Character> result = new Vector<Character>();
            for (LPair pair : combination) {
                for (int i = 0; i < pair.count; i++) {
                    result.add(pair.letter);
                }
            }
            Collections.shuffle(result);
            return result;
        }
    }

    public class LPair {
        public Character letter;
        public Integer count;

        public LPair(char let, int i) {
            letter = let;
            count = i;
        }
    }

    public class SPair {
        private String original;
        private String translated;

        public String getOriginal() {
            return original;
        }
        public String getTranslated() {
            return translated;
        }

        public Vector<LPair> getLettersOriginal() {
            return getLetters(original);
        }

        public Vector<LPair> getLettersTranslated() {
            return getLetters(translated);
        }

        private Vector<LPair> getLetters(String word){
            Vector<LPair> result = new Vector<LPair>();
            outerLoop:
            for (int i = 0; i < word.length(); i++) {
                for (LPair letter : result) {
                    if (letter.letter == word.charAt(i)) {
                        letter.count++;
                        continue outerLoop;
                    }
                }
                result.add(new LPair(word.charAt(i),1));
            }
            return result;
        }
    }

    ////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\
    public static Vector<Lang> langs = new Vector<>();

    public static void load() throws IOException {
        InputStream json_file = MainActivity
                .getApp()
                .getResources()
                .openRawResource(R.raw.data);
        Type dataType = new TypeToken<Vector<Lang>>(){}.getType();
        Reader reader = new InputStreamReader(json_file, "UTF-8");
        langs = new Gson().fromJson(reader, dataType);
    }
    public static void save() {
        try {
            Writer writer = new OutputStreamWriter(MainActivity.getApp().openFileOutput("data.json", 0), "UTF-8");
            new Gson().toJson(langs, writer);
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
