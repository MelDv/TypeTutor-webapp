package wad.logic;

import java.util.Random;

/**
 * This class creates random strings for practicing.
 * <p>
 * The users level will determine which letters she is going to practice. Those
 * letters are then used to create random strings for practicing. When the user
 * knows enough letters, the class will find proper words from a file or
 * database and string them together for the user.
 *
 * @author Maaret Dufva
 * @version 0.1.0
 * @since 0.1.0
 */
public class Letters {

    private int level;
    private String letters;
    private String[] words;
    private final String[] letterSequences;
    private final String[] learnedLetterSequences;
    private String learnedLetters;

    /**
     * Constructor.
     */
    public Letters() {
        this.level = 0;
        this.letters = "";
        this.words = new String[15];
        this.letterSequences = new String[]{"jf", "kd", "jfkd", "ls", "jfkdls",
            "aaöä", "lsöaä", "hg", ",-.!?", "hgjfkdls",
            "hgjfkdlsööaaää", "ru", "ie", "urie", "yt",
            "tyruueeii", "jfkdlsööaaatyyruueeeii", "ow", "pqå", "owpqå",
            "jfkdlsööaaatyyruueeeiioowpqå", "mv", "cxz", "nb", "mvcxznb",
            "jfkdlsööaaaatyruuueeeeiiioowpqåmvcxznb"};
        this.learnedLetterSequences = new String[]{"---- ---- ---- ---- ---- ---- -----", "---- -F-- -J-- ---- ---- ---- -----",
            "---D -F-- -JK- ---- ---- ---- -----", "---D -F-- -JK- ---- ---- ---- -----",
            "---D -F-- -JKL ---- --S- ---- -----", "---D -F-- -JKL ---- --S- ---- -----",
            "A--D -F-- -JKL ---- --S- ---- ---ÄÖ", "A--D -F-- -JKL ---- --S- ---- ---ÄÖ",
            "A--D -FGH -JKL ---- --S- ---- ---ÄÖ", "A--D -FGH -JKL ---- --S- ---- ---ÄÖ ?!.,-", "A--D -FGH -JKL ---- --S- ---- ---ÄÖ ?!.,-", "A--D -FGH -JKL ---- --S- ---- ---ÄÖ ?!.,-",
            "A--D -FGH -JKL ---- -RS- U--- ---ÄÖ ?!.,-",
            "A--D EFGH IJKL ---- -RS- U--- ---ÄÖ ?!.,-", "A--D EFGH IJKL ---- -RS- U--- ---ÄÖ ?!.,-",
            "A--D EFGH IJKL ---- -RST U--- Y--ÄÖ ?!.,-", "A--D EFGH IJKL ---- -RST U--- Y--ÄÖ ?!.,-", "A--D EFGH IJKL ---- -RST U--- Y--ÄÖ ?!.,-",
            "A--D EFGH IJKL --O- -RST U-W- Y--ÄÖ ?!.,-",
            "A--D EFGH IJKL --OP QRST U-W- Y-ÅÄÖ ?!.,-", "A--D EFGH IJKL --OP QRST U-W- Y-ÅÄÖ ?!.,-", "A--D EFGH IJKL --OP QRST U-W- Y-ÅÄÖ ?!.,-",
            "A--D EFGH IJKL M-OP QRST UVW- Y-ÅÄÖ ?!.,-",
            "A-CD EFGH IJKL M-OP QRST UVWX YZÅÄÖ ?!.,-",
            "ABCD EFGH IJKL MNOP QRST UVWX YZÅÄÖ ?!.,-", "ABCD EFGH IJKL MNOP QRST UVWX YZÅÄÖ ?!.,-"};
        this.learnedLetters = "ABCD EFGH IJKL MNOP QRST UVWX YZÅÄÖ";
    }

    /**
     * Determines the characters which the user should learn next based on the
     * level given as parameter. Calls methods randomWords and randomString.
     *
     * @param givenLevel Game class provides level information.
     * @return the characters as a String that can be used in the next String to
     * be typed.
     */
    public String determineString(int givenLevel) {
        determineLevel(givenLevel);

        this.letters = letterSequences[level];

        if (level < 4) {
            randomString();
        } else if (level < 9) {
            randomStringWithCapitals();
        } else if (level < 16) {
            randomStringWithEndPunctuation();
        } else {
            randomStringWithPunctuation();
        }

        String stringOfWords = "";
        for (int i = 0; i < words.length; i++) {
            stringOfWords += words[i];
        }

        return stringOfWords;
    }

    /**
     * Determines the correct level based on a parameter.
     *
     * @param givenLevel Game class provides level information.
     * @return the correct level.
     */
    public int determineLevel(int givenLevel) {
        if (givenLevel <= 0) {
            this.level = 0;
        } else if (givenLevel >= letterSequences.length) {
            this.level = letterSequences.length - 1;
        } else {
            this.level = givenLevel;
        }

        return this.level;
    }

    /**
     * Creates a random Sring using characters given as a String parameter. Each
     * random word has the number of characters given as an integer parameter.
     *
     * @return a String table of ten random character sequences.
     */
    public String[] randomString() {
        Random r = new Random();
        int wordLength = 0;

        for (int i = 0; i < words.length; i++) {
            String typeThis = "";
            if (this.letters.length() < 5) {
                wordLength = 3;
            } else {
                wordLength = r.nextInt(3) + 6;
            }
            for (int j = 0; j < wordLength; j++) {
                char letter = (this.letters.charAt(r.nextInt(this.letters.length())));
                typeThis = typeThis + letter;
            }
            if (i > 0) {
                words[i] = " " + typeThis;
            } else {
                words[i] = typeThis;
            }
        }
        return words;
    }

    private String[] randomStringWithCapitals() {
        randomString();

        for (int i = 0; i < words.length; i++) {
            if (i % 3 == 0) {
                String temp = words[i];
                temp = temp.trim();
                temp = " " + temp.substring(0, 1).toUpperCase() + temp.substring(1);
                words[i] = temp;
            }
        }
        return words;
    }

    private String[] randomStringWithEndPunctuation() {
        Random r = new Random();
        String[] punctuation = {".", "!", "?"};

        randomString();
        randomStringWithCapitals();

        for (int i = 0; i < words.length; i++) {
            if (i != 0 && i % 2 == 0) {
                String temp = words[i];
                temp = temp + punctuation[r.nextInt(punctuation.length)];
                words[i] = temp;
            }
        }
        return words;
    }

    private String[] randomStringWithPunctuation() {
        Random r = new Random();
        String[] punctuation = {",", ":", ";", " –"};

        randomString();
        randomStringWithCapitals();
        randomStringWithEndPunctuation();

        for (int i = 0; i < words.length; i++) {
            if (i != 0 && i % 4 == 0) {
                String temp = words[i];
                temp = temp + punctuation[r.nextInt(punctuation.length)];
                words[i] = temp;
            }
        }
        return words;
    }

    public String getLetters() {
        return letters;
    }

    /**
     * Returns the letters already learned as a String. The String is determined
     * based on level given as parameter.
     *
     * @param givenLevel provided by Game class.
     * @return String representation of the learned letters.
     */
    public String getLearnedLetters(int givenLevel) {
        determineLevel(givenLevel);
        this.learnedLetters = this.learnedLetterSequences[level];
        return this.learnedLetters;
    }
}
