package com.afklm.rigui.util;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

/**
 * An object that will generate text from a regular expression. In a way, it's the opposite of a regular expression
 * matcher: an instance of this class will produce text that is guaranteed to match the regular expression passed in.
 */
public class GenerateStringFromRegex {

    private final Automaton automaton;

	/**
	 * Algorithm to do real random
	 */
	private static final String SHA1_PRNG = "SHA1PRNG";
	/**
	 * Number max of try to generate password
	 */
	private static final int MAX_TRY_TO_GENERATE = 10000;
	private final SecureRandom random;

	/** logger */
	private static Log log = LogFactory.getLog(GenerateStringFromRegex.class);

    /**
	 * Constructs a new instance, accepting the regular expression and the
	 * randomizer.
	 *
	 * @param regex
	 *            The regular expression. (Not <code>null</code>.)
	 * @param random
	 *            The object that will randomize the way the String is
	 *            generated. (Not <code>null</code>.)
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalArgumentException
	 *             If the regular expression is invalid.
	 */
	public GenerateStringFromRegex(String regex) throws NoSuchAlgorithmException, IllegalArgumentException {
		if (regex == null) {
			log.warn("GenerateStringFromRegex:generateStringFromRegex : no regex passed in parameter !");
			regex = "";
		}
        this.automaton = new RegExp(regex).toAutomaton();
		this.random = SecureRandom.getInstance(SHA1_PRNG);
    }

    /**
     * Generates a random String that is guaranteed to match the regular expression passed to the constructor.
     */
    public String generate() {
        StringBuilder builder = new StringBuilder();
        generate(builder, automaton.getInitialState());
        return builder.toString();
    }

	/**
	 * Generates a random String that is guaranteed to match the regular
	 * expression passed to the constructor. The string must validating other
	 * patterns pasted in parameter. (Nb of try determine by
	 * MAX_TRY_TO_GENERATE)
	 */
	public String generateWithPatterns(List<String> patterns) {
		String generatedPassword;

		int iterationToGenerate = 0;
		boolean isMatching = false;
		do {
			generatedPassword = generate();
			isMatching = SicStringUtils.isMatchingStringWithPatterns(generatedPassword, patterns);
			iterationToGenerate++;
			//System.out.println("Nb iteration : " + iterationToGenerate + " " + generatedPassword);
		} while (!isMatching && iterationToGenerate < MAX_TRY_TO_GENERATE);

		log.debug("GenerateStringFromRegex:generateWithPatterns : string generated : " + generatedPassword);

		if (iterationToGenerate >= MAX_TRY_TO_GENERATE) {
			log.warn("GenerateStringFromRegex:generateWithPatterns : can't match with patterns password !");
		}

		return generatedPassword;
	}

	/**
	 * From
	 * https://github.com/MukulShukla/xeger/blob/master/src/main/java/nl/flotsam
	 * /xeger/Xeger.java
	 * 
	 * @param builder
	 * @param state
	 */
    private void generate(StringBuilder builder, State state) {
        List<Transition> transitions = state.getSortedTransitions(true);
        if (transitions.size() == 0) {
            assert state.isAccept();
            return;
        }
        int nroptions = state.isAccept() ? transitions.size() : transitions.size() - 1;
		int option = getRandomInt(0, nroptions);
        if (state.isAccept() && option == 0) {          // 0 is considered stop
            return;
        }
        // Moving on to next transition
        Transition transition = transitions.get(option - (state.isAccept() ? 1 : 0));
        appendChoice(builder, transition);
        generate(builder, transition.getDest());
    }

    private void appendChoice(StringBuilder builder, Transition transition) {
		char c = (char) getRandomInt(transition.getMin(), transition.getMax());
        builder.append(c);
    }

	private int getRandomInt(int min, int max) {
		int dif = max - min;
		float number = random.nextFloat(); // 0 <= number < 1
		return min + Math.round(number * dif);
	}

}
