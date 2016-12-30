package wad.logic;

import wad.domain.Account;

/**
 * This is the main logic class.
 * <p>
 * This class creates characters and strings for the gui and account. It sends
 * them to the listener and determines which level the account is currently on.
 *
 * @author Maaret Dufva
 * @version 0.1.0
 * @since 0.1.0
 */
public class Game {

    private Account account;
    private String typeThis;
    private int level;
    private char typeLetter;
    private String learned;

    /**
     * Constructor.
     *
     * @param givenUser Gui class provides account.
     */
    public Game(Account givenUser) {
        this.account = givenUser;
        this.typeThis = null;
        this.level = account.getLevel();
        this.typeLetter = 's';
        this.learned = "";
    }

    public Game() {
        this.account = new Account();
        this.typeThis = null;
        this.level = 0;
        this.typeLetter = 's';
        this.learned = "";
    }

    public void setUser(Account user) {
        this.account = user;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Determines the account's level.
     *
     * @return level as an integer.
     */
    public int determineLevel() {
        if (this.level != account.countLevelbyPoints()) {
            this.level = account.countLevelbyPoints();
        }
        return this.level;
    }

    /**
     * Determines a string by calling method determineString in Letters class.
     * Saves the string into a private parameter 'typeThis'.
     */
    public void determineTypeThis() {
        Letters letters = new Letters();
        typeThis = letters.determineString(level);
    }

    /**
     * Determines a string of learned lettersby calling method getLearnedLetters
     * in Letters class. Saves the string into a private parameter 'learned'.
     *
     * @return String of letters learned.
     */
    public String determineLettersLearned() {
        Letters letters = new Letters();
        this.learned = letters.getLearnedLetters(level);
        return learned;
    }

    /**
     * Takes the first character from the string to be typed and returns it. The
     * Listener class uses this method to test wheter the account has typed the
     * correct character.
     *
     * @return the character that should be typed next
     */
    public char sendToListener() {
        if (typeThis == null) {
            determineLevel();
            determineTypeThis();
            determineLettersLearned();
            typeLetter = typeThis.charAt(0);
            return typeLetter;
        } else if (typeThis.length() == 1) {
            typeLetter = typeThis.charAt(0);
            determineLevel();
            determineTypeThis();
            determineLettersLearned();
            return typeLetter;
        }

        typeLetter = typeThis.charAt(0);
        typeThis = typeThis.substring(1);
        return typeLetter;
    }

    public char getKey() {
        return typeThis.charAt(0);
    }

    public String getTypeThis() {
        return typeThis;
    }

    public int getLevel() {
        return level;
    }

    public String getLettersLearned() {
        return this.learned;
    }

    public Account getUser() {
        return this.account;
    }

    public void setTypeThis(String typeThis) {
        this.typeThis = typeThis;
    }
}
